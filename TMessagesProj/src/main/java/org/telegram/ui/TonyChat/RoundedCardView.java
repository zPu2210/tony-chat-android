package org.telegram.ui.TonyChat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.FrameLayout;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.Theme;

/**
 * Simple rounded-corner card wrapper with ripple support.
 * Shared across Tony Chat custom screens.
 */
public class RoundedCardView extends FrameLayout {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF rect = new RectF();
    private final float radius = AndroidUtilities.dp(12);

    public RoundedCardView(Context context) {
        super(context);
        setWillNotDraw(false);
        paint.setColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        TonyColors.applyRipple(this, context, 12);
    }

    public void setCardColor(int color) {
        paint.setColor(color);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        rect.set(0, 0, getWidth(), getHeight());
        canvas.drawRoundRect(rect, radius, radius, paint);
    }
}
