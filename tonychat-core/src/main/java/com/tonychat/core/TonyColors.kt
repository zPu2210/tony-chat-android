package com.tonychat.core

/**
 * Tony Chat brand color palette.
 * Used by TonyChatThemeOverlay to map onto Telegram's theme system.
 */
object TonyColors {
    // Primary
    const val PRIMARY = 0xFF1A73E8.toInt()
    const val PRIMARY_DARK = 0xFF1557B0.toInt()
    const val PRIMARY_LIGHT = 0xFF4A90D9.toInt()

    // Accent
    const val ACCENT = 0xFF34A853.toInt()
    const val ACCENT_DARK = 0xFF2D8F47.toInt()

    // Error / Destructive
    const val ERROR = 0xFFEA4335.toInt()

    // Light theme
    const val LIGHT_BACKGROUND = 0xFFFFFFFF.toInt()
    const val LIGHT_SURFACE = 0xFFF8F9FA.toInt()
    const val LIGHT_TEXT_PRIMARY = 0xFF202124.toInt()
    const val LIGHT_TEXT_SECONDARY = 0xFF5F6368.toInt()
    const val LIGHT_DIVIDER = 0xFFDADCE0.toInt()
    const val LIGHT_ICON = 0xFF9AA0A6.toInt()
    const val LIGHT_TAB_UNACTIVE = 0xFF9AA0A6.toInt()
    const val LIGHT_CHAT_IN_BUBBLE = 0xFFFFFFFF.toInt()
    const val LIGHT_CHAT_OUT_BUBBLE = 0xFFE3F2FD.toInt()

    // Dark theme
    const val DARK_BACKGROUND = 0xFF121212.toInt()
    const val DARK_SURFACE = 0xFF1E1E1E.toInt()
    const val DARK_TEXT_PRIMARY = 0xFFE8EAED.toInt()
    const val DARK_TEXT_SECONDARY = 0xFF9AA0A6.toInt()
    const val DARK_DIVIDER = 0xFF3C4043.toInt()
    const val DARK_ICON = 0xFF9AA0A6.toInt()
    const val DARK_TAB_UNACTIVE = 0xFF9AA0A6.toInt()
    const val DARK_CHAT_IN_BUBBLE = 0xFF2D2D2D.toInt()
    const val DARK_CHAT_OUT_BUBBLE = 0xFF1A3A5C.toInt()
}
