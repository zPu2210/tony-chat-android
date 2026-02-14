package org.telegram.ui.Components.voip;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Path;
import android.graphics.drawable.Drawable;

/**
 * STUB - Cell flicker animation removed for Tony Chat
 */
public class CellFlickerDrawable extends Drawable {

    public boolean drawFrame;
    public float progress;
    public boolean repeatEnabled;
    public float repeatProgress;
    public float animationSpeedScale = 1.0f;

    public CellFlickerDrawable() {
    }

    public CellFlickerDrawable(int repeatDelay, int repeatCount) {
    }

    // Tony Chat: Additional constructor for Stories
    public CellFlickerDrawable(int arg1, int arg2, int arg3) {
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public void setParentWidth(int parentWidth) {
    }

    public void draw(Canvas canvas, Object rect, float radius, Object view) {
    }

    // Tony Chat: Additional draw overload for Premium features
    public void draw(Canvas canvas, Path path, Object view) {
    }

    @Override
    public void draw(Canvas canvas) {
    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
    }

    @Override
    public int getOpacity() {
        return 0;
    }

    // Tony Chat: Additional stub methods for VoIP/Premium removal
    public float getProgress() {
        return progress;
    }

    public void setOnRestartCallback(Runnable callback) {
    }
}
