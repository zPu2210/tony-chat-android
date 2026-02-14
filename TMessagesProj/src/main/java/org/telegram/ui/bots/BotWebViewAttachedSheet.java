package org.telegram.ui.bots;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.IntDef;

import org.telegram.messenger.NotificationCenter;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheetTabsOverlay;
import org.telegram.ui.ActionBar.BottomSheetTabs;
import org.telegram.ui.ActionBar.Theme;

/**
 * STUB: Bot Web View Attached Sheet removed from Tony Chat.
 * Bot platform UI features are not supported.
 */
public class BotWebViewAttachedSheet implements NotificationCenter.NotificationCenterDelegate, BaseFragment.AttachedSheet {

    public final static int TYPE_WEB_VIEW_BUTTON = 0;
    public final static int TYPE_SIMPLE_WEB_VIEW_BUTTON = 1;
    public final static int TYPE_BOT_MENU_BUTTON = 2;
    public final static int TYPE_WEB_VIEW_BOT_APP = 3;
    public final static int TYPE_WEB_VIEW_BOT_MAIN = 4;

    @IntDef({TYPE_WEB_VIEW_BUTTON, TYPE_SIMPLE_WEB_VIEW_BUTTON, TYPE_BOT_MENU_BUTTON, TYPE_WEB_VIEW_BOT_APP, TYPE_WEB_VIEW_BOT_MAIN})
    public @interface WebViewType {}

    public BotWebViewAttachedSheet(Context context, BaseFragment fragment, Theme.ResourcesProvider resourcesProvider) {
        // Stub constructor
    }

    public static void openPrivacy(int currentAccount, long userId) {
        // Do nothing
    }

    public void requestWebView(WebViewRequestProps props) {
        // Do nothing
    }

    public long getBotId() {
        return 0;
    }

    public boolean isShown() {
        return false;
    }

    @Override
    public void didReceivedNotification(int id, int account, Object... args) {
        // Do nothing
    }

    @Override
    public View getWindowView() {
        return new View(new android.content.ContextWrapper(null) {
            @Override
            public Context getBaseContext() {
                return null;
            }
        });
    }

    public void dismiss() {
        // Do nothing
    }

    public void dismiss(boolean tabs, Runnable callback) {
        dismiss();
        if (callback != null) {
            callback.run();
        }
    }

    @Override
    public void dismiss(boolean tabs) {
        dismiss(tabs, null);
    }

    @Override
    public void release() {}

    @Override
    public boolean isFullyVisible() {
        return false;
    }

    @Override
    public boolean attachedToParent() {
        return false;
    }

    @Override
    public boolean onAttachedBackPressed() {
        return false;
    }

    @Override
    public boolean showDialog(android.app.Dialog dialog) {
        return false;
    }

    @Override
    public void setKeyboardHeightFromParent(int keyboardHeight) {}

    @Override
    public boolean isAttachedLightStatusBar() {
        return false;
    }

    @Override
    public int getNavigationBarColor(int color) {
        return color;
    }

    @Override
    public void setOnDismissListener(Runnable onDismiss) {}


    public void setParentActivity(Activity activity) {
        // Do nothing
    }

    public void onPause() {
        // Do nothing
    }

    public void onResume() {
        // Do nothing
    }

    public void onDestroy() {
        // Do nothing
    }

    public boolean onBackPressed() {
        return false;
    }

    /**
     * Stub inner class
     */
    public static class MainButtonSettings {
        public boolean isVisible;
        public boolean isActive;
        public String text;
        public int color;
        public int textColor;
        public boolean isProgressVisible;
    }
}
