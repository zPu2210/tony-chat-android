import { serve } from "https://deno.land/std@0.177.0/http/server.ts";
import { createClient } from "https://esm.sh/@supabase/supabase-js@2.39.0";

const SUPABASE_URL = Deno.env.get("SUPABASE_URL")!;
const SUPABASE_SERVICE_KEY = Deno.env.get("SUPABASE_SERVICE_ROLE_KEY")!;
const GEMINI_API_KEY = Deno.env.get("GEMINI_API_KEY") ?? "";

const supabase = createClient(SUPABASE_URL, SUPABASE_SERVICE_KEY);

const CATEGORIES = ["technology", "business", "world", "entertainment", "sports"];

const GNEWS_QUERIES: Record<string, string> = {
  technology: "technology+AI+software",
  business: "business+economy+finance",
  world: "world+international+politics",
  entertainment: "entertainment+movies+music",
  sports: "sports+football+basketball",
};

interface Article {
  title: string;
  source_url: string;
  source_name: string;
  image_url: string | null;
  category: string;
  published_at: string;
  summary?: string | null;
}

// Simple regex XML tag extractor (avoids deno_dom dependency issues)
function extractTag(xml: string, tag: string): string {
  const re = new RegExp(`<${tag}[^>]*>([\\s\\S]*?)</${tag}>`, "i");
  const m = xml.match(re);
  return m ? m[1].replace(/<!\[CDATA\[([\s\S]*?)\]\]>/g, "$1").trim() : "";
}

function extractTagAttr(xml: string, tag: string, attr: string): string {
  const re = new RegExp(`<${tag}[^>]*${attr}="([^"]*)"`, "i");
  const m = xml.match(re);
  return m ? m[1].trim() : "";
}

async function fetchGoogleNewsRSS(category: string): Promise<Article[]> {
  const query = GNEWS_QUERIES[category];
  if (!query) return [];

  const url = `https://news.google.com/rss/search?q=${query}&hl=en-US&gl=US&ceid=US:en`;
  console.log(`Fetching: ${url}`);

  try {
    const resp = await fetch(url, {
      headers: {
        "User-Agent": "Mozilla/5.0 (compatible; TonyChatBot/1.0)",
        "Accept": "application/rss+xml, application/xml, text/xml",
      },
    });

    console.log(`Response status for ${category}: ${resp.status}`);
    if (!resp.ok) {
      console.error(`RSS fetch failed: ${resp.status} ${resp.statusText}`);
      return [];
    }

    const xml = await resp.text();
    console.log(`XML length for ${category}: ${xml.length}`);

    // Split into <item> blocks
    const itemBlocks = xml.split(/<item>/i).slice(1); // skip everything before first <item>
    const articles: Article[] = [];

    for (let i = 0; i < Math.min(itemBlocks.length, 15); i++) {
      const block = itemBlocks[i];
      const title = extractTag(block, "title");
      const link = extractTag(block, "link");
      const pubDate = extractTag(block, "pubDate");
      const sourceName = extractTag(block, "source") || "News";

      if (!title || !link) continue;

      let publishedAt: string;
      try {
        publishedAt = new Date(pubDate).toISOString();
      } catch {
        publishedAt = new Date().toISOString();
      }

      articles.push({
        title,
        source_url: link,
        source_name: sourceName,
        image_url: null,
        category,
        published_at: publishedAt,
      });
    }

    console.log(`Parsed ${articles.length} articles for ${category}`);
    return articles;
  } catch (e) {
    console.error(`RSS fetch error for ${category}:`, e);
    return [];
  }
}

async function summarizeWithGemini(title: string): Promise<string> {
  if (!GEMINI_API_KEY) return "";

  try {
    const resp = await fetch(
      `https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=${GEMINI_API_KEY}`,
      {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          contents: [{
            parts: [{
              text: `Write a 2-3 sentence news summary for this headline. Be informative and neutral. Headline: "${title}"`,
            }],
          }],
          generationConfig: { maxOutputTokens: 150, temperature: 0.3 },
        }),
      }
    );
    if (!resp.ok) return "";
    const data = await resp.json();
    return data.candidates?.[0]?.content?.parts?.[0]?.text?.trim() ?? "";
  } catch {
    return "";
  }
}

async function processArticles(articles: Article[]): Promise<number> {
  if (articles.length === 0) return 0;

  // Check existing URLs to avoid re-summarizing
  const urls = articles.map((a) => a.source_url);
  const { data: existing } = await supabase
    .from("news_articles")
    .select("source_url")
    .in("source_url", urls);

  const existingUrls = new Set((existing ?? []).map((e: { source_url: string }) => e.source_url));
  const newArticles = articles.filter((a) => !existingUrls.has(a.source_url));

  console.log(`New articles to process: ${newArticles.length} (${existingUrls.size} already exist)`);
  if (newArticles.length === 0) return 0;

  // Summarize new articles (batch of 5 concurrent)
  for (let i = 0; i < newArticles.length; i += 5) {
    const batch = newArticles.slice(i, i + 5);
    const summaries = await Promise.all(
      batch.map((a) => summarizeWithGemini(a.title))
    );
    batch.forEach((a, idx) => {
      a.summary = summaries[idx] || null;
    });
  }

  // Upsert into DB
  const { error } = await supabase.from("news_articles").upsert(
    newArticles.map((a) => ({
      title: a.title,
      summary: a.summary,
      source_url: a.source_url,
      source_name: a.source_name,
      image_url: a.image_url,
      category: a.category,
      published_at: a.published_at,
    })),
    { onConflict: "source_url" }
  );

  if (error) {
    console.error("Upsert error:", error);
    return 0;
  }

  console.log(`Upserted ${newArticles.length} articles`);
  return newArticles.length;
}

async function cleanupOldArticles(): Promise<void> {
  const sevenDaysAgo = new Date(Date.now() - 7 * 24 * 60 * 60 * 1000).toISOString();
  const { error } = await supabase
    .from("news_articles")
    .delete()
    .lt("published_at", sevenDaysAgo);
  if (error) console.error("Cleanup error:", error);
}

serve(async (_req) => {
  try {
    console.log("fetch-news invoked");

    // Fetch all categories in parallel
    const allArticles = await Promise.all(
      CATEGORIES.map((cat) => fetchGoogleNewsRSS(cat))
    );
    const flatArticles = allArticles.flat();

    console.log(`Total fetched: ${flatArticles.length} articles across ${CATEGORIES.length} categories`);

    const inserted = await processArticles(flatArticles);
    await cleanupOldArticles();

    return new Response(
      JSON.stringify({ ok: true, fetched: flatArticles.length, inserted }),
      { headers: { "Content-Type": "application/json" } }
    );
  } catch (e) {
    console.error("fetch-news error:", e);
    return new Response(
      JSON.stringify({ ok: false, error: String(e) }),
      { status: 500, headers: { "Content-Type": "application/json" } }
    );
  }
});
