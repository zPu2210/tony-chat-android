# Compile Error Fixes Report

## Executed Phase
- Task: Fix compile errors in PhotoViewer, ChatAttachAlert, ChatRightsEditActivity
- Work context: /Users/pu/Documents/Playground/Tony Chat/android/
- Status: completed

## Files Modified

### 1. PhotoViewer.java (lines 1280-1354)
- **Issue**: BottomBuilder stub removed, replaced with AlertDialog.Builder
- **Root cause**: Code used BottomBuilder methods (addTitle, addItems, setOnPreDismissListener) + BottomSheet-specific styling methods
- **Fix**: Rewrote using standard AlertDialog.Builder API
  - Changed `addTitle()` → `setTitle()`
  - Changed `addItems()` signature to match AlertDialog
  - Removed lambda return `Unit.INSTANCE` (not needed for AlertDialog)
  - Removed BottomSheet variable + styling calls (setItemColor, setBackgroundColor, setTitleColor, setCalcMandatoryInsets, setNavigationBarColor, setLightNavigationBar, scrollNavBar)
  - Simplified haptic feedback (removed `if (!false)` dead code)
- **Lines changed**: ~94 → 76 lines (-18 lines)

### 2. ChatAttachAlert.java (lines 3516-3866)
- **Issue**: Translation feature references (Translator.Companion, TranslateDb, TranslatorKt)
- **Fix**: Removed entire translation block
  - Deleted translate menu option (lines 3518-3540)
  - Deleted `translateComment()` method (lines 3818-3866, ~48 lines)
- **Lines removed**: ~54 lines

### 3. ChatRightsEditActivity.java (line 1377)
- **Issue**: VibrateUtil.vibrate() - stub removed
- **Fix**: Replaced with standard Android HapticFeedbackConstants
  - Changed `VibrateUtil.vibrate()` → `listView.performHapticFeedback(LONG_PRESS, FLAG_IGNORE_GLOBAL_SETTING)`
  - Wrapped in try-catch for safety
- **Lines changed**: 1 → 3 lines (+2 lines)

## Tasks Completed
- [x] PhotoViewer.java: BottomBuilder → AlertDialog conversion
- [x] ChatAttachAlert.java: Translation feature removal
- [x] ChatRightsEditActivity.java: VibrateUtil replacement

## Tests Status
- Type check: **not run** (per task instructions)
- Compile: **not run** (per task instructions)
- All syntax verified via code review

## Issues Encountered
None. All fixes straightforward:
- PhotoViewer: Standard dialog API replacement
- ChatAttachAlert: Complete feature removal
- ChatRightsEditActivity: Vibration API swap

## Next Steps
- Ready for compilation test
- No additional NekoX/Nagram references in these files

## Summary
Fixed 3 files, removed ~72 lines total. All NekoX dependencies eliminated.
- PhotoViewer: custom BottomBuilder → AlertDialog
- ChatAttachAlert: translation feature purged
- ChatRightsEditActivity: VibrateUtil → HapticFeedbackConstants
