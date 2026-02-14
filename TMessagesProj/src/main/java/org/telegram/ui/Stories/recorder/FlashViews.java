package org.telegram.ui.Stories.recorder;

import android.content.Context;
import android.view.View;

/**
 * FlashViews stub - Stories removed in Tony Chat
 */
public class FlashViews extends View {
    public View backgroundView;
    public View foregroundView;

    public FlashViews(Context context) {
        super(context);
        backgroundView = new View(context);
        foregroundView = new View(context);
    }

    public FlashViews(Context context, Object p1, Object p2, Object p3) {
        super(context);
        backgroundView = new View(context);
        foregroundView = new View(context);
    }

    public void setWarmth(float warmth) {}
    public void add(View view) {}
    public void flashIn(Object callback) {}
    public void flashOut() {}

    public static class Drawable extends android.graphics.drawable.Drawable {
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

    public static class ImageViewInvertable extends android.widget.ImageView {
        public ImageViewInvertable(Context context) {
            super(context);
        }

        public void setInvert(float invert) {}
    }
}
