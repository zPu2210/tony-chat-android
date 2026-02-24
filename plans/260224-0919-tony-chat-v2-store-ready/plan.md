# Tony Chat v2.0 — Store-Ready Plan

**Date**: 2026-02-24 | **PRD**: [prd-v2.md](prd-v2.md) (v1.4)
**Goal**: Differentiate from Telegram enough to pass Google Play / App Store review
**Team**: 2 devs (1 UI/Android, 1 Integration/Data)
**Timeline**: 2 weeks (best case) / 2.5 weeks (realistic)

---

## Strategic Context

Tongram (previous product) rejected by stores: too identical to Telegram (UI, icon, listing).
Tony Chat pivots: **AI & Social first, messaging second**. Telegram chat = side feature.

**Key Differentiators**:
- AI Hub as default first tab (6 standalone tools, no Telegram login needed to browse)
- Explore (AI News Feed) as unique content feature
- KakaoTalk-inspired yellow reskin (ThemeColors.java override, no Telegram blue anywhere)
- Custom icon + branding (ready)
- New Korean developer account (in progress)

**Store Reviewer Experience**: Open app → onboarding slides → AI tools grid → News Feed → "oh it also chats"

---

## Phases

| Phase | Name | Effort | Status | Dependencies |
|-------|------|--------|--------|--------------|
| 1 | [Navigation + Theme Shell](phase-01-navigation-theme-shell.md) | 4-5d | **95%** (integration test remaining) | None |
| 2 | [AI Hub Standalone](phase-02-ai-hub-standalone.md) | 5-6d | **80%** (code complete, device testing needed) | Phase 1 (nav) |
| 3 | [Explore / AI News Feed](phase-03-explore-news-feed.md) | 3-4d | **90%** (code complete, device testing needed) | Phase 1 (nav) |
| ~~4~~ | ~~Emoji Kitchen + Chat Lock~~ | — | **OBSOLETE** | Emoji Kitchen + Chat Lock deferred to v1.1 |
| 5 | [Store Submission](phase-05-store-submission.md) | 2d | Pending | Phases 1-3 |

**Phases 2 and 3 run in PARALLEL** after Phase 1 completes.

---

## AI Hub Tools (6 total, v1.4)

| Tool | Type | API | Rate Limit |
|------|------|-----|------------|
| AI Writer | Text | Gemini Flash / GPT-4o-mini | 50/day |
| AI Translator | Text | Gemini Flash / Google Translate | Unlimited |
| Remove BG | Image | ClipDrop | 5/day (shared) |
| Upscale | Image | ClipDrop | 5/day (shared) |
| Remove Text | Image | ClipDrop | 5/day (shared) |
| AI Generate | Image | ClipDrop | 5/day (shared) |

**All tools are stateless**: enter → input → result → Copy/Save → done. No history, no undo, no share sheets.

---

## Timeline (2 devs)

```
Week 1:  Phase 1 (both devs) ──────────────────────┐
Week 2:  Phase 2 (Dev A) ║ Phase 3 (Dev B) ────────┤ parallel
         Phase 5 (both devs) ───────────────────────┘
```

---

## Architecture

```
4-Tab Navigation (floating pill)
├── Tony AI (DEFAULT, 1st tab)
│   ├── AI Writer (pills: Fix Grammar, Pro, Casual, Polite, Email, Greeting, Meeting, Thanks)
│   ├── AI Translator (language picker + dual text area)
│   ├── Remove BG (ClipDrop, photo → transparent PNG)
│   ├── Upscale (ClipDrop, photo → 2x resolution)
│   ├── Remove Text (ClipDrop, photo → text removed)
│   └── AI Generate (ClipDrop, text prompt → 1024x1024 image)
├── Explore (2nd tab) — AI News Feed
│   ├── Category tabs: All | Tech | Business | World | Entertainment | Sports
│   ├── News cards: headline + source + AI summary preview + image
│   └── Tap → login check → Chrome Custom Tab (original article)
├── Chats (3rd tab)
│   └── Telegram chat list (existing, yellow theme)
│       └── In-chat AI tools (existing: translate, rewrite, summary)
└── Settings (4th tab)
    └── Existing settings
```

**CRITICAL**: All tabs **browsable** without login. Any **action** (tool use, article read, chat) requires Telegram login.

---

## Success Criteria

| Metric | Target |
|--------|--------|
| Store approval | First submission accepted |
| AI Hub tools | 6 tools functional (input → result → copy/save) |
| AI News Feed | Always has content, loads with articles |
| First-launch | Onboarding → AI Hub (not Telegram login) |
| APK size | <100MB |
| Crash rate | <2% |

---

## Risks

| Risk | Severity | Mitigation |
|------|----------|------------|
| Store flags as Telegram clone | HIGH | New dev account, AI-first listing, KakaoTalk reskin |
| Yellow reskin incomplete | HIGH | Systematic ThemeColors.java audit, screenshot every screen |
| Hamburger drawer removal breaks nav | HIGH | Test all entry points route through bottom tabs |
| AI Hub feels thin | LOW | 6 tool cards (3x2 grid) + News Feed = strong differentiation |
| ClipDrop costs at scale | MEDIUM | Client-side 5/day shared limit, credit monitoring |
| Timeline slip | MEDIUM | Phase 4 already cut, scope is tight |
