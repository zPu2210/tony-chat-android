package tw.nekomimi.nekogram.utils;
import android.os.Handler;
import android.os.Looper;
public class UIUtil {
    private static final Handler handler = new Handler(Looper.getMainLooper());
    public static void runOnUIThread(Runnable r) { handler.post(r); }
    public static void runOnIoDispatcher(Runnable r) { new Thread(r).start(); }
    public static void post(Runnable r) { handler.post(r); }
}
