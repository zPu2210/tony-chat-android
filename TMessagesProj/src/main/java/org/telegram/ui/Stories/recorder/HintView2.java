package org.telegram.ui.Stories.recorder;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextPaint;
import android.view.View;

/**
 * HintView2 stub - Stories removed in Tony Chat
 */
public class HintView2 extends View {
    public static final int DIRECTION_TOP = 0;
    public static final int DIRECTION_BOTTOM = 1;
    public static final int DIRECTION_LEFT = 2;
    public static final int DIRECTION_RIGHT = 3;

    private CharSequence text = "";

    public HintView2(Context context) {
        super(context);
    }

    public HintView2(Context context, int direction) {
        super(context);
    }

    public HintView2 setJoint(float x, float y) { return this; }
    public HintView2 setText(CharSequence text) {
        this.text = text;
        return this;
    }
    public boolean shown() { return false; }
    public CharSequence getText() {
        return text;
    }
    public TextPaint getTextPaint() {
        return new TextPaint();
    }
    public HintView2 setMultilineText(boolean multiline) { return this; }
    public HintView2 setMaxWidthPx(int width) { return this; }
    public HintView2 setIcon(int resId) { return this; }
    public HintView2 setIcon(Object drawable) { return this; }
    public HintView2 setRounding(int rounding) { return this; }
    public void show() {}
    public void hide() {}
    public void hide(boolean animated) {}
    public HintView2 setOnHiddenListener(Runnable callback) { return this; }
    public HintView2 setInnerPadding(int left, int top, int right, int bottom) { return this; }
    public HintView2 setIconMargin(int margin) { return this; }
    public HintView2 setIconTranslate(float x, float y) { return this; }
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
    }

    public static int cutInFancyHalf(CharSequence text, TextPaint paint) {
        return 300; // Default reasonable width
    }

    public static float measureCorrectly(CharSequence text, TextPaint paint) {
        if (text == null || paint == null) return 0;
        return paint.measureText(text.toString());
    }

    public HintView2 setText(CharSequence text, boolean animated) {
        this.text = text;
        return this;
    }

    public HintView2 setTextAlign(Object alignment) {
        return this;
    }

    public HintView2 setCloseButton(boolean show) {
        return this;
    }

    public HintView2 setDuration(long duration) {
        return this;
    }

    public HintView2 setHideByTouch(boolean hide) {
        return this;
    }

    public HintView2 useScale(boolean scale) {
        return this;
    }

    public HintView2 setJointPx(float pos, float offset) {
        return this;
    }

    public HintView2 setMaxWidth(int width) {
        return this;
    }

    public HintView2 setBgColor(int color) {
        return this;
    }

    public HintView2 setFlicker(float intensity, int color) {
        return this; // Tony Chat: Stories removed
    }

    public HintView2 setTextSize(float size) {
        return this; // Tony Chat: Stories removed
    }

    public HintView2 setTextTypeface(Object typeface) {
        return this; // Tony Chat: Stories removed
    }

    public HintView2 setInnerPadding(float left, float top, float right, float bottom) {
        return this; // Tony Chat: Stories removed - overload with floats
    }

    public HintView2 setArrowSize(float width, float height) {
        return this; // Tony Chat: Stories removed
    }

    public HintView2 setRoundingWithCornerEffect(boolean withEffect) {
        return this; // Tony Chat: Stories removed
    }
}
