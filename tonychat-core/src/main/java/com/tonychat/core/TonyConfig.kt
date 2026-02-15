package com.tonychat.core

import android.content.Context
import android.content.SharedPreferences

/**
 * Centralized configuration for Tony Chat.
 * Consolidates all settings from NekoConfig, NaConfig, NekoXConfig.
 * All features OFF by default - Tony Chat focuses on core messaging.
 *
 * This is the single source of truth for all config.
 * NekoConfig/NaConfig/NekoXConfig are thin bridges delegating here.
 */
object TonyConfig {
    private const val PREFS_NAME = "tonychat_config"
    private lateinit var prefs: SharedPreferences

    // ============ Feature Flags (Tony Chat specific) ============
    var showStories: Boolean
        get() = prefs.getBoolean("show_stories", false)
        set(value) = prefs.edit().putBoolean("show_stories", value).apply()

    var showStars: Boolean
        get() = prefs.getBoolean("show_stars", false)
        set(value) = prefs.edit().putBoolean("show_stars", value).apply()

    var showGifts: Boolean
        get() = prefs.getBoolean("show_gifts", false)
        set(value) = prefs.edit().putBoolean("show_gifts", value).apply()

    var showBots: Boolean
        get() = prefs.getBoolean("show_bots", false)
        set(value) = prefs.edit().putBoolean("show_bots", value).apply()

    var showGames: Boolean
        get() = prefs.getBoolean("show_games", false)
        set(value) = prefs.edit().putBoolean("show_games", value).apply()

    var showTonWallet: Boolean
        get() = prefs.getBoolean("show_ton_wallet", false)
        set(value) = prefs.edit().putBoolean("show_ton_wallet", value).apply()

    var showPremiumUpsell: Boolean
        get() = prefs.getBoolean("show_premium_upsell", false)
        set(value) = prefs.edit().putBoolean("show_premium_upsell", value).apply()

    var showPassport: Boolean
        get() = prefs.getBoolean("show_passport", false)
        set(value) = prefs.edit().putBoolean("show_passport", value).apply()

    var showBusiness: Boolean
        get() = prefs.getBoolean("show_business", false)
        set(value) = prefs.edit().putBoolean("show_business", value).apply()

    var showCalls: Boolean
        get() = prefs.getBoolean("show_calls", false)
        set(value) = prefs.edit().putBoolean("show_calls", value).apply()

    var showTranslation: Boolean
        get() = prefs.getBoolean("show_translation", false)
        set(value) = prefs.edit().putBoolean("show_translation", value).apply()

    var showProxy: Boolean
        get() = prefs.getBoolean("show_proxy", false)
        set(value) = prefs.edit().putBoolean("show_proxy", value).apply()

    var showSponsored: Boolean
        get() = prefs.getBoolean("show_sponsored", false)
        set(value) = prefs.edit().putBoolean("show_sponsored", value).apply()

    // ============ UI Settings (from NekoConfig) ============
    var disableVibration: Boolean
        get() = prefs.getBoolean("disable_vibration", false)
        set(value) = prefs.edit().putBoolean("disable_vibration", value).apply()

    var hidePhone: Boolean
        get() = prefs.getBoolean("hide_phone", false)
        set(value) = prefs.edit().putBoolean("hide_phone", value).apply()

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

    // ============ Behavior Settings (from NekoConfig) ============
    var disableInstantCamera: Boolean
        get() = prefs.getBoolean("disable_instant_camera", false)
        set(value) = prefs.edit().putBoolean("disable_instant_camera", value).apply()

    var disablePhotoSideAction: Boolean
        get() = prefs.getBoolean("disable_photo_side_action", true)
        set(value) = prefs.edit().putBoolean("disable_photo_side_action", value).apply()

    var disableSwipeToNext: Boolean
        get() = prefs.getBoolean("disable_swipe_to_next", true)
        set(value) = prefs.edit().putBoolean("disable_swipe_to_next", value).apply()

    var disableReactionsWhenSelecting: Boolean
        get() = prefs.getBoolean("disable_reactions_when_selecting", false)
        set(value) = prefs.edit().putBoolean("disable_reactions_when_selecting", value).apply()

    var disableRemoteEmojiInteractions: Boolean
        get() = prefs.getBoolean("disable_remote_emoji_interactions", false)
        set(value) = prefs.edit().putBoolean("disable_remote_emoji_interactions", value).apply()

    var autoPauseVideo: Boolean
        get() = prefs.getBoolean("auto_pause_video", false)
        set(value) = prefs.edit().putBoolean("auto_pause_video", value).apply()

    var disableChoosingSticker: Boolean
        get() = prefs.getBoolean("disable_choosing_sticker", false)
        set(value) = prefs.edit().putBoolean("disable_choosing_sticker", value).apply()

    var disablePremiumStickerAnimation: Boolean
        get() = prefs.getBoolean("disable_premium_sticker_animation", false)
        set(value) = prefs.edit().putBoolean("disable_premium_sticker_animation", value).apply()

    var enableStickerPin: Boolean
        get() = prefs.getBoolean("enable_sticker_pin", false)
        set(value) = prefs.edit().putBoolean("enable_sticker_pin", value).apply()

    var maxRecentStickerCount: Int
        get() = prefs.getInt("max_recent_sticker_count", 20)
        set(value) = prefs.edit().putInt("max_recent_sticker_count", value).apply()

    var unlimitedFavedStickers: Boolean
        get() = prefs.getBoolean("unlimited_faved_stickers", false)
        set(value) = prefs.edit().putBoolean("unlimited_faved_stickers", value).apply()

    var unlimitedPinnedDialogs: Boolean
        get() = prefs.getBoolean("unlimited_pinned_dialogs", false)
        set(value) = prefs.edit().putBoolean("unlimited_pinned_dialogs", value).apply()

    var disableSystemAccount: Boolean
        get() = prefs.getBoolean("disable_system_account", false)
        set(value) = prefs.edit().putBoolean("disable_system_account", value).apply()

    var disableNotificationBubbles: Boolean
        get() = prefs.getBoolean("disable_notification_bubbles", false)
        set(value) = prefs.edit().putBoolean("disable_notification_bubbles", value).apply()

    var skipOpenLinkConfirm: Boolean
        get() = prefs.getBoolean("skip_open_link_confirm", false)
        set(value) = prefs.edit().putBoolean("skip_open_link_confirm", value).apply()

    var openArchiveOnPull: Boolean
        get() = prefs.getBoolean("open_archive_on_pull", false)
        set(value) = prefs.edit().putBoolean("open_archive_on_pull", value).apply()

    var takeGIFasVideo: Boolean
        get() = prefs.getBoolean("take_gif_as_video", false)
        set(value) = prefs.edit().putBoolean("take_gif_as_video", value).apply()

    var mediaPreview: Boolean
        get() = prefs.getBoolean("media_preview", true)
        set(value) = prefs.edit().putBoolean("media_preview", value).apply()

    var rearVideoMessages: Boolean
        get() = prefs.getBoolean("rear_video_messages", false)
        set(value) = prefs.edit().putBoolean("rear_video_messages", value).apply()

    var disableProximityEvents: Boolean
        get() = prefs.getBoolean("disable_proximity_events", false)
        set(value) = prefs.edit().putBoolean("disable_proximity_events", value).apply()

    var askBeforeCall: Boolean
        get() = prefs.getBoolean("ask_before_call", false)
        set(value) = prefs.edit().putBoolean("ask_before_call", value).apply()

    var confirmAVMessage: Boolean
        get() = prefs.getBoolean("confirm_av_message", false)
        set(value) = prefs.edit().putBoolean("confirm_av_message", value).apply()

    var disableChatAction: Boolean
        get() = prefs.getBoolean("disable_chat_action", false)
        set(value) = prefs.edit().putBoolean("disable_chat_action", value).apply()

    var sortByUnread: Boolean
        get() = prefs.getBoolean("sort_by_unread", false)
        set(value) = prefs.edit().putBoolean("sort_by_unread", value).apply()

    var sortByUnmuted: Boolean
        get() = prefs.getBoolean("sort_by_unmuted", false)
        set(value) = prefs.edit().putBoolean("sort_by_unmuted", value).apply()

    var sortByUser: Boolean
        get() = prefs.getBoolean("sort_by_user", false)
        set(value) = prefs.edit().putBoolean("sort_by_user", value).apply()

    var sortByContacts: Boolean
        get() = prefs.getBoolean("sort_by_contacts", false)
        set(value) = prefs.edit().putBoolean("sort_by_contacts", value).apply()

    var disableNumberRounding: Boolean
        get() = prefs.getBoolean("disable_number_rounding", false)
        set(value) = prefs.edit().putBoolean("disable_number_rounding", value).apply()

    var disableLinkPreviewByDefault: Boolean
        get() = prefs.getBoolean("disable_link_preview_by_default", false)
        set(value) = prefs.edit().putBoolean("disable_link_preview_by_default", value).apply()

    var markReadAfterSend: Boolean
        get() = prefs.getBoolean("mark_read_after_send", false)
        set(value) = prefs.edit().putBoolean("mark_read_after_send", value).apply()

    var rememberAllBackMessages: Boolean
        get() = prefs.getBoolean("remember_all_back_messages", false)
        set(value) = prefs.edit().putBoolean("remember_all_back_messages", value).apply()

    var sendCommentAfterForward: Boolean
        get() = prefs.getBoolean("send_comment_after_forward", true)
        set(value) = prefs.edit().putBoolean("send_comment_after_forward", value).apply()

    var dontSendGreetingSticker: Boolean
        get() = prefs.getBoolean("dont_send_greeting_sticker", false)
        set(value) = prefs.edit().putBoolean("dont_send_greeting_sticker", value).apply()

    var repeatConfirm: Boolean
        get() = prefs.getBoolean("repeat_confirm", false)
        set(value) = prefs.edit().putBoolean("repeat_confirm", value).apply()

    // ============ Privacy & Ghost Mode Settings ============
    var ignoreBlocked: Boolean
        get() = prefs.getBoolean("ignore_blocked", false)
        set(value) = prefs.edit().putBoolean("ignore_blocked", value).apply()

    var ignoreMutedCount: Boolean
        get() = prefs.getBoolean("ignore_muted_count", false)
        set(value) = prefs.edit().putBoolean("ignore_muted_count", value).apply()

    var hideProxySponsorChannel: Boolean
        get() = prefs.getBoolean("hide_proxy_sponsor_channel", false)
        set(value) = prefs.edit().putBoolean("hide_proxy_sponsor_channel", value).apply()

    var hideSponsoredMessage: Boolean
        get() = prefs.getBoolean("hide_sponsored_message", false)
        set(value) = prefs.edit().putBoolean("hide_sponsored_message", value).apply()

    var sendOnlinePackets: Boolean
        get() = prefs.getBoolean("send_online_packets", true)
        set(value) = prefs.edit().putBoolean("send_online_packets", value).apply()

    var sendReadMessagePackets: Boolean
        get() = prefs.getBoolean("send_read_message_packets", true)
        set(value) = prefs.edit().putBoolean("send_read_message_packets", value).apply()

    var sendReadStoryPackets: Boolean
        get() = prefs.getBoolean("send_read_story_packets", true)
        set(value) = prefs.edit().putBoolean("send_read_story_packets", value).apply()

    var sendUploadProgress: Boolean
        get() = prefs.getBoolean("send_upload_progress", true)
        set(value) = prefs.edit().putBoolean("send_upload_progress", value).apply()

    var sendOfflineAfterOnline: Int
        get() = prefs.getInt("send_offline_after_online", 0)
        set(value) = prefs.edit().putInt("send_offline_after_online", value).apply()

    var showGhostToggleInDrawer: Boolean
        get() = prefs.getBoolean("show_ghost_toggle_in_drawer", false)
        set(value) = prefs.edit().putBoolean("show_ghost_toggle_in_drawer", value).apply()

    var isGhostModeActive: Boolean
        get() = prefs.getBoolean("is_ghost_mode_active", false)
        set(value) = prefs.edit().putBoolean("is_ghost_mode_active", value).apply()

    // ============ Chat Features ============
    var labelChannelUser: Boolean
        get() = prefs.getBoolean("label_channel_user", false)
        set(value) = prefs.edit().putBoolean("label_channel_user", value).apply()

    var channelAlias: Boolean
        get() = prefs.getBoolean("channel_alias", false)
        set(value) = prefs.edit().putBoolean("channel_alias", value).apply()

    var hideGroupSticker: Boolean
        get() = prefs.getBoolean("hide_group_sticker", false)
        set(value) = prefs.edit().putBoolean("hide_group_sticker", value).apply()

    var hideSendAsChannel: Boolean
        get() = prefs.getBoolean("hide_send_as_channel", false)
        set(value) = prefs.edit().putBoolean("hide_send_as_channel", value).apply()

    var showAddToSavedMessages: Boolean
        get() = prefs.getBoolean("show_add_to_saved_messages", true)
        set(value) = prefs.edit().putBoolean("show_add_to_saved_messages", value).apply()

    var showRepeat: Boolean
        get() = prefs.getBoolean("show_repeat", false)
        set(value) = prefs.edit().putBoolean("show_repeat", value).apply()

    var showMessageHide: Boolean
        get() = prefs.getBoolean("show_message_hide", false)
        set(value) = prefs.edit().putBoolean("show_message_hide", value).apply()

    var showAdminActions: Boolean
        get() = prefs.getBoolean("show_admin_actions", false)
        set(value) = prefs.edit().putBoolean("show_admin_actions", value).apply()

    var showChangePermissions: Boolean
        get() = prefs.getBoolean("show_change_permissions", false)
        set(value) = prefs.edit().putBoolean("show_change_permissions", value).apply()

    var showDeleteDownloadedFile: Boolean
        get() = prefs.getBoolean("show_delete_downloaded_file", true)
        set(value) = prefs.edit().putBoolean("show_delete_downloaded_file", value).apply()

    var showMessageDetails: Boolean
        get() = prefs.getBoolean("show_message_details", false)
        set(value) = prefs.edit().putBoolean("show_message_details", value).apply()

    var showShareMessages: Boolean
        get() = prefs.getBoolean("show_share_messages", false)
        set(value) = prefs.edit().putBoolean("show_share_messages", value).apply()

    var showReport: Boolean
        get() = prefs.getBoolean("show_report", false)
        set(value) = prefs.edit().putBoolean("show_report", value).apply()

    var showViewHistory: Boolean
        get() = prefs.getBoolean("show_view_history", true)
        set(value) = prefs.edit().putBoolean("show_view_history", value).apply()

    var showSpoilersDirectly: Boolean
        get() = prefs.getBoolean("show_spoilers_directly", false)
        set(value) = prefs.edit().putBoolean("show_spoilers_directly", value).apply()

    var showTranslate: Boolean
        get() = prefs.getBoolean("show_translate", false)
        set(value) = prefs.edit().putBoolean("show_translate", value).apply()

    var ignoreContentRestrictions: Boolean
        get() = prefs.getBoolean("ignore_content_restrictions", false)
        set(value) = prefs.edit().putBoolean("ignore_content_restrictions", value).apply()

    // ============ Media & Download Settings ============
    var disableAutoDownloadingWin32Executable: Boolean
        get() = prefs.getBoolean("disable_auto_downloading_win32_executable", true)
        set(value) = prefs.edit().putBoolean("disable_auto_downloading_win32_executable", value).apply()

    var disableAutoDownloadingArchive: Boolean
        get() = prefs.getBoolean("disable_auto_downloading_archive", true)
        set(value) = prefs.edit().putBoolean("disable_auto_downloading_archive", value).apply()

    var customAudioBitrate: Int
        get() = prefs.getInt("custom_audio_bitrate", 32)
        set(value) = prefs.edit().putInt("custom_audio_bitrate", value).apply()

    var cachePath: String
        get() = prefs.getString("cache_path", "") ?: ""
        set(value) = prefs.edit().putString("cache_path", value).apply()

    var customSavePath: String
        get() = prefs.getString("custom_save_path", "") ?: ""
        set(value) = prefs.edit().putString("custom_save_path", value).apply()

    var enhancedFileLoader: Boolean
        get() = prefs.getBoolean("enhanced_file_loader", false)
        set(value) = prefs.edit().putBoolean("enhanced_file_loader", value).apply()

    // ============ Map & Location Settings ============
    var mapPreviewProvider: Int
        get() = prefs.getInt("map_preview_provider", 0)
        set(value) = prefs.edit().putInt("map_preview_provider", value).apply()

    var useOSMDroidMap: Boolean
        get() = prefs.getBoolean("use_osm_droid_map", false)
        set(value) = prefs.edit().putBoolean("use_osm_droid_map", value).apply()

    var fixDriftingForGoogleMaps: Boolean
        get() = prefs.getBoolean("fix_drifting_for_google_maps", false)
        set(value) = prefs.edit().putBoolean("fix_drifting_for_google_maps", value).apply()

    // ============ Translation Settings ============
    var translateToLang: String
        get() = prefs.getString("translate_to_lang", "en") ?: "en"
        set(value) = prefs.edit().putString("translate_to_lang", value).apply()

    var translateInputLang: String
        get() = prefs.getString("translate_input_lang", "en") ?: "en"
        set(value) = prefs.edit().putString("translate_input_lang", value).apply()

    var ccInputLang: String
        get() = prefs.getString("cc_input_lang", "") ?: ""
        set(value) = prefs.edit().putString("cc_input_lang", value).apply()

    var useTelegramTranslateInChat: Boolean
        get() = prefs.getBoolean("use_telegram_translate_in_chat", false)
        set(value) = prefs.edit().putBoolean("use_telegram_translate_in_chat", value).apply()

    // ============ Proxy & Network Settings ============
    var hideProxyByDefault: Boolean
        get() = prefs.getBoolean("hide_proxy_by_default", false)
        set(value) = prefs.edit().putBoolean("hide_proxy_by_default", value).apply()

    var useProxyItem: Boolean
        get() = prefs.getBoolean("use_proxy_item", true)
        set(value) = prefs.edit().putBoolean("use_proxy_item", value).apply()

    var useIPv6: Boolean
        get() = prefs.getBoolean("use_ipv6", false)
        set(value) = prefs.edit().putBoolean("use_ipv6", value).apply()

    var useMediaStreamInVoip: Boolean
        get() = prefs.getBoolean("use_media_stream_in_voip", false)
        set(value) = prefs.edit().putBoolean("use_media_stream_in_voip", value).apply()

    // ============ Advanced/Misc Settings ============
    var localPremium: Boolean
        get() = prefs.getBoolean("local_premium", false)
        set(value) = prefs.edit().putBoolean("local_premium", value).apply()

    var localeToDBC: Boolean
        get() = prefs.getBoolean("locale_to_dbc", false)
        set(value) = prefs.edit().putBoolean("locale_to_dbc", value).apply()

    var usePersianCalendar: Boolean
        get() = prefs.getBoolean("use_persian_calendar", false)
        set(value) = prefs.edit().putBoolean("use_persian_calendar", value).apply()

    var useSystemEmoji: Boolean
        get() = prefs.getBoolean("use_system_emoji", false)
        set(value) = prefs.edit().putBoolean("use_system_emoji", value).apply()

    var openPGPApp: String
        get() = prefs.getString("open_pgp_app", "") ?: ""
        set(value) = prefs.edit().putString("open_pgp_app", value).apply()

    var openPGPKeyId: Long
        get() = prefs.getLong("open_pgp_key_id", 0L)
        set(value) = prefs.edit().putLong("open_pgp_key_id", value).apply()

    var autoUpdateSubInfo: Boolean
        get() = prefs.getBoolean("auto_update_sub_info", false)
        set(value) = prefs.edit().putBoolean("auto_update_sub_info", value).apply()

    // ============ NaConfig Settings ============
    var tabStyle: Int
        get() = prefs.getInt("tab_style", 0)
        set(value) = prefs.edit().putInt("tab_style", value).apply()

    var doubleTapAction: Int
        get() = prefs.getInt("double_tap_action", 0)
        set(value) = prefs.edit().putInt("double_tap_action", value).apply()

    var forceCopy: Boolean
        get() = prefs.getBoolean("force_copy", false)
        set(value) = prefs.edit().putBoolean("force_copy", value).apply()

    var centerActionBarTitle: Boolean
        get() = prefs.getBoolean("center_action_bar_title", false)
        set(value) = prefs.edit().putBoolean("center_action_bar_title", value).apply()

    var disableMarkdown: Boolean
        get() = prefs.getBoolean("disable_markdown", false)
        set(value) = prefs.edit().putBoolean("disable_markdown", value).apply()

    var showRepeatAsCopy: Boolean
        get() = prefs.getBoolean("show_repeat_as_copy", false)
        set(value) = prefs.edit().putBoolean("show_repeat_as_copy", value).apply()

    var showNoQuoteForward: Boolean
        get() = prefs.getBoolean("show_no_quote_forward", false)
        set(value) = prefs.edit().putBoolean("show_no_quote_forward", value).apply()

    var showCopyPhoto: Boolean
        get() = prefs.getBoolean("show_copy_photo", false)
        set(value) = prefs.edit().putBoolean("show_copy_photo", value).apply()

    var showMessageID: Boolean
        get() = prefs.getBoolean("show_message_id", false)
        set(value) = prefs.edit().putBoolean("show_message_id", value).apply()

    var showReactions: Boolean
        get() = prefs.getBoolean("show_reactions", true)
        set(value) = prefs.edit().putBoolean("show_reactions", value).apply()

    var disableStories: Boolean
        get() = prefs.getBoolean("disable_stories", false)
        set(value) = prefs.edit().putBoolean("disable_stories", value).apply()

    var disableGifts: Boolean
        get() = prefs.getBoolean("disable_gifts", false)
        set(value) = prefs.edit().putBoolean("disable_gifts", value).apply()

    var disablePremiumUpgrade: Boolean
        get() = prefs.getBoolean("disable_premium_upgrade", false)
        set(value) = prefs.edit().putBoolean("disable_premium_upgrade", value).apply()

    var codeSyntaxHighlight: Boolean
        get() = prefs.getBoolean("code_syntax_highlight", false)
        set(value) = prefs.edit().putBoolean("code_syntax_highlight", value).apply()

    var showOnlineStatus: Boolean
        get() = prefs.getBoolean("show_online_status", true)
        set(value) = prefs.edit().putBoolean("show_online_status", value).apply()

    var showRecentOnlineStatus: Boolean
        get() = prefs.getBoolean("show_recent_online_status", true)
        set(value) = prefs.edit().putBoolean("show_recent_online_status", value).apply()

    var showServicesTime: Boolean
        get() = prefs.getBoolean("show_services_time", false)
        set(value) = prefs.edit().putBoolean("show_services_time", value).apply()

    var disableSendReadStories: Boolean
        get() = prefs.getBoolean("disable_send_read_stories", false)
        set(value) = prefs.edit().putBoolean("disable_send_read_stories", value).apply()

    var realHideTimeForSticker: Boolean
        get() = prefs.getBoolean("real_hide_time_for_sticker", false)
        set(value) = prefs.edit().putBoolean("real_hide_time_for_sticker", value).apply()

    var showSquareAvatar: Boolean
        get() = prefs.getBoolean("show_square_avatar", false)
        set(value) = prefs.edit().putBoolean("show_square_avatar", value).apply()

    var combineMessage: Boolean
        get() = prefs.getBoolean("combine_message", false)
        set(value) = prefs.edit().putBoolean("combine_message", value).apply()

    var showFullAbout: Boolean
        get() = prefs.getBoolean("show_full_about", false)
        set(value) = prefs.edit().putBoolean("show_full_about", value).apply()

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

    var enhancedVideoBitrate: Boolean
        get() = prefs.getBoolean("enhanced_video_bitrate", false)
        set(value) = prefs.edit().putBoolean("enhanced_video_bitrate", value).apply()

    var externalStickerCache: Boolean
        get() = prefs.getBoolean("external_sticker_cache", false)
        set(value) = prefs.edit().putBoolean("external_sticker_cache", value).apply()

    var removeFavouriteStickersInRecentStickers: Boolean
        get() = prefs.getBoolean("remove_favourite_stickers_in_recent_stickers", false)
        set(value) = prefs.edit().putBoolean("remove_favourite_stickers_in_recent_stickers", value).apply()

    var regexFiltersEnabled: Boolean
        get() = prefs.getBoolean("regex_filters_enabled", false)
        set(value) = prefs.edit().putBoolean("regex_filters_enabled", value).apply()

    var regexFiltersEnableInChats: Boolean
        get() = prefs.getBoolean("regex_filters_enable_in_chats", false)
        set(value) = prefs.edit().putBoolean("regex_filters_enable_in_chats", value).apply()

    var showPremiumAvatarAnimation: Boolean
        get() = prefs.getBoolean("show_premium_avatar_animation", true)
        set(value) = prefs.edit().putBoolean("show_premium_avatar_animation", value).apply()

    var showPremiumStarInChat: Boolean
        get() = prefs.getBoolean("show_premium_star_in_chat", true)
        set(value) = prefs.edit().putBoolean("show_premium_star_in_chat", value).apply()

    var quickToggleAnonymous: Boolean
        get() = prefs.getBoolean("quick_toggle_anonymous", false)
        set(value) = prefs.edit().putBoolean("quick_toggle_anonymous", value).apply()

    var showInvertReply: Boolean
        get() = prefs.getBoolean("show_invert_reply", false)
        set(value) = prefs.edit().putBoolean("show_invert_reply", value).apply()

    var disableDialogsFloatingButton: Boolean
        get() = prefs.getBoolean("disable_dialogs_floating_button", false)
        set(value) = prefs.edit().putBoolean("disable_dialogs_floating_button", value).apply()

    var disableProxyWhenVpnEnabled: Boolean
        get() = prefs.getBoolean("disable_proxy_when_vpn_enabled", false)
        set(value) = prefs.edit().putBoolean("disable_proxy_when_vpn_enabled", value).apply()

    var ignoreFolderCount: Boolean
        get() = prefs.getBoolean("ignore_folder_count", false)
        set(value) = prefs.edit().putBoolean("ignore_folder_count", value).apply()

    var hideFilterMuteAll: Boolean
        get() = prefs.getBoolean("hide_filter_mute_all", false)
        set(value) = prefs.edit().putBoolean("hide_filter_mute_all", value).apply()

    var hideInstantCamera: Boolean
        get() = prefs.getBoolean("hide_instant_camera", false)
        set(value) = prefs.edit().putBoolean("hide_instant_camera", value).apply()

    var chatActivityNavbarTransparent: Boolean
        get() = prefs.getBoolean("chat_activity_navbar_transparent", false)
        set(value) = prefs.edit().putBoolean("chat_activity_navbar_transparent", value).apply()

    var coloredAdminTitle: Boolean
        get() = prefs.getBoolean("colored_admin_title", false)
        set(value) = prefs.edit().putBoolean("colored_admin_title", value).apply()

    var showUserIconsInChatsList: Boolean
        get() = prefs.getBoolean("show_user_icons_in_chats_list", false)
        set(value) = prefs.edit().putBoolean("show_user_icons_in_chats_list", value).apply()

    var showRecentChatsInSidebar: Boolean
        get() = prefs.getBoolean("show_recent_chats_in_sidebar", false)
        set(value) = prefs.edit().putBoolean("show_recent_chats_in_sidebar", value).apply()

    var typeMessageHintUseGroupName: Boolean
        get() = prefs.getBoolean("type_message_hint_use_group_name", false)
        set(value) = prefs.edit().putBoolean("type_message_hint_use_group_name", value).apply()

    var showTimeHint: Boolean
        get() = prefs.getBoolean("show_time_hint", false)
        set(value) = prefs.edit().putBoolean("show_time_hint", value).apply()

    var alwaysSaveChatOffset: Boolean
        get() = prefs.getBoolean("always_save_chat_offset", false)
        set(value) = prefs.edit().putBoolean("always_save_chat_offset", value).apply()

    var alwaysShowDownloadIcon: Boolean
        get() = prefs.getBoolean("always_show_download_icon", false)
        set(value) = prefs.edit().putBoolean("always_show_download_icon", value).apply()

    var customEditedMessage: String
        get() = prefs.getString("custom_edited_message", "") ?: ""
        set(value) = prefs.edit().putString("custom_edited_message", value).apply()

    var customChannelLabel: String
        get() = prefs.getString("custom_channel_label", "") ?: ""
        set(value) = prefs.edit().putString("custom_channel_label", value).apply()

    var hideBotButtonInInputField: Boolean
        get() = prefs.getBoolean("hide_bot_button_in_input_field", false)
        set(value) = prefs.edit().putBoolean("hide_bot_button_in_input_field", value).apply()

    var disableClickCommandToSend: Boolean
        get() = prefs.getBoolean("disable_click_command_to_send", false)
        set(value) = prefs.edit().putBoolean("disable_click_command_to_send", value).apply()

    var disablePremiumChristmas: Boolean
        get() = prefs.getBoolean("disable_premium_christmas", false)
        set(value) = prefs.edit().putBoolean("disable_premium_christmas", value).apply()

    var disablePremiumExpiring: Boolean
        get() = prefs.getBoolean("disable_premium_expiring", false)
        set(value) = prefs.edit().putBoolean("disable_premium_expiring", value).apply()

    var disablePremiumRestore: Boolean
        get() = prefs.getBoolean("disable_premium_restore", false)
        set(value) = prefs.edit().putBoolean("disable_premium_restore", value).apply()

    var disablePremiumSendTodo: Boolean
        get() = prefs.getBoolean("disable_premium_send_todo", false)
        set(value) = prefs.edit().putBoolean("disable_premium_send_todo", value).apply()

    var disableStarsSubscription: Boolean
        get() = prefs.getBoolean("disable_stars_subscription", false)
        set(value) = prefs.edit().putBoolean("disable_stars_subscription", value).apply()

    var disableEmptyStarButton: Boolean
        get() = prefs.getBoolean("disable_empty_star_button", false)
        set(value) = prefs.edit().putBoolean("disable_empty_star_button", value).apply()

    var disableChannelMuteButton: Boolean
        get() = prefs.getBoolean("disable_channel_mute_button", false)
        set(value) = prefs.edit().putBoolean("disable_channel_mute_button", value).apply()

    var disableNonPremiumChannelChatShow: Boolean
        get() = prefs.getBoolean("disable_non_premium_channel_chat_show", false)
        set(value) = prefs.edit().putBoolean("disable_non_premium_channel_chat_show", value).apply()

    var disableAutoWebLogin: Boolean
        get() = prefs.getBoolean("disable_auto_web_login", false)
        set(value) = prefs.edit().putBoolean("disable_auto_web_login", value).apply()

    var disableBirthdayContact: Boolean
        get() = prefs.getBoolean("disable_birthday_contact", false)
        set(value) = prefs.edit().putBoolean("disable_birthday_contact", value).apply()

    var disablePhoneSharePrompt: Boolean
        get() = prefs.getBoolean("disable_phone_share_prompt", false)
        set(value) = prefs.edit().putBoolean("disable_phone_share_prompt", value).apply()

    var doNotShareMyPhoneNumber: Boolean
        get() = prefs.getBoolean("do_not_share_my_phone_number", false)
        set(value) = prefs.edit().putBoolean("do_not_share_my_phone_number", value).apply()

    var disableRepeatInChannel: Boolean
        get() = prefs.getBoolean("disable_repeat_in_channel", false)
        set(value) = prefs.edit().putBoolean("disable_repeat_in_channel", value).apply()

    var disableCustomWallpaperChannel: Boolean
        get() = prefs.getBoolean("disable_custom_wallpaper_channel", false)
        set(value) = prefs.edit().putBoolean("disable_custom_wallpaper_channel", value).apply()

    var disableCustomWallpaperUser: Boolean
        get() = prefs.getBoolean("disable_custom_wallpaper_user", false)
        set(value) = prefs.edit().putBoolean("disable_custom_wallpaper_user", value).apply()

    var zalgoFilter: Boolean
        get() = prefs.getBoolean("zalgo_filter", false)
        set(value) = prefs.edit().putBoolean("zalgo_filter", value).apply()

    var showRPCError: Boolean
        get() = prefs.getBoolean("show_rpc_error", false)
        set(value) = prefs.edit().putBoolean("show_rpc_error", value).apply()

    var showHiddenFeature: Boolean
        get() = prefs.getBoolean("show_hidden_feature", false)
        set(value) = prefs.edit().putBoolean("show_hidden_feature", value).apply()

    var playerDecoder: Int
        get() = prefs.getInt("player_decoder", 0)
        set(value) = prefs.edit().putInt("player_decoder", value).apply()

    var fakeHighPerformanceDevice: Boolean
        get() = prefs.getBoolean("fake_high_performance_device", false)
        set(value) = prefs.edit().putBoolean("fake_high_performance_device", value).apply()

    var sendMp4DocumentAsVideo: Boolean
        get() = prefs.getBoolean("send_mp4_document_as_video", false)
        set(value) = prefs.edit().putBoolean("send_mp4_document_as_video", value).apply()

    var showSmallGIF: Boolean
        get() = prefs.getBoolean("show_small_gif", false)
        set(value) = prefs.edit().putBoolean("show_small_gif", value).apply()

    var autoReplaceRepeat: Boolean
        get() = prefs.getBoolean("auto_replace_repeat", false)
        set(value) = prefs.edit().putBoolean("auto_replace_repeat", value).apply()

    var disableFeatuerdEmojis: Boolean
        get() = prefs.getBoolean("disable_featuerd_emojis", false)
        set(value) = prefs.edit().putBoolean("disable_featuerd_emojis", value).apply()

    var disableFeaturedStickers: Boolean
        get() = prefs.getBoolean("disable_featured_stickers", false)
        set(value) = prefs.edit().putBoolean("disable_featured_stickers", value).apply()

    var disableFeaturedGifs: Boolean
        get() = prefs.getBoolean("disable_featured_gifs", false)
        set(value) = prefs.edit().putBoolean("disable_featured_gifs", value).apply()

    var disablePremiumFavoriteEmojiTags: Boolean
        get() = prefs.getBoolean("disable_premium_favorite_emoji_tags", false)
        set(value) = prefs.edit().putBoolean("disable_premium_favorite_emoji_tags", value).apply()

    var disableFavoriteSearchEmojiTags: Boolean
        get() = prefs.getBoolean("disable_favorite_search_emoji_tags", false)
        set(value) = prefs.edit().putBoolean("disable_favorite_search_emoji_tags", value).apply()

    var disableEmojiDrawLimit: Boolean
        get() = prefs.getBoolean("disable_emoji_draw_limit", false)
        set(value) = prefs.edit().putBoolean("disable_emoji_draw_limit", value).apply()

    var disablePreviewVideoSoundShortcut: Boolean
        get() = prefs.getBoolean("disable_preview_video_sound_shortcut", false)
        set(value) = prefs.edit().putBoolean("disable_preview_video_sound_shortcut", value).apply()

    var disableShortcutTagActions: Boolean
        get() = prefs.getBoolean("disable_shortcut_tag_actions", false)
        set(value) = prefs.edit().putBoolean("disable_shortcut_tag_actions", value).apply()

    var disableGlobalSearch: Boolean
        get() = prefs.getBoolean("disable_global_search", false)
        set(value) = prefs.edit().putBoolean("disable_global_search", value).apply()

    var disableSuggestionView: Boolean
        get() = prefs.getBoolean("disable_suggestion_view", false)
        set(value) = prefs.edit().putBoolean("disable_suggestion_view", value).apply()

    var disableSecondAddress: Boolean
        get() = prefs.getBoolean("disable_second_address", false)
        set(value) = prefs.edit().putBoolean("disable_second_address", value).apply()

    var disableBotOpenButton: Boolean
        get() = prefs.getBoolean("disable_bot_open_button", false)
        set(value) = prefs.edit().putBoolean("disable_bot_open_button", value).apply()

    var disableClickProfileGalleryView: Boolean
        get() = prefs.getBoolean("disable_click_profile_gallery_view", false)
        set(value) = prefs.edit().putBoolean("disable_click_profile_gallery_view", value).apply()

    var hideMessageSeenTooltip: Boolean
        get() = prefs.getBoolean("hide_message_seen_tooltip", false)
        set(value) = prefs.edit().putBoolean("hide_message_seen_tooltip", value).apply()

    var autoInsertGIFCaption: Boolean
        get() = prefs.getBoolean("auto_insert_gif_caption", false)
        set(value) = prefs.edit().putBoolean("auto_insert_gif_caption", value).apply()

    var disableActionBarButtonCopy: Boolean
        get() = prefs.getBoolean("disable_action_bar_button_copy", false)
        set(value) = prefs.edit().putBoolean("disable_action_bar_button_copy", value).apply()

    var disableActionBarButtonEdit: Boolean
        get() = prefs.getBoolean("disable_action_bar_button_edit", false)
        set(value) = prefs.edit().putBoolean("disable_action_bar_button_edit", value).apply()

    var disableActionBarButtonForward: Boolean
        get() = prefs.getBoolean("disable_action_bar_button_forward", false)
        set(value) = prefs.edit().putBoolean("disable_action_bar_button_forward", value).apply()

    var disableActionBarButtonReply: Boolean
        get() = prefs.getBoolean("disable_action_bar_button_reply", false)
        set(value) = prefs.edit().putBoolean("disable_action_bar_button_reply", value).apply()

    var disableActionBarButtonSelectBetween: Boolean
        get() = prefs.getBoolean("disable_action_bar_button_select_between", false)
        set(value) = prefs.edit().putBoolean("disable_action_bar_button_select_between", value).apply()

    var showVoteCountBeforeVote: Boolean
        get() = prefs.getBoolean("show_vote_count_before_vote", false)
        set(value) = prefs.edit().putBoolean("show_vote_count_before_vote", value).apply()

    var showGreatOrPoor: Boolean
        get() = prefs.getBoolean("show_great_or_poor", false)
        set(value) = prefs.edit().putBoolean("show_great_or_poor", value).apply()

    var customGreat: String
        get() = prefs.getString("custom_great", "") ?: ""
        set(value) = prefs.edit().putString("custom_great", value).apply()

    var customPoor: String
        get() = prefs.getString("custom_poor", "") ?: ""
        set(value) = prefs.edit().putString("custom_poor", value).apply()

    var customTitle: String
        get() = prefs.getString("custom_title", "") ?: ""
        set(value) = prefs.edit().putString("custom_title", value).apply()

    var customTitleUserName: String
        get() = prefs.getString("custom_title_user_name", "") ?: ""
        set(value) = prefs.edit().putString("custom_title_user_name", value).apply()

    var customArtworkApi: String
        get() = prefs.getString("custom_artwork_api", "") ?: ""
        set(value) = prefs.edit().putString("custom_artwork_api", value).apply()

    var defaultHlsVideoQuality: Int
        get() = prefs.getInt("default_hls_video_quality", 0)
        set(value) = prefs.edit().putInt("default_hls_video_quality", value).apply()

    var defaultMonoLanguage: String
        get() = prefs.getString("default_mono_language", "") ?: ""
        set(value) = prefs.edit().putString("default_mono_language", value).apply()

    var searchHashtagDefaultPageChat: Boolean
        get() = prefs.getBoolean("search_hashtag_default_page_chat", false)
        set(value) = prefs.edit().putBoolean("search_hashtag_default_page_chat", value).apply()

    var searchHashtagDefaultPageChannel: Boolean
        get() = prefs.getBoolean("search_hashtag_default_page_channel", false)
        set(value) = prefs.edit().putBoolean("search_hashtag_default_page_channel", value).apply()

    var enablePanguOnSending: Boolean
        get() = prefs.getBoolean("enable_pangu_on_sending", false)
        set(value) = prefs.edit().putBoolean("enable_pangu_on_sending", value).apply()

    var enablePanguOnReceiving: Boolean
        get() = prefs.getBoolean("enable_pangu_on_receiving", false)
        set(value) = prefs.edit().putBoolean("enable_pangu_on_receiving", value).apply()

    var enablePanguOnEditing: Boolean
        get() = prefs.getBoolean("enable_pangu_on_editing", false)
        set(value) = prefs.edit().putBoolean("enable_pangu_on_editing", value).apply()

    var showTextBold: Boolean
        get() = prefs.getBoolean("show_text_bold", true)
        set(value) = prefs.edit().putBoolean("show_text_bold", value).apply()

    var showTextItalic: Boolean
        get() = prefs.getBoolean("show_text_italic", true)
        set(value) = prefs.edit().putBoolean("show_text_italic", value).apply()

    var showTextMono: Boolean
        get() = prefs.getBoolean("show_text_mono", true)
        set(value) = prefs.edit().putBoolean("show_text_mono", value).apply()

    var showTextStrikethrough: Boolean
        get() = prefs.getBoolean("show_text_strikethrough", true)
        set(value) = prefs.edit().putBoolean("show_text_strikethrough", value).apply()

    var showTextUnderline: Boolean
        get() = prefs.getBoolean("show_text_underline", true)
        set(value) = prefs.edit().putBoolean("show_text_underline", value).apply()

    var showTextSpoiler: Boolean
        get() = prefs.getBoolean("show_text_spoiler", true)
        set(value) = prefs.edit().putBoolean("show_text_spoiler", value).apply()

    var showTextCreateLink: Boolean
        get() = prefs.getBoolean("show_text_create_link", true)
        set(value) = prefs.edit().putBoolean("show_text_create_link", value).apply()

    var showTextCreateMention: Boolean
        get() = prefs.getBoolean("show_text_create_mention", true)
        set(value) = prefs.edit().putBoolean("show_text_create_mention", value).apply()

    var showTextRegular: Boolean
        get() = prefs.getBoolean("show_text_regular", true)
        set(value) = prefs.edit().putBoolean("show_text_regular", value).apply()

    var showTextQuote: Boolean
        get() = prefs.getBoolean("show_text_quote", true)
        set(value) = prefs.edit().putBoolean("show_text_quote", value).apply()

    var showTextUndoRedo: Boolean
        get() = prefs.getBoolean("show_text_undo_redo", true)
        set(value) = prefs.edit().putBoolean("show_text_undo_redo", value).apply()

    var showQuickReplyInBotCommands: Boolean
        get() = prefs.getBoolean("show_quick_reply_in_bot_commands", false)
        set(value) = prefs.edit().putBoolean("show_quick_reply_in_bot_commands", value).apply()

    var showSetReminder: Boolean
        get() = prefs.getBoolean("show_set_reminder", false)
        set(value) = prefs.edit().putBoolean("show_set_reminder", value).apply()

    var showSendAsUnderMessageHint: Boolean
        get() = prefs.getBoolean("show_send_as_under_message_hint", false)
        set(value) = prefs.edit().putBoolean("show_send_as_under_message_hint", value).apply()

    var openUrlOutBotWebViewRegex: String
        get() = prefs.getString("open_url_out_bot_web_view_regex", "") ?: ""
        set(value) = prefs.edit().putString("open_url_out_bot_web_view_regex", value).apply()

    var pushServiceType: Int
        get() = prefs.getInt("push_service_type", 0)
        set(value) = prefs.edit().putInt("push_service_type", value).apply()

    var pushServiceTypeInAppDialog: Boolean
        get() = prefs.getBoolean("push_service_type_in_app_dialog", false)
        set(value) = prefs.edit().putBoolean("push_service_type_in_app_dialog", value).apply()

    var pushServiceTypeUnifiedGateway: String
        get() = prefs.getString("push_service_type_unified_gateway", "") ?: ""
        set(value) = prefs.edit().putString("push_service_type_unified_gateway", value).apply()

    // ============ NekoXConfig Settings ============
    var customApi: Boolean
        get() = prefs.getBoolean("custom_api", false)
        set(value) = prefs.edit().putBoolean("custom_api", value).apply()

    var customAppId: Int
        get() = prefs.getInt("custom_app_id", 0)
        set(value) = prefs.edit().putInt("custom_app_id", value).apply()

    var customAppHash: String
        get() = prefs.getString("custom_app_hash", "") ?: ""
        set(value) = prefs.edit().putString("custom_app_hash", value).apply()

    var disableFlagSecure: Boolean
        get() = prefs.getBoolean("disable_flag_secure", false)
        set(value) = prefs.edit().putBoolean("disable_flag_secure", value).apply()

    var autoUpdateReleaseChannel: Int
        get() = prefs.getInt("auto_update_release_channel", 0)
        set(value) = prefs.edit().putInt("auto_update_release_channel", value).apply()

    /**
     * Initialize the config with application context.
     * Call this from Application.onCreate()
     */
    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Reset all feature flags to default (all OFF)
     */
    fun resetToDefaults() {
        prefs.edit().clear().apply()
    }

    // Ghost mode helper function (from NekoConfig)
    // ============ Theme ============
    var darkMode: Boolean
        get() = prefs.getBoolean("dark_mode", false)
        set(value) = prefs.edit().putBoolean("dark_mode", value).apply()

    var useTonyTheme: Boolean
        get() = prefs.getBoolean("use_tony_theme", true)
        set(value) = prefs.edit().putBoolean("use_tony_theme", value).apply()

    fun setGhostMode(enabled: Boolean) {
        isGhostModeActive = enabled
        if (enabled) {
            sendOnlinePackets = false
            sendReadMessagePackets = false
            sendReadStoryPackets = false
            sendUploadProgress = false
        } else {
            sendOnlinePackets = true
            sendReadMessagePackets = true
            sendReadStoryPackets = true
            sendUploadProgress = true
        }
    }
}
