package org.telegram.ui.Stories.recorder;

import android.content.Context;
import android.view.View;

/**
 * TimelineView stub - Stories removed in Tony Chat
 */
public class TimelineView extends View {
    public TimelineView(Context context) {
        super(context);
    }

    // PhotoViewerCoverEditor constructor compatibility
    public TimelineView(Context context, Object videoPlayer, Object audioPlayer, Object resourcesProvider, Object blurManager) {
        super(context);
    }

    public void setCover() {}
    public void setDelegate(TimelineDelegate delegate) {}
    public void setVideo(boolean isStory, String path, long duration, float volume) {}
    public void setVideoLeft(float left) {}
    public void setVideoRight(float right) {}
    public void setCoverVideo(long start, long end) {}
    public void normalizeScrollByVideo() {}

    public static float heightDp() {
        return 64f;
    }

    public static abstract class TimelineDelegate {
        public void onVideoLeftChange(boolean released, float left) {}
    }
}
