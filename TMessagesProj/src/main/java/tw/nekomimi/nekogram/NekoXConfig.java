package tw.nekomimi.nekogram;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import org.telegram.messenger.ApplicationLoader;

public class NekoXConfig {

    public static String FAQ_URL = "https://github.com/NextAlone/Nagram#faq";
    public static long[] officialChats = {1500637449, 1645699549, 2001739482};
    public static long[] developers = {896711046, 380570774, 784901712, 457896977, 782954985, 5412523572L, 676660002, 1068402676, 6244360706L};

    public static final int TITLE_TYPE_TEXT = 0;
    public static final int TITLE_TYPE_ICON = 1;
    public static final int TITLE_TYPE_MIX = 2;

    public static boolean loadSystemEmojiFailed = false;

    public static SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("nekox_config", Context.MODE_PRIVATE);

    public static boolean disableFlagSecure = false;

    public static int autoUpdateReleaseChannel = 2;
    public static int customApi = 0;
    public static int customAppId = 0;
    public static String customAppHash = "";

    public static int currentAppId() {
        return 0;
    }

    public static String currentAppHash() {
        return "";
    }

    public static void saveCustomApi() {}

    public static void setAutoUpdateReleaseChannel(int channel) {
        autoUpdateReleaseChannel = channel;
    }

    public static boolean isDeveloper() {
        return false;
    }

    public static String getOpenPGPAppName() {
        return "None";
    }

    public static String formatLang(String name) {
        return name != null && !name.isEmpty() ? name : "Default";
    }

    public static Typeface getSystemEmojiTypeface() {
        return null;
    }

    public static int getNotificationColor() {
        return 0;
    }

    public static void setChannelAlias(long channelID, String name) {}

    public static void emptyChannelAlias(long channelID) {}

    public static String getChannelAlias(long channelID) {
        return null;
    }
}
