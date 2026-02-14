package org.telegram.ui;

import android.content.Context;

import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.ChatObject;

/**
 * STUB - Group call functionality removed for Tony Chat
 */
public class GroupCallActivity {

    public static GroupCallActivity groupCallInstance;

    public static GroupCallActivity getInstance() {
        return groupCallInstance;
    }

    public static void create(Context context, AccountInstance account, Object chat, Object call) {
    }

    public static void create(Context context, AccountInstance account, Object chat, Object call, boolean schedule, Object hash) {
    }

    public static boolean isLandscapeMode() {
        return false;
    }

    public void dismiss() {
    }

    // Tony Chat: Additional stub fields/methods for VoIP removal
    public static final int MAX_AMPLITUDE = 8500;
    public static boolean groupCallUiVisible = false;
    public static boolean isLandscapeMode = false;

    public void dismissInternal() {
    }

    public void onResume() {
    }

    public void enableCamera() {
    }

    public void onPause() {
    }

    public static void onLeaveClick(Object context, Runnable onLeave, boolean fromOverlayWindow, boolean animated) {
    }

    public android.widget.FrameLayout getContainer() {
        return null;
    }
}
