package tw.nekomimi.nekogram.helpers;

import android.app.Activity;

public class PasscodeHelper {
    public static boolean checkPasscode(Activity activity, String passcode) {
        return false;
    }

    public static void removePasscodeForAccount(int account) {
    }

    public static boolean isAccountAllowPanic(int account) {
        return false;
    }

    public static boolean isAccountHidden(int account) {
        return false;
    }

    public static void setAccountAllowPanic(int account, boolean panic) {
    }

    public static void setHideAccount(int account, boolean hide) {
    }

    public static void setPasscodeForAccount(String firstPassword, int account) {
    }

    public static boolean hasPasscodeForAccount(int account) {
        return false;
    }

    public static boolean hasPanicCode() {
        return false;
    }

    public static String getSettingsKey() {
        return "";
    }

    public static boolean isSettingsHidden() {
        return false;
    }

    public static void setHideSettings(boolean hide) {
    }

    public static boolean isEnabled() {
        return false;
    }

    public static void clearAll() {
    }
}
