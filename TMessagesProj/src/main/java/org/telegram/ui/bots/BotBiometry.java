package org.telegram.ui.bots;

import android.content.Context;
import org.telegram.messenger.MessagesController;

/**
 * STUB: Bot Biometry API removed from Tony Chat.
 * Bot biometric authentication features are not supported.
 */
public class BotBiometry {

    public final int currentAccount;
    public final long botId;

    public BotBiometry(int currentAccount, long botId) {
        this.currentAccount = currentAccount;
        this.botId = botId;
    }

    public static BotBiometry get(int currentAccount, long botId) {
        return new BotBiometry(currentAccount, botId);
    }

    public static BotBiometry get(Context context, int currentAccount, long userId) {
        return new BotBiometry(currentAccount, userId);
    }

    public static void clear() {
        // Do nothing
    }

    public boolean granted() {
        return false;
    }

    public void setGranted(boolean granted) {
        // Do nothing
    }

    public void request(String reason, Callback callback) {
        if (callback != null) {
            callback.onResult(false, null);
        }
    }

    public boolean isAvailable() {
        return false;
    }

    public void updateSettings() {
        // Do nothing
    }

    public boolean asked() {
        return false;
    }

    public static void getBots(android.content.Context context, int currentAccount, androidx.core.util.Consumer<java.util.ArrayList<Bot>> callback) {
        if (callback != null) {
            callback.accept(new java.util.ArrayList<>());
        }
    }

    /**
     * Stub interface
     */
    public interface Callback {
        void onResult(boolean success, String token);
    }

    /**
     * Stub inner class
     */
    public static class Bot {
        public long botId;
        public String botName;
    }
}
