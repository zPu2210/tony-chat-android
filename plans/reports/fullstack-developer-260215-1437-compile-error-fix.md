# Compile Error Fix Report

**Date:** 2026-02-15 14:37
**Task:** Fix compile errors after NekoX/Nagram stub deletion
**Initial Errors:** 90
**Current Errors:** 55
**Progress:** 35 errors fixed (39% reduction)

## Fixed Errors (35)

### 1. String Resource Errors (3)
- `ChatActivity.java:32819, 32822, 40217` - Fixed `R.string.UnsupportedFileType` → `R.string.UnsupportedAttachment`

### 2. Lambda Signature Error (1)
- `AlertsCreator.java:5338` - Fixed lambda from `(i, text, cell)` → `(dialogInterface, i)` for BottomSheet.Builder.setItems

### 3. Dead Code Removal (1)
- `ChatRightsEditActivity.java:1377-1381` - Removed `if (!false)` block with HapticFeedbackConstants

### 4. Missing Imports (3)
- `MessagesController.java` - Added `import android.widget.Toast;`
- `DownloadController.java` - Added `import android.text.TextUtils;`
- `FilteredSearchView.java` - Added `import android.widget.Toast;` and `import org.telegram.ui.ActionBar.AlertDialog;`

### 5. SaveToDownloadReceiver Removal (12 locations)
- `MediaController.java:4975, 4996, 5131, 5170, 5185, 5212, 5236, 5278, 5289, 5351, 5366, 5397` - Removed all references to deleted SaveToDownloadReceiver class

### 6. Drawable Resource Fix (3)
- `FilterTabsView.java:454, 784, 785` - Changed `R.drawable.msg_folders_chat` → `R.drawable.msg_folders`

### 7. Toast makeText Fix (2)
- `LaunchActivity.java:4278` - Fixed `Toast.makeText(ctx, error, ...)` → `.error.text`
- `LoginActivity.java:10655` - Fixed `Toast.makeText(ctx, error, ...)` → `.error.text`

### 8. BottomBuilder Replacement (1)
- `FilteredSearchView.java:1162-1182` - Replaced BottomBuilder with AlertDialog.Builder

### 9. NekoX Activity Removal (2)
- `LaunchActivity.java:665` - Removed NekoGhostModeActivity reference
- `LaunchActivity.java:3632` - Removed NekoSettingsActivity reference

## Remaining Errors (55)

### Category Breakdown

#### **Emoji Helper (4 errors)** - EASY
- `Emoji.java:92, 93, 151, 155` - EmojiHelper class deleted
- **Fix:** Remove or stub out custom emoji font loading

#### **MessagesStorage String Constructor (4 errors)** - MEDIUM
- `MessagesStorage.java:17201, 17247, 17325, 17437` - Invalid String constructor
- **Fix:** Check what these are constructing (likely should be String.valueOf())

#### **BottomSheet Package Error (2 errors)** - EASY
- `SharedMediaLayout.java:7505` - BottomSheet.NekoXBuilder reference
- `ImageUpdater.java:270` - BottomSheet.NekoXBuilder reference
- **Fix:** Replace with standard BottomSheet.Builder or AlertDialog.Builder

#### **File Type Conversion (2 errors)** - EASY
- `SharedConfig.java:1729` - String → File conversion
- `DocumentSelectActivity.java:1343` - String → File conversion
- **Fix:** Wrap with `new File(...)`

#### **Deleted Helper Methods (8+ errors)** - MEDIUM
- `CacheControlActivity.java:1138, 1146` - deleteRecursive method
- `ChatObject.java:2434, 2443, 2454, 2463` - Unknown deleted methods
- `UserObject.java:195, 197, 205, 207, 217, 219, 227, 229` - Unknown deleted methods
- **Fix:** Need to read code to understand what was deleted

#### **Other Symbol Errors (35 errors)** - VARIES
- Various "cannot find symbol" and null dereference errors across multiple files
- **Fix:** Case-by-case analysis required

## Files Requiring Attention (Priority Order)

### HIGH Priority (Quick wins, ~15 errors)
1. `Emoji.java` (4 errors) - Remove EmojiHelper calls
2. `MessagesStorage.java` (4 errors) - Fix String constructors
3. `SharedMediaLayout.java` (2 errors) - Fix BottomSheet refs
4. `ImageUpdater.java` (2 errors) - Fix BottomSheet refs
5. `SharedConfig.java` / `DocumentSelectActivity.java` (2 errors) - Add `new File(...)`

### MEDIUM Priority (~20 errors)
6. `CacheControlActivity.java` (2 errors) - Implement or stub deleteRecursive
7. `ChatObject.java` (4 errors) - Analyze deleted method calls
8. `UserObject.java` (8 errors) - Analyze deleted method calls
9. `ApplicationLoader.java` (2 errors) - Unknown symbols

### LOW Priority (One-offs, ~20 errors)
- Various single-error files that need individual analysis

## Next Steps

1. **Quick Fix Round:** Tackle HIGH priority files (15 errors) - estimated 10-15 minutes
2. **Analysis Round:** Read MEDIUM priority files to understand what was deleted
3. **Cleanup Round:** Fix remaining one-off errors
4. **Verify:** Full compile to 0 errors

## Build Command
```bash
cd "/Users/pu/Documents/Playground/Tony Chat/android" && \
ANDROID_HOME=~/Library/Android/sdk \
JAVA_HOME=/Library/Java/JavaVirtualMachines/temurin-17.jdk/Contents/Home \
./gradlew TMessagesProj:compileReleaseJavaWithJavac
```

## Notes
- No structural issues - all errors are from deleted stub references
- No brace balance issues detected
- No syntax errors - only missing symbols and type mismatches
- Pattern: Most errors are from NekoX/Nagram helper classes (EmojiHelper, BottomBuilder, etc.)
