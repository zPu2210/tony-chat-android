package xyz.nextalone.nagram.helper;
import android.content.Context;
import org.telegram.messenger.MessageObject;
import org.telegram.tgnet.TLRPC;
import android.text.Editable;
import java.util.ArrayList;
public class MessageHelper {
    public static MessageHelper INSTANCE = new MessageHelper();
    public static MessageHelper getInstance(int a) { return INSTANCE; }
    public void saveStickerToGallery(Context activity, MessageObject msg) {}
    public void saveStickerToGallery(Context ctx, TLRPC.Document doc) {}
    public String getPathToMessage(MessageObject m) { return ""; }
    public void processForwardFromMyName(ArrayList<MessageObject> m, long d, boolean s, int sm) {}
    public String getMessageForRepeat(MessageObject m, Object group) { return null; }
    public MessageObject getLastMessageFromUnblock(long id) { return null; }
    public void addFileToClipboard(Object f, Runnable r) {}
    public void showForwardDate(MessageObject m, Object cb) {}
    public static String showForwardDate(MessageObject m, String s) { return s; }
    public String getTimeHintText(MessageObject m) { return ""; }
    public String getUriToMessage(MessageObject m) { return ""; }
    public static CharSequence zalgoFilter(CharSequence text) { return text; }
    public static String zalgoFilter(String text) { return text; }
    public static boolean canSendAsDice(String text, Object chat, long id) { return false; }
    public static boolean containsMarkdown(Editable text) { return false; }
    public MessageObject blurify(MessageObject m) { return m; }
    public void addMessageToClipboard(MessageObject m, Runnable cb) {}
    public void addMessageToClipboardAsSticker(MessageObject m, Runnable cb) {}
    public void addStickerToClipboard(TLRPC.Document doc, Runnable cb) {}
    public void addStickerToClipboardAsPNG(TLRPC.Document doc, Runnable cb) {}
    public boolean getCombineMessage() { return false; }
    public boolean getForceCopy() { return false; }
    public boolean messageObjectIsFile(int account, MessageObject m) { return false; }
    public boolean messageObjectIsDownloading(int msgId) { return false; }
    public String getMessagePlainText(MessageObject m) { return ""; }
    public void resetMessageContent(long dialogId, ArrayList<MessageObject> messages) {}
    public void resetMessageContent(long dialogId, MessageObject message) {}
    public int getRegexFiltersEnabled() { return 0; }
    public String getTextOrBase64(byte[] data) { return ""; }
    public void createDeleteHistoryAlert(Object activity, Object chat, Object topic, long id, Object theme) {}
}
