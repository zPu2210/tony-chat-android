package tw.nekomimi.nekogram;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Stub: Save to download broadcast receiver
 */
public class SaveToDownloadReceiver extends BroadcastReceiver {
    public static final String NOTIFICATION_TAG = "MediaController";
    public static final String ACTION_CANCEL_DOWNLOAD = "com.tonychat.messenger.CANCEL_SAVE_TO_DOWNLOAD";
    public static final String EXTRA_ID = "com.tonychat.messenger.NOTIFICATION_ID";

    @Override
    public void onReceive(Context context, Intent intent) {
        // No-op stub
    }

    public static int createNotificationId() {
        return (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
    }

    public static void showNotification(Context context, int id, int count, Runnable onCancel) {}

    public static void cancelNotification(int id) {}

    public static void updateNotification(int id, int progress) {}
}
