package com.tonychat.ui.theme

import com.tonychat.core.TonyColors
import com.tonychat.core.TonyConfig

/**
 * Theme helper for Tony Chat UI components.
 * Provides resolved colors based on current dark/light mode.
 */
object TonyChatTheme {
    val isDark: Boolean get() = TonyConfig.ui.darkMode

    val primary: Int get() = if (isDark) TonyColors.DARK_PRIMARY else TonyColors.PRIMARY
    val primaryDark: Int get() = TonyColors.PRIMARY_DARK
    val aiAccent: Int get() = if (isDark) TonyColors.DARK_AI_ACCENT else TonyColors.AI_ACCENT
    val aiAccentText: Int get() = if (isDark) TonyColors.DARK_AI_ACCENT_TEXT else TonyColors.AI_ACCENT_TEXT
    val success: Int get() = if (isDark) TonyColors.DARK_SUCCESS else TonyColors.SUCCESS
    val error: Int get() = if (isDark) TonyColors.DARK_ERROR else TonyColors.ERROR

    val background: Int get() = if (isDark) TonyColors.DARK_BACKGROUND else TonyColors.LIGHT_BACKGROUND
    val surface: Int get() = if (isDark) TonyColors.DARK_SURFACE else TonyColors.LIGHT_SURFACE
    val surface2: Int get() = if (isDark) TonyColors.DARK_SURFACE2 else TonyColors.LIGHT_SURFACE2
    val textPrimary: Int get() = if (isDark) TonyColors.DARK_TEXT_PRIMARY else TonyColors.LIGHT_TEXT_PRIMARY
    val textSecondary: Int get() = if (isDark) TonyColors.DARK_TEXT_SECONDARY else TonyColors.LIGHT_TEXT_SECONDARY
    val textTertiary: Int get() = if (isDark) TonyColors.DARK_TEXT_TERTIARY else TonyColors.LIGHT_TEXT_TERTIARY
    val border: Int get() = if (isDark) TonyColors.DARK_BORDER else TonyColors.LIGHT_BORDER
    val navGlass: Int get() = if (isDark) TonyColors.DARK_NAV_GLASS else TonyColors.LIGHT_NAV_GLASS
}
