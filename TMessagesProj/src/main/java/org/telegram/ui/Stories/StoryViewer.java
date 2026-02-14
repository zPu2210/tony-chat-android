package org.telegram.ui.Stories;

import android.app.Activity;
import android.view.View;
import org.telegram.tgnet.tl.TL_stories;
import org.telegram.ui.ActionBar.BaseFragment;

/**
 * StoryViewer stub - Stories removed in Tony Chat
 */
public class StoryViewer implements BaseFragment.AttachedSheet {
    public static boolean animationInProgress = false;
    public Activity activity;
    public int currentAccount;
    public boolean fromBottomSheet;

    public StoryViewer(BaseFragment fragment) {
        this.currentAccount = fragment.getCurrentAccount();
    }

    public StoryViewer(Activity activity, int account, Object resourcesProvider) {
        this.activity = activity;
        this.currentAccount = account;
    }

    public static StoryViewer getInstance(Activity activity, int account) {
        return null;
    }

    public void open(int account, android.content.Context context, TL_stories.StoryItem storyItem, Object placeProvider) {}
    public void open(long dialogId, int storyId, Object placeProvider) {}
    public void open(long dialogId, Object storiesList, int selectedPosition, Object placeProvider) {}
    // Tony Chat: Stories removed - additional open overloads
    public void open(android.content.Context context, int storyId, Object storiesList, Object placeProvider) {}
    public void open(android.content.Context context, long dialogId, Object storiesList, Object placeProvider) {}
    public void open(android.content.Context context, long dialogId, Object placeProvider) {} // ProfileActivity fix
    public void open(android.content.Context context, Object stories, Object placeProvider) {} // ProfileActivity fix
    public void close(boolean animated) {}
    public boolean isShown() { return false; }
    public void instantClose() {}
    public void onResume() {}
    public void onPause() {}
    public void updatePlayingMode() {}
    public void listenToAttachedSheet(BaseFragment.AttachedSheet sheet) {}
    public static boolean isShowingImage(Object messageObject) { return false; }
    public static void closeGlobalInstances() {} // Tony Chat: Stories removed
    public void doOnAnimationReady(Runnable runnable) {} // Tony Chat: Stories removed

    // BaseFragment.AttachedSheet implementation
    @Override
    public View getWindowView() { return null; }

    @Override
    public boolean attachedToParent() { return false; }

    @Override
    public void setOnDismissListener(Runnable listener) {}

    @Override
    public void dismiss() {}

    @Override
    public void release() {}

    @Override
    public boolean isFullyVisible() { return false; }

    @Override
    public boolean onAttachedBackPressed() { return false; }

    @Override
    public boolean showDialog(android.app.Dialog dialog) { return false; }

    @Override
    public void setKeyboardHeightFromParent(int keyboardHeight) {}

    @Override
    public boolean isAttachedLightStatusBar() { return false; }

    @Override
    public int getNavigationBarColor(int color) { return color; }

    // Additional methods
    public android.widget.FrameLayout getContainerForBulletin() { return null; }
    public Object getResourceProvider() { return null; }

    // Tony Chat: open overload for CalendarActivity
    public void open(android.content.Context context, TL_stories.StoryItem storyItem, int messageId, Object storiesList, boolean fromCalendar, PlaceProvider placeProvider) {}

    public interface PlaceProvider {
        default boolean findView(long dialogId, int messageId, int storyId, int type, TransitionViewHolder holder) { return false; }
        default void preLayout(long currentDialogId, int messageId, Runnable o) { if (o != null) o.run(); }
        default Object getView(long dialogId) { return null; }
    }

    public static class TransitionViewHolder {
        public Object storyImage;
        public Object drawAbove;
        public View view;
        public View clipParent;
        public float clipTop;
        public float clipBottom;
        public Object avatarImage;
    }

    @FunctionalInterface
    public interface HolderDrawAbove {
        void draw(android.graphics.Canvas canvas, android.graphics.RectF bounds, float alpha, boolean opening);
    }
}
