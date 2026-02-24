import { serve } from "https://deno.land/std@0.177.0/http/server.ts";
import {
  checkAndIncrement,
  corsHeaders,
  errorResponse,
  getDeviceId,
  rateLimitedResponse,
} from "../_shared/rate-limiter.ts";

const GEMINI_API_KEY = Deno.env.get("GEMINI_API_KEY") ?? "";

const STYLE_PROMPTS: Record<string, string> = {
  "fix_grammar": "Fix all grammar and spelling errors. Keep the original meaning and tone.",
  "professional": "Rewrite in a professional, formal tone suitable for business communication.",
  "casual": "Rewrite in a casual, friendly tone.",
  "polite": "Rewrite to be more polite and courteous.",
  "email": "Rewrite as a professional email.",
  "greeting": "Write a warm greeting message based on this context.",
  "meeting": "Rewrite as meeting notes or a meeting invitation.",
  "thanks": "Rewrite as a thank you message.",
};

serve(async (req) => {
  if (req.method === "OPTIONS") {
    return new Response("ok", { headers: corsHeaders });
  }

  try {
    const deviceId = getDeviceId(req);
    if (!deviceId) return errorResponse("Missing X-Device-Id header", 400);

    const rate = await checkAndIncrement(deviceId, "rewrite");
    if (!rate.allowed) return rateLimitedResponse();

    const { text, style } = await req.json();
    if (!text) return errorResponse("Missing 'text' field", 400);

    const stylePrompt = STYLE_PROMPTS[style] || STYLE_PROMPTS["fix_grammar"];

    const resp = await fetch(
      `https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=${GEMINI_API_KEY}`,
      {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          contents: [{
            parts: [{
              text: `${stylePrompt}\n\nText:\n${text}`,
            }],
          }],
          generationConfig: { maxOutputTokens: 1024, temperature: 0.3 },
        }),
      },
    );

    if (!resp.ok) {
      const err = await resp.text();
      console.error("Gemini error:", err);
      return errorResponse("AI service temporarily unavailable", 502);
    }

    const data = await resp.json();
    const result = data.candidates?.[0]?.content?.parts?.[0]?.text?.trim() ?? "";

    return new Response(
      JSON.stringify({ result, remaining: rate.remaining }),
      {
        headers: {
          ...corsHeaders,
          "Content-Type": "application/json",
          "X-Remaining": String(rate.remaining),
        },
      },
    );
  } catch (e) {
    console.error("ai-rewrite error:", e);
    return errorResponse("Internal server error");
  }
});
