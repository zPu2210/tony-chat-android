package org.telegram.ui.bots;

import android.content.Context;

import org.telegram.messenger.NotificationCenter;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.ChatAttachAlert;
import org.telegram.ui.web.BotWebViewContainer;

/**
 * STUB: Chat Attach Alert Bot Web View Layout removed from Tony Chat.
 * Bot platform attachment features are not supported.
 */
public class ChatAttachAlertBotWebViewLayout extends ChatAttachAlert.AttachAlertLayout implements NotificationCenter.NotificationCenterDelegate {

    public android.view.View settingsItem;

    public ChatAttachAlertBotWebViewLayout(ChatAttachAlert alert, Context context, Theme.ResourcesProvider resourcesProvider) {
        super(alert, context, resourcesProvider);
    }

    public void setDelegate(BotWebViewContainer.Delegate delegate) {
        // Do nothing
    }

    public void setBotButtonAvailable(boolean available) {
        // Do nothing
    }

    public void requestWebView(int currentAccount, long peerId, long botId, boolean simple, int replyToMsgId, String startCommand, long sendMonoForumPeerId) {
        // Do nothing
    }

    public String getStartCommand() {
        return null;
    }

    public boolean needReload() {
        return false;
    }

    public void setNeedCloseConfirmation(boolean need) {
        // Do nothing
    }

    public void setAllowSwipes(boolean allow) {
        // Do nothing
    }

    public void setCustomActionBarBackground(int color) {
        // Do nothing
    }

    public void setCustomBackground(int color) {
        // Do nothing
    }

    public org.telegram.ui.web.BotWebViewContainer getWebViewContainer() {
        return null;
    }

    public boolean canExpandByRequest() {
        return false;
    }

    public boolean isBotButtonAvailable() {
        return false;
    }

    public void setMeasureOffsetY(int offset) {
        // Do nothing
    }

    public void disallowSwipeOffsetAnimation() {
        // Do nothing
    }

    public void showJustAddedBulletin() {
        // Do nothing
    }

    @Override
    public void didReceivedNotification(int id, int account, Object... args) {
        // Do nothing
    }

    @Override
    public void scrollToTop() {
        // Do nothing
    }

    @Override
    public int needsActionBar() {
        return 0;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        // Do nothing
    }

    @Override
    public void onShow(ChatAttachAlert.AttachAlertLayout previousLayout) {
        // Do nothing
    }

    @Override
    public void onHide() {
        // Do nothing
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public int getCurrentItemTop() {
        return 0;
    }

    @Override
    public void setTranslationY(float translationY) {
        // Do nothing
    }

    @Override
    public int getFirstOffset() {
        return 0;
    }

    @Override
    public void onPreMeasure(int availableWidth, int availableHeight) {
        // Do nothing
    }

    @Override
    public int getButtonsHideOffset() {
        return 0;
    }

    @Override
    public void onMenuItemClick(int id) {
        // Do nothing
    }

    /**
     * Stub inner class
     */
    public static class WebViewSwipeContainer extends android.widget.FrameLayout {
        public WebViewSwipeContainer(android.content.Context context) {
            super(context);
        }

        public void setOffsetY(float offsetY) {
            // Do nothing
        }

        public void setShouldWaitWebViewScroll(boolean wait) {
            // Do nothing
        }

        public void setFullSize(boolean fullSize) {
            // Do nothing
        }

        public void setAllowFullSizeSwipe(boolean allow) {
            // Do nothing
        }

        public void setWebView(org.telegram.ui.web.BotWebViewContainer.MyWebView webView) {
            // Do nothing
        }

        public float getOffsetY() {
            return 0f;
        }

        public float getTopActionBarOffsetY() {
            return 0f;
        }

        public float getSwipeOffsetY() {
            return 0f;
        }

        public void setTopActionBarOffsetY(float offsetY) {
            // Do nothing
        }

        public void setScrollListener(Runnable listener) {
            // Do nothing
        }

        public void setScrollEndListener(Runnable listener) {
            // Do nothing
        }

        public void setDelegate(Delegate delegate) {
            // Do nothing
        }

        public boolean allowingScroll(boolean allow) {
            return false;
        }

        public boolean isScrolling = false;

        public void isPageLoaded() {
            // Do nothing
        }

        public void stickTo(float offsetY) {
            // Do nothing
        }

        public void setSwipeOffsetY(float offsetY) {
            // Do nothing
        }

        public float offsetY = 0f;
        public float topActionBarOffsetY = 0f;

        public boolean isUrlDangerous() {
            return false;
        }

        /**
         * Stub interface
         */
        public interface Delegate {
            void onDismiss(boolean byTap);
        }
    }
}
