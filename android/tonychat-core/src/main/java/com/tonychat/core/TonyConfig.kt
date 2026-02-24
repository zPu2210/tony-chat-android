package com.tonychat.core

import android.content.Context
import android.content.SharedPreferences
import com.tonychat.core.config.*

/**
 * Centralized configuration facade for Tony Chat.
 * Delegates to domain-specific configs for better organization.
 * This is the single source of truth for all config.
 */
object TonyConfig {
    private const val PREFS_NAME = "tonychat_config"
    private lateinit var prefs: SharedPreferences

    // Domain-specific configs
    lateinit var privacy: PrivacyConfig
        private set
    lateinit var chat: ChatConfig
        private set
    lateinit var ui: UiConfig
        private set
    lateinit var community: CommunityConfig
        private set
    lateinit var media: MediaConfig
        private set

    // ============ Feature Flags (Tony Chat specific) ============
    var bottomNavEnabled: Boolean
        get() = prefs.getBoolean("bottom_nav_enabled", true)
        set(value) = prefs.edit().putBoolean("bottom_nav_enabled", value).apply()

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

    // ============ Additional Settings (remaining from original) ============
    var disableReactionsWhenSelecting: Boolean
        get() = prefs.getBoolean("disable_reactions_when_selecting", false)
        set(value) = prefs.edit().putBoolean("disable_reactions_when_selecting", value).apply()

    var disableRemoteEmojiInteractions: Boolean
        get() = prefs.getBoolean("disable_remote_emoji_interactions", false)
        set(value) = prefs.edit().putBoolean("disable_remote_emoji_interactions", value).apply()

    var unlimitedPinnedDialogs: Boolean
        get() = prefs.getBoolean("unlimited_pinned_dialogs", false)
        set(value) = prefs.edit().putBoolean("unlimited_pinned_dialogs", value).apply()

    var disableNotificationBubbles: Boolean
        get() = prefs.getBoolean("disable_notification_bubbles", false)
        set(value) = prefs.edit().putBoolean("disable_notification_bubbles", value).apply()

    var disableProximityEvents: Boolean
        get() = prefs.getBoolean("disable_proximity_events", false)
        set(value) = prefs.edit().putBoolean("disable_proximity_events", value).apply()

    var disableNumberRounding: Boolean
        get() = prefs.getBoolean("disable_number_rounding", false)
        set(value) = prefs.edit().putBoolean("disable_number_rounding", value).apply()

    var ignoreMutedCount: Boolean
        get() = prefs.getBoolean("ignore_muted_count", false)
        set(value) = prefs.edit().putBoolean("ignore_muted_count", value).apply()

    var showAdminActions: Boolean
        get() = prefs.getBoolean("show_admin_actions", false)
        set(value) = prefs.edit().putBoolean("show_admin_actions", value).apply()

    var showChangePermissions: Boolean
        get() = prefs.getBoolean("show_change_permissions", false)
        set(value) = prefs.edit().putBoolean("show_change_permissions", value).apply()

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

    var ignoreContentRestrictions: Boolean
        get() = prefs.getBoolean("ignore_content_restrictions", false)
        set(value) = prefs.edit().putBoolean("ignore_content_restrictions", value).apply()

    var mapPreviewProvider: Int
        get() = prefs.getInt("map_preview_provider", 0)
        set(value) = prefs.edit().putInt("map_preview_provider", value).apply()

    var useOSMDroidMap: Boolean
        get() = prefs.getBoolean("use_osm_droid_map", false)
        set(value) = prefs.edit().putBoolean("use_osm_droid_map", value).apply()

    var fixDriftingForGoogleMaps: Boolean
        get() = prefs.getBoolean("fix_drifting_for_google_maps", false)
        set(value) = prefs.edit().putBoolean("fix_drifting_for_google_maps", value).apply()

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

    var doubleTapAction: Int
        get() = prefs.getInt("double_tap_action", 0)
        set(value) = prefs.edit().putInt("double_tap_action", value).apply()

    var forceCopy: Boolean
        get() = prefs.getBoolean("force_copy", false)
        set(value) = prefs.edit().putBoolean("force_copy", value).apply()

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

    var showServicesTime: Boolean
        get() = prefs.getBoolean("show_services_time", false)
        set(value) = prefs.edit().putBoolean("show_services_time", value).apply()

    var realHideTimeForSticker: Boolean
        get() = prefs.getBoolean("real_hide_time_for_sticker", false)
        set(value) = prefs.edit().putBoolean("real_hide_time_for_sticker", value).apply()

    var showFullAbout: Boolean
        get() = prefs.getBoolean("show_full_about", false)
        set(value) = prefs.edit().putBoolean("show_full_about", value).apply()

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

    var coloredAdminTitle: Boolean
        get() = prefs.getBoolean("colored_admin_title", false)
        set(value) = prefs.edit().putBoolean("colored_admin_title", value).apply()

    var typeMessageHintUseGroupName: Boolean
        get() = prefs.getBoolean("type_message_hint_use_group_name", false)
        set(value) = prefs.edit().putBoolean("type_message_hint_use_group_name", value).apply()

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

    var fakeHighPerformanceDevice: Boolean
        get() = prefs.getBoolean("fake_high_performance_device", false)
        set(value) = prefs.edit().putBoolean("fake_high_performance_device", value).apply()

    var autoReplaceRepeat: Boolean
        get() = prefs.getBoolean("auto_replace_repeat", false)
        set(value) = prefs.edit().putBoolean("auto_replace_repeat", value).apply()

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

    var customApi: Boolean
        get() = prefs.getBoolean("custom_api", false)
        set(value) = prefs.edit().putBoolean("custom_api", value).apply()

    var customAppId: Int
        get() = prefs.getInt("custom_app_id", 0)
        set(value) = prefs.edit().putInt("custom_app_id", value).apply()

    var customAppHash: String
        get() = prefs.getString("custom_app_hash", "") ?: ""
        set(value) = prefs.edit().putString("custom_app_hash", value).apply()

    var autoUpdateReleaseChannel: Int
        get() = prefs.getInt("auto_update_release_channel", 0)
        set(value) = prefs.edit().putInt("auto_update_release_channel", value).apply()

    var hideSendAsChannel: Boolean
        get() = prefs.getBoolean("hide_send_as_channel", false)
        set(value) = prefs.edit().putBoolean("hide_send_as_channel", value).apply()

    /**
     * Initialize the config with application context.
     * Call this from Application.onCreate()
     */
    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        privacy = PrivacyConfig(prefs)
        chat = ChatConfig(prefs)
        ui = UiConfig(prefs)
        community = CommunityConfig(prefs)
        media = MediaConfig(prefs)
    }

    /**
     * Reset all feature flags to default (all OFF)
     */
    fun resetToDefaults() {
        prefs.edit().clear().apply()
    }

    // ============ Java Interop - Deprecated Accessors ============
    // These provide backward compatibility for Java callers

    @JvmStatic
    @Deprecated("Use privacy.isGhostModeActive", ReplaceWith("privacy.isGhostModeActive"))
    fun getIsGhostModeActive() = privacy.isGhostModeActive

    @JvmStatic
    @Deprecated("Use privacy.isGhostModeActive = value", ReplaceWith("privacy.isGhostModeActive = value"))
    fun setIsGhostModeActive(value: Boolean) { privacy.isGhostModeActive = value }

    @JvmStatic
    @Deprecated("Use privacy.sendOnlinePackets", ReplaceWith("privacy.sendOnlinePackets"))
    fun getSendOnlinePackets() = privacy.sendOnlinePackets

    @JvmStatic
    @Deprecated("Use privacy.sendReadMessagePackets", ReplaceWith("privacy.sendReadMessagePackets"))
    fun getSendReadMessagePackets() = privacy.sendReadMessagePackets

    @JvmStatic
    @Deprecated("Use privacy.sendUploadProgress", ReplaceWith("privacy.sendUploadProgress"))
    fun getSendUploadProgress() = privacy.sendUploadProgress

    @JvmStatic
    @Deprecated("Use privacy.suppressTypingIndicator", ReplaceWith("privacy.suppressTypingIndicator"))
    fun getSuppressTypingIndicator() = privacy.suppressTypingIndicator

    @JvmStatic
    @Deprecated("Use ui.darkMode", ReplaceWith("ui.darkMode"))
    fun getDarkMode() = ui.darkMode

    @JvmStatic
    @Deprecated("Use ui.useTonyTheme", ReplaceWith("ui.useTonyTheme"))
    fun getUseTonyTheme() = ui.useTonyTheme

    @JvmStatic
    @Deprecated("Use privacy.setGhostMode(enabled)", ReplaceWith("privacy.setGhostMode(enabled)"))
    fun setGhostMode(enabled: Boolean) = privacy.setGhostMode(enabled)
}
