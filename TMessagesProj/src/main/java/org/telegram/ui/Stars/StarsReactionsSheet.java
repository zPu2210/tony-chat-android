package org.telegram.ui.Stars;

import android.content.Context;
import org.telegram.ui.ActionBar.BottomSheet;

/**
 * STUB: Stars reactions feature removed from Tony Chat.
 */
public class StarsReactionsSheet extends BottomSheet {

    public StarsReactionsSheet(Context context) {
        super(context, false);
    }

    // Varargs constructor for all call patterns
    public StarsReactionsSheet(Context context, Object... args) {
        super(context, false);
    }

    public void setMessageCell(Object... args) {}

    public static void show(Object... args) {}

    public static class Particles {
        public static final int TYPE_RADIAL = 0;
        public static final int TYPE_LINEAR = 1;
        public final android.graphics.RectF bounds = new android.graphics.RectF();

        public Particles(int type, int count) {}

        public void setVisible(float visible) {}
        public void setBounds(android.graphics.Rect bounds) {}
        public void setBounds(android.graphics.RectF bounds) {}
        public boolean process() { return false; }
        public void draw(android.graphics.Canvas canvas, int color) {}
        public void draw(android.graphics.Canvas canvas, Integer color) {}
    }
}
