package org.telegram.ui.web;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Consumer;

import org.telegram.messenger.NotificationCenter;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.ui.ActionBar.ActionBarMenuSubItem;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.bots.WebViewRequestProps;

/**
 * STUB: Bot Web View Container removed from Tony Chat.
 * Bot platform WebView features are not supported.
 */
public abstract class BotWebViewContainer extends FrameLayout implements NotificationCenter.NotificationCenterDelegate {

    public final boolean bot;
    public static boolean firstWebView = true;

    public BotWebViewContainer(
            @NonNull Activity activity,
            Theme.ResourcesProvider resourcesProvider,
            int backgroundColor,
            boolean bot) {
        super(activity);
        this.bot = bot;
    }

    public BotWebViewContainer(
            @NonNull android.content.Context context,
            Theme.ResourcesProvider resourcesProvider,
            int backgroundColor,
            boolean bot) {
        super(context);
        this.bot = bot;
    }

    public void showLinkCopiedBulletin() {}

    public void setViewPortByMeasureSuppressed(boolean viewPortByMeasureSuppressed) {}

    public void setFlickerViewColor(int bgColor) {}

    public void checkCreateWebView() {}

    public void replaceWebView(int currentAccount, MyWebView webView, Object proxy) {}

    public BotWebViewProxy getBotProxy() {
        return null;
    }

    public WebViewProxy getProxy() {
        return null;
    }

    public void setOpener(MyWebView webView) {}

    public void setKeyboardFocusable(boolean focusable) {}

    public static int getMainButtonRippleColor(int buttonColor) {
        return 0;
    }

    public static Drawable getMainButtonRippleDrawable(int buttonColor) {
        return null;
    }

    public static boolean isTonsite(String url) {
        return false;
    }

    public static String magic2tonsite(String url) {
        return url;
    }

    public void updateFlickerBackgroundColor(int backgroundColor) {}

    protected void onTitleChanged(String title) {}

    protected void onFaviconChanged(Bitmap favicon) {}

    protected void onURLChanged(String url, boolean first, boolean last) {}

    public boolean onBackPressed() {
        return false;
    }

    public void setPageLoaded(String url, boolean animated) {}

    protected void onErrorShown(boolean shown, int errorCode, String description) {}

    protected void onDangerousTriggered(DangerousWebWarning warning) {}

    public void setState(boolean loaded, String url) {}

    public void setIsBackButtonVisible(boolean visible) {}

    public String getUrlLoaded() {
        return null;
    }

    public boolean hasUserPermissions() {
        return false;
    }

    public void setBotUser(TLRPC.User botUser) {}

    public boolean isPageLoaded() {
        return false;
    }

    public void setParentActivity(Activity parentActivity) {}

    public void restoreButtonData() {}

    public void onInvoiceStatusUpdate(String slug, String status) {}

    public void onInvoiceStatusUpdate(String slug, String status, boolean ignoreCurrentCheck) {}

    public void onSettingsButtonPressed() {}

    public void onMainButtonPressed() {}

    public void onSecondaryButtonPressed() {}

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {}

    public void onActivityResult(int requestCode, int resultCode, Intent data) {}

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public void invalidateViewPortHeight() {}

    public void invalidateViewPortHeight(boolean isStable) {}

    public int getMinHeight() {
        return 0;
    }

    public void setViewPortHeightOffset(float viewPortHeightOffset) {}

    public void invalidateViewPortHeight(boolean isStable, boolean force) {}

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        return super.drawChild(canvas, child, drawingTime);
    }

    public void setForceHeight(int height) {}

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setWebViewProgressListener(Consumer<Float> webViewProgressListener) {}

    public MyWebView getWebView() {
        return null;
    }

    public void loadFlickerAndSettingsItem(int currentAccount, long botId, ActionBarMenuSubItem settingsItem) {}

    public void reload() {}

    public void loadUrl(int currentAccount, String url) {}

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void preserveWebView() {}

    public void destroyWebView() {}

    public void resetWebView() {}

    public boolean isBackButtonVisible() {
        return false;
    }

    public void evaluateJs(String script) {}

    public void evaluateJs(String script, boolean create) {}

    @Override
    public void didReceivedNotification(int id, int account, Object... args) {}

    public void notifyThemeChanged() {}

    public void setWebViewScrollListener(WebViewScrollListener webViewScrollListener) {}

    public void setOnCloseRequestedListener(Runnable listener) {}

    public void setWasOpenedByLinkIntent(boolean value) {}

    public void setWasOpenedByBot(WebViewRequestProps props) {}

    public void setDelegate(Delegate delegate) {}

    public String getOpenURL() {
        return null;
    }

    public void reportSafeInsets(Rect insets, int topContentMargin) {}

    public void notifyEmojiStatusAccess(String status) {}

    public void onWebViewCreated(MyWebView webView) {}

    public void onWebViewDestroyed(MyWebView webView) {}

    /**
     * Stub inner class
     */
    public static class BotWebViewProxy {
        public BotWebViewContainer container;

        public BotWebViewProxy(BotWebViewContainer container) {
            this.container = container;
        }

        public void setContainer(BotWebViewContainer container) {
            this.container = container;
        }

        public void postEvent(String eventType, String eventData) {}
    }

    /**
     * Stub inner class
     */
    public static class WebViewProxy {
        public BotWebViewContainer container;
        public final MyWebView webView;

        public WebViewProxy(MyWebView webView, BotWebViewContainer container) {
            this.webView = webView;
            this.container = container;
        }

        public void setContainer(BotWebViewContainer container) {
            this.container = container;
        }

        public void post(String type, String data) {}

        public void resolveShare(String json, byte[] file, String fileName, String fileMimeType) {}
    }

    /**
     * Stub interface
     */
    public interface WebViewScrollListener {
        void onWebViewScrolled(WebView webView, int scrollX, int scrollY);
    }

    /**
     * Stub interface - all methods have default implementations
     */
    public interface Delegate {
        default void onCloseRequested(Runnable callback) {}
        default void onWebAppSetupClosingBehavior(boolean needConfirmation) {}
        default void onWebAppSwipingBehavior(boolean allowSwiping) {}
        default void onCloseToTabs() {}
        default void onSharedTo(java.util.ArrayList<Long> dialogIds) {}
        default void onOrientationLockChanged(boolean locked) {}
        default void onOpenBackFromTabs() {}
        default void onSendWebViewData(String data) {}
        default void onWebAppSetActionBarColor(int colorKey, int color, boolean isOverrideColor) {}
        default void onWebAppSetNavigationBarColor(int color) {}
        default void onWebAppSetBackgroundColor(int color) {}
        default void onLocationGranted(boolean granted) {}
        default void onEmojiStatusGranted(boolean granted) {}
        default void onEmojiStatusSet(TLRPC.Document document) {}
        default void onSetBackButtonVisible(boolean visible) {}
        default void onSetSettingsButtonVisible(boolean visible) {}
        default void onWebAppOpenInvoice(TLRPC.InputInvoice inputInvoice, String slug, TLObject response) {}
        default void onWebAppExpand() {}
        default void onWebAppSwitchInlineQuery(TLRPC.User botUser, String query, java.util.List<String> chatTypes) {}
        default void onSetupMainButton(boolean isVisible, boolean isActive, String text, int color, int textColor, boolean isProgressVisible, boolean hasShineEffect) {}
        default void onSetupSecondaryButton(boolean isVisible, boolean isActive, String text, int color, int textColor, boolean isProgressVisible, boolean hasShineEffect, String position) {}
        default String getWebAppName() { return null; }
        default boolean isClipboardAvailable() { return false; }
        default String onFullscreenRequested(boolean fullscreen) { return null; }
        default void onInstantClose() {}
        default void onWebAppBackgroundChanged(boolean actionBarColor, int color) {}
        default Object getBotSensors() { return null; }
    }

    /**
     * Stub inner class
     */
    public static class DangerousWebWarning {
        public String domain;
        public String message;

        public DangerousWebWarning(String domain, String message) {
            this.domain = domain;
            this.message = message;
        }
    }

    /**
     * Stub inner class
     */
    public static class MyWebView extends WebView {
        public boolean lastTitleGot = false;
        public String lastTitle = null;
        public String lastSiteName = null;
        public boolean lastActionBarColorGot = false;
        public int lastActionBarColor = 0;
        public boolean lastBackgroundColorGot = false;
        public int lastBackgroundColor = 0;
        public boolean lastFaviconGot = false;
        public Bitmap lastFavicon = null;

        public MyWebView(Activity activity) {
            super(activity);
        }

        public void d(String s) {}

        public Bitmap getFavicon(String url) {
            return null;
        }

        public void loadUrl(String url, java.util.Map<String, String> additionalHttpHeaders) {
            // Override Android WebView loadUrl to do nothing
        }

        public void loadUrl(String url, Object metadata) {
            // Stub overload for WebMetadata
        }

        public int getSearchIndex() {
            return 0;
        }

        public int getSearchCount() {
            return 0;
        }

        public void searchNext() {
            // Do nothing
        }

        public void searchPrev() {
            // Do nothing
        }

        public void closeSearch() {
            // Do nothing
        }

        public void search(String query, Runnable callback) {
            if (callback != null) {
                callback.run();
            }
        }

        public String getOpenURL() {
            return null;
        }

        public boolean isPageLoaded() {
            return false;
        }

        public boolean isUrlDangerous() {
            return false;
        }

        public float getScrollProgress() {
            return 0f;
        }

        public void setScrollProgress(float progress) {
            // Do nothing
        }
    }

    /**
     * Stub inner class for PopupButton referenced by callers
     */
    public final static class PopupButton {
        public String id;
        public String type;
        public String text;
    }
}
