package org.telegram.messenger.voip;

import org.telegram.messenger.BaseController;
import org.telegram.tgnet.TLRPC;

/**
 * GUTTED FOR TONY CHAT - Group call messages functionality removed
 */
public class GroupCallMessagesController extends BaseController {

    public interface CallMessageListener {
        void onNewGroupCallMessage(long callId, Object message);
        void onPopGroupCallMessage();
    }

    public GroupCallMessagesController(int currentAccount) {
        super(currentAccount);
    }

    public void processUpdate(TLRPC.TL_updateGroupCallMessage update) {
    }

    public void processUpdate(TLRPC.TL_updateGroupCallEncryptedMessage update) {
    }

    // Tony Chat: Additional stub methods for VoIP removal
    public static GroupCallMessagesController getInstance(int account) {
        return null;
    }

    public void subscribeToCallMessages(long callId, CallMessageListener listener) {
    }

    public void unsubscribeFromCallMessages(long callId, CallMessageListener listener) {
    }
}
