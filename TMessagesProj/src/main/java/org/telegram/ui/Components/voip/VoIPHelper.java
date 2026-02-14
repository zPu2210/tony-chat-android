package org.telegram.ui.Components.voip;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.ChatObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.ui.LaunchActivity;

/**
 * STUB - VoIP helper removed for Tony Chat
 */
public class VoIPHelper {

    public static void startCall(TLRPC.User user, boolean videoCall, boolean canVideoCall, final Activity activity, TLRPC.UserFull userFull, AccountInstance accountInstance) {
    }

    // Tony Chat: Additional overload for startCall
    public static void startCall(TLRPC.User user, boolean videoCall, boolean canVideoCall, final Activity activity, TLRPC.UserFull userFull, AccountInstance accountInstance, boolean askBeforeCall) {
    }

    public static void startCall(TLRPC.Chat chat, Object peer, String hash, boolean createCall, final Activity activity, Object fragment, AccountInstance accountInstance) {
    }

    public static void startCall(TLRPC.Chat chat, Object peer, String hash, boolean createCall, boolean hasFel, final Activity activity, Object fragment, AccountInstance accountInstance) {
    }

    public static void joinConference(Activity activity, int account, Object inputGroupCall, boolean schedule, Object callback) {
    }

    // Tony Chat: Additional overload for joinConference
    public static void joinConference(Activity activity, int account, Object inputGroupCall, boolean schedule, Object call, Object hash) {
    }

    public static void showRateAlert(Context context, Runnable onDismiss, long callId, long userId, long accessHash, int account, boolean video) {
    }

    public static void showRateAlert(Context context, Object messageAction) {
    }

    public static void showGroupCallAlert(Object fragment, TLRPC.Chat chat, Object inputPeer, boolean schedule, AccountInstance accountInstance) {
    }

    public static boolean canRateCall(Object messageAction) {
        return false;
    }

    public static void permissionDenied(Activity activity, Runnable onFinish, int currentAccount) {
    }

    public static void showCallDebugSettings(Activity activity) {
    }

    public static String getLogFilePath(String filename) {
        return "";
    }

    public static String getLogFilePath(String filename, boolean compress) {
        return "";
    }

    public static int getDataSavingDefault() {
        return 0;
    }
}
