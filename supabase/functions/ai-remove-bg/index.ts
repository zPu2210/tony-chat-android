import { serve } from "https://deno.land/std@0.177.0/http/server.ts";
import {
  checkAndIncrement,
  corsHeaders,
  errorResponse,
  getDeviceId,
  rateLimitedResponse,
} from "../_shared/rate-limiter.ts";
import { geminiEditImage } from "../_shared/gemini.ts";

const PROMPT =
  "Remove the background completely and replace it with a pure white background (RGB 255,255,255). Keep the subject sharp, edges clean. Do not alter the subject in any way.";

serve(async (req) => {
  if (req.method === "OPTIONS") {
    return new Response("ok", { headers: corsHeaders });
  }

  try {
    const deviceId = getDeviceId(req);
    if (!deviceId) return errorResponse("Missing X-Device-Id header", 400);

    const rate = await checkAndIncrement(deviceId, "remove-bg");
    if (!rate.allowed) return rateLimitedResponse();

    const formData = await req.formData();
    const imageFile = formData.get("image_file") as File | null;
    if (!imageFile) return errorResponse("Missing 'image_file' field", 400);

    const imageBytes = await imageFile.arrayBuffer();
    const mimeType = imageFile.type || "image/jpeg";

    const resultBytes = await geminiEditImage(imageBytes, mimeType, PROMPT);

    return new Response(resultBytes, {
      headers: {
        ...corsHeaders,
        "Content-Type": "image/png",
        "X-Remaining": String(rate.remaining),
      },
    });
  } catch (e) {
    console.error("ai-remove-bg error:", e);
    return errorResponse("Image processing failed", 502);
  }
});
