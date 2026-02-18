package com.tonychat.ui.theme

import com.tonychat.core.TonyColors
import com.tonychat.core.TonyConfig

/**
 * Theme helper for Tony Chat UI components.
 * Provides resolved colors based on current dark/light mode.
 */
object TonyChatTheme {
    val isDark: Boolean get() = TonyConfig.darkMode

    val primary: Int get() = TonyColors.PRIMARY
    val primaryDark: Int get() = TonyColors.PRIMARY_DARK
    val accent: Int get() = TonyColors.ACCENT
    val error: Int get() = TonyColors.ERROR

    val background: Int get() = if (isDark) TonyColors.DARK_BACKGROUND else TonyColors.LIGHT_BACKGROUND
    val surface: Int get() = if (isDark) TonyColors.DARK_SURFACE else TonyColors.LIGHT_SURFACE
    val textPrimary: Int get() = if (isDark) TonyColors.DARK_TEXT_PRIMARY else TonyColors.LIGHT_TEXT_PRIMARY
    val textSecondary: Int get() = if (isDark) TonyColors.DARK_TEXT_SECONDARY else TonyColors.LIGHT_TEXT_SECONDARY
    val divider: Int get() = if (isDark) TonyColors.DARK_DIVIDER else TonyColors.LIGHT_DIVIDER
}
