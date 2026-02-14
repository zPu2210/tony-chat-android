package tw.nekomimi.nekogram.ui;

import android.content.Context;
import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.BaseController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.TLRPC;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import java.util.ArrayList;

/**
 * Stub: Message helper utilities
 */
public class MessageHelper extends BaseController {
    public MessageHelper(int num) {
        super(num);
    }

    public static MessageHelper getInstance(int num) {
        return new MessageHelper(num);
    }

    public void resetMessageContent(long dialog_id, MessageObject messageObject) {}
    public void resetMessageContent(long dialog_id, ArrayList<MessageObject> messageObjects) {}

    public void processForwardFromMyName(ArrayList<MessageObject> messages, long did, boolean notify, int scheduleDate) {}

    public void deleteUserChannelHistoryWithSearch(Context ctx, final long dialog_id, final TLRPC.User user) {}
    public void deleteUserChannelHistoryWithSearch(AlertDialog progress, final long dialog_id, final TLRPC.User user, final int offset_id, int index) {}

    public void deleteChannelHistory(final long dialog_id, TLRPC.Chat chat, final int offset_id) {}
    public void deleteUserChannelHistory(final TLRPC.Chat chat, long userId, int offset) {}

    public MessageObject getLastMessageFromUnblock(long dialogId) {
        return null;
    }

    public void saveStickerToGallery(Context context, MessageObject messageObject) {}
    public void saveStickerToGallery(Context context, TLRPC.Document document) {}

    public void addStickerToClipboard(TLRPC.Document document, Runnable callback) {}
    public void addStickerToClipboardAsPNG(TLRPC.Document document, Runnable callback) {}

    public MessageObject getMessageForRepeat(MessageObject selectedObject, MessageObject.GroupedMessages selectedObjectGroup) {
        return null;
    }

    public void createDeleteHistoryAlert(BaseFragment fragment, TLRPC.Chat chat, TLRPC.TL_forumTopic forumTopic, long mergeDialogId, Theme.ResourcesProvider resourcesProvider) {}

    public static void showDeleteHistoryBulletin(BaseFragment fragment, int count, boolean search, Runnable delayedAction, Theme.ResourcesProvider resourcesProvider) {}

    public static String getTextOrBase64(byte[] data) {
        return "";
    }
}
