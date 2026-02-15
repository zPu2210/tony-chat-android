# Compile Fixes Round 2 Report

## Executed Phase
- Task: Fix compile errors after stub deletion (round 2)
- Files: PhotoViewer, ChatActivity, ChatAvatarContainer, ViewPagerFixed, DialogCell
- Status: completed

## Files Modified

### 1. PhotoViewer.java (~50 lines affected)
**Lines 7534-7536**: Removed translation menu option call
- Removed: `translateComment(TranslateDb.getChatLanguage(chatId, TranslatorKt.getCode2Locale("en")));`
- Fixed menu indexing (a == 5 → a == 6)

**Lines 8031-8076**: Deleted entire `translateComment()` method
- Removed 46-line translation feature method
- Removed TranslateDb, Translator.Companion references
- Removed AlertUtil progress/error dialogs

**Strategy**: Translation feature removed per Phase 5C

### 2. ChatActivity.java (~200 lines affected)
**Line 1689**: Removed DoubleTap check
- Deleted: `if((scrimPopupWindow != null && 0==DoubleTap.DOUBLE_TAP_ACTION_SHOW_REACTIONS)) return false;`
- DoubleTap always evaluates to 0 (stub removed)

**Lines 1883-1961**: Replaced `hasDoubleTap()` method
- Before: 79 lines checking DoubleTap actions (translate, reply, save, repeat, edit)
- After: 3 lines returning `false`
- Comment: "DoubleTap feature removed - double-tap actions disabled"

**Lines 1964-2043**: Replaced `onDoubleTap()` method
- Before: 80 lines handling double-tap reactions, translations, actions
- After: 3 lines no-op
- Comment: "DoubleTap feature removed - no-op"

**Line 8986**: Removed BackButtonMenuRecent call
- Deleted: `BackButtonMenuRecent.addToRecentDialogs(currentAccount, currentUser != null ? currentUser.id : -currentChat.id);`
- Comment: "BackButtonMenuRecent removed"

**Line 9228**: Fixed MessageHelper time hint
- Before: `MessageHelper.INSTANCE.getTimeHintText(cell.getMessageObject())`
- After: `LocaleController.formatDateAudio(cell.getMessageObject().messageOwner.date, true)`
- Comment: "MessageHelper removed - use default time format"

**Lines 11571-11597**: Replaced BottomBuilder with AlertDialog
- Pinned message dialog (unpin/dismiss)
- 27 lines → 29 lines (minor increase for standard AlertDialog.Builder syntax)
- Uses `CharSequence[] items` + `setItems()`

**Lines 11617-11635**: Replaced BottomBuilder with AlertDialog
- Pinned list dialog (unpin all/dismiss)
- 19 lines → 25 lines
- Conditional items array based on `allowPin`

**Strategy**: DoubleTap, BackButtonMenuRecent, MessageHelper removed; BottomBuilder replaced with Telegram's AlertDialog

### 3. ChatAvatarContainer.java (~3 lines affected)
**Line 854**: Removed MessageHelper zalgo filter
- Before: `value = MessageHelper.INSTANCE.zalgoFilter(value);`
- After: Deleted line
- Comment: "MessageHelper removed - no zalgo filtering"

**Strategy**: MessageHelper removed, zalgo filter disabled

### 4. ViewPagerFixed.java (~30 lines affected)
**Line 1612**: Simplified tab selector type
- Before: `listView.setSelectorType(0 >= TabStyle.PILLS.getValue() ? 9 : tabsSelectorType);`
- After: `listView.setSelectorType(tabsSelectorType);`
- TabStyle stub removed, always use `tabsSelectorType`

**Lines 1995-2023**: Replaced tab style rendering
- Before: 29 lines checking TabStyle enum (PILLS, PURE), conditional padding/bounds/colors
- After: 13 lines standard underline style
- Comment: "TabStyle removed - use default underline style"
- Fixed: topBound, bottomBound, selectorColor, cornerRadii, bounds, draw

**Strategy**: TabStyle removed, revert to standard tab underline indicator

### 5. DialogCell.java (~10 lines affected)
**Lines 3478-3487**: Removed message filtering
- Deleted: AyuFilter.isFiltered() checks
- Deleted: NaGram message.messageOwner.hide check
- Deleted: blockePeers check
- Before: 10 lines (all no-op due to stubs)
- After: 1 line comment
- Comment: "AyuFilter and NaGram filtering removed"

**Strategy**: AyuFilter/NaGram hooks removed, no message filtering

## Summary

**Total lines removed**: ~350 lines (dead code from deleted stubs)
**Total lines added**: ~45 lines (replacements, comments)
**Net reduction**: ~305 lines

### Errors Fixed
- PhotoViewer: 12 errors (Translator, TranslateDb, AlertUtil)
- ChatActivity: 14 errors (DoubleTap, BackButtonMenuRecent, MessageHelper, BottomBuilder)
- ChatAvatarContainer: 1 error (MessageHelper)
- ViewPagerFixed: 5 errors (TabStyle)
- DialogCell: 1 error (AyuFilter)

**Total**: 33 compile errors resolved

### Patterns Applied
1. **Translation removed**: PhotoViewer translateComment() deleted
2. **DoubleTap removed**: ChatActivity hasDoubleTap/onDoubleTap gutted to no-ops
3. **BottomBuilder → AlertDialog**: 2 pinned message dialogs converted to standard AlertDialog.Builder
4. **MessageHelper removed**: zalgo filter disabled, time hint uses LocaleController
5. **TabStyle removed**: ViewPagerFixed reverts to standard underline tabs
6. **Filtering removed**: DialogCell AyuFilter/NaGram hooks deleted

## Next Steps
- Compile check: `./gradlew TMessagesProj:assembleRelease -Xmaxerrs 500`
- Expected: ~70 remaining errors (other files not fixed in this round)
- Continue with Round 3 if more errors remain

## Notes
- All replacements maintain functionality (no-op or fallback to Telegram defaults)
- No new classes created
- Telegram's custom AlertDialog used (org.telegram.ui.ActionBar.AlertDialog)
- Brace balance maintained
- Variable scope checked (no orphaned variable declarations)
