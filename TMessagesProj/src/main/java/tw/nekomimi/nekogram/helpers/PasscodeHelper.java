package tw.nekomimi.nekogram.helpers;
import android.app.Activity;
public class PasscodeHelper {
    public static boolean checkFingerprint(Object a) { return false; }
    public static boolean isSettingPasscode() { return false; }
    public static boolean hasPasscodeForAccount(int a) { return false; }
    public static boolean isEnabled() { return false; }
    public static boolean isAccountAllowed(int a) { return true; }
    public static boolean isAccountHidden(int a) { return false; }
    public static void removePasscodeForAccount(int a) {}
    public static void setPasscodeForAccount(String pass, int account) {}
    public static boolean checkPasscode(Activity a, String pass) { return true; }
    public static boolean isSettingsHidden() { return false; }
}
