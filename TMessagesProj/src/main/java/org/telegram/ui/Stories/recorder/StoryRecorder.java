package org.telegram.ui.Stories.recorder;

import android.app.Activity;
import android.view.View;
import org.telegram.ui.ActionBar.Theme;

/**
 * StoryRecorder stub - Stories removed in Tony Chat
 */
public class StoryRecorder {
    public static StoryRecorder getInstance(Activity activity, int account) {
        return new StoryRecorder();
    }

    public StoryRecorder closeToWhenSent(ClosingViewProvider provider) { return this; }
    public StoryRecorder selectedPeerId(long peerId) { return this; } // Tony Chat: Stories removed
    public StoryRecorder canChangePeer(boolean canChange) { return this; } // Tony Chat: Stories removed
    public void open(SourceView sourceView, boolean someFlag) {}
    public void open(Object entry) {}
    public void close(boolean animated) {}
    public static boolean isVisible() { return false; }
    public void openBot(long dialogId, String lang, Object preview) {}
    public static void destroyInstance() {} // Tony Chat: Stories removed

    public static CharSequence cameraBtnSpan(android.content.Context context) {
        return "";
    }

    public interface Touchable {
        boolean additionalTouchEvent(android.view.MotionEvent ev);
    }

    public interface ClosingViewProvider {
        void preLayout(long dialogId, Runnable whenReady);
        SourceView getView(long dialogId);
    }

    public static class SourceView {
        public SourceView(Object view) {}
        public static SourceView fromStoryCell(Object storyCell) { return new SourceView(null); }
        public static SourceView fromFloatingButton(View view) { return new SourceView(null); }
        public static SourceView fromAvatarImage(Object avatarImage, boolean isForum) { return new SourceView(null); } // Tony Chat: Stories removed
    }

    public static class WindowView extends android.widget.FrameLayout {
        public WindowView(android.content.Context context, Theme.ResourcesProvider resourcesProvider) {
            super(context);
        }
    }
}
