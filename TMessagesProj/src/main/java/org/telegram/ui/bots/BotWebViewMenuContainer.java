package org.telegram.ui.bots;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import org.telegram.messenger.NotificationCenter;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BottomSheetTabsOverlay;
import org.telegram.ui.ActionBar.BottomSheetTabs;
import org.telegram.ui.ActionBar.Theme;

/**
 * STUB: Bot Web View Menu Container removed from Tony Chat.
 * Bot platform menu features are not supported.
 */
public class BotWebViewMenuContainer extends FrameLayout implements NotificationCenter.NotificationCenterDelegate, BottomSheetTabsOverlay.Sheet, BottomSheetTabsOverlay.SheetView {

    public BotWebViewMenuContainer(Context context, Theme.ResourcesProvider resourcesProvider) {
        super(context);
    }

    public void show(int currentAccount, long botId, String buttonText) {
        // Do nothing
    }

    public void dismiss() {
        // Do nothing
    }

    public boolean isShown() {
        return false;
    }

    @Override
    public void didReceivedNotification(int id, int account, Object... args) {
        // Do nothing
    }

    @Override
    public BottomSheetTabsOverlay.SheetView getWindowView() {
        return this;
    }

    @Override
    public void show() {}

    @Override
    public void dismiss(boolean tabs) {
        dismiss();
    }

    @Override
    public BottomSheetTabs.WebTabData saveState() {
        return null;
    }

    @Override
    public boolean restoreState(org.telegram.ui.ActionBar.BaseFragment fragment, BottomSheetTabs.WebTabData tab) {
        return false;
    }

    @Override
    public void release() {}

    @Override
    public boolean isFullSize() {
        return false;
    }

    @Override
    public boolean setDialog(org.telegram.ui.ActionBar.BottomSheetTabDialog dialog) {
        return false;
    }

    @Override
    public int getNavigationBarColor(int color) {
        return color;
    }

    @Override
    public void setDrawingFromOverlay(boolean value) {}

    @Override
    public android.graphics.RectF getRect() {
        return new android.graphics.RectF();
    }

    @Override
    public float drawInto(android.graphics.Canvas canvas, android.graphics.RectF finalRect, float progress, android.graphics.RectF clipRect, float alpha, boolean opening) {
        return 0f;
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
    public static class ActionBarColorsAnimating {
        public int getColor(int key) {
            return 0;
        }

        public void setFrom(int color, Theme.ResourcesProvider resourcesProvider) {
            // Do nothing
        }

        public void setTo(int color, Theme.ResourcesProvider resourcesProvider) {
            // Do nothing
        }

        public void updateActionBar(ActionBar actionBar, float progress) {
            // Do nothing
        }

        public void updateColors(Theme.ResourcesProvider resourcesProvider) {
            // Do nothing
        }
    }
}
