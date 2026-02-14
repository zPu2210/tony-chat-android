package org.telegram.messenger.voip;

import android.content.Context;
import android.content.Intent;

import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.tl.TL_phone;

import java.util.ArrayList;

/**
 * GUTTED FOR TONY CHAT - VoIP pre-notification functionality removed
 */
public class VoIPPreNotificationService {

    public static final class State implements VoIPServiceState {
        private final int currentAccount;
        private final long userId;
        private final TL_phone.PhoneCall call;

        public State(int currentAccount, long userId, TL_phone.PhoneCall phoneCall) {
            this.currentAccount = currentAccount;
            this.userId = userId;
            this.call = phoneCall;
        }

        @Override
        public org.telegram.tgnet.TLRPC.User getUser() {
            return null;
        }

        @Override
        public boolean isOutgoing() {
            return false;
        }

        @Override
        public int getCallState() {
            return VoIPService.STATE_ENDED;
        }

        @Override
        public TL_phone.PhoneCall getPrivateCall() {
            return call;
        }

        @Override
        public boolean isCallingVideo() {
            return false;
        }

        @Override
        public void acceptIncomingCall() {
        }

        @Override
        public void declineIncomingCall() {
        }

        @Override
        public void stopRinging() {
        }

        public void destroy() {
        }

        @Override
        public boolean isConference() {
            return false;
        }

        @Override
        public org.telegram.tgnet.TLRPC.GroupCall getGroupCall() {
            return null;
        }

        @Override
        public ArrayList<org.telegram.tgnet.TLRPC.GroupCallParticipant> getGroupParticipants() {
            return null;
        }
    }

    public static TL_phone.PhoneCall pendingCall;
    public static Intent pendingVoIP;
    public static State currentState;

    public static State getState() {
        return currentState;
    }

    public static void show(Context context, Intent intent, TL_phone.PhoneCall call) {
    }

    public static boolean open(Context context) {
        return false;
    }

    public static boolean isVideo() {
        return false;
    }

    public static void answer(Context context) {
    }

    public static void decline(Context context, int reason) {
    }

    public static void dismiss(Context context, boolean answered) {
    }

    public static void startRinging(Context context, int account, long user_id) {
    }

    public static void stopRinging() {
    }
}
