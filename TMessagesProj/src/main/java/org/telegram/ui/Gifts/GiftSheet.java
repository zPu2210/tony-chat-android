package org.telegram.ui.Gifts;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.widget.FrameLayout;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.Theme;
import java.util.List;

/**
 * STUB: Gifts feature removed from Tony Chat.
 */
public class GiftSheet extends BottomSheet {

    public GiftSheet(Context context) { super(context, false); }
    public GiftSheet(Context context, int account, long dialogId, Object param1) { super(context, false); }
    public GiftSheet(Context context, int account, long dialogId, Object param1, Object param2) { super(context, false); }
    public GiftSheet(Context context, int account, long dialogId, List options, Runnable dismiss) { super(context, false); }
    public GiftSheet(Activity activity, int account, long dialogId, Object param1) { super(activity, false); }

    public GiftSheet setBirthday(boolean b) { return this; }

    public static void show(Object... args) {}

    public interface Delegate { void onGiftSent(); }

    public static class GiftCell extends FrameLayout {
        public GiftCell(Context context, int account, Theme.ResourcesProvider rp) { super(context); }
        public void set(Object gift, Object rp) {}
        public void setSelected(boolean selected, boolean animated) {}
        public void setStarsGift(Object gift, boolean a, boolean b, boolean c, boolean d) {}
        public long getGiftId() { return 0; }
    }

    public static class CardBackground extends Drawable {
        public static final float PADDING_HORIZONTAL_DP = 12f;
        public static final float PADDING_VERTICAL_DP = 12f;

        public CardBackground() {}
        public CardBackground(Object parent, Object resourcesProvider, boolean flag) {}

        public void setBackdrop(Object backdrop) {}
        public void setPattern(Object pattern) {}
        public void setSelected(boolean selected, boolean animated) {}
        @Override public void draw(Canvas canvas) {}
        @Override public void setAlpha(int alpha) {}
        @Override public void setColorFilter(ColorFilter cf) {}
        @Override public int getOpacity() { return 0; }
        public void setBounds(int left, int top, int right, int bottom) { super.setBounds(left, top, right, bottom); }
    }

    public static class RibbonDrawable {
        public static void fillRibbonPath(Object path, float scale) {}
    }

    public static class Tabs extends FrameLayout {
        public Tabs(Context context, boolean flag, Object resourceProvider) { super(context); }
        public void set(Object tabs) {}
        public void set(int start, java.util.ArrayList<CharSequence> tabs, int selected, org.telegram.messenger.Utilities.Callback<Integer> callback) {}
        public void setSelected(int index) {}
        public void setSelected(int index, boolean animated) {}
        public void updateColors() {}
        public void setOnTabSelected(Object listener) {}
    }
}
