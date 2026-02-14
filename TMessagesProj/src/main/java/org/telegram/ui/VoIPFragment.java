package org.telegram.ui;

import android.app.Activity;
import android.view.View;

/**
 * STUB - VoIP fragment removed for Tony Chat
 */
public class VoIPFragment {

    private static VoIPFragment instance;

    public static VoIPFragment getInstance() {
        return instance;
    }

    public static void show(boolean video, Object lastCall) {
    }

    public static void show(boolean animated) {
    }

    // Tony Chat: Additional stub methods for VoIP removal
    public static void show(Activity activity, int account) {
    }

    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    }

    public static void onPause() {
    }

    public static void onResume() {
    }

    public void onStateChanged(int state) {
    }

    public boolean onBackPressed() {
        return false;
    }

    public View getWindowView() {
        return null;
    }

    public void finish() {
    }
}
