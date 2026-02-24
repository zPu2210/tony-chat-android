import { createClient } from "https://esm.sh/@supabase/supabase-js@2.39.0";

const SUPABASE_URL = Deno.env.get("SUPABASE_URL")!;
const SUPABASE_SERVICE_KEY = Deno.env.get("SUPABASE_SERVICE_ROLE_KEY")!;

const supabase = createClient(SUPABASE_URL, SUPABASE_SERVICE_KEY);

const LIMITS: Record<string, number> = {
  "rewrite": 50,
  "translate": 50,
  "remove-bg": 5,
  "upscale": 5,
  "remove-text": 5,
  "generate-image": 3,
};

export interface RateResult {
  allowed: boolean;
  remaining: number;
  limit: number;
}

/** Check rate limit and increment if allowed. Returns remaining quota. */
export async function checkAndIncrement(
  deviceId: string,
  endpoint: string,
): Promise<RateResult> {
  const limit = LIMITS[endpoint];
  if (!limit) return { allowed: false, remaining: 0, limit: 0 };

  // Upsert: increment count or insert with count=1
  const { data, error } = await supabase.rpc("check_ai_rate_limit", {
    p_device_id: deviceId,
    p_endpoint: endpoint,
    p_limit: limit,
  });

  if (error) {
    // Fallback: direct query if RPC doesn't exist yet
    return await checkAndIncrementFallback(deviceId, endpoint, limit);
  }

  return {
    allowed: data.allowed,
    remaining: data.remaining,
    limit,
  };
}

async function checkAndIncrementFallback(
  deviceId: string,
  endpoint: string,
  limit: number,
): Promise<RateResult> {
  // Get current count
  const today = new Date().toISOString().split("T")[0];
  const { data: existing } = await supabase
    .from("ai_usage")
    .select("count")
    .eq("device_id", deviceId)
    .eq("endpoint", endpoint)
    .eq("used_at", today)
    .maybeSingle();

  const currentCount = existing?.count ?? 0;

  if (currentCount >= limit) {
    return { allowed: false, remaining: 0, limit };
  }

  // Upsert with increment
  const { error } = await supabase.from("ai_usage").upsert(
    {
      device_id: deviceId,
      endpoint,
      used_at: today,
      count: currentCount + 1,
    },
    { onConflict: "device_id,endpoint,used_at" },
  );

  if (error) {
    console.error("Rate limit upsert error:", error);
    // Allow on error (fail open for better UX)
    return { allowed: true, remaining: limit - currentCount - 1, limit };
  }

  return { allowed: true, remaining: limit - currentCount - 1, limit };
}

/** Standard CORS headers for all Edge Functions */
export const corsHeaders = {
  "Access-Control-Allow-Origin": "*",
  "Access-Control-Allow-Headers":
    "authorization, x-client-info, apikey, content-type, x-device-id",
  "Access-Control-Allow-Methods": "POST, OPTIONS",
};

/** Extract device ID from request headers */
export function getDeviceId(req: Request): string | null {
  return req.headers.get("x-device-id") || null;
}

/** Create a 429 rate-limited response */
export function rateLimitedResponse(): Response {
  return new Response(
    JSON.stringify({ error: "Daily limit exceeded. Try again tomorrow." }),
    {
      status: 429,
      headers: { ...corsHeaders, "Content-Type": "application/json" },
    },
  );
}

/** Create an error response */
export function errorResponse(message: string, status = 500): Response {
  return new Response(
    JSON.stringify({ error: message }),
    {
      status,
      headers: { ...corsHeaders, "Content-Type": "application/json" },
    },
  );
}
