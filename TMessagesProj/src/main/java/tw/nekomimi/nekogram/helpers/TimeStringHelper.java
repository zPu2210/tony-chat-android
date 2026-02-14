package tw.nekomimi.nekogram.helpers;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ReplacementSpan;
import android.view.View;

import androidx.annotation.NonNull;

public class TimeStringHelper {
    public static CharSequence getColoredAdminString(View parent, TextPaint namePaint, SpannableStringBuilder sb) {
        return "";
    }

    public static class adminStringSpan extends ReplacementSpan {
        public adminStringSpan(View parent, TextPaint namePaint, SpannableStringBuilder sb) {
        }

        public void setText(SpannableStringBuilder sb, boolean animated) {
        }

        public void setColor(int color) {
        }

        @Override
        public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
            return 0;
        }

        public int getWidth() {
            return 0;
        }

        @Override
        public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
        }
    }
}
