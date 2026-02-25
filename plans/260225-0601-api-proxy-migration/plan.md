# API Proxy Migration Plan

**Date:** 2026-02-25
**Goal:** Move all AI API calls behind Supabase Edge Functions so users never see/enter API keys
**Priority:** BLOCKER for store submission
**Brainstorm:** [reports/brainstorm-260225-0601-api-proxy-architecture.md](../reports/brainstorm-260225-0601-api-proxy-architecture.md)

## Phases

| Phase | Description | Status | Commit | Depends On |
|-------|------------|--------|--------|------------|
| 1 | Supabase Edge Functions + DB | **Complete** | `6c61c677` | — |
| 2 | Android Client Migration | **Complete** | `2cc02706` | Phase 1 |
| 3 | Validation + Cleanup | **Code-Complete** | — | Phase 2 |

## Architecture

```
Android Client
  ├─ Text tools (rewrite/translate) → POST /ai/rewrite, /ai/translate
  └─ Image tools (4 ClipDrop)      → POST /ai/remove-bg, /ai/upscale, /ai/remove-text, /ai/generate-image
        │
        ▼
Supabase Edge Functions
  ├─ Check rate limit (ai_usage table)
  ├─ Call external API (ClipDrop / Gemini)
  └─ Return result + remaining quota headers
```

## Supabase Secrets Required

```bash
supabase secrets set CLIPDROP_API_KEY=<key> --project-ref omuajrrvkhzeruupwjot
supabase secrets set GEMINI_API_KEY=<key> --project-ref omuajrrvkhzeruupwjot
```

## Estimated Effort

- Phase 1: ~2-3 hours (6 Edge Functions + DB table)
- Phase 2: ~1-2 hours (swap URLs, remove key UI)
- Phase 3: ~1 hour (test + build)
- **Total: ~4-6 hours**
