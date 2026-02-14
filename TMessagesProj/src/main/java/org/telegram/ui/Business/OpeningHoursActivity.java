package org.telegram.ui.Business;

import org.telegram.messenger.NotificationCenter;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.tl.TL_account;
import org.telegram.ui.ActionBar.BaseFragment;

public class OpeningHoursActivity extends BaseFragment implements NotificationCenter.NotificationCenterDelegate {

    public OpeningHoursActivity() {
        super();
    }

    public static String toString(int currentAccount, TLRPC.User user, TL_account.TL_businessWorkHours business_work_hours) {
        return "";
    }

    @Override
    public void didReceivedNotification(int id, int account, Object... args) {
    }
}
