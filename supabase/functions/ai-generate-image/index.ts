import { serve } from "https://deno.land/std@0.177.0/http/server.ts";
import {
  checkAndIncrement,
  corsHeaders,
  errorResponse,
  getDeviceId,
  rateLimitedResponse,
} from "../_shared/rate-limiter.ts";
import { geminiGenerateImage } from "../_shared/gemini.ts";

serve(async (req) => {
  if (req.method === "OPTIONS") {
    return new Response("ok", { headers: corsHeaders });
  }

  try {
    const deviceId = getDeviceId(req);
    if (!deviceId) return errorResponse("Missing X-Device-Id header", 400);

    const rate = await checkAndIncrement(deviceId, "generate-image");
    if (!rate.allowed) return rateLimitedResponse();

    const { prompt } = await req.json();
    if (!prompt) return errorResponse("Missing 'prompt' field", 400);

    const resultBytes = await geminiGenerateImage(prompt);

    return new Response(resultBytes, {
      headers: {
        ...corsHeaders,
        "Content-Type": "image/png",
        "X-Remaining": String(rate.remaining),
      },
    });
  } catch (e) {
    console.error("ai-generate-image error:", e);
    return errorResponse("Image generation failed", 502);
  }
});
