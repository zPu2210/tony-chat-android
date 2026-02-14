package org.telegram.ui.Components.Premium;

import android.content.Context;

import org.telegram.messenger.NotificationCenter;
import org.telegram.ui.Components.RecyclerListView;

/**
 * STUB: Premium stickers preview recycler removed.
 * This stub prevents compilation errors in callers.
 */
public class PremiumStickersPreviewRecycler extends RecyclerListView implements NotificationCenter.NotificationCenterDelegate, PagerHeaderView {

    public PremiumStickersPreviewRecycler(Context context, int currentAccount) {
        super(context);
    }

    @Override
    public void didReceivedNotification(int id, int account, Object... args) {
        // No-op
    }

    @Override
    public void setOffset(float v) {
        // No-op
    }
}
