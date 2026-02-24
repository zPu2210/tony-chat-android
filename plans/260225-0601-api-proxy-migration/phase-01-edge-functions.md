# Phase 1: Supabase Edge Functions + Rate Limiting DB

**Priority:** High | **Status:** Pending | **Depends on:** Nothing

## Overview

Create 6 Edge Functions that proxy AI API calls and a `ai_usage` table for server-side rate limiting. All API keys stored as Supabase secrets.

## Key Insights

- Existing pattern: `supabase/functions/fetch-news/index.ts` (Deno, createClient, service role)
- ClipDrop uses `x-api-key` header, multipart form data for images
- Gemini uses URL query param `?key=`
- Image EFs need to handle binary request/response (multipart in, image out)
- Text EFs are simple JSON in/out

## Requirements

### Functional
- 6 Edge Functions matching current ClipDropProvider.kt + Gemini endpoints
- Server-side rate limiting per device_id per endpoint per day
- Return remaining quota in response headers
- Handle errors gracefully (map upstream errors to user-friendly messages)

### Non-Functional
- <500ms added latency for text, <2s for image proxy
- Support images up to 6MB (Supabase EF body limit)
- No data persistence (stateless proxy, only rate limit tracking)

## Database: `ai_usage` table

```sql
-- Run via Supabase SQL Editor
CREATE TABLE ai_usage (
  id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  device_id text NOT NULL,
  endpoint text NOT NULL,
  used_at date NOT NULL DEFAULT CURRENT_DATE,
  count int NOT NULL DEFAULT 1,
  created_at timestamptz NOT NULL DEFAULT now()
);

-- Unique constraint for upsert
CREATE UNIQUE INDEX idx_ai_usage_device_endpoint_date
  ON ai_usage(device_id, endpoint, used_at);

-- Auto-cleanup: delete records older than 30 days
-- (reuse pg_cron if available, or manual cleanup)

-- RLS: Edge Functions use service_role (bypasses RLS)
ALTER TABLE ai_usage ENABLE ROW LEVEL SECURITY;
```

## Edge Functions

### Shared: `_shared/rate-limiter.ts`

```typescript
// Shared rate limit checker used by all 6 functions
// - Check ai_usage count for device_id + endpoint + today
// - If under limit → increment and proceed
// - If over limit → return 429 with reset time
// - Return remaining count for response headers
```

**Rate limits config:**
```typescript
const LIMITS: Record<string, number> = {
  'rewrite': 50,
  'translate': 50,
  'remove-bg': 5,
  'upscale': 5,
  'remove-text': 5,
  'generate-image': 3,
};
```

### Function 1: `ai-rewrite/index.ts`
- **Input:** `{ text: string, style: string }` + `X-Device-Id` header
- **Backend:** Gemini Flash API (`generateContent`)
- **Prompt:** System prompt with style instruction + user text
- **Output:** `{ result: string, remaining: number }`

### Function 2: `ai-translate/index.ts`
- **Input:** `{ text: string, targetLang: string, sourceLang?: string }` + `X-Device-Id`
- **Backend:** Gemini Flash API
- **Prompt:** Translation instruction with language pair
- **Output:** `{ result: string, detectedLang?: string, remaining: number }`

### Function 3: `ai-remove-bg/index.ts`
- **Input:** Multipart form with `image_file` + `X-Device-Id`
- **Backend:** `POST https://clipdrop-api.co/remove-background/v1`
- **Output:** PNG image binary + `X-Remaining` header

### Function 4: `ai-upscale/index.ts`
- **Input:** Multipart form with `image_file` + `X-Device-Id`
- **Backend:** `POST https://clipdrop-api.co/image-upscaling/v1/upscale`
- **Output:** PNG image binary + `X-Remaining` header

### Function 5: `ai-remove-text/index.ts`
- **Input:** Multipart form with `image_file` + `X-Device-Id`
- **Backend:** `POST https://clipdrop-api.co/cleanup/v1` (mask = full white)
- **Output:** PNG image binary + `X-Remaining` header

### Function 6: `ai-generate-image/index.ts`
- **Input:** `{ prompt: string }` + `X-Device-Id`
- **Backend:** `POST https://clipdrop-api.co/text-to-image/v1`
- **Output:** PNG image binary + `X-Remaining` header

## Related Code Files

**Create:**
- `supabase/functions/_shared/rate-limiter.ts`
- `supabase/functions/ai-rewrite/index.ts`
- `supabase/functions/ai-translate/index.ts`
- `supabase/functions/ai-remove-bg/index.ts`
- `supabase/functions/ai-upscale/index.ts`
- `supabase/functions/ai-remove-text/index.ts`
- `supabase/functions/ai-generate-image/index.ts`

**Reference:**
- `supabase/functions/fetch-news/index.ts` (pattern to follow)

## Implementation Steps

1. Create `ai_usage` table via Supabase SQL Editor
2. Create `_shared/rate-limiter.ts` with checkRate/incrementRate functions
3. Create `ai-rewrite` Edge Function (text → Gemini → text)
4. Create `ai-translate` Edge Function (text → Gemini → text)
5. Create `ai-remove-bg` Edge Function (image → ClipDrop → image)
6. Create `ai-upscale` Edge Function (image → ClipDrop → image)
7. Create `ai-remove-text` Edge Function (image → ClipDrop → image)
8. Create `ai-generate-image` Edge Function (text → ClipDrop → image)
9. Set Supabase secrets: `CLIPDROP_API_KEY`, `GEMINI_API_KEY`
10. Deploy all functions: `supabase functions deploy --project-ref omuajrrvkhzeruupwjot`
11. Test each endpoint via curl

## Todo

- [ ] Create `ai_usage` table + index
- [ ] Create `_shared/rate-limiter.ts`
- [ ] Create `ai-rewrite` Edge Function
- [ ] Create `ai-translate` Edge Function
- [ ] Create `ai-remove-bg` Edge Function
- [ ] Create `ai-upscale` Edge Function
- [ ] Create `ai-remove-text` Edge Function
- [ ] Create `ai-generate-image` Edge Function
- [ ] Set CLIPDROP_API_KEY secret
- [ ] Set GEMINI_API_KEY secret
- [ ] Deploy all Edge Functions
- [ ] Test all 6 endpoints via curl

## Success Criteria

- All 6 endpoints return correct responses
- Rate limiting blocks requests after daily limit
- `X-Remaining` header returns correct count
- No API keys exposed in request/response
- Image endpoints handle up to 6MB uploads

## Risk Assessment

| Risk | Impact | Mitigation |
|------|--------|------------|
| Supabase EF body limit <6MB | Image uploads fail | Client pre-resizes (already done) |
| ClipDrop API rate limits (their side) | 429 from upstream | Map to user-friendly error |
| Gemini quota exhaustion | Text tools fail | Monitor usage, upgrade plan if needed |
| Cold start latency | Slow first request | Acceptable for AI tools (user expects wait) |
