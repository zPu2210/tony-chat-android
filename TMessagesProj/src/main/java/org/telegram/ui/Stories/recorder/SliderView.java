package org.telegram.ui.Stories.recorder;

import android.content.Context;
import android.view.View;

/**
 * SliderView stub - Stories removed in Tony Chat
 */
public class SliderView extends View {
    public static final int TYPE_DIMMING = 1;

    public SliderView(Context context) {
        super(context);
    }

    public SliderView(Context context, int type) {
        super(context);
    }

    public void setValue(float value) {}
    public float getValue() { return 0f; }
    public void setMinMax(float min, float max) {}
    public void setOnValueChange(OnValueChange listener) {}
    public void animateValueTo(float value) {}

    public interface OnValueChange {
        void onValueChange(float value);
    }
}
