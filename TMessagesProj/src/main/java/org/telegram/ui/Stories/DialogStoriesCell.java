package org.telegram.ui.Stories;

import android.content.Context;
import android.view.HapticFeedbackConstants;
import android.widget.FrameLayout;

import org.telegram.ui.ActionBar.ActionBar;

/**
 * DialogStoriesCell stub - Stories removed in Tony Chat
 */
public class DialogStoriesCell extends FrameLayout {
    public static final int TYPE_ARCHIVE = 0;
    public static final int TYPE_DIALOGS = 1;
    public static final float HEIGHT_IN_DP = 86f;

    public boolean allowGlobalUpdates = true;

    public DialogStoriesCell(Context context, Object fragment, int currentAccount, int type) {
        super(context);
        setVisibility(GONE);
    }

    // View management
    public PremiumHint getPremiumHint() { return new PremiumHint(getContext()); }
    public void setClipTop(int top) {}
    public void setOverscoll(float overscroll) {}

    // Scroll management
    public void scrollToFirstCell() {}
    public void scrollTo(long dialogId) {}
    public Object findStoryCell(long dialogId) { return null; }

    // Hint stub
    public static class PremiumHint extends android.view.View {
        public PremiumHint(Context context) { super(context); }
        public void hide() {}
        public boolean shown() { return false; }
    }

    // Progress and collapse
    public void setProgressToCollapse(float progress) {}
    public void setProgressToCollapse(float progress, boolean animated) {}
    public float getCollapsedProgress() { return 1f; }
    public float overscrollProgress() { return 0f; }

    // State checks
    public boolean isExpanded() { return false; }
    public boolean isFullExpanded() { return false; }
    public boolean scrollToFirst() { return false; }

    // Actions
    public void openSelfStories() {}
    public void openStoryRecorder() {}
    public void openStoryRecorder(long dialogId) {}
    public void openStoryForCell(Object storyCell) {}
    public void openOverscrollSelectedStory() {}
    public void showPremiumHint() {}

    // Updates
    public void updateItems(boolean animated, boolean visibilityChanged) {}
    public void updateColors() {}
    public void setItems(java.util.ArrayList<Object> items) {}
    public void setTitleOverlayText(String title, int titleId) {}

    // Lifecycle
    public void onResume() {}

    // Configuration
    public void setActionBar(ActionBar actionBar) {}

    // Callbacks
    public void onUserLongPressed(android.view.View view, long dialogId) {}
    public void onMiniListClicked() {}
}
