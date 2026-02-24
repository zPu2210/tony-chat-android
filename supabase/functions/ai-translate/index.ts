import { serve } from "https://deno.land/std@0.177.0/http/server.ts";
import {
  checkAndIncrement,
  corsHeaders,
  errorResponse,
  getDeviceId,
  rateLimitedResponse,
} from "../_shared/rate-limiter.ts";

const GEMINI_API_KEY = Deno.env.get("GEMINI_API_KEY") ?? "";

serve(async (req) => {
  if (req.method === "OPTIONS") {
    return new Response("ok", { headers: corsHeaders });
  }

  try {
    const deviceId = getDeviceId(req);
    if (!deviceId) return errorResponse("Missing X-Device-Id header", 400);

    const rate = await checkAndIncrement(deviceId, "translate");
    if (!rate.allowed) return rateLimitedResponse();

    const { text, targetLang, sourceLang } = await req.json();
    if (!text) return errorResponse("Missing 'text' field", 400);
    if (!targetLang) return errorResponse("Missing 'targetLang' field", 400);

    const sourceInstruction = sourceLang
      ? `from ${sourceLang} `
      : "";
    const prompt =
      `Translate the following text ${sourceInstruction}to ${targetLang}. ` +
      `Return ONLY the translated text, nothing else. ` +
      `If you can detect the source language, include it as the first line in format "DETECTED:langname" followed by a newline, then the translation.\n\n${text}`;

    const resp = await fetch(
      `https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=${GEMINI_API_KEY}`,
      {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          contents: [{ parts: [{ text: prompt }] }],
          generationConfig: { maxOutputTokens: 1024, temperature: 0.1 },
        }),
      },
    );

    if (!resp.ok) {
      console.error("Gemini error:", await resp.text());
      return errorResponse("AI service temporarily unavailable", 502);
    }

    const data = await resp.json();
    let result = data.candidates?.[0]?.content?.parts?.[0]?.text?.trim() ?? "";

    // Parse detected language if present
    let detectedLang: string | undefined;
    if (result.startsWith("DETECTED:")) {
      const newlineIdx = result.indexOf("\n");
      if (newlineIdx > 0) {
        detectedLang = result.substring(9, newlineIdx).trim();
        result = result.substring(newlineIdx + 1).trim();
      }
    }

    const body: Record<string, unknown> = { result, remaining: rate.remaining };
    if (detectedLang) body.detectedLang = detectedLang;

    return new Response(JSON.stringify(body), {
      headers: {
        ...corsHeaders,
        "Content-Type": "application/json",
        "X-Remaining": String(rate.remaining),
      },
    });
  } catch (e) {
    console.error("ai-translate error:", e);
    return errorResponse("Internal server error");
  }
});
