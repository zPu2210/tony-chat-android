import { serve } from "https://deno.land/std@0.177.0/http/server.ts";
import {
  checkAndIncrement,
  corsHeaders,
  errorResponse,
  getDeviceId,
  rateLimitedResponse,
} from "../_shared/rate-limiter.ts";

const CLIPDROP_API_KEY = Deno.env.get("CLIPDROP_API_KEY") ?? "";

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

    // ClipDrop text-to-image uses form data
    const clipForm = new FormData();
    clipForm.append("prompt", prompt);

    const resp = await fetch("https://clipdrop-api.co/text-to-image/v1", {
      method: "POST",
      headers: { "x-api-key": CLIPDROP_API_KEY },
      body: clipForm,
    });

    if (!resp.ok) {
      console.error("ClipDrop error:", resp.status, await resp.text());
      return errorResponse("Image generation failed", 502);
    }

    const imageBytes = await resp.arrayBuffer();
    return new Response(imageBytes, {
      headers: {
        ...corsHeaders,
        "Content-Type": "image/png",
        "X-Remaining": String(rate.remaining),
      },
    });
  } catch (e) {
    console.error("ai-generate-image error:", e);
    return errorResponse("Internal server error");
  }
});
