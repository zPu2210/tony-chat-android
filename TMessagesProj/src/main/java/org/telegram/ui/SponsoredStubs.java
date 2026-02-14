package org.telegram.ui;

import android.content.Context;
import android.text.TextPaint;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import org.telegram.tgnet.tl.TL_stars;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.Theme;

/**
 * Stub utilities for removed sponsored/monetization features.
 * Provides no-op implementations to avoid breaking existing code.
 */
public class SponsoredStubs {

    /**
     * Stub for ChannelMonetizationLayout.replaceTON - returns text as-is
     */
    public static CharSequence replaceTON(CharSequence text, TextPaint textPaint) {
        return text;
    }

    public static CharSequence replaceTON(CharSequence text, TextPaint textPaint, boolean large) {
        return text;
    }

    public static CharSequence replaceTON(CharSequence text, TextPaint textPaint, float scale, boolean large) {
        return text;
    }

    public static CharSequence replaceTON(CharSequence text, TextPaint textPaint, float scale, float translateY, boolean large) {
        return text;
    }
}
