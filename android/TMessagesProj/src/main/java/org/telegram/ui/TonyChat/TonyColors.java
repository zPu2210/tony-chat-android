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
 * v2.0: Yellow #F9E000 brand palette.
 */
public final class TonyColors {

    private TonyColors() {
    }

    // ==================== Brand ====================

    /** Primary brand yellow — used for action bars, FABs, CTAs */
    public static int primary() {
        return 0xFFF9E000;
    }

    /** Text color safe to render ON primary yellow bg */
    public static int onPrimary() {
        return 0xFF111111;
    }

    // ==================== Navigation ====================

    /** Active nav tab color (yellow in both modes) */
    public static int navActive() {
        return 0xFFF9E000;
    }

    /** Inactive nav tab color */
    public static int navInactive() {
        return Theme.isCurrentThemeDark() ? 0xFF94A3B8 : 0xFF999999;
    }

    // ==================== Text ====================

    public static int textPrimary() {
        return Theme.isCurrentThemeDark() ? 0xFFF1F5F9 : 0xFF111111;
    }

    public static int textSecondary() {
        return Theme.isCurrentThemeDark() ? 0xFF94A3B8 : 0xFF666666;
    }

    public static int textTertiary() {
        return Theme.isCurrentThemeDark() ? 0xFF64748B : 0xFF999999;
    }

    // ==================== Surfaces ====================

    public static int background() {
        return Theme.isCurrentThemeDark() ? 0xFF0F172A : 0xFFFFFFFF;
    }

    public static int backgroundSecondary() {
        return Theme.isCurrentThemeDark() ? 0xFF1E293B : 0xFFF2F2F2;
    }

    public static int navGlass() {
        return Theme.isCurrentThemeDark() ? 0xCC0F172A : 0xCCFFFFFF;
    }

    // ==================== AI Accent ====================

    public static int aiAccent() {
        return Theme.isCurrentThemeDark() ? 0xFFF59E0B : 0xFFD97706;
    }

    public static int aiAccentIcon() {
        return 0xFFF59E0B;
    }

    // ==================== Semantic ====================

    public static int success() {
        return Theme.isCurrentThemeDark() ? 0xFF34D399 : 0xFF10B981;
    }

    public static int error() {
        return Theme.isCurrentThemeDark() ? 0xFFFB7185 : 0xFFF43F5E;
    }

    public static int warning() {
        return Theme.isCurrentThemeDark() ? 0xFFFBBF24 : 0xFFF59E0B;
    }

    // ==================== Settings Icon Colors ====================

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

    // ==================== Utility ====================

    public static int setupPromptBg() {
        return Theme.isCurrentThemeDark() ? 0x1AF9E000 : 0x1AF9E000;
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
