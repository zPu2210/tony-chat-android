package org.telegram.ui.Business;

import org.telegram.messenger.NotificationCenter;
import org.telegram.ui.ActionBar.BaseFragment;

public class LocationActivity extends BaseFragment implements NotificationCenter.NotificationCenterDelegate {

    public static final int LOCATION_TYPE_GROUP_VIEW = 3;

    public LocationActivity() {
        super();
    }

    public LocationActivity(int type) {
        super();
    }

    @Override
    public void didReceivedNotification(int id, int account, Object... args) {
    }
}
