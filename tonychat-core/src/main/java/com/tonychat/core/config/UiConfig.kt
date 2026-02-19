package com.tonychat.core.config

import android.content.SharedPreferences

/**
 * UI and Theme configuration
 */
class UiConfig(private val prefs: SharedPreferences) {
    // Theme
    var darkMode: Boolean
        get() = prefs.getBoolean("dark_mode", false)
        set(value) = prefs.edit().putBoolean("dark_mode", value).apply()

    var useTonyTheme: Boolean
        get() = prefs.getBoolean("use_tony_theme", true)
        set(value) = prefs.edit().putBoolean("use_tony_theme", value).apply()

    // Visual settings
    var disableVibration: Boolean
        get() = prefs.getBoolean("disable_vibration", false)
        set(value) = prefs.edit().putBoolean("disable_vibration", value).apply()

    var showSeconds: Boolean
        get() = prefs.getBoolean("show_seconds", false)
        set(value) = prefs.edit().putBoolean("show_seconds", value).apply()

    var disableUndo: Boolean
        get() = prefs.getBoolean("disable_undo", false)
        set(value) = prefs.edit().putBoolean("disable_undo", value).apply()

    var actionBarDecoration: Int
        get() = prefs.getInt("action_bar_decoration", 0)
        set(value) = prefs.edit().putInt("action_bar_decoration", value).apply()

    var avatarBackgroundBlur: Boolean
        get() = prefs.getBoolean("avatar_background_blur", false)
        set(value) = prefs.edit().putBoolean("avatar_background_blur", value).apply()

    var avatarBackgroundDarken: Boolean
        get() = prefs.getBoolean("avatar_background_darken", false)
        set(value) = prefs.edit().putBoolean("avatar_background_darken", value).apply()

    var chatBlueAlphaValue: Int
        get() = prefs.getInt("chat_blue_alpha_value", 0)
        set(value) = prefs.edit().putInt("chat_blue_alpha_value", value).apply()

    var disableAppBarShadow: Boolean
        get() = prefs.getBoolean("disable_app_bar_shadow", false)
        set(value) = prefs.edit().putBoolean("disable_app_bar_shadow", value).apply()

    var hideKeyboardOnChatScroll: Boolean
        get() = prefs.getBoolean("hide_keyboard_on_chat_scroll", false)
        set(value) = prefs.edit().putBoolean("hide_keyboard_on_chat_scroll", value).apply()

    var largeAvatarInDrawer: Boolean
        get() = prefs.getBoolean("large_avatar_in_drawer", false)
        set(value) = prefs.edit().putBoolean("large_avatar_in_drawer", value).apply()

    var showIdAndDc: Boolean
        get() = prefs.getBoolean("show_id_and_dc", false)
        set(value) = prefs.edit().putBoolean("show_id_and_dc", value).apply()

    var stickerSize: Float
        get() = prefs.getFloat("sticker_size", 14.0f)
        set(value) = prefs.edit().putFloat("sticker_size", value).apply()

    var tabletMode: Int
        get() = prefs.getInt("tablet_mode", 0)
        set(value) = prefs.edit().putInt("tablet_mode", value).apply()

    var tabsTitleType: Int
        get() = prefs.getInt("tabs_title_type", 0)
        set(value) = prefs.edit().putInt("tabs_title_type", value).apply()

    var unreadBadgeOnBackButton: Boolean
        get() = prefs.getBoolean("unread_badge_on_back_button", false)
        set(value) = prefs.edit().putBoolean("unread_badge_on_back_button", value).apply()

    var newYear: Boolean
        get() = prefs.getBoolean("new_year", false)
        set(value) = prefs.edit().putBoolean("new_year", value).apply()

    var nameOrder: Int
        get() = prefs.getInt("name_order", 1)
        set(value) = prefs.edit().putInt("name_order", value).apply()

    var typeface: Int
        get() = prefs.getInt("typeface", 0)
        set(value) = prefs.edit().putInt("typeface", value).apply()

    var forceBlurInChat: Boolean
        get() = prefs.getBoolean("force_blur_in_chat", false)
        set(value) = prefs.edit().putBoolean("force_blur_in_chat", value).apply()

    var hideTimeForSticker: Boolean
        get() = prefs.getBoolean("hide_time_for_sticker", false)
        set(value) = prefs.edit().putBoolean("hide_time_for_sticker", value).apply()

    var hideAllTab: Boolean
        get() = prefs.getBoolean("hide_all_tab", false)
        set(value) = prefs.edit().putBoolean("hide_all_tab", value).apply()

    var useChatAttachMediaMenu: Boolean
        get() = prefs.getBoolean("use_chat_attach_media_menu", false)
        set(value) = prefs.edit().putBoolean("use_chat_attach_media_menu", value).apply()

    var showBottomActionsWhenSelecting: Boolean
        get() = prefs.getBoolean("show_bottom_actions_when_selecting", false)
        set(value) = prefs.edit().putBoolean("show_bottom_actions_when_selecting", value).apply()

    var tabStyle: Int
        get() = prefs.getInt("tab_style", 0)
        set(value) = prefs.edit().putInt("tab_style", value).apply()

    var centerActionBarTitle: Boolean
        get() = prefs.getBoolean("center_action_bar_title", false)
        set(value) = prefs.edit().putBoolean("center_action_bar_title", value).apply()

    var showSquareAvatar: Boolean
        get() = prefs.getBoolean("show_square_avatar", false)
        set(value) = prefs.edit().putBoolean("show_square_avatar", value).apply()

    var navigationAnimationSpring: Boolean
        get() = prefs.getBoolean("navigation_animation_spring", false)
        set(value) = prefs.edit().putBoolean("navigation_animation_spring", value).apply()

    var forceEdgeToEdge: Boolean
        get() = prefs.getBoolean("force_edge_to_edge", false)
        set(value) = prefs.edit().putBoolean("force_edge_to_edge", value).apply()

    var iconDecoration: Int
        get() = prefs.getInt("icon_decoration", 0)
        set(value) = prefs.edit().putInt("icon_decoration", value).apply()

    var chatDecoration: Int
        get() = prefs.getInt("chat_decoration", 0)
        set(value) = prefs.edit().putInt("chat_decoration", value).apply()

    var notificationIcon: Int
        get() = prefs.getInt("notification_icon", 0)
        set(value) = prefs.edit().putInt("notification_icon", value).apply()

    var chatActivityNavbarTransparent: Boolean
        get() = prefs.getBoolean("chat_activity_navbar_transparent", false)
        set(value) = prefs.edit().putBoolean("chat_activity_navbar_transparent", value).apply()

    var disableDialogsFloatingButton: Boolean
        get() = prefs.getBoolean("disable_dialogs_floating_button", false)
        set(value) = prefs.edit().putBoolean("disable_dialogs_floating_button", value).apply()

    var showUserIconsInChatsList: Boolean
        get() = prefs.getBoolean("show_user_icons_in_chats_list", false)
        set(value) = prefs.edit().putBoolean("show_user_icons_in_chats_list", value).apply()

    var showRecentChatsInSidebar: Boolean
        get() = prefs.getBoolean("show_recent_chats_in_sidebar", false)
        set(value) = prefs.edit().putBoolean("show_recent_chats_in_sidebar", value).apply()

    var showTimeHint: Boolean
        get() = prefs.getBoolean("show_time_hint", false)
        set(value) = prefs.edit().putBoolean("show_time_hint", value).apply()
}
