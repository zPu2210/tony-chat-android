# Phase 5C Implementation Report: Stories Removal

## Executed Phase
- Phase: Phase 5C - Surgical Stories Removal
- Plan: /Users/pu/Documents/Playground/Tony Chat/android/plans/260213-0952-tony-chat-bootstrap/
- Status: Partial (in progress, ~90% complete)

## Files Modified/Created

### Deleted
- **101 Stories files** (~86K lines) removed from `TMessagesProj/src/main/java/org/telegram/ui/Stories/`
- Entire `recorder/` subdirectory (70+ files)
- Entire `bots/` subdirectory
- All major Stories UI classes (StoryViewer, PeerStoriesView, StoryRecorder, etc.)

### Gutted to Stubs
- **StoriesController.java**: 5,160 lines → 130 lines (no-op singleton with public API intact)

### Stub Files Created (35+ files)
**Core Stubs:**
- StoriesController.java (gutted, 130 lines)
- StoryViewer.java (implements AttachedSheet)
- StoriesUtilities.java
- StoriesListPlaceProvider.java
- DialogStoriesCell.java
- DarkThemeResourceProvider.java (implements ResourcesProvider)

**recorder/ Stubs:**
- StoryEntry.java (with HDRInfo nested class)
- StoryRecorder.java
- ButtonWithCounterView.java
- HintView2.java
- KeyboardNotifier.java
- PreviewView.java
- CaptionContainerView.java (with PeriodDrawable)
- StoryPrivacyBottomSheet.java (with StoryPrivacy)
- StoryUploadingService.java
- DownloadButton.java (with PreparingVideoToast)
- FlashViews.java (with Drawable, ImageViewInvertable)
- AlbumButton.java
- GallerySheet.java
- SliderView.java
- TimelineView.java
- Weather.java
- DualCameraView.java
- EmojiBottomSheet.java
- DominantColors.java
- CollageLayout.java

**Other Stubs:**
- bots/BotPreviewsEditContainer.java
- HighlightMessageSheet.java
- ChannelBoostUtilities.java
- PeerStoriesView.java
- ProfileStoriesView.java
- PublicStoriesList.java
- StealthModeAlert.java
- StoriesGradientTools.java
- MessageMediaStoryFull.java
- MessageMediaStoryFull_old.java
- LivePlayer.java
- LiveStoryPipOverlay.java
- StoriesStorage.java
- RoundRectOutlineProvider.java
- UserListPoller.java
- StoryMediaAreasView.java
- StoryReactionWidgetView.java
- StoryReactionWidgetBackground.java
- UploadingDotsSpannable.java
- StoryWidgetsImageDecorator.java
- ViewsForPeerStoriesRequester.java

## Tests Status
- Type check: **IN PROGRESS** (~100 errors remaining, down from initial ~1400+)
- Unit tests: Not run yet
- Integration tests: Not run yet

## Progress Summary
- **Deleted**: 101 files, ~86,000 lines of Stories code
- **Created**: 47 stub files, 885 lines total
- **Code reduction**: **99%** (86K → 885 lines)
- **Errors**: Reduced from ~1400+ to ~100 (93% error reduction)
- **Build time**: ~54 seconds for Java compilation

## Issues Encountered

### Remaining Errors (~100)
Most common issues:
1. **StoryViewer constructors**: Multiple constructor signatures needed
2. **Cannot find symbol**: Missing methods/fields in stub classes
3. **Method overrides**: Some stubs need @Override annotations removed
4. **Type incompatibilities**: Cast issues (e.g., ViewGroup → StoryMediaAreasView)

### Files with Most Errors
- ChatActivityEnterView.java (14 errors)
- BaseFragment.java (12 errors)
- PhotoViewer.java (10 errors)
- ShareAlert.java (6 errors)
- SharedMediaLayout.java (6 errors)

## Next Steps
1. Fix StoryViewer constructor overloads
2. Add missing methods to stubs (based on compilation errors)
3. Remove incorrect @Override annotations
4. Fix type compatibility issues
5. Verify build reaches 0 errors
6. Run tests

## Strategy Used
- **Surgical approach**: Delete massive files, keep shell classes as stubs
- **Preserve APIs**: Maintained public interfaces to prevent breaking 24+ importers
- **No-op pattern**: All stub methods return null/false/empty collections
- **Minimal viable stubs**: Only add methods/constructors as compilation demands

## Unresolved Questions
- Should we remove TL_stories TLRPC types entirely or keep them for server compatibility?
- Are there UI entry points (menu items, buttons) that still try to launch Stories?
- Should we add TonyConfig flag check in StoriesController methods or keep pure no-ops?
