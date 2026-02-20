package com.tonychat.core

/**
 * Tony Chat v2.0 brand color palette — Warm Indigo.
 * Mirrors Theme.key_tony_* constants for use in tonychat-core/ai/community modules
 * that cannot import Theme.java directly.
 */
object TonyColors {
    // Primary Warm Indigo
    const val PRIMARY = 0xFF6366F1.toInt()
    const val PRIMARY_DARK = 0xFF4F46E5.toInt()
    const val PRIMARY_LIGHT = 0xFFE0E7FF.toInt()
    const val PRIMARY_400 = 0xFF818CF8.toInt()

    // AI Accent (Amber)
    const val AI_ACCENT = 0xFFF59E0B.toInt()
    const val AI_ACCENT_TEXT = 0xFFD97706.toInt()  // WCAG safe for text
    const val AI_LIGHT = 0xFFFEF3C7.toInt()

    // Semantic
    const val SUCCESS = 0xFF10B981.toInt()
    const val ERROR = 0xFFF43F5E.toInt()
    const val WARNING = 0xFFF59E0B.toInt()

    // Light surfaces
    const val LIGHT_BACKGROUND = 0xFFFAFAFA.toInt()
    const val LIGHT_SURFACE = 0xFFFFFFFF.toInt()
    const val LIGHT_SURFACE2 = 0xFFF3F4F6.toInt()
    const val LIGHT_BORDER = 0xFFE5E7EB.toInt()
    const val LIGHT_TEXT_PRIMARY = 0xFF1A1A1A.toInt()
    const val LIGHT_TEXT_SECONDARY = 0xFF64748B.toInt()
    const val LIGHT_TEXT_TERTIARY = 0xFF9CA3AF.toInt()
    const val LIGHT_NAV_GLASS = 0xCCFFFFFF.toInt()

    // Dark surfaces (true dark for OLED)
    const val DARK_BACKGROUND = 0xFF0F172A.toInt()
    const val DARK_SURFACE = 0xFF1E293B.toInt()
    const val DARK_SURFACE2 = 0xFF334155.toInt()
    const val DARK_BORDER = 0xFF475569.toInt()
    const val DARK_TEXT_PRIMARY = 0xFFF1F5F9.toInt()
    const val DARK_TEXT_SECONDARY = 0xFF94A3B8.toInt()
    const val DARK_TEXT_TERTIARY = 0xFF64748B.toInt()
    const val DARK_NAV_GLASS = 0xCC0F172A.toInt()

    // Dark overrides (lighter variants for dark bg)
    const val DARK_PRIMARY = 0xFF818CF8.toInt()
    const val DARK_AI_ACCENT = 0xFFFBBF24.toInt()
    const val DARK_AI_ACCENT_TEXT = 0xFFF59E0B.toInt()
    const val DARK_SUCCESS = 0xFF34D399.toInt()
    const val DARK_ERROR = 0xFFFB7185.toInt()
}
