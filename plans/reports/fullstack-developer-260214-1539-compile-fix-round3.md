# Compile Error Fixes - Round 3

## Status
**Partial Progress**: 218 → 134 errors (84 fixed, 39% progress)

## Files Modified (18 files)

### Stub Signature Fixes
1. **BottomBuilder.java**
   - Changed ItemClickListener return type: void → Object (for Unit.INSTANCE)
   - Added addRadioItem(), doRadioCheck() methods

2. **ProxyUtil.java**
   - Added tryReadQR(Activity, Bitmap) overload
   - Added showQrDialog(Activity, String) returning Object

3. **Translator.java**
   - Added translate(Locale, String, Companion.TranslateCallBack)
   - Added translate(Locale, Locale, Companion.TranslateCallBack)
   - Added getMessagePlainText(Object) static method

4. **ExternalStickerCacheHelper.java**
   - Added refreshCacheFiles(Object) overload (stickerSet)
   - Added deleteCacheFiles(Object) overload (stickerSet)

5. **StickerSetHelper.java**
   - Added copyStickerSet(String, String, Object, int) - 4 params

6. **PGPUtil.java**
   - Initialized api = new PGPApi()
   - Consolidated executeApiAsync to single InputStream overload
   - Fixed post() to actually run the Runnable

7. **EmojiHelper.java**
   - Added setEmojiPack(String)
   - Fixed EmojiPackBase.getPackName()
   - Fixed EmojiSetBulletinLayout constructor signature

8. **ConnectionsManager.java**
   - Fixed DnsFactory.lookup() call (removed .toArray())

9. **MonetHelper.java**
   - Added getColor(String, boolean) overload

10. **PinnedStickerHelper.java**
    - Added reorderPinnedStickersForSS(ArrayList, boolean)
    - Added sendOrderSyncForSS(ArrayList)

11. **UpdateHelper.java**
    - Added checkNewVersionAvailable(Object) single-param overload

### Core Fixes
- **ProfileActivity.java**: Deleted NekoX auto-update channel switcher (42 lines)
- **ProfileActivity.java**: Deleted NaConfig hidden feature toggle
- **LoginActivity.java**: Deleted entire doCustomApi() method body (114 lines)

## Remaining Errors: 134

### By Category

**1. "Object is not a functional interface" (15 errors)**
- ChatActivityEnterView.java: lines 4744, 4763, 4779, 6235
- ChatActivity.java: lines 13258, 13300, 33867
- PhotoViewer.java: line 7557
- EmojiPacksAlert.java: line 1534
- StickersAlert.java: line 1458
- BlockingUpdateView.java: line 296
- FileRefController.java: line 581

**2. "cannot find symbol" - NekoX helpers (40 errors)**
- LaunchActivity.java: 10 errors (lines 775-7395) - NekoX helper calls
- MediaController.java: 3 errors (lines 4733-4903) - NekoX audio features
- CacheControlActivity.java: 1 error (line 1105)
- ConnectionsManager.java: 2 errors (lines 997, 1301) - DNS/proxy
- MediaDataController.java: 1 error (line 3369)
- DialogsActivity.java: 1 error (line 5346)
- ChatActivity.java: 3 errors (lines 37549-37551) - translation features

**3. Type mismatches (10 errors)**
- ChatActivity.java:14556: CharSequence → String
- ChatActivity.java:33896: String → Uri
- ChatActivity.java:33898: void → boolean
- ChatActivity.java:37547: long → int
- ChatActivity.java:43672: MessageObject → Runnable
- ChatActivityEnterView.java:6365: void → TextCheckCell
- ChatMessageCell.java:17889: 'void' type not allowed
- StickersAlert.java:1478: CharSequence → String

**4. Method signature mismatches (8 errors)**
- AyuFilter.isFiltered() - 4 calls with wrong type
- Bulletin.make() - wrong params
- ChatActivity translate calls - still missing overloads

## Strategy for Next Round

### High Priority (Quick Wins)
1. **Delete NekoX feature blocks** instead of stubbing:
   - LaunchActivity helper calls (10 errors)
   - MediaController audio features (3 errors)
   - ChatActivity translation UI (3 errors)
   
2. **Fix type conversions** (cast at call site):
   - ChatActivity.java:14556: .toString()
   - ChatActivity.java:33896: Uri.parse()
   - ChatActivity.java:43672: wrap in lambda
   
3. **Add missing method returns**:
   - ChatActivityEnterView:6365: return null
   - ChatActivity:33898: return false

### Medium Priority
1. Fix "Object is not a functional interface" by adding proper functional interface stubs
2. Fix AyuFilter.isFiltered signature or delete calls
3. Fix Bulletin.make signature

### Low Priority
1. Remaining translation overload signatures
2. Edge case type conversions

## Next Steps
Continue with high-priority deletions first, then type fixes. Target: <50 errors by end of next round.

## Unresolved Questions
None - path clear for next round.
