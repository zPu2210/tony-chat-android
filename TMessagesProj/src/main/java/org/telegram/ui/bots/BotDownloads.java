package org.telegram.ui.bots;

import android.app.Activity;

/**
 * STUB: Bot Downloads API removed from Tony Chat.
 * Bot file download features are not supported.
 */
public class BotDownloads {

    public BotDownloads(int currentAccount, Activity activity) {
        // Stub constructor
    }

    public static void clear() {
        // Do nothing
    }

    public void download(String url, String fileName, Callback callback) {
        if (callback != null) {
            callback.onResult(false, null);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // Do nothing
    }

    /**
     * Stub interface
     */
    public interface Callback {
        void onResult(boolean success, String filePath);
    }
}
