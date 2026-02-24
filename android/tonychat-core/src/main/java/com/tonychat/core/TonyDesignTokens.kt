package com.tonychat.core

/**
 * Tony Chat v2.0 design tokens — spacing, sizing, radius, colors.
 * Programmatic constants for Telegram's programmatic layout pattern.
 * All dimension values in dp (convert with AndroidUtilities.dp()).
 */
object TonyDesignTokens {
    // ==================== Spacing (8dp base grid) ====================
    const val SPACE_XS = 4
    const val SPACE_SM = 8
    const val SPACE_MD = 12
    const val SPACE_BASE = 16
    const val SPACE_LG = 24
    const val SPACE_XL = 32
    const val SPACE_2XL = 48
    const val SPACE_3XL = 64

    // ==================== Corner Radius ====================
    const val RADIUS_XS = 4
    const val RADIUS_SM = 8
    const val RADIUS_MD = 12
    const val RADIUS_LG = 16
    const val RADIUS_XL = 24
    const val RADIUS_PILL = 33

    // ==================== Component Sizes ====================
    const val BOTTOM_NAV_HEIGHT = 66
    const val BOTTOM_NAV_WIDTH = 280
    const val BOTTOM_NAV_MARGIN_BOTTOM = 24
    const val BOTTOM_NAV_MARGIN_SIDE = 0
    const val AVATAR_SM = 32
    const val AVATAR_MD = 48
    const val AVATAR_LG = 72
    const val TOUCH_TARGET_MIN = 48
    const val CHAT_ITEM_HEIGHT = 72
    const val SETTINGS_ITEM_HEIGHT = 56
    const val PILL_HEIGHT = 32
    const val PILL_RADIUS = 16
    const val FAB_SIZE = 60
    const val FAB_RADIUS = 20

    // ==================== Typography Sizes (sp) ====================
    const val TEXT_DISPLAY_LARGE = 28
    const val TEXT_DISPLAY_MEDIUM = 24
    const val TEXT_HEADLINE_LARGE = 22
    const val TEXT_HEADLINE_MEDIUM = 20
    const val TEXT_TITLE_LARGE = 18
    const val TEXT_TITLE_MEDIUM = 16
    const val TEXT_BODY_LARGE = 16
    const val TEXT_BODY_MEDIUM = 14
    const val TEXT_LABEL_SMALL = 12
    const val TEXT_NAV_LABEL = 12

    // ==================== Brand Colors (ARGB) ====================
    const val PRIMARY = 0xFFF9E000.toInt()
    const val ON_PRIMARY = 0xFF111111.toInt()
    const val BG_PRIMARY = 0xFFFFFFFF.toInt()
    const val BG_SECONDARY = 0xFFF2F2F2.toInt()
    const val TEXT_PRIMARY = 0xFF111111.toInt()
    const val TEXT_SECONDARY = 0xFF777777.toInt()
    const val TEXT_TERTIARY = 0xFF999999.toInt()
    const val AI_ACCENT = 0xFFF59E0B.toInt()
    const val AI_ACCENT_TEXT = 0xFFD97706.toInt()
}
