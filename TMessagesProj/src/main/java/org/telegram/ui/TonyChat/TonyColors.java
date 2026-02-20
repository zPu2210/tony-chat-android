package org.telegram.ui.TonyChat;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.graphics.Outline;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.Theme;

/**
 * Dark-mode-aware color constants for Tony Chat custom screens.
 * Uses Theme.isCurrentThemeDark() to pick light vs dark variants.
 */
public final class TonyColors {

    private TonyColors() {}

    // Primary (Warm Indigo)
    public static int primary() {
        return Theme.isCurrentThemeDark() ? 0xFF818CF8 : 0xFF6366F1;
    }

    // Darker primary for small text (WCAG AA on light bg)
    public static int primarySmall() {
        return Theme.isCurrentThemeDark() ? 0xFF818CF8 : 0xFF4F46E5;
    }

    // AI accent — text-safe
    public static int aiAccent() {
        return Theme.isCurrentThemeDark() ? 0xFFF59E0B : 0xFFD97706;
    }

    // AI accent — icons/decorative only
    public static int aiAccentIcon() {
        return 0xFFF59E0B;
    }

    // Success green
    public static int success() {
        return Theme.isCurrentThemeDark() ? 0xFF34D399 : 0xFF10B981;
    }

    // Error red
    public static int error() {
        return Theme.isCurrentThemeDark() ? 0xFFFB7185 : 0xFFF43F5E;
    }

    // Warning amber
    public static int warning() {
        return Theme.isCurrentThemeDark() ? 0xFFFBBF24 : 0xFFF59E0B;
    }

    // Settings icon colors — adapt slightly for dark mode visibility
    public static int blue() {
        return Theme.isCurrentThemeDark() ? 0xFF60A5FA : 0xFF3B82F6;
    }

    public static int purple() {
        return Theme.isCurrentThemeDark() ? 0xFFA78BFA : 0xFF8B5CF6;
    }

    public static int cyan() {
        return Theme.isCurrentThemeDark() ? 0xFF22D3EE : 0xFF06B6D4;
    }

    public static int violet() {
        return Theme.isCurrentThemeDark() ? 0xFF8B5CF6 : 0xFF7C3AED;
    }

    public static int orange() {
        return Theme.isCurrentThemeDark() ? 0xFFFB923C : 0xFFF97316;
    }

    // Setup prompt background (indigo 10% alpha)
    public static int setupPromptBg() {
        return Theme.isCurrentThemeDark() ? 0x1A818CF8 : 0x1A6366F1;
    }

    /**
     * Apply ripple foreground + rounded outline clipping to a view.
     * Safe for API 21+ (ripple only on API 23+).
     */
    public static void applyRipple(View view, Context context, float radiusDp) {
        if (Build.VERSION.SDK_INT >= 23) {
            TypedValue outValue = new TypedValue();
            context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
            Drawable ripple = context.getDrawable(outValue.resourceId);
            view.setForeground(ripple);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            final float radius = AndroidUtilities.dp(radiusDp);
            view.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View v, Outline outline) {
                    outline.setRoundRect(0, 0, v.getWidth(), v.getHeight(), radius);
                }
            });
            view.setClipToOutline(true);
        }
    }
}
