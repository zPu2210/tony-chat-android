package org.telegram.ui.Components.Premium;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * STUB: Carousel view for Premium features removed.
 * This stub prevents compilation errors in callers.
 */
public class CarouselView extends View implements PagerHeaderView {

    public CarouselView(Context context) {
        super(context);
    }

    @Override
    public void setOffset(float v) {
        // No-op
    }

    // Inner class referenced by PremiumFeatureBottomSheet and ReactionDrawingObject
    public static class DrawingObject {
        public float x;
        public float y;
        public float h;
        public float w;
        public float xFinished;
        public float yFinished;
        public float randomScale;
        public int position;

        public CarouselView carouselView;

        public void draw(Canvas canvas, long time, float scale, float width, float height, Paint paint) {
        }

        public void draw(Canvas canvas, float cX, float cY, float globalScale) {
        }

        public void onAttachToWindow(View parentView, int i) {
        }

        public void onDetachFromWindow() {
        }

        public boolean checkOnScreen(float width, float height) {
            return false;
        }

        public boolean checkTap(float x, float y) {
            return false;
        }

        public void select() {
        }

        public void hideAnimation() {
        }

        public void set(Object reaction) {
        }
    }

    public void autoplayToNext() {
    }
}
