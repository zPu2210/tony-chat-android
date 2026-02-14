package org.telegram.ui.bots;

import android.app.Activity;
import android.content.Context;

/**
 * STUB: Bot Location API removed from Tony Chat.
 * Bot location access features are not supported.
 */
public class BotLocation {

    public BotLocation(int currentAccount, Activity activity) {
        // Stub constructor
    }

    public static BotLocation get(Context context, int currentAccount, long userId) {
        return new BotLocation(currentAccount, (Activity) context);
    }

    public static void clear() {
        // Do nothing
    }

    public boolean granted() {
        return false;
    }

    public void setGranted(boolean granted, Runnable callback) {
        if (callback != null) {
            callback.run();
        }
    }

    public void request(Callback callback) {
        if (callback != null) {
            callback.onResult(false, null);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // Do nothing
    }

    public boolean asked() {
        return false;
    }

    /**
     * Stub interface
     */
    public interface Callback {
        void onResult(boolean success, Object location);
    }
}
