package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import org.telegram.ui.ActionBar.Theme;

/**
 * STUB - Group call user cell removed for Tony Chat
 */
public class GroupCallUserCell extends View {

    public GroupCallUserCell(Context context) {
        super(context);
    }

    // Tony Chat: Additional stub methods for VoIP removal
    public void setGrayIconColor(int key1, int color) {
    }
    
    public static class AvatarWavesDrawable extends Drawable {

        public float amplitude;
        public float animateToAmplitude;
        public float animateAmplitudeDiff;

        public AvatarWavesDrawable(int w, int h) {
        }

        @Override
        public void draw(Canvas canvas) {
        }

        public void draw(Canvas canvas, float cx, float cy, Object view) {
        }

        @Override
        public void setAlpha(int alpha) {
        }

        @Override
        public void setColorFilter(android.graphics.ColorFilter colorFilter) {
        }

        @Override
        public int getOpacity() {
            return 0;
        }

        public void setShowWaves(boolean show, Object view) {
        }

        public void setAmplitude(double value) {
            this.animateToAmplitude = (float) value;
        }

        // Tony Chat: Additional stub methods for VoIP removal
        public void setColor(int color) {
        }

        public void update() {
        }

        public float getAvatarScale() {
            return 1.0f;
        }
    }
}
