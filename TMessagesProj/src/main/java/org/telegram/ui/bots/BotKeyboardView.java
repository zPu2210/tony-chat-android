package org.telegram.ui.bots;

import android.content.Context;
import android.widget.LinearLayout;

import org.telegram.tgnet.TLRPC;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.inset.InAppKeyboardInsetView;

/**
 * STUB: Bot Keyboard View removed from Tony Chat.
 * Bot inline keyboard UI features are not supported.
 */
public class BotKeyboardView extends LinearLayout implements InAppKeyboardInsetView {

    public BotKeyboardView(Context context, Theme.ResourcesProvider resourcesProvider) {
        super(context);
    }

    public BotKeyboardView(Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }

    public BotKeyboardView(Context context) {
        super(context);
    }

    public void setButtons(TLRPC.TL_replyKeyboardMarkup buttons) {
        // Do nothing
    }

    public void setDelegate(Delegate delegate) {
        // Do nothing
    }

    public boolean isVisible() {
        return false;
    }

    public void setVisibility(int visibility) {
        super.setVisibility(GONE);
    }

    public void updateColors() {
        // Do nothing
    }

    public void setPanelHeight(int height) {
        // Do nothing
    }

    public int getKeyboardHeight() {
        return 0;
    }

    public boolean isFullSize() {
        return false;
    }

    public void invalidateViews() {
        // Do nothing
    }

    @Override
    public void invalidate() {
        // Do nothing
    }

    @Override
    public void applyInAppKeyboardAnimatedHeight(float height) {
        // Do nothing - InAppKeyboardInsetView method
    }

    @Override
    public void applyNavigationBarHeight(int height) {
        // Do nothing - InAppKeyboardInsetView method
    }

    /**
     * Stub interface
     */
    public interface Delegate {
        void onBotKeyboardButtonPressed(TLRPC.KeyboardButton button);
    }
}
