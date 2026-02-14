package org.telegram.ui.Components.Premium;

import android.content.Context;
import android.widget.FrameLayout;

import org.telegram.messenger.NotificationCenter;

/**
 * STUB: Video screen preview for Premium features removed.
 * This stub prevents compilation errors in callers.
 */
public class VideoScreenPreview extends FrameLayout implements PagerHeaderView, NotificationCenter.NotificationCenterDelegate {

    public VideoScreenPreview(Context context) {
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
