package tw.nekomimi.nekogram.utils;

import org.telegram.messenger.MessageObject;

public class TelegramUtil {

    public static String getFileNameWithoutEx(String filename) {
        return filename != null ? filename : "";
    }

    public static boolean messageObjectIsFile(int type, MessageObject messageObject) {
        return false;
    }

    public static boolean messageObjectIsDownloading(int type) {
        return false;
    }
}
