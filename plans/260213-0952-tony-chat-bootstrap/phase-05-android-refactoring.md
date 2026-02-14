---
phase: 5
title: Android Refactoring (Revised)
status: pending
effort: 6w
priority: P1
---

# Phase 5: Android Refactoring (Revised)

## Context Links
- [Phase 3 Architecture](phase-03-directory-architecture.md) - Multi-module setup with TonyConfig
- [Codebase Analysis](../reports/scout-260213-1116-nagram-codebase-analysis.md) - Coupling details

## Overview

Incremental refactoring strategy after learning Phase 3 deletion attempts caused 6,500+ errors. Deep coupling discovered: 199 files reference NekoConfig/NaConfig (497 occurrences), Stories integrated into DialogCell (6,301 lines), Theme.java is 10,734 lines. Cannot mass-delete, must disable at runtime first, then surgical removal.

## Key Insights
- NekoConfig/NaConfig spread across 199 files in org.telegram base code
- Stories/Gifts/Stars deeply integrated into UI (DialogCell, ChatActivity, ProfileActivity)
- God classes: DialogCell 6,301 lines, Theme.java 10,734 lines, ChatActivity 47K lines
- TonyConfig exists with 13 feature flags (all ON by default)
- Full release APK builds successfully now

## Architecture

### Strangler Fig Pattern - Revised
Work outside-in with runtime disabling before code removal:
```
Weeks 1-2: Wire TonyConfig feature flags into codebase (disable at runtime)
Weeks 3-4: Replace NekoConfig/NaConfig with TonyConfig (one file at a time)
Weeks 5-6: Remove dead feature code surgically (compile after each feature)
```

### What NOT to Touch
- C++/C native code (TgNet, voip, ffmpeg)
- TLRPC protocol classes (auto-generated)
- ConnectionsManager (networking)
- SQLite layer (MessagesStorage)

## Sub-Phases

### 5A: Wire TonyConfig Feature Flags (Week 1-2)

Replace feature checks to use TonyConfig, disable unwanted features via defaults.

#### Related Code Files
- `tonychat-core/src/main/java/com/tonychat/core/TonyConfig.kt` (edit defaults)
- `TMessagesProj/src/main/java/org/telegram/ui/Cells/DialogCell.java` (Stories checks)
- `TMessagesProj/src/main/java/org/telegram/ui/ChatActivity.java` (Bots, Games, Calls checks)
- `TMessagesProj/src/main/java/org/telegram/ui/ProfileActivity.java` (Premium, Business checks)
- `TMessagesProj/src/main/java/org/telegram/ui/LaunchActivity.java` (TON wallet entry points)

#### Todo List
- [x] Set TonyConfig defaults to OFF for 13 unwanted features
- [x] Wire Stories flag in DialogsActivity (stories header hidden)
- [x] Wire Premium/Stars/TON/Business/Gifts flags in ProfileActivity
- [x] Wire Calls/Proxy flags in DrawerLayoutAdapter
- [x] Wire Bots/Translation/Sponsored flags in ChatActivity
- [x] Build passes, 11/13 flags wired
- [ ] Test on device: verify features actually hidden
- [ ] Commit: "feat(config): wire TonyConfig flags to disable unwanted features"

#### Success Criteria
- TonyConfig defaults set to OFF for 13 unwanted features
- UI no longer shows Stories tab, Premium badges, Bot buttons, etc.
- App builds and runs, core messaging works
- No crashes when opening chats, profiles, settings

#### Risk Assessment
| Risk | Probability | Impact | Mitigation |
|------|-------------|--------|------------|
| Missing feature entry points | Medium | Medium | Thorough grep for each feature |
| Flag checks break existing logic | Medium | Medium | Test each change on device |

---

### 5B: Replace NekoConfig/NaConfig with TonyConfig (Week 3-4)

Migrate settings we keep, remove NekoConfig/NaConfig references one file at a time.

#### Related Code Files
- All 199 files that import `tw.nekomimi.nekogram.NekoConfig` or `xyz.nextalone.nagram.NaConfig`
- `tw/nekomimi/nekogram/utils/VibrateUtil.kt` (example: line 34 uses NekoConfig.disableVibration)
- Priority files: `DialogsActivity.java` (11 refs), `ProfileActivity.java` (23 refs), `ChatActivity.java` (22 refs), `LaunchActivity.java` (20 refs)

#### Todo List
- [x] Identify all NekoConfig/NaConfig/NekoXConfig fields used (200+ fields mapped)
- [x] Expand TonyConfig with all fields (1,084 lines)
- [x] Verify build still passes
- [ ] **REVISED**: Bridge creation merged into 5C (atomic delete+bridge approach)
- [ ] When deleting NekoX/Nagram dirs: create bridge NekoConfig/NaConfig that delegates to TonyConfig
- [ ] Then gradually replace imports in 199 files from NekoConfig → TonyConfig direct

#### Success Criteria
- All 199 files migrated from NekoConfig/NaConfig to TonyConfig
- App compiles with zero warnings
- All messaging features work (chats, groups, channels, media)
- Settings kept from Nagram (cache, appearance) work via TonyConfig

#### Risk Assessment
| Risk | Probability | Impact | Mitigation |
|------|-------------|--------|------------|
| Breaking change per file | High | Medium | Compile and test after each file |
| Missing property mapping | Medium | Medium | Maintain mapping doc while working |

---

### 5C: Surgical Feature Code Removal (Week 5-6)

Remove dead code for disabled features. One feature per commit, compile after each.

#### Related Code Files
- Stories: `TMessagesProj/src/main/java/org/telegram/ui/Stories/*` (~15 files)
- Stars/Gifts: `TMessagesProj/src/main/java/org/telegram/ui/Stars/*`, `TMessagesProj/src/main/java/org/telegram/ui/Gifts/*`
- VoIP: `org/telegram/messenger/voip/*`, `GroupCallActivity.java`
- Bots: `BotWebViewContainer.java`, bot-related fragments
- TON: TON wallet integration code
- Premium: Premium upsell UI, paywall code
- Translation: `TranslateController.java`, translation UI
- Proxy: `ProxyListActivity.java`, proxy settings

#### Feature Removal Priority (Most Isolated First)
1. **Translation** (most isolated, 1 controller + UI)
2. **Proxy** (settings UI, not deeply coupled)
3. **Sponsored messages** (ad loading code)
4. **Passport** (rarely used, isolated feature)
5. **TON Wallet** (separate module-like code)
6. **Stars/Gifts** (payment flows, moderately coupled)
7. **Premium upsell UI** (overlays on existing features)
8. **Business features** (business hours, auto-replies)
9. **Games** (bot games platform)
10. **Bots** (inline bots, bot commands)
11. **VoIP** (calls system, moderately coupled to notifications)
12. **Stories** (MOST coupled - last to remove, integrated into DialogCell/ProfileActivity)

#### Todo List
- [ ] Remove Translation feature: TranslateController, translation UI
- [ ] Build, test, commit: "remove: Translation feature"
- [ ] Remove Proxy feature: ProxyListActivity, settings
- [ ] Build, test, commit: "remove: Proxy feature"
- [ ] Remove Sponsored: ad loading, sponsored message code
- [ ] Build, test, commit: "remove: Sponsored messages"
- [ ] Remove Passport: PassportActivity, passport forms
- [ ] Build, test, commit: "remove: Passport feature"
- [ ] Remove TON Wallet: wallet UI, TON integration
- [ ] Build, test, commit: "remove: TON Wallet"
- [ ] Remove Stars/Gifts: Stars UI, Gifts UI, payment flows
- [ ] Build, test, commit: "remove: Stars and Gifts"
- [ ] Remove Premium upsell UI: paywall overlays
- [ ] Build, test, commit: "remove: Premium upsell"
- [ ] Remove Business: business hours, auto-reply features
- [ ] Build, test, commit: "remove: Business features"
- [ ] Remove Games: bot games platform
- [ ] Build, test, commit: "remove: Games platform"
- [ ] Remove Bots: inline bots UI (keep bot messages in chats for now)
- [ ] Build, test, commit: "remove: Bot platform UI"
- [ ] Remove VoIP: GroupCallActivity, VoIPService, voip UI
- [ ] Build, test, commit: "remove: VoIP/Calls"
- [ ] Remove Stories: StoriesController, Stories UI (carefully - deeply coupled)
- [ ] Fix DialogCell references to Stories (stub out or remove)
- [ ] Build, test, commit: "remove: Stories feature"
- [ ] Delete tw/nekomimi/nekogram and xyz/nextalone/nagram directories
- [ ] Final build and test: core messaging only
- [ ] Commit: "remove: NekoX and Nagram code directories"

#### Success Criteria
- All 13 unwanted features removed from codebase
- NekoX/Nagram directories deleted
- App compiles with zero warnings
- Core messaging works: 1-to-1, groups, channels, media sharing
- APK size reduced by ~20-30% from feature removal

#### Risk Assessment
| Risk | Probability | Impact | Mitigation |
|------|-------------|--------|------------|
| Breaking core features | Medium | High | Test after each feature removal |
| Stories removal breaks UI | High | High | Do Stories LAST, test DialogCell heavily |
| Missing dependencies | Medium | Medium | Use grep to find all feature refs before deleting |

---

### 5D: Extract Theme + Module Interfaces (Week 5-6, parallel with 5C)

While removing features, extract theme system and finalize module boundaries.

#### Related Code Files
- `TMessagesProj/src/main/java/org/telegram/ui/ActionBar/Theme.java` (10,734 lines)
- `tonychat-ui/src/main/java/com/tonychat/ui/TonyTheme.kt` (create)
- `tonychat-core/src/main/java/com/tonychat/core/hooks/` (hook system)

#### Todo List
- [ ] Study Theme.java structure (color keys, ThemeInfo, ThemeAccent)
- [ ] Create TonyTheme.kt with light/dark color definitions
- [ ] Extract 20-30 most-used colors to TonyTheme
- [ ] Add bridge in Theme.java to read from TonyTheme
- [ ] Test theme switching (light/dark)
- [ ] Define hook interfaces in tonychat-core (MessageHook, UIHook)
- [ ] Implement 1-2 example hooks (e.g., onMessageSent hook)
- [ ] Document module boundaries (core, ui, telegram-base)
- [ ] Commit: "refactor: extract TonyTheme and hook system"

#### Success Criteria
- TonyTheme has 20-30 core colors defined
- Light/dark mode switching works via TonyTheme
- Hook system has 2 working examples
- Module boundaries documented

---

### 5E: Testing + Stabilization (Week 6)

Comprehensive testing and bug fixes from refactoring.

#### Todo List
- [ ] Write unit tests for TonyConfig (all 13 flags)
- [ ] Write unit test for TonyTheme (light/dark colors)
- [ ] Integration test: send 1-to-1 message
- [ ] Integration test: send group message
- [ ] Integration test: send media (photo, video, file)
- [ ] Integration test: create channel, post message
- [ ] UI test: login flow
- [ ] UI test: chat list display
- [ ] UI test: open chat, send message
- [ ] Manual test: all core messaging features
- [ ] Fix any bugs found during testing
- [ ] Measure APK size (compare to Phase 4 baseline)
- [ ] Measure build time (compare to Phase 4)
- [ ] Commit: "test: comprehensive tests for Phase 5 refactoring"

#### Success Criteria
- 20+ unit tests pass
- All integration tests pass
- All UI tests pass
- Manual testing: no critical bugs
- APK size reduced by 20-30%
- Build time maintained or improved

---

## Overall Success Criteria
- All 13 unwanted features disabled via TonyConfig (5A), migrated off NekoConfig/NaConfig (5B), and removed from codebase (5C)
- NekoX/Nagram directories deleted
- TonyTheme extracted with light/dark mode
- Hook system working with examples
- Core messaging works: chats, groups, channels, media
- 20+ unit tests, all integration tests pass
- APK size reduced, build time acceptable

## Security Considerations
- Don't remove encryption, auth, session management
- Keep Telegram certificate pinning
- Preserve two-factor auth
- Keep end-to-end encryption for secret chats

## Next Steps
- After Phase 5: ready for Tony Chat AI features
- Phase 6: iOS planning (can start in parallel with 5C/5D)

## Unresolved Questions
- Should we keep bot message rendering (remove bot platform but keep viewing bot messages)?
- How much of VoIP native code is safe to delete (C++ layer)?
- Extract more from ChatActivity (47K lines) or defer to Phase 7?
