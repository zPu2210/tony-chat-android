package tw.nekomimi.nekogram.transtale;
import org.telegram.messenger.MessageObject;
public class DialogConfig {
    public static boolean isAutoTranslateEnable(long dialogId, int topicId) { return false; }
    public static boolean isAutoTranslateEnable(long dialogId) { return false; }
    public static boolean isCustomForumTabsEnable(long dialogId) { return false; }
    public static boolean hasCustomForumTabsConfig(long dialogId) { return false; }
    public boolean isMessageObjectAutoTranslatable(MessageObject m) { return false; }
}
