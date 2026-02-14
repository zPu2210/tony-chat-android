package org.telegram.ui.Stories;

import android.graphics.drawable.Drawable;

/**
 * StoryReactionWidgetBackground stub - Stories removed in Tony Chat
 */
public class StoryReactionWidgetBackground extends Drawable {
    public int style;

    // Tony Chat: ReactionWidgetEntityView constructor
    public StoryReactionWidgetBackground(Object view) {}

    public void setColor(int color) {}
    public void nextStyle() {}
    public void updateShadow() {}
    public void updateShadowLayer(float scale) {} // Tony Chat: ReactionWidgetEntityView
    public boolean isDarkStyle() { return false; } // Tony Chat: ReactionWidgetEntityView
    public void setMirror(boolean mirror, boolean animated) {} // Tony Chat: ReactionWidgetEntityView

    @Override
    public void draw(android.graphics.Canvas canvas) {}

    @Override
    public void setAlpha(int alpha) {}

    @Override
    public void setColorFilter(android.graphics.ColorFilter colorFilter) {}

    @Override
    public int getOpacity() {
        return android.graphics.PixelFormat.TRANSLUCENT;
    }
}
