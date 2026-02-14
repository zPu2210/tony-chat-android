# Phase 5C Implementation Report: Bot Platform UI Removal

## Executed Phase
- Phase: phase-05C-bot-platform-removal
- Plan: plans/260213-0952-tony-chat-bootstrap/
- Status: PARTIAL - Stubs complete, callsite fixes needed
- Date: 2026-02-14

## Summary
Deleted 22 bot platform UI files (14,000+ lines), replaced with minimal stubs. All bot platform features (web views, commands menu, keyboards, sensors, biometry, storage) now disabled. Build has ~100 compile errors from callsites needing updates.

## Files Modified

### Deleted and Stubbed (22 files)

**Major UI Components (11 files, ~12,500 lines deleted)**
- `BotWebViewSheet.java` (2689→185 lines) - Main bot web view dialog
- `BotWebViewContainer.java` (5069→295 lines) - Web view container
- `BotWebViewAttachedSheet.java` (1929→120 lines) - Attached sheet
- `BotWebViewMenuContainer.java` (1307→80 lines) - Menu container
- `ChatAttachAlertBotWebViewLayout.java` (1312→110 lines) - Attachment layout
- `BotCommandsMenuView.java` (391→90 lines) - Commands menu view
- `BotCommandsMenuContainer.java` (310→90 lines) - Commands menu container
- `BotKeyboardView.java` (266→55 lines) - Inline keyboard
- `BotButtons.java` (357→45 lines) - Button UI
- `BotVerifySheet.java` (432→38 lines) - Verification sheet
- `BotShareSheet.java` (608→30 lines) - Share sheet

**API Features (6 files, ~2,600 lines deleted)**
- `BotBiometry.java` (507→50 lines) - Biometric auth API
- `BotBiometrySettings.java` (104→25 lines) - Biometry settings
- `BotSensors.java` (463→35 lines) - Device sensors API
- `BotLocation.java` (568→30 lines) - Location access API
- `BotStorage.java` (592→50 lines) - Bot storage API
- `BotDownloads.java` (893→35 lines) - File download API

**Other (5 files)**
- `BotPreviewsEditContainer.java` (1993→30 lines) - Preview editing
- `ChatActivityBotWebViewButton.java` (154→55 lines) - STUBBED (not deleted)
- `SetupEmojiStatusSheet.java` (508 lines) - KEPT (not bot-specific)
- `AffiliateProgramFragment.java` (766 lines) - KEPT (Stars-related, handled in Phase 5)
- `ChannelAffiliateProgramsFragment.java` (1182 lines) - KEPT (Stars-related)
- `SuggestedAffiliateProgramsFragment.java` (121 lines) - KEPT (Stars-related)
- `WebViewRequestProps.java` (108 lines) - KEPT (data class)

**Total: ~14,000 lines deleted, replaced with ~1,400 lines of stubs**

## Tasks Completed
- ✅ Analyzed bot platform file dependencies
- ✅ Deleted BotWebViewSheet and created stub
- ✅ Deleted BotWebViewContainer and created stub
- ✅ Deleted BotWebViewAttachedSheet and created stub
- ✅ Deleted BotWebViewMenuContainer and created stub
- ✅ Deleted ChatAttachAlertBotWebViewLayout and created stub
- ✅ Deleted BotCommandsMenuView and BotCommandsMenuContainer
- ✅ Deleted remaining bot platform UI files (BotKeyboardView, BotButtons, etc.)
- ✅ Deleted bot API feature files (BotBiometry, BotSensors, etc.)
- ✅ Stubbed ChatActivityBotWebViewButton to disable functionality
- ⚠️ Build attempted - 100 compile errors from callsites

## Stub Strategy
All stubs follow same pattern:
- Extend same parent class/interfaces as original
- Keep all constructor signatures
- Static methods return null/0/false
- Instance methods are no-ops
- Inner classes preserved (MainButtonSettings, ButtonsState, Bot, etc.)
- Constants preserved (TYPE_* flags)

### Key Stub Classes Added
- `BotWebViewContainer.BotWebViewProxy`
- `BotWebViewContainer.WebViewProxy`
- `BotWebViewContainer.Delegate` (19 methods)
- `BotWebViewAttachedSheet.MainButtonSettings`
- `BotButtons.ButtonsState`
- `BotBiometry.Bot`
- `ChatAttachAlertBotWebViewLayout.WebViewSwipeContainer`
- `BotCommandsMenuView.BotCommandsAdapter`
- `BotCommandsMenuView.BotCommandView`

## Compile Errors Status
**100 errors remaining** - mostly in:
- `ChatActivityEnterView.java` (~50 errors) - bot menu/keyboard usage
- `ChatAttachAlert.java` (~30 errors) - bot web view integration
- `ChatActivity.java` (~5 errors)
- `MessagesController.java` (~3 errors)
- `SharedMediaLayout.java` (~3 errors)
- `PrivacySettingsActivity.java` (biometry bots)

Error types:
- 80x "cannot find symbol" - missing fields/methods on stubs
- 7x override issues - method signature mismatches
- 13x type incompatibility issues

## Next Steps

### Immediate (to reach 0 errors)
1. Fix callsites in ChatActivityEnterView (~50 errors)
   - Bot menu button calls
   - Bot keyboard interactions
   - Bot commands adapter usage
2. Fix ChatAttachAlert Delegate implementation
3. Add missing stub methods/fields identified by compile errors
4. Build until 0 errors

### Post-Build
5. Runtime test: verify bot UI doesn't appear
6. Commit: "remove: Bot platform UI"
7. Continue Phase 5 with next feature (Games or VoIP)

## Issues Encountered
- Many inner classes referenced by external code
- BotWebViewContainer.Delegate has 19 methods
- ChatActivityEnterView heavily coupled to bot features
- Some interface implementations incomplete

## Architecture Impact
- Bot web views: DISABLED
- Bot commands menu: DISABLED
- Bot inline keyboards: DISABLED (display only, non-functional)
- Bot API features (sensors, biometry, location, storage): DISABLED
- Basic bot message receiving: STILL WORKS (not removed)

## Unresolved Questions
- Should ChatActivityBotWebViewButton be deleted entirely vs stubbed?
- Are affiliate fragments truly Stars-related or bot-related?
- Should SetupEmojiStatusSheet stay (emoji status not bot-specific)?
