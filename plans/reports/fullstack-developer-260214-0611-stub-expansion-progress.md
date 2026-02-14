# Phase Implementation Report - Stub Expansion Progress

## Executed Phase
- Phase: Story stub expansion for compilation
- Status: Partial (496 errors remaining, down from 568)
- Work context: /Users/pu/Documents/Playground/Tony Chat/android

## Files Modified

### Story Stubs Expanded (10 files)
1. `DialogStoriesCell.java` - Added 65 lines: HEIGHT_IN_DP, TYPE constants, methods for UI lifecycle
2. `StoriesController.java` - Added 7 methods: hasOnlySelfStories, canPostStories, getStealthMode, etc.
3. `UserListPoller.java` - Added getInstance, checkList methods
4. `HintView2.java` - Added setBgColor method
5. `StoryRecorder.java` - Added cameraBtnSpan, openBot, made isVisible static
6. `StoryEntry.java` - Added calculateInSampleSize (bitmap sampling utility)
7. `LivePlayer.java` - Added static recording field, account/dialog/story fields, getWatchersCount
8. `StealthModeAlert.java` - Added TYPE constant, constructor overload, StealthModeListener interface
9. `StoriesListPlaceProvider.java` - Added static of() factory methods
10. `BotPreviewsEditContainer.java` - Added 5 methods: checkPinchToZoom, getItemsCount, getCurrentLang, isSelectedAll

### Story Stubs Created (1 file)
1. `StoryCell.java` - Basic View stub

### Code Commented Out (3 files)
1. `DialogsAdapter.java` - Disabled getOrCreateStoryViewer calls (2 locations)
2. `ChatActivity.java` - Disabled getOrCreateStoryViewer.open call (line 40828)
3. `DialogsActivity.java` - Disabled doOnAnimationReady call (line 5567)

### Bug Fixes
1. `HighlightMessageSheet.java` - Fixed parseTiersString/parseTiers/tiersToString/tiersEqual return types (Object → int[])

## Tasks Completed
- ✅ Expanded DialogStoriesCell with 15+ methods
- ✅ Fixed tier parsing type mismatches
- ✅ Added StealthMode listener interface
- ✅ Made StoryRecorder.isVisible() static
- ✅ Added SearchStoriesList constructors
- ✅ Created BotPreviewsEditContainer methods
- ⚠️ Commented out getOrCreateStoryViewer calls (3 locations)

## Tests Status
- Type check: Not run (compilation still failing)
- Unit tests: Not run
- Build status: FAILED - 496 errors (down from 568)

## Error Distribution
- SharedMediaLayout.java: 72 errors (story viewer, album editing, bot previews)
- DialogsActivity.java: 22 errors (story UI interactions)
- HashtagsSearchAdapter.java: 5 errors
- FragmentContextView.java: 1 error

## Root Causes of Remaining Errors

### 1. SharedMediaLayout Story Features (72 errors)
**Lines 464-469**: getOrCreateStoryViewer() method missing from ProfileActivity
**Lines 1822-1836**: botPreviewsContainer method calls (getItemsCount, isSelectedAll, getCurrentLang)
**Lines 9637-9850**: SearchStoriesList API mismatch (constructors, methods)

### 2. DialogsActivity Story UI (22 errors)
**Lines 4552-4760**: HintView shown() method, StoryRecorder interaction
**Lines 5179-5200**: StoryCell instanceof checks, StealthModeAlert callbacks
**Lines 13829**: getHiddenList() usage

### 3. Missing Stub Methods
- `profileActivity.getOrCreateStoryViewer()`
- `HintView2.shown()`
- `SearchStoriesList.load()`, `getMessageObjects()`, other instance methods
- `StoriesController.BotPreviewsList` class

## Systematic Fix Strategy

### Immediate Actions Required
1. **Comment out story viewer code** in SharedMediaLayout (lines 464-469, 9637-9850)
2. **Expand SearchStoriesList** with load(), reset(), getters
3. **Add HintView2.shown()** method
4. **Create BotPreviewsList** stub class in StoriesController
5. **Fix HashtagsSearchAdapter** errors (5 total)

### Estimated Remaining Work
- 15-20 more stub method additions
- 5-10 code comment-outs
- 1-2 new stub classes

## Issues Encountered
- **Deep story integration**: getOrCreateStoryViewer() used in 6+ locations across DialogsActivity, SharedMediaLayout, DialogsAdapter
- **Type mismatches**: Object return types need specific TL types (TL_storiesStealthMode, etc.)
- **Interface complexity**: StealthModeListener, SearchStoriesList need full API stubs
- **Bot preview coupling**: BotPreviewsEditContainer tightly coupled to story recorder

## Next Steps
1. Systematically comment out all getOrCreateStoryViewer() calls
2. Expand SearchStoriesList with full API surface
3. Add remaining DialogStoriesCell callbacks
4. Create BotPreviewsList stub
5. Run build, iterate until 0 errors
6. Run type check + tests

## Unresolved Questions
- Should getOrCreateStoryViewer() be stubbed or all calls commented out?
- Are SearchStoriesList features needed for any core messaging?
- Can bot preview features be fully disabled via TonyConfig?
