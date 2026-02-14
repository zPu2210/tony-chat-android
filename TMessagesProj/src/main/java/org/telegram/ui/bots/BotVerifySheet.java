package org.telegram.ui.bots;

import android.content.Context;

import org.telegram.ui.ActionBar.BottomSheet;

/**
 * STUB: Bot Verify Sheet removed from Tony Chat.
 * Bot verification UI features are not supported.
 */
public class BotVerifySheet {

    public BotVerifySheet(Context context) {
        // Stub constructor
    }

    public static void openVerify(int currentAccount, long userId, Object verifierSettings) {
        // Do nothing
    }

    public void show() {
        // Do nothing
    }

    public void dismiss() {
        // Do nothing
    }

    public void setDelegate(Delegate delegate) {
        // Do nothing
    }

    /**
     * Stub interface
     */
    public interface Delegate {
        void onVerified(boolean verified);
    }
}
