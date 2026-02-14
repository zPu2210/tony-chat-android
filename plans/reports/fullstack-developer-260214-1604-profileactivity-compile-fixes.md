# ProfileActivity.java Compile Error Fixes

## Summary
Fixed 23 compile errors in ProfileActivity.java by removing NekoX/Nagram feature code blocks.

## Files Modified

### ProfileActivity.java
- Deleted 6 NekoX feature code blocks (~180 lines)
- Removed 11 unused NekoX imports
- Replaced complex blocks with inline comments

### DatacenterActivity.java (stub)
- Added missing `DatacenterActivity(int dc)` constructor

## Errors Fixed

### Type Errors (Lines 2399, 6490)
**Issue**: ProxyUtil.showQrDialog expected wrong lambda signature
**Fix**: Deleted QR code generation blocks

### Cannot Find Symbol (Lines 4601, 4634)
**Issue**: AlertUtil.call() doesn't exist in stub
**Fix**: Deleted entire BottomBuilder phone action blocks (numberRow, phoneRow)

### Cannot Find Symbol (Lines 7837)
**Issue**: DialogTransKt.startTrans() doesn't exist in stub
**Fix**: Deleted BottomBuilder copy/translate action block

### Cannot Find Symbol (Lines 8035, 8037)
**Issue**: NekoXConfig.emptyChannelAlias/setChannelAlias not in stub
**Fix**: Deleted entire setChannelAlias() method (~52 lines)

### Cannot Find Symbol (Lines 12387-12410)
**Issue**: AlertUtil.copyAndAlert/copyLinkAndAlert not in stub
**Fix**: Deleted BottomBuilder ID click actions block (~47 lines)

### Constructor Error (Line 12417)
**Issue**: DatacenterActivity(int) constructor missing
**Fix**: Added constructor to DatacenterActivity stub

### Type Conversion (Line 12997)
**Issue**: ProfileActivity.this not convertible to Context
**Fix**: Deleted 4 NekoX feature methods:
- createAutoTranslateItem()
- createCustomForumTabsItem()
- createShareTargetItem()
- createMessageFilterItem()

### Cannot Find Symbol (Lines 15252-15258 x6)
**Issue**: SettingsHelper.onCreateSearchArray/SettingsSearchResult don't exist
**Fix**: Deleted Nagram settings search integration (~17 lines), returned arr directly

## Imports Removed
- tw.nekomimi.nekogram.helpers.ProfileDateHelper
- tw.nekomimi.nekogram.helpers.SettingsHelper
- tw.nekomimi.nekogram.helpers.SettingsSearchResult
- tw.nekomimi.nekogram.transtale.popupwrapper.AutoTranslatePopupWrapper
- tw.nekomimi.nekogram.transtale.popupwrapper.CustomForumTabsPopupWrapper
- tw.nekomimi.nekogram.transtale.popupwrapper.ShareTargetPopupWrapper
- tw.nekomimi.nekogram.parts.DialogTransKt
- tw.nekomimi.nekogram.utils.AlertUtil
- tw.nekomimi.nekogram.utils.ProxyUtil
- tw.nekomimi.nekogram.ui.BottomBuilder
- tw.nekomimi.nekogram.DatacenterActivity

## Code Blocks Deleted
1. generateLink() - QR code generation
2. numberRow - BottomBuilder phone actions
3. phoneRow - BottomBuilder phone actions
4. channelInfoRow/userInfoRow/locationRow/bioRow - BottomBuilder copy/translate
5. setChannelAlias() - entire method
6. ID click handler - BottomBuilder actions
7. createAutoTranslateItem() - 2 overloads
8. createCustomForumTabsItem()
9. createShareTargetItem()
10. createMessageFilterItem()
11. Nagram settings search integration

## Strategy
- Deleted entire feature blocks, not just error lines
- Preserved surrounding code structure
- Maintained brace balance
- Added inline comments marking removal
- Did NOT build - per instructions

## Next Steps
- Compile to verify all errors resolved
- Check for runtime issues from deleted blocks
- Remove dead code referencing deleted methods
