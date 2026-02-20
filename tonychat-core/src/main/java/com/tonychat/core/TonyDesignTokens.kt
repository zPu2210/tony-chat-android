package com.tonychat.core

/**
 * Tony Chat v2.0 design tokens — spacing, sizing, radius.
 * Programmatic constants for Telegram's programmatic layout pattern.
 * All values in dp (convert with AndroidUtilities.dp()).
 */
object TonyDesignTokens {
    // Spacing (8dp base grid)
    const val SPACE_XS = 4
    const val SPACE_SM = 8
    const val SPACE_MD = 12
    const val SPACE_BASE = 16
    const val SPACE_LG = 24
    const val SPACE_XL = 32
    const val SPACE_2XL = 48
    const val SPACE_3XL = 64

    // Corner radius
    const val RADIUS_XS = 4
    const val RADIUS_SM = 8
    const val RADIUS_MD = 12
    const val RADIUS_LG = 16
    const val RADIUS_XL = 24

    // Component sizes
    const val BOTTOM_NAV_HEIGHT = 56
    const val BOTTOM_NAV_TOTAL_HEIGHT = 80  // includes safe area
    const val AVATAR_SM = 32
    const val AVATAR_MD = 48
    const val AVATAR_LG = 72
    const val TOUCH_TARGET_MIN = 48
    const val CHAT_ITEM_HEIGHT = 72
    const val SETTINGS_ITEM_HEIGHT = 56
    const val PILL_HEIGHT = 32
    const val PILL_RADIUS = 16

    // Typography sizes (sp)
    const val TEXT_DISPLAY_LARGE = 28
    const val TEXT_DISPLAY_MEDIUM = 24
    const val TEXT_HEADLINE_LARGE = 22
    const val TEXT_HEADLINE_MEDIUM = 20
    const val TEXT_TITLE_LARGE = 18
    const val TEXT_TITLE_MEDIUM = 16
    const val TEXT_BODY_LARGE = 16
    const val TEXT_BODY_MEDIUM = 14
    const val TEXT_LABEL_SMALL = 12
}
