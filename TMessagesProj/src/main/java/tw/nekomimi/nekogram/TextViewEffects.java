package tw.nekomimi.nekogram;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import org.telegram.ui.Components.LinkSpanDrawable;

/**
 * Stub: TextView with spoiler effects
 */
public class TextViewEffects extends LinkSpanDrawable.LinksTextView {
    public TextViewEffects(Context context) {
        super(context);
    }

    public TextViewEffects(Context context, AttributeSet attrs) {
        super(context, null);
    }

    public TextViewEffects(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, null, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
