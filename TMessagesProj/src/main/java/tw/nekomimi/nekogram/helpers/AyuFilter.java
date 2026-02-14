package tw.nekomimi.nekogram.helpers;
import org.telegram.messenger.MessageObject;
public class AyuFilter {
    public static boolean isFiltered(Object msg) { return false; }
    public static boolean isFiltered(Object msg, Object group) { return false; }
    public static boolean isFiltered(MessageObject msg) { return false; }
    public static boolean isFiltered(MessageObject msg, Object group) { return false; }
    public static CharSequence doRegex(CharSequence text) { return text; }
}
