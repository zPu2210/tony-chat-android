package com.tonychat.core

/**
 * Tony Chat v2.0 brand color palette — Yellow #F9E000.
 * Mirrors TonyColors.java for use in tonychat-core/ai/community modules
 * that cannot import Theme.java directly.
 */
object TonyColors {
    // Primary Yellow
    const val PRIMARY = 0xFFF9E000.toInt()
    const val PRIMARY_DARK = 0xFFD4BF00.toInt()
    const val PRIMARY_LIGHT = 0xFFFFF176.toInt()
    const val ON_PRIMARY = 0xFF111111.toInt()

    // AI Accent (Amber)
    const val AI_ACCENT = 0xFFF59E0B.toInt()
    const val AI_ACCENT_TEXT = 0xFFD97706.toInt()
    const val AI_LIGHT = 0xFFFEF3C7.toInt()

    // Semantic
    const val SUCCESS = 0xFF10B981.toInt()
    const val ERROR = 0xFFF43F5E.toInt()
    const val WARNING = 0xFFF59E0B.toInt()

    // Light surfaces
    const val LIGHT_BACKGROUND = 0xFFFFFFFF.toInt()
    const val LIGHT_SURFACE = 0xFFFFFFFF.toInt()
    const val LIGHT_SURFACE2 = 0xFFF2F2F2.toInt()
    const val LIGHT_BORDER = 0xFFE5E7EB.toInt()
    const val LIGHT_TEXT_PRIMARY = 0xFF111111.toInt()
    const val LIGHT_TEXT_SECONDARY = 0xFF777777.toInt()
    const val LIGHT_TEXT_TERTIARY = 0xFF999999.toInt()
    const val LIGHT_NAV_GLASS = 0xCCFFFFFF.toInt()

    // Dark surfaces
    const val DARK_BACKGROUND = 0xFF0F172A.toInt()
    const val DARK_SURFACE = 0xFF1E293B.toInt()
    const val DARK_SURFACE2 = 0xFF334155.toInt()
    const val DARK_BORDER = 0xFF475569.toInt()
    const val DARK_TEXT_PRIMARY = 0xFFF1F5F9.toInt()
    const val DARK_TEXT_SECONDARY = 0xFF94A3B8.toInt()
    const val DARK_TEXT_TERTIARY = 0xFF64748B.toInt()
    const val DARK_NAV_GLASS = 0xCC0F172A.toInt()

    // Nav
    const val NAV_ACTIVE = 0xFFF9E000.toInt()
    const val NAV_INACTIVE = 0xFF777777.toInt()

    // Dark overrides
    const val DARK_PRIMARY = 0xFFF9E000.toInt()
    const val DARK_AI_ACCENT = 0xFFFBBF24.toInt()
    const val DARK_AI_ACCENT_TEXT = 0xFFF59E0B.toInt()
    const val DARK_SUCCESS = 0xFF34D399.toInt()
    const val DARK_ERROR = 0xFFFB7185.toInt()
}
