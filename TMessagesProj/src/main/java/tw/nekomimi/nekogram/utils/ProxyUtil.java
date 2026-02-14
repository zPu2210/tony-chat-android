package tw.nekomimi.nekogram.utils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
public class ProxyUtil {
    public static void init() {}
    public static Activity getOwnerActivity(Context c) { return (c instanceof Activity) ? (Activity)c : null; }
    public static void showQrDialog(Context c, String text) {}
    public static AlertDialog showQrDialog(Context c, String text, String title) { return null; }
    public static AlertDialog showQrDialog(Activity a, String text) { return null; }
    public static String tryReadQR(Bitmap bitmap) { return null; }
    public static String tryReadQR(Activity activity, Uri uri) { return null; }
    public static void processDeepLink(Object activity, Uri uri, Object cb, Runnable fallback) {}
    public static void registerNetworkCallback() {}
    public static void showLinkAlert(Activity a, String url) {}
    public static boolean isIpv6Address(String addr) { return addr != null && addr.contains(":"); }
    public static boolean isVPNEnabled() { return false; }
    public static void importFromClipboard(Activity a) {}
}
