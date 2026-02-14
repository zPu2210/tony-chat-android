# Phase Implementation Report: Stories Stub Compilation Fixes

## Executed Phase
- Phase: Stories Removal Compilation Fixes
- Status: **PARTIAL** (100 → 100 errors remaining)
- Work: Fix Stories stub classes to satisfy all caller requirements

## Files Modified

### Stories Stub Files Enhanced
1. **StoryViewer.java** - Added missing methods/fields:
   - `fromBottomSheet` field
   - `onResume()`, `onPause()`, `updatePlayingMode()`, `listenToAttachedSheet()`, `isShowingImage()`
   - `HolderDrawAbove` inner class

2. **StoriesListPlaceProvider.java** - Added:
   - `AvatarOverlaysView` interface with `setStoryParams()`

3. **StoriesUtilities.java** - Added:
   - `createExpiredStoryString()`, `createReplyStoryString()`, `getExpiredStoryDrawable()`
   - `setImage()`, `setStoryMiniImage()`
   - `STATE_EMPTY`, `STATE_HAS_UNREAD`, `STATE_READ` constants
   - Enhanced `AvatarStoryParams` with `allowLongress`, `drawnLive`, `currentState`, constructor, helper methods

4. **StoryMediaAreasView.java** - Added:
   - `hasSelected()`, `hasAreaAboveAt()` methods
   - `AreaView` inner class

5. **StoryReactionWidgetView.java** - Added:
   - `mediaArea` field
   - `getAnimatedEmojiDrawable()` method

6. **HighlightMessageSheet.java** - Added:
   - `parseTiers()`, `parseTiersString()`, `tiersEqual()` static methods

7. **StoryRecorder.java** - Added:
   - `Touchable` interface with `additionalTouchEvent()`

8. **StoryPrivacyBottomSheet.java** - Added:
   - `UserCell` inner class with constructor, `set()`, `setChecked()`

9. **HintView2.java** - Added:
   - Direction constants (TOP, BOTTOM, LEFT, RIGHT)
   - Constructor with direction parameter
   - `getText()`, `getTextPaint()`, `setMultilineText()`, `setMaxWidthPx()`, `setIcon()`, `setRounding()`
   - `cutInFancyHalf()` static method

10. **StoryEntry.java** - Added:
    - `makeCacheFile()` static method

11. **KeyboardNotifier.java** - Added:
    - `Callback` interface
    - Constructor overload with Callback parameter

12. **CaptionContainerView.java** - Major enhancements:
    - Changed `editText` to `EditTextCaption` type with `isPopupView()` method
    - Added `mentionContainer`, `keyboardNotifier` initialization in constructor
    - Constructor overload for PhotoViewer usage
    - Fields: `currentAccount`, `toKeyboardShow`, `bounds`, `backgroundBlur`, `backgroundPaint`, `moveButtonText`
    - Methods: `setAccount()`, `isAtTop()`, `updateMentionContainer()`, `applyService()`, `updateKeyboard()`, `customBlur()`, `drawBlur()`
    - `PeriodDrawable` - Added no-arg constructor, fields (`diameterDp`, `textOffsetX`, `textOffsetY`, `strokePaint`), methods (`draw(Canvas, float)`, `updateColors()`, `setTextSize()`, `setValue()`, `setClear()`, `setCenterXY()`)
    - `EditTextCaption` inner class with `isPopupView()`

13. **DominantColors.java** - Added:
    - `getColorsSync()` static method returning int array

14. **ButtonWithCounterView.java** - Added:
    - `setSubText()` method
    - `subTextSplitToWords()` protected method

15. **DarkThemeResourceProvider.java** - Added:
    - `sparseIntArray` field
    - `appendColors()` method stub

16. **MessageMediaStoryFull.java** - Changed to extend TLRPC.MessageMedia:
    - Added no-arg constructor for TLRPC usage
    - Kept original constructor for UI usage

17. **MessageMediaStoryFull_old.java** - Changed to extend TLRPC.MessageMedia:
    - Added no-arg constructor for TLRPC usage
    - Kept original constructor for UI usage

18. **StoriesController.java** - Added:
    - `getTotalStoriesCount(boolean)` method
    - `BotPreview` inner class stub

## Remaining Errors: 100

### Error Distribution (Latest)
- PhotoViewer.java: 42 errors
- CaptionPhotoViewer.java: 26 errors
- DialogCell.java: 16 errors
- ChatActivityEnterView.java: 9 errors
- UniversalAdapter.java: 6 errors
- EmojiAnimationsOverlay.java: 1 error

### Fixed (Round 2)
- ✅ AndroidUtilities.java: Commented out broken story media area check
- ✅ MessageObject.java: Made BotPreview extend TL_stories.StoryItem
- ✅ CaptionContainerView.java: Added RectF import
- ✅ DialogCell.java: Implemented setStoryParams() from AvatarOverlaysView interface
- ✅ StoryReactionWidgetView: Changed getAnimatedEmojiDrawable() to return AnimatedEmojiDrawable, added MediaArea class with reaction field
- ✅ EmojiAnimationsOverlay: 1 error remaining (down from 2)

### Root Causes
1. **CaptionContainerView** still missing fields/methods that PhotoViewer/CaptionPhotoViewer expect
2. **DialogCell** missing `setStoryParams()` implementation (from AvatarOverlaysView interface)
3. **PhotoViewer** needs more CaptionContainerView API
4. Many override errors where base class methods don't exist in stubs

## Next Steps

### Immediate Fixes Required
1. Add `setStoryParams()` to DialogCell (implement AvatarOverlaysView)
2. Expand Caption ContainerView with all missing fields/methods PhotoViewer expects
3. Add missing override methods to base stub classes
4. Fix incompatible type errors (AnimatedImageDrawable → AnimatedEmojiDrawable conversions)
5. Comment out dead story code in caller files if stub expansion becomes impractical

### Alternative Strategy
If stub expansion continues to be whack-a-mole:
- Comment out story-related code blocks in PhotoViewer, CaptionPhotoViewer, DialogCell
- Mark as technical debt for Phase 5D (full cleanup)
- Priority: Get to BUILD SUCCESSFUL, even if some story features are non-functional stubs

## Strategy Analysis

### What Worked
- Adding missing methods/fields to stubs (StoryViewer, StoriesUtilities, etc.)
- Implementing required interfaces (AvatarOverlaysView.setStoryParams)
- Commenting out broken story code in callers (AndroidUtilities)
- Type fixes (BotPreview extends StoryItem, AnimatedImageDrawable → AnimatedEmojiDrawable)

### What Didn't Work
- Whack-a-mole stub expansion: PhotoViewer/CaptionPhotoViewer keep requiring more CaptionContainerView API
- Errors remain at ~100 despite 18+ stub files enhanced
- Root cause: PhotoViewer/CaptionPhotoViewer/ChatActivityEnterView deeply coupled to story recording/editing UI

### Recommended Next Steps

**Option A: Comment Out Story UI Code (Fastest)**
- Comment out story-related blocks in PhotoViewer (caption editing for stories)
- Comment out CaptionPhotoViewer story-specific features
- Mark as tech debt for Phase 5D cleanup
- **Estimated effort:** 30-60 min to BUILD SUCCESSFUL

**Option B: Complete Stub Expansion (Thorough)**
- Add all missing CaptionContainerView fields/methods PhotoViewer expects
- Risk: May uncover more missing APIs in cascade
- **Estimated effort:** 2-4 hours, uncertain success

**Option C: Hybrid Approach (Recommended)**
- Fix critical/easy errors via stubs (DialogCell, UniversalAdapter, ChatActivityEnterView)
- Comment out PhotoViewer story caption editing code
- Get to BUILD SUCCESSFUL, document removed features
- **Estimated effort:** 1-2 hours

## Notes
- Token budget: 87343/200000 used (43.7%)
- 18+ stub files enhanced across 2 rounds
- Errors stuck at ~100 due to deep PhotoViewer/CaptionPhotoViewer/story UI coupling
- PhotoViewer's story-related caption editing likely non-essential to core messaging
