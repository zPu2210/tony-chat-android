package org.telegram.ui.Business;

import android.content.Context;
import org.telegram.messenger.NotificationCenter;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.ChatAttachAlert;

public class ChatAttachAlertQuickRepliesLayout extends ChatAttachAlert.AttachAlertLayout implements NotificationCenter.NotificationCenterDelegate {

    public ChatAttachAlertQuickRepliesLayout(ChatAttachAlert alert, Context context, Theme.ResourcesProvider resourcesProvider) {
        super(alert, context, resourcesProvider);
    }

    @Override
    public void didReceivedNotification(int id, int account, Object... args) {
    }
}
