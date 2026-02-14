package tw.nekomimi.nekogram;
import org.telegram.messenger.BuildVars;
public class NekoXConfig {
    public static boolean disableFlagSecure = false;
    public static int TITLE_TYPE_TEXT = 0;
    public static int TITLE_TYPE_ICON = 1;
    public static int TITLE_TYPE_MIX = 2;
    public static String FAQ_URL = "";
    public static String wikiUrl = "";
    public static int autoUpdateReleaseChannel = 0;
    public static long[] developers = {};
    public static long[] officialChats = {};
    public static boolean isDeveloper() { return false; }
    public static String getChannelAlias(long chatId) { return null; }
    public static String formatLang(String lang) { return lang; }
    public static String getSystemEmojiTypeface() { return null; }
    public static int getNotificationColor() { return 0; }
    public static String getTipsUrl() { return ""; }
    public static int currentAppId() { return BuildVars.APP_ID; }
    public static String currentAppHash() { return BuildVars.APP_HASH; }
    public static int getAppId() { return BuildVars.APP_ID; }
    public static String getTextOrBase64(byte[] data) { return ""; }
}
