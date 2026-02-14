package org.telegram.ui.Stories;

import org.telegram.messenger.AccountInstance;

/**
 * UserListPoller stub - Stories removed in Tony Chat
 */
public class UserListPoller {
    public UserListPoller(int account) {}

    public static UserListPoller getInstance(int num) {
        return AccountInstance.getInstance(num).getUserListPoller();
    }

    public void start() {}
    public void stop() {}
    public void checkList(Object fragment) {}
}
