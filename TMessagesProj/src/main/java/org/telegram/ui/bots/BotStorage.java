package org.telegram.ui.bots;

import org.json.JSONObject;

/**
 * STUB: Bot Storage API removed from Tony Chat.
 * Bot storage features are not supported.
 */
public class BotStorage {

    public BotStorage(int currentAccount, long botId) {
        // Stub constructor
    }

    public static BotStorage get(int currentAccount, long botId) {
        return new BotStorage(currentAccount, botId);
    }

    public void set(String key, String value, Callback callback) {
        if (callback != null) {
            callback.onResult(false);
        }
    }

    public void get(String key, Callback callback) {
        if (callback != null) {
            callback.onResult(false);
        }
    }

    public void delete(String key, Callback callback) {
        if (callback != null) {
            callback.onResult(false);
        }
    }

    public void getKeys(Callback callback) {
        if (callback != null) {
            callback.onResult(false);
        }
    }

    /**
     * Stub interface
     */
    public interface Callback {
        void onResult(boolean success);
    }
}
