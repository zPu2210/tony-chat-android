package com.tonychat.core.config

import android.content.SharedPreferences

/**
 * Chat and AI features configuration
 */
class ChatConfig(private val prefs: SharedPreferences) {
    // Translation
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

    var showTranslate: Boolean
        get() = prefs.getBoolean("show_translate", false)
        set(value) = prefs.edit().putBoolean("show_translate", value).apply()

    // Behavior
    var disableChatAction: Boolean
        get() = prefs.getBoolean("disable_chat_action", false)
        set(value) = prefs.edit().putBoolean("disable_chat_action", value).apply()

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

    var disableLinkPreviewByDefault: Boolean
        get() = prefs.getBoolean("disable_link_preview_by_default", false)
        set(value) = prefs.edit().putBoolean("disable_link_preview_by_default", value).apply()

    var skipOpenLinkConfirm: Boolean
        get() = prefs.getBoolean("skip_open_link_confirm", false)
        set(value) = prefs.edit().putBoolean("skip_open_link_confirm", value).apply()

    var askBeforeCall: Boolean
        get() = prefs.getBoolean("ask_before_call", false)
        set(value) = prefs.edit().putBoolean("ask_before_call", value).apply()

    var confirmAVMessage: Boolean
        get() = prefs.getBoolean("confirm_av_message", false)
        set(value) = prefs.edit().putBoolean("confirm_av_message", value).apply()

    // Sorting
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

    // Additional chat features
    var labelChannelUser: Boolean
        get() = prefs.getBoolean("label_channel_user", false)
        set(value) = prefs.edit().putBoolean("label_channel_user", value).apply()

    var channelAlias: Boolean
        get() = prefs.getBoolean("channel_alias", false)
        set(value) = prefs.edit().putBoolean("channel_alias", value).apply()

    var showAddToSavedMessages: Boolean
        get() = prefs.getBoolean("show_add_to_saved_messages", true)
        set(value) = prefs.edit().putBoolean("show_add_to_saved_messages", value).apply()

    var showRepeat: Boolean
        get() = prefs.getBoolean("show_repeat", false)
        set(value) = prefs.edit().putBoolean("show_repeat", value).apply()

    var showMessageHide: Boolean
        get() = prefs.getBoolean("show_message_hide", false)
        set(value) = prefs.edit().putBoolean("show_message_hide", value).apply()

    var combineMessage: Boolean
        get() = prefs.getBoolean("combine_message", false)
        set(value) = prefs.edit().putBoolean("combine_message", value).apply()

    var alwaysSaveChatOffset: Boolean
        get() = prefs.getBoolean("always_save_chat_offset", false)
        set(value) = prefs.edit().putBoolean("always_save_chat_offset", value).apply()
}
