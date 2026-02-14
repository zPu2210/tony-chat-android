package tw.nekomimi.nekogram;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
public class SaveToDownloadReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context c, Intent i) {}
    public static int createNotificationId() { return 0; }
    public static void showNotification(Context ctx, int id, int total, Runnable onCancel) {}
    public static void updateNotification(int id, int progress) {}
    public static void cancelNotification(int id) {}
}
