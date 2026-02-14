# Games Feature Removal Report

**Date:** 2026-02-13
**Agent:** fullstack-developer (a1dcb90)
**Work Context:** /Users/pu/Documents/Playground/Tony Chat/android/
**Status:** ✅ COMPLETED

---

## Executive Summary

Successfully removed Games UI feature from Tony Chat Android. Deleted standalone game drawable, disabled game-opening UI code, preserved protocol layer (TLRPC.TL_game) for backward compatibility.

---

## Files Modified

### Deleted (1 file, 152 lines)
- `TMessagesProj/src/main/java/org/telegram/ui/Components/PlayingGameDrawable.java` ❌

### Modified (4 files)

1. **Theme.java**
   - Removed `PlayingGameDrawable` import (line 121)
   - Disabled statusDrawable slot 3 initialization (line 10578)
   - Added comment: "Game feature removed - slot 3 unused"

2. **ChatAvatarContainer.java**
   - Set `statusDrawables[3]` to `null` (line 346)
   - Added comment: "Game feature removed"

3. **PopupNotificationActivity.java**
   - Removed `PlayingGameDrawable` import (line 71)
   - Set `statusDrawables[3]` to `null` (line 181)
   - Added comment: "Game feature removed"

4. **ChatActivity.java**
   - Gutted `showOpenGameAlert()` method (lines 35210-35238)
   - Replaced with bulletin notification: "Games are not supported in Tony Chat"
   - Method signature preserved for compatibility with `SendMessagesHelper.java` caller

---

## Implementation Details

### Strategy
Games embedded in message protocol (TLRPC.TL_game classes), cannot delete data model. Instead:
1. Delete UI rendering class (PlayingGameDrawable)
2. Null out status drawable array slots
3. Disable game launch UI - show "not supported" message
4. Keep protocol handlers intact (MessageObject.java, TLRPC.java untouched)

### Status Drawable Array
```java
statusDrawables[0] = TypingDotsDrawable
statusDrawables[1] = RecordStatusDrawable
statusDrawables[2] = SendingFileDrawable
statusDrawables[3] = null  // Was PlayingGameDrawable - NOW REMOVED
statusDrawables[4] = RoundStatusDrawable
statusDrawables[5] = ChoosingStickerStatusDrawable
```

### Game Message Handling
- Game messages still parse correctly (protocol intact)
- Display as regular messages (no special "playing game" status)
- Clicking game button shows: "Games are not supported in Tony Chat"
- Existing game score actions (TL_messageActionGameScore) still processed but not rendered specially

---

## Verification

### Build Status
```bash
./gradlew TMessagesProj:compileReleaseJavaWithJavac
✅ BUILD SUCCESSFUL in 1m 31s
```

### Code Search
```bash
grep -r "PlayingGameDrawable" TMessagesProj/src/main/java
✅ No matches found
```

---

## Protocol Layer Preserved

**Untouched Classes** (as per instructions):
- `TLRPC.java` - TL_game, TL_messageMediaGame, TL_keyboardButtonGame
- `MessageObject.java` - game field references
- `SendMessagesHelper.java` - still calls showOpenGameAlert (now displays error)

**Why:** Deleting protocol classes would break message parsing for chats containing game messages. Instead, we:
- Parse game messages correctly
- Don't render special game UI
- Show "not supported" if user tries to launch game

---

## User Impact

### Before
- Game messages showed animated "playing game" status
- Clicking game button opened WebView or browser
- Game scores displayed in chat

### After
- Game messages appear as regular messages
- No special "playing game" status animation
- Clicking game button shows: "Games are not supported in Tony Chat"
- Game score actions process silently (no UI impact)

---

## Remaining Game References

Safe to keep (non-UI):
- `TLRPC.TL_game`, `TL_messageMediaGame`, `TL_keyboardButtonGame` - protocol classes
- `MessageObject.messageOwner.media.game` - data field
- `TL_messageActionGameScore` - score update action
- Theme color keys: `key_chat_previewGameText`, `chat_gamePaint` - unused but harmless
- String resources: `R.string.GameCantSendSecretChat`, `R.string.BotPermissionGameAlert` - unused

These can be removed in Phase 5D (string cleanup) or left as technical debt.

---

## Next Steps

**Phase 5C Progress:**
- ✅ Games removed
- ⏭️ Next: Remove VoIP/Calls feature

**Future Cleanup (Phase 5D):**
- Remove unused game-related string resources
- Remove unused Theme color keys (chat_gamePaint, key_chat_previewGameText)
- Consider removing TLRPC game protocol classes if 100% certain no legacy messages exist

---

## Questions

None - implementation clean, build passes, no regressions expected.
