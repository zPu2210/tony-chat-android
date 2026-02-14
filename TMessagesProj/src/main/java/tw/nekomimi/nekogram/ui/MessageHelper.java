package tw.nekomimi.nekogram.ui;
import android.app.Activity;
import android.content.Context;
import org.telegram.messenger.MessageObject;
import org.telegram.tgnet.TLRPC;
import java.util.ArrayList;
public class MessageHelper {
    public static MessageHelper getInstance(int a) { return new MessageHelper(); }
    public void saveStickerToGallery(Activity a, TLRPC.Document d) {}
    public void saveStickerToGallery(android.app.Activity a, org.telegram.messenger.MessageObject m) {}
    public void saveStickerToGallery(Context c, TLRPC.Document d) {}
    public void saveStickerToGallery(Object ctx, Runnable r) {}
    public String getPathToMessage(MessageObject m) { return ""; }
    public void processForwardFromMyName(ArrayList<MessageObject> m, long d, boolean s, int sm) {}
    public String getMessageForRepeat(MessageObject m, Object group) { return null; }
    public MessageObject getLastMessageFromUnblock(long id) { return null; }
    public void addFileToClipboard(Object f, Runnable r) {}
    public void resetMessageContent(long dialogId, ArrayList<MessageObject> messages) {}
    public void resetMessageContent(long dialogId, MessageObject message) {}
    public void createDeleteHistoryAlert(Object activity, Object chat, Object topic, long mergeDialogId, Object themeDelegate) {}
    public String getTextOrBase64(byte[] data) { return ""; }
    public void addStickerToClipboard(TLRPC.Document doc, Runnable cb) {}
    public void addStickerToClipboardAsPNG(TLRPC.Document doc, Runnable cb) {}
    public void addMessageToClipboard(MessageObject m, Runnable cb) {}
    public void addMessageToClipboardAsSticker(MessageObject m, Runnable cb) {}
    public static boolean isMessageObjectAutoTranslatable(MessageObject m) { return false; }
    public String getMessagePlainText(MessageObject m) { return ""; }
}
