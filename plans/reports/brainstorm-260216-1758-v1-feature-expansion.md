# Brainstorm: Tony Chat v1.0.0 Feature Expansion
**Date:** 2026-02-16 | **Decision:** Full-scope MVP before release

---

## Problem Statement

Tony Chat has core messaging + AI features + ghost mode working (Phase 6.1-6.5 done). Current build is feature-complete for a "Privacy + AI Telegram fork." User wants to expand v1.0.0 scope with unique features that **differentiate from ALL Telegram forks** — not just another fork with AI.

**Goal:** Lower chat feature visibility, position as unique product.

---

## Agreed Feature Set for v1.0.0

### Tier 1: Committed Features

| # | Feature | Effort | Cost/mo | Module |
|---|---------|--------|---------|--------|
| 1 | **AI Emoji Remix (Genmoji)** | 2-3 wk | $5-10 | tonychat-ai |
| 2 | **Nearby Community Board** | 4-5 wk | $0 (Supabase free) | tonychat-community (new) |
| 3 | **AI Image Editing** | 2-3 wk | $1-2 | tonychat-ai |

### Tier 2: Suggested Additions (high impact, low effort)

| # | Feature | Effort | Cost/mo | Why |
|---|---------|--------|---------|-----|
| 4 | **Voice Note Transcription** | 1 wk | ~$0.006/min (Whisper) | Most requested missing feature in all forks |
| 5 | **AI Chat Search** | 2 wk | Uses existing provider | "Find that restaurant link from last week" — semantic search |
| 6 | **Smart Notification Summary** | 1 wk | Uses existing provider | 10+ unreads → "3 about dinner, 2 memes" |

**Total MVP cost: ~$6-12/month.** Total effort: ~12-15 weeks for Tier 1+2.

---

## Architecture Decisions

### 1. AI Emoji Remix / Genmoji

**MVP approach:** Text prompt → AI generates 256-512px sticker → save locally, share as inline image

**Provider strategy:**
- MVP: Google Gemini 2.5 Flash (FREE: 1,500 generations/day — more than enough)
- Upgrade: Replicate SDXL Emoji ($0.0035/image, Apple emoji-style quality)
- Premium: DALL-E 3 ($0.04/image)

**UI:** New "Emoji Lab" screen accessible from sticker panel or attachment menu
- Text prompt input with style selector (emoji, sticker, cartoon)
- Grid gallery of generated results
- Tap to send in chat or save to collection
- Recent generations cached locally

**Module:** Extends existing `tonychat-ai` — add `ImageGenerationProvider` interface

### 2. Nearby Community Board

**Backend: Supabase + PostGIS** (clear winner over Firebase)
- Native spatial queries (no geohash hacks)
- Real-time subscriptions for new nearby posts
- Free tier: 500MB storage, 2M API calls/month (~1K DAU)
- PostGIS single-query: find posts within radius, sorted by distance

**New module:** `tonychat-community`
- Supabase Kotlin client
- PostsRepository (CRUD + nearby query)
- Real-time observer (new posts in radius)

**UI:** "Nearby" tab in main navigation (not buried in drawer)
- Feed of posts within configurable radius (1-10km)
- Post types: hangout invite, question, event, general
- Interactions: like, comment, flag
- Anonymous or linked to Telegram identity (user choice)
- Post expiry: 24h default (keeps feed fresh)

**Auth:** Supabase anonymous auth for reads, Telegram-linked for writes

**Schema:**
```sql
posts (id uuid, author_id, title, content, post_type,
       location geography(POINT), radius_km int,
       expires_at timestamp, created_at timestamp)
comments (id uuid, post_id FK, author_id, content, created_at)
reactions (post_id FK, author_id, type, created_at)
```

### 3. AI Image Editing

**MVP: 3 tools, triggered from image viewer**
- Background removal → remove.bg ($0.0025/image)
- Object/person removal → ClipDrop Cleanup ($0.03/image)
- Image enhance/upscale → ClipDrop Reimagine ($0.02/image)

**UI:** Long-press image in chat → "AI Edit" → tool picker → preview → save/share

**Module:** Extends `tonychat-ai` — add `ImageEditProvider` interface

### 4. Voice Transcription (Suggested)

**MVP:** Tap voice message → "Transcribe" button → Whisper API → text overlay
- OpenAI Whisper: $0.006/minute, 25MB max
- Show transcription as expandable text below voice bubble
- Cache transcriptions locally

### 5. AI Chat Search (Suggested)

**MVP:** Search bar → natural language query → AI finds relevant messages
- Extract recent N messages → embed query + messages → find semantic matches
- Could use existing AI provider (OpenAI/Anthropic) for matching
- Fallback: keyword search (already exists in Telegram)

### 6. Smart Notification Summary (Suggested)

**MVP:** When 10+ unread in a chat → notification body = AI summary
- Extract last 10-20 messages → summarize → set as notification text
- Uses existing `AiManager.summarize()` — minimal new code
- Rate limit: 1 summary per chat per 5 minutes

---

## Figma Integration

**For app icon + splash + UI screens:**
- Manual export recommended (<20 assets)
- Export at 1x→4x → map to mdpi/hdpi/xhdpi/xxhdpi/xxxhdpi
- For bulk: Figma REST API with personal access token (`GET /v1/images/:file_key`)

---

## Version Strategy

| Version | Scope | Status |
|---------|-------|--------|
| v0.5.0-beta | Current build (chat + AI + ghost + about) | DONE |
| v1.0.0 | + Emoji remix + Community + Image edit + Voice transcription | TARGET |
| v1.1.0 | + AI search + Smart notifications + polish | FUTURE |

**Recommendation:** Rebrand current build as v0.5.0 internally. Ship v1.0.0 when Tier 1 features complete.

---

## New Module Structure

```
tonychat-core/        ← TonyConfig, TonyColors (existing)
tonychat-ai/          ← AI providers, rate limit, cache (existing)
                      + ImageGenerationProvider, ImageEditProvider, WhisperProvider
tonychat-community/   ← NEW: Supabase client, PostsRepository, real-time
tonychat-ui/          ← Theme (existing) + EmojiLab, NearbyFeed, ImageEditor screens
TMessagesProj/        ← Main app, wiring, Telegram UI hooks
```

---

## Risk Assessment

| Risk | Impact | Mitigation |
|------|--------|------------|
| Community board moderation/abuse | HIGH | Launch with report button + manual review, add keyword filter |
| Location permission rejection by users | MEDIUM | Community tab works without location (shows trending), prompt explains value |
| API cost surprise | LOW | Rate limits already in place, free tiers cover MVP |
| Supabase free tier limits | LOW | 1K DAU well within limits, upgrade to Pro ($25/mo) if needed |
| Scope creep delays v1.0.0 | HIGH | Strict MVP: each feature has "done" criteria, no polish until all 3 ship |

---

## Implementation Order (Recommended)

1. **AI Image Editing** (2-3 wk) — easiest, extends existing AI module
2. **AI Emoji Remix** (2-3 wk) — extends AI module, high wow-factor
3. **Voice Transcription** (1 wk) — quick win, uses Whisper
4. **Nearby Community Board** (4-5 wk) — biggest effort, new module + backend
5. **App icon + splash** (from Figma) — parallel with any phase
6. **Tag v1.0.0** release

**Total: ~10-12 weeks** if sequential. Could compress to 8-9 weeks with parallel work on community backend while shipping AI features.

---

## Unresolved Questions

1. **Emoji quality vs speed** — start with free Gemini or paid Replicate? UX test needed
2. **Community identity** — anonymous or Telegram-linked? Privacy implications
3. **Community moderation** — manual review, keyword filter, or AI moderation?
4. **Supabase scaling** — test PostGIS with 50K mock posts before launch
5. **Notification summary** — how to handle rate limiting without draining battery?
6. **Figma assets** — how many screens/components to export? Just icon+splash or full design system?
