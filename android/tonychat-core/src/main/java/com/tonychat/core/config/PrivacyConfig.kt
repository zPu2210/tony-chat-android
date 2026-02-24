package com.tonychat.core.config

import android.content.SharedPreferences

/**
 * Privacy and Ghost Mode related settings
 */
class PrivacyConfig(private val prefs: SharedPreferences) {
    var isGhostModeActive: Boolean
        get() = prefs.getBoolean("is_ghost_mode_active", false)
        set(value) = prefs.edit().putBoolean("is_ghost_mode_active", value).apply()

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

    var suppressTypingIndicator: Boolean
        get() = prefs.getBoolean("suppress_typing_indicator", false)
        set(value) = prefs.edit().putBoolean("suppress_typing_indicator", value).apply()

    var showGhostToggleInDrawer: Boolean
        get() = prefs.getBoolean("show_ghost_toggle_in_drawer", false)
        set(value) = prefs.edit().putBoolean("show_ghost_toggle_in_drawer", value).apply()

    var sendOfflineAfterOnline: Int
        get() = prefs.getInt("send_offline_after_online", 0)
        set(value) = prefs.edit().putInt("send_offline_after_online", value).apply()

    var ignoreBlocked: Boolean
        get() = prefs.getBoolean("ignore_blocked", false)
        set(value) = prefs.edit().putBoolean("ignore_blocked", value).apply()

    var hideProxySponsorChannel: Boolean
        get() = prefs.getBoolean("hide_proxy_sponsor_channel", false)
        set(value) = prefs.edit().putBoolean("hide_proxy_sponsor_channel", value).apply()

    var hideSponsoredMessage: Boolean
        get() = prefs.getBoolean("hide_sponsored_message", false)
        set(value) = prefs.edit().putBoolean("hide_sponsored_message", value).apply()

    var hidePhone: Boolean
        get() = prefs.getBoolean("hide_phone", false)
        set(value) = prefs.edit().putBoolean("hide_phone", value).apply()

    var disableSystemAccount: Boolean
        get() = prefs.getBoolean("disable_system_account", false)
        set(value) = prefs.edit().putBoolean("disable_system_account", value).apply()

    var disableFlagSecure: Boolean
        get() = prefs.getBoolean("disable_flag_secure", false)
        set(value) = prefs.edit().putBoolean("disable_flag_secure", value).apply()

    var showOnlineStatus: Boolean
        get() = prefs.getBoolean("show_online_status", true)
        set(value) = prefs.edit().putBoolean("show_online_status", value).apply()

    var showRecentOnlineStatus: Boolean
        get() = prefs.getBoolean("show_recent_online_status", true)
        set(value) = prefs.edit().putBoolean("show_recent_online_status", value).apply()

    var disableSendReadStories: Boolean
        get() = prefs.getBoolean("disable_send_read_stories", false)
        set(value) = prefs.edit().putBoolean("disable_send_read_stories", value).apply()

    var doNotShareMyPhoneNumber: Boolean
        get() = prefs.getBoolean("do_not_share_my_phone_number", false)
        set(value) = prefs.edit().putBoolean("do_not_share_my_phone_number", value).apply()

    var disablePhoneSharePrompt: Boolean
        get() = prefs.getBoolean("disable_phone_share_prompt", false)
        set(value) = prefs.edit().putBoolean("disable_phone_share_prompt", value).apply()

    fun setGhostMode(enabled: Boolean) {
        isGhostModeActive = enabled
        if (enabled) {
            sendOnlinePackets = false
            sendReadMessagePackets = false
            sendReadStoryPackets = false
            sendUploadProgress = false
            suppressTypingIndicator = true
        } else {
            sendOnlinePackets = true
            sendReadMessagePackets = true
            sendReadStoryPackets = true
            sendUploadProgress = true
            suppressTypingIndicator = false
        }
    }
}
