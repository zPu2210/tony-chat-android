package org.telegram.messenger.voip;

/**
 * GUTTED FOR TONY CHAT - Group call messages removed
 */
public class GroupCallMessage {
    public final int currentAccount;
    public final long fromId;
    public final long randomId;

    public GroupCallMessage(int currentAccount, long fromId, long randomId, Object message) {
        this.currentAccount = currentAccount;
        this.fromId = fromId;
        this.randomId = randomId;
    }

    public boolean isOut() {
        return false;
    }
}
