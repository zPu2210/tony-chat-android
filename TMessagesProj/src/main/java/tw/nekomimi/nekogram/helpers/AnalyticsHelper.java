package tw.nekomimi.nekogram.helpers;

import android.app.Application;

public class AnalyticsHelper {
    public static String DSN = "";
    public static boolean loaded = false;

    public static void start(Application application) {
    }

    public static void captureException(Throwable e) {
    }

    public static boolean getSentryStatus(Application application) {
        return false;
    }
}
