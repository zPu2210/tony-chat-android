# Phase 3: Explore / AI News Feed

**Priority**: P1 (unique differentiator — always has content, no cold start)
**Effort**: 3-4 days (Dev B primary)
**Status**: 90% (code complete, device testing needed)
**Depends on**: Phase 1 (nav shell)

---

## Context

- [plan.md](plan.md) — overview
- [brainstorm-260224-1127-prd-v2-revisions.md](../reports/brainstorm-260224-1127-prd-v2-revisions.md) — decision rationale
- Replaces original community board plan (phase-03-explore-look-around.md)

---

## Overview

AI-curated news reading feed. Supabase Edge Function fetches news from RSS/API sources, AI summarizes each article, client displays by category with direct links to original articles.

**Browsable** without login (see headlines + summaries). **Tapping article** to read requires Telegram login.

**In-memory cache only** (PRD §7): no Room DB, no SharedPrefs article cache. Fetch on first tab visit, hold in memory, re-fetch if >30min stale or pull-to-refresh. App kill = fresh fetch on next launch.

**Why News Feed over Community Board:**
- Always has content (no cold start problem)
- No moderation needed
- Simpler backend (no PostGIS, RLS, UGC)
- AI summaries = core differentiator
- 1 day less dev effort

---

## Key Insights

- Supabase already deployed (omuajrrvkhzeruupwjot, ap-southeast-2)
- Edge Functions available on Supabase (Deno runtime)
- Google News RSS: free, broad categories, always fresh
- NewsData.io: free 200 req/day, Korean news support
- Gemini Flash: ~$0.0005/summary, handles news well
- Chrome Custom Tabs: native Android, fast, minimal code

---

## Requirements

### Functional
- Category tabs: All | Tech | Business | World | Entertainment | Sports
- News card: headline, source name, time, category chip, AI summary (2-3 lines), thumbnail
- Tap card → open original article URL (Chrome Custom Tab)
- Pull-to-refresh (triggers client re-fetch from Supabase)
- Pagination: 20 articles per load
- Browse without login: see all headlines, summaries, thumbnails
- Tap article to read full → requires Telegram login (LoginPromptSheet)
- Auto-refresh: re-fetch if in-memory cache >30min stale on app foreground

### Non-Functional
- Feed load: <2s from Supabase cache
- AI summaries: pre-computed server-side (not on-demand)
- Article freshness: max 1h old (Edge Function runs every 30min)

---

## Architecture

### Supabase Schema

```sql
CREATE TABLE news_articles (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  title text NOT NULL,
  summary text, -- AI-generated 2-3 sentence summary
  source_url text UNIQUE NOT NULL,
  source_name text NOT NULL,
  image_url text,
  category text NOT NULL DEFAULT 'general',
  published_at timestamptz NOT NULL,
  created_at timestamptz DEFAULT now()
);

CREATE INDEX idx_news_category ON news_articles(category, published_at DESC);
CREATE INDEX idx_news_published ON news_articles(published_at DESC);

-- Auto-cleanup: delete articles older than 7 days
-- (run via pg_cron or Edge Function)
```

### Supabase Edge Function

```
supabase/functions/fetch-news/index.ts
├── Fetch from Google News RSS (by category)
├── Fetch from NewsData.io API (Korean news)
├── Parse: title, source_url, source_name, image_url, published_at, category
├── Deduplicate by source_url (UPSERT)
├── AI summarize new articles (Gemini Flash)
├── Insert into news_articles table
└── Cleanup articles > 7 days old

Trigger: pg_cron every 30 minutes OR Supabase cron job
```

### News Sources

| Source | Type | Categories | Language | Cost |
|--------|------|------------|----------|------|
| Google News RSS | RSS | All | EN, KR | Free |
| NewsData.io | REST API | All | EN, KR, VI | Free 200 req/day |
| Naver News RSS | RSS | All | KR | Free |

### Android Architecture

```
NewsFeedFragment.java (Explore tab)
├── Header: category tabs (horizontal scroll chips)
├── RecyclerView + NewsCardAdapter
│   └── NewsCard: thumbnail + headline + source + time + summary preview
├── Pull-to-refresh (SwipeRefreshLayout)
├── Pagination (scroll → load more)
└── Tap handler → Chrome Custom Tab (original URL)

NewsRepository.kt (tonychat-community module, reuse)
├── getArticles(category, limit, offset) → Supabase REST
└── In-memory cache only (no Room DB, no SharedPrefs for articles)
```

---

## UI Design

### News Feed Screen

```
┌─────────────────────────────────────────────┐
│ ☰  Explore                          🔍     │ ← Glass App Bar
├─────────────────────────────────────────────┤
│ [All] [Tech] [Business] [World] [Ent] [Sports] → │
│  ← scrollable category chips ──────────── │
├─────────────────────────────────────────────┤
│                                             │
│ ┌─────────────────────────────────────────┐ │
│ │ ┌──────┐ Reuters · 2h ago        Tech  │ │
│ │ │ img  │ Apple unveils new AI chip     │ │
│ │ │      │ that runs models locally...    │ │
│ │ └──────┘                                │ │
│ │ Apple announced a new M5 chip that      │ │
│ │ enables on-device AI processing...      │ │
│ └─────────────────────────────────────────┘ │
│                                             │
│ ┌─────────────────────────────────────────┐ │
│ │ ┌──────┐ BBC · 5h ago          World   │ │
│ │ │ img  │ G20 summit reaches new        │ │
│ │ │      │ climate agreement...           │ │
│ │ └──────┘                                │ │
│ │ World leaders at G20 agreed on a        │ │
│ │ framework to reduce emissions by 50%... │ │
│ └─────────────────────────────────────────┘ │
│                                             │
│ ┌─ (more cards, scrollable) ──────────────┐ │
│                                             │
├─────────────────────────────────────────────┤
│ [Tony AI] [Explore✓] [Chats] [Settings]    │ ← Nav pill
└─────────────────────────────────────────────┘
```

### News Card Layout

```
┌─────────────────────────────────────────────┐
│ ┌──────┐  Source · Time            Category │
│ │ thumb│  Headline text (2 lines max,      │
│ │ 80x80│  bold, 15sp)                      │
│ └──────┘                                    │
│ AI summary text (2-3 lines, 13sp, #666)     │
└─────────────────────────────────────────────┘
```

- Thumbnail: 80x80 rounded corner (8dp), left-aligned
- Source: Inter 12sp #999, dot separator, relative time
- Category: chip/badge, top-right, colored by category
- Headline: Inter 15sp bold #111, max 2 lines
- Summary: Inter 13sp #666, max 3 lines

---

## Related Code Files

### Create
- `supabase/functions/fetch-news/index.ts` — Edge Function (fetch + summarize + store)
- `TMessagesProj/.../TonyChat/NewsFeedFragment.java` — Explore tab UI
- `TMessagesProj/.../TonyChat/NewsCardAdapter.java` — RecyclerView adapter
- `tonychat-community/NewsRepository.kt` — Supabase REST client for news

### Modify
- `AiHubFragment.java` or tab host — Explore tab now shows NewsFeedFragment
- `tonychat-community/build.gradle` — add Chrome Custom Tabs dependency

### Delete (old community screens, code kept dormant)
- No code deletion needed — community code stays but unlinked from nav

---

## Implementation Steps

1. **Supabase: news_articles table + indexes** (Dev B, Day 1)
   - Run migration SQL
   - Verify via Supabase dashboard

2. **Supabase Edge Function: fetch-news** (Dev B, Day 1-2)
   - Deno function: fetch Google News RSS + NewsData.io
   - Parse RSS XML → extract fields
   - Deduplicate by source_url (UPSERT)
   - AI summarize: call Gemini Flash API for new articles
   - Insert summaries into DB
   - Set up pg_cron: every 30 min
   - Test: verify articles populate in table

3. **NewsRepository.kt** (Dev B, Day 2)
   - `getArticles(category, limit, offset)` → Supabase REST
   - `/rest/v1/news_articles?category=eq.tech&order=published_at.desc&limit=20&offset=0`
   - In-memory cache (hold articles list, no persistence)

4. **NewsFeedFragment.java** (Dev B, Day 2-3)
   - Category tabs: horizontal scroll chips (yellow active, gray inactive)
   - RecyclerView with NewsCardAdapter
   - Pull-to-refresh: re-fetch from Supabase
   - Pagination: load more on scroll bottom
   - Empty state: "Loading latest news..." spinner

5. **Chrome Custom Tab integration** (Dev B, Day 3)
   - Tap news card → login check → open `source_url` in Chrome Custom Tab
   - If not logged in → show LoginPromptSheet
   - Fallback: Intent.ACTION_VIEW if Chrome not available
   - Toolbar color: #F9E000 (yellow brand)

6. **Connect to nav** (Dev B, Day 3-4)
   - Explore tab → NewsFeedFragment (replace CommunityFeedFragment)
   - Browse mode: see all content without login, tap article requires login
   - Verify nav pill highlights correctly

7. **Testing** (Dev B, Day 4)
   - Edge Function runs on schedule
   - Categories filter correctly
   - Pagination works
   - Chrome Custom Tab opens articles
   - Pull-to-refresh works
   - Offline: show "No connection" empty state with retry button

---

## Todo

- [x] Create news_articles table + indexes in Supabase
- [x] Build Edge Function (RSS fetch + AI summarize + store)
- [x] Set up pg_cron for 30-min refresh
- [x] Build NewsRepository.kt (REST client + cache)
- [x] Build NewsFeedFragment.java (category tabs + card list)
- [x] Build NewsCardAdapter.java
- [x] Chrome Custom Tab integration
- [x] Connect to Explore nav tab
- [ ] Set GEMINI_API_KEY secret on Edge Function (for AI summaries)
- [ ] Device testing: categories, pagination, pull-to-refresh, Chrome Custom Tab
- [ ] Verify login gate on article tap

---

## Success Criteria

- Feed loads in <2s with 20+ articles
- Category tabs filter correctly
- Tap card opens original article
- Pull-to-refresh works
- Browse without login (headlines + summaries visible)
- Tap article → login gate works
- Articles refresh every 30min (server-side)
- AI summaries are coherent 2-3 sentences

---

## Risk Assessment

| Risk | Impact | Mitigation |
|------|--------|------------|
| News API free tier limits | LOW | Google News RSS is unlimited; NewsData.io 200/day sufficient |
| AI summary quality | LOW | Gemini Flash handles news well, structured prompt |
| Edge Function timeout | LOW | Process in batches, 30-min window is generous |
| RSS format changes | LOW | Graceful error handling, multiple sources as fallback |
| Copyright concerns | LOW | Show summary + link to original (fair use), not full text |
