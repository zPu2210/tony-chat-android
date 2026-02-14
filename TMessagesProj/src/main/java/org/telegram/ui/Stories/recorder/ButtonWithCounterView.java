package org.telegram.ui.Stories.recorder;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.FrameLayout;

/**
 * ButtonWithCounterView stub - Stories removed in Tony Chat
 */
public class ButtonWithCounterView extends FrameLayout {
    protected boolean wrapContentDynamic = false;
    public TextHelper text = new TextHelper(); // Tony Chat: Made public for PostsSearchContainer access
    public SubTextHelper subText = new SubTextHelper(); // Tony Chat: Added for PostsSearchContainer
    protected boolean loading = false;

    public static class TextHelper {
        private CharSequence currentText = "";
        public float getCurrentWidth() {
            if (currentText == null) return 0;
            return currentText.length() * 10; // Rough estimate
        }
        public void setTypeface(Typeface typeface) {}
        public void setText(CharSequence text) {
            this.currentText = text;
        }
        public android.text.TextPaint getTextPaint() {
            return new android.text.TextPaint();
        }
        public void setHacks(boolean a, boolean b, boolean c) {}
    }

    public static class SubTextHelper {
        public void setHacks(boolean a, boolean b, boolean c) {}
    }

    public ButtonWithCounterView(Context context, Object resourcesProvider) {
        super(context);
    }

    public ButtonWithCounterView(Context context, boolean withCounter, Object resourcesProvider) {
        super(context);
    }

    public void setCount(int count, boolean animated) {}
    public void setText(String text, boolean animated) {}
    public void setText(CharSequence text, boolean animated) {}
    public void setText(android.text.SpannableStringBuilder text, boolean animated, boolean parseEntities) {} // Tony Chat: MultiContactsSelectorBottomSheet
    public void setEnabled(boolean enabled) {}
    public void withCounterIcon() {}
    public void setSubText(CharSequence text, boolean animated) {}
    public void setTextColor(int color) {}
    public void setFlickeringLoading(boolean flickering) {}
    public void disableRippleView() {}
    public void wrapContentDynamic() {
        wrapContentDynamic = true;
    }
    public boolean isLoading() {
        return loading;
    }
    public void setLoading(boolean loading) {
        this.loading = loading;
    }
    public void setTimer(int seconds, Runnable callback) {} // Tony Chat: UnconfirmedAuthHintCell
    public boolean isTimerActive() { return false; } // Tony Chat: UnconfirmedAuthHintCell
    public void setColor(int color) {} // Tony Chat: CreateRtmpStreamBottomSheet
    public void setCounterColor(int color) {} // Tony Chat: ReassignBoostBottomSheet
    public void setShowZero(boolean show) {} // Tony Chat: MultiContactsSelectorBottomSheet
    public void updateColors() {} // Tony Chat: PeerColorActivity no-arg
    public void updateColors(org.telegram.ui.ActionBar.Theme.ResourcesProvider resourcesProvider) {} // Tony Chat: PeerColorActivity
    public android.text.TextPaint getTextPaint() {
        return text.getTextPaint();
    } // Tony Chat: PostsSearchContainer compatibility

    protected boolean subTextSplitToWords() {
        return true;
    }

    // Tony Chat: MultiContactsSelectorBottomSheet override
    protected float calculateCounterWidth(float width, float percent) {
        return width;
    }
}
