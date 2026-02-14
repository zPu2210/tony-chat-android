package tw.nekomimi.nekogram.utils;
import android.app.Activity;
import android.content.Context;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BaseFragment;
public class AlertUtil {
    public static void showToast(String msg) {}
    public static void showToast(Throwable t) {}
    public static void showToast(Exception e) {}
    public static void showToast(Object obj) {}
    public static void showSimpleAlert(BaseFragment f, String title, String msg) {}
    public static void showSimpleAlert(Object f, String msg) {}
    public static void copyAndAlert(String text) {}
    public static AlertDialog showProgress(Activity a) { return null; }
    public static AlertDialog showProgress(Context c) { return null; }
    public static void showTransFailedDialog(Activity a, boolean unsupported, String message, Runnable onRetry) {}
    public static void showTransFailedDialog(Context c, boolean unsupported, String message, Runnable onRetry) {}
    public static void showConfirm(Activity a, String title, int icon, String message, boolean red, Runnable onConfirm) {}
    public static void showCopyAlert(Activity a, String text) {}
}
