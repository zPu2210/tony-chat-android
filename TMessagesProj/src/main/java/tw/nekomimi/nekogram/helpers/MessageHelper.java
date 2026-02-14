package tw.nekomimi.nekogram.helpers;

import org.telegram.messenger.BaseController;

public class MessageHelper extends BaseController {
    public MessageHelper(int num) {
        super(num);
    }

    public static String getDCLocation(int dc) {
        return "Unknown";
    }

    public static String getDCName(int dc) {
        return "Unknown";
    }
}
