package tw.nekomimi.nekogram.utils;
import org.telegram.messenger.MessageObject;
public class TelegramUtil {
    public static boolean messageObjectIsFile(int type, MessageObject m) { return false; }
    public static boolean messageObjectIsDownloading(int msgId) { return false; }
    public static String getFileNameWithoutEx(String name) { return name; }
}
