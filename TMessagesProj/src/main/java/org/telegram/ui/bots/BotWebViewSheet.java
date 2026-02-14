package org.telegram.ui.bots;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import org.json.JSONObject;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.TLRPC;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheetTabDialog;
import org.telegram.ui.ActionBar.BottomSheetTabs;
import org.telegram.ui.ActionBar.BottomSheetTabsOverlay;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.SizeNotifierFrameLayout;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * STUB: Bot Web View Sheet removed from Tony Chat.
 * Bot platform UI features are not supported.
 */
public class BotWebViewSheet extends Dialog implements NotificationCenter.NotificationCenterDelegate, BottomSheetTabsOverlay.Sheet {

    public final static int TYPE_WEB_VIEW_BUTTON = 0;
    public final static int TYPE_SIMPLE_WEB_VIEW_BUTTON = 1;
    public final static int TYPE_BOT_MENU_BUTTON = 2;
    public final static int TYPE_WEB_VIEW_BOT_APP = 3;
    public final static int TYPE_WEB_VIEW_BOT_MAIN = 4;

    public final static int FLAG_FROM_INLINE_SWITCH = 1;
    public final static int FLAG_FROM_SIDE_MENU = 2;

    public static HashSet<BotWebViewSheet> activeSheets = new HashSet<>();

    @IntDef({TYPE_WEB_VIEW_BUTTON, TYPE_SIMPLE_WEB_VIEW_BUTTON, TYPE_BOT_MENU_BUTTON, TYPE_WEB_VIEW_BOT_APP, TYPE_WEB_VIEW_BOT_MAIN})
    public @interface WebViewType {}

    public boolean fromTab;
    public boolean showExpanded;
    public float showOffsetY;

    public BotWebViewSheet(@NonNull Context context, Theme.ResourcesProvider resourcesProvider) {
        super(context);
    }

    public void showJustAddedBulletin() {}

    public BottomSheetTabs.WebTabData saveState() {
        return null;
    }

    public Activity getActivity() {
        return null;
    }

    public boolean restoreState(BaseFragment fragment, BottomSheetTabs.WebTabData tab) {
        return false;
    }

    public void setParentActivity(Activity parentActivity) {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void updateFullscreenLayout() {}

    public void updateWindowFlags() {}

    public void onAttachedToWindow() {}

    public void onDetachedFromWindow() {}

    public static JSONObject makeThemeParams(Theme.ResourcesProvider resourcesProvider) {
        return new JSONObject();
    }

    public static JSONObject makeThemeParams(Theme.ResourcesProvider resourcesProvider, boolean hexify) {
        return new JSONObject();
    }

    public void setDefaultFullsize(boolean fullsize) {}

    public void setWasOpenedByBot(WebViewRequestProps props) {}

    public void setWasOpenedByLinkIntent(boolean value) {}

    public void setNeedsContext(boolean needsContext) {}

    public boolean isFullSize() {
        return false;
    }

    public boolean setDialog(BottomSheetTabDialog dialog) {
        return false;
    }

    public boolean hadDialog() {
        return false;
    }

    public void setOnVerifiedAge(Utilities.Callback4<Boolean, Double, String, Double> callback) {}

    public void setFullscreen(boolean fullscreen, boolean animated) {}

    public long getBotId() {
        return 0;
    }

    public void requestWebView(BaseFragment fragment, WebViewRequestProps props) {}

    public static void deleteBot(int currentAccount, long botId, Runnable onDone) {
        if (onDone != null) {
            onDone.run();
        }
    }

    @Override
    public void show() {
        // Do nothing - bot web views disabled
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void didReceivedNotification(int id, int account, Object... args) {}

    @Override
    public BottomSheetTabsOverlay.SheetView getWindowView() {
        return new WindowView(getContext());
    }

    @Override
    public void dismiss(boolean tabs) {
        dismiss();
    }

    @Override
    public void release() {}

    @Override
    public int getNavigationBarColor(int color) {
        return color;
    }

    public void dismiss(boolean animated, Runnable onDismiss) {
        if (onDismiss != null) {
            onDismiss.run();
        }
    }

    public String getOpenURL() {
        return null;
    }

    public boolean halfSize() {
        return false;
    }

    /**
     * Stub inner class for compatibility
     */
    public class WindowView extends SizeNotifierFrameLayout implements BottomSheetTabsOverlay.SheetView {
        public WindowView(Context context) {
            super(context);
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
    }
}
