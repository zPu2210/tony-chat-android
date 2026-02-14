package org.telegram.ui.Stars;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;

import org.telegram.messenger.utils.tlutils.AmountUtils;
import org.telegram.tgnet.tl.TL_stars;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.Theme;

/**
 * STUB: Stars intro feature removed from Tony Chat.
 */
public class StarsIntroActivity extends BaseFragment {

    // showStarsIntro - no-op
    public static void showStarsIntro(Object... args) {}

    // formatStarsAmount - all variants
    public static String formatStarsAmount(long amount) { return String.valueOf(amount); }
    public static String formatStarsAmount(TL_stars.StarsAmount amount) { return amount == null ? "0" : String.valueOf(amount.amount); }
    public static String formatStarsAmount(TL_stars.StarsAmount amount, float textSize) { return amount == null ? "0" : String.valueOf(amount.amount); }
    public static String formatStarsAmount(TL_stars.StarsAmount amount, float textSize, char separator) { return amount == null ? "0" : String.valueOf(amount.amount); }
    public static String formatStarsAmountShort(long amount) { return String.valueOf(amount); }
    public static String formatStarsAmountShort(TL_stars.StarsAmount amount) { return amount == null ? "0" : String.valueOf(amount.amount); }

    // replaceStarsWithPlain - all variants return String to match caller expectations
    public static String replaceStarsWithPlain(CharSequence text) { return text != null ? text.toString() : ""; }
    public static String replaceStarsWithPlain(CharSequence text, float size) { return text != null ? text.toString() : ""; }
    public static String replaceStarsWithPlain(boolean flag, CharSequence text) { return text != null ? text.toString() : ""; }
    public static String replaceStarsWithPlain(boolean flag, CharSequence text, float size) { return text != null ? text.toString() : ""; }
    public static String replaceStarsWithPlain(boolean flag, CharSequence text, float size, Object[] spans) { return text != null ? text.toString() : ""; }
    public static String replaceStarsWithPlain(boolean flag, String text, float size, Object[] spans) { return text; }

    // replaceStars - all variants return String to match caller expectations
    public static String replaceStars(String text, Object[] spans) { return text; }
    public static CharSequence replaceStars(String text, float size, Object[] spans) { return text; }
    public static String replaceStars(Object text) { return text != null ? text.toString() : ""; }
    public static String replaceStars(CharSequence text) { return text != null ? text.toString() : ""; }
    public static String replaceStars(CharSequence text, float size, Object obj, int a, int b, float c) { return text != null ? text.toString() : ""; }
    public static String replaceStars(String text) { return text; }
    public static String replaceStars(String text, float size) { return text; }
    public static String replaceStars(boolean flag, String text) { return text; }
    public static String replaceStars(boolean flag, SpannableStringBuilder text) { return text != null ? text.toString() : ""; }
    public static SpannableStringBuilder replaceStars(SpannableStringBuilder text, float size, Object[] spans) { return text; }
    public static SpannableStringBuilder replaceStars(SpannableStringBuilder text) { return text; }

    // showTransactionSheet - all variants no-op
    public static void showTransactionSheet(Object... args) {}

    // getTonGiftEmoji
    public static String getTonGiftEmoji(long amount) { return ""; }

    // showMediaPriceSheet
    public static void showMediaPriceSheet(Object... args) {}
    public static <T> void showMediaPriceSheet(Context context, long price, boolean flag, org.telegram.messenger.Utilities.Callback2<Long, Runnable> callback, Object resourceProvider) {}
    public static <T> void showMediaPriceSheet(Context context, Object amount, boolean flag, org.telegram.messenger.Utilities.Callback2<Long, Runnable> callback, Object resourceProvider) {}

    // StarsNeededSheet stub
    public static class StarsNeededSheet {
        public static final int TYPE_PRIVATE_MESSAGE = 0;
        public static final int TYPE_SUBSCRIPTION = 1;
        public static final int TYPE_LINK = 2;
        public static final int TYPE_BOT = 3;
        public static final int TYPE_CHAT_ADS = 4;
        public static final int TYPE_REACTIONS = 5;

        public StarsNeededSheet(Context context, Object resourceProvider, long amount, int type, String name, Runnable callback, long dialogId) {}
        public StarsNeededSheet(Context context, Object resourceProvider, Object amount, int type, CharSequence name, Runnable callback, long dialogId) {}
        public void show() {}
        public void setOnDismissListener(Object listener) {}
    }

    // StarsOptionsSheet stub
    public static class StarsOptionsSheet {
        public StarsOptionsSheet(Context context, Object resourceProvider) {}
        public void show() {}
    }

    public static class StarsTransactionView {
        public static Drawable getPlatformDrawable(Context context, String platform) { return null; }
    }

    // GiftStarsSheet stub
    public static class GiftStarsSheet extends BottomSheet {
        public GiftStarsSheet(Context context) {
            super(context, false);
        }

        public GiftStarsSheet(Context context, Object resourceProvider, Object user, Runnable dismiss) {
            super(context, false);
        }

        public GiftStarsSheet(BaseFragment fragment, Object... args) {
            super(fragment.getContext(), false);
        }

        public void makeAttached(Object fragment) {}
        public void show() {}
    }

    // ExpandView stub
    public static class ExpandView extends android.view.View {
        public ExpandView(Context context) {
            super(context);
        }

        public ExpandView(Context context, Object resourcesProvider) {
            super(context);
        }

        public void set(Object... args) {}
        public void updateGradient() {}
    }

    // showBoostsSheet static method
    public static void showBoostsSheet(Object... args) {
        // No-op stub
    }
}
