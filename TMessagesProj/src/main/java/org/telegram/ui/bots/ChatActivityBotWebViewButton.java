package org.telegram.ui.bots;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.widget.FrameLayout;

import org.telegram.ui.Components.SimpleFloatPropertyCompat;

/**
 * STUB: Chat Activity Bot Web View Button disabled in Tony Chat.
 * Bot web view button functionality is not supported.
 * This stub exists to prevent compilation errors but renders nothing.
 */
public class ChatActivityBotWebViewButton extends FrameLayout {
    public final static SimpleFloatPropertyCompat<ChatActivityBotWebViewButton> PROGRESS_PROPERTY =
        new SimpleFloatPropertyCompat<ChatActivityBotWebViewButton>("progress",
            obj -> 0f,
            (obj, value) -> {})
            .setMultiplier(100f);

    public ChatActivityBotWebViewButton(Context context) {
        super(context);
        setVisibility(GONE);
        setWillNotDraw(true);
    }

    public void setBotMenuButton(BotCommandsMenuView menuButton) {
        // Do nothing - bot buttons disabled
    }

    public void setupButtonParams(boolean isActive, String text, int color, int textColor, boolean isProgressVisible) {
        // Do nothing - bot buttons disabled
        setVisibility(GONE);
    }

    public void setProgress(float progress) {
        // Do nothing - bot buttons disabled
    }

    public void setMeasuredButtonWidth(int width) {
        // Do nothing - bot buttons disabled
    }

    @Override
    public void draw(Canvas canvas) {
        // Do nothing - bot buttons disabled
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(0, 0);
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(GONE);
    }
}
