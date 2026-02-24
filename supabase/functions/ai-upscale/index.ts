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

    const rate = await checkAndIncrement(deviceId, "upscale");
    if (!rate.allowed) return rateLimitedResponse();

    const formData = await req.formData();
    const imageFile = formData.get("image_file");
    if (!imageFile) return errorResponse("Missing 'image_file' field", 400);

    // Forward to ClipDrop with 2x target
    const clipForm = new FormData();
    clipForm.append("image_file", imageFile);
    clipForm.append("target_width", "2048");
    clipForm.append("target_height", "2048");

    const resp = await fetch(
      "https://clipdrop-api.co/image-upscaling/v1/upscale",
      {
        method: "POST",
        headers: { "x-api-key": CLIPDROP_API_KEY },
        body: clipForm,
      },
    );

    if (!resp.ok) {
      console.error("ClipDrop error:", resp.status, await resp.text());
      return errorResponse("Image processing failed", 502);
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
    console.error("ai-upscale error:", e);
    return errorResponse("Internal server error");
  }
});
