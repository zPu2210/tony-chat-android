package com.tonychat.core.config

import android.content.SharedPreferences

/**
 * Media and AI media features configuration
 */
class MediaConfig(private val prefs: SharedPreferences) {
    // AI Media features
    var imageEditEnabled: Boolean
        get() = prefs.getBoolean("image_edit_enabled", true)
        set(value) = prefs.edit().putBoolean("image_edit_enabled", value).apply()

    var emojiRemixEnabled: Boolean
        get() = prefs.getBoolean("emoji_remix_enabled", true)
        set(value) = prefs.edit().putBoolean("emoji_remix_enabled", value).apply()

    var voiceTranscribeEnabled: Boolean
        get() = prefs.getBoolean("voice_transcribe_enabled", true)
        set(value) = prefs.edit().putBoolean("voice_transcribe_enabled", value).apply()

    var maxCacheSizeMb: Int
        get() = prefs.getInt("max_cache_size_mb", 100)
        set(value) = prefs.edit().putInt("max_cache_size_mb", value).apply()

    // Media behavior
    var disableInstantCamera: Boolean
        get() = prefs.getBoolean("disable_instant_camera", false)
        set(value) = prefs.edit().putBoolean("disable_instant_camera", value).apply()

    var disablePhotoSideAction: Boolean
        get() = prefs.getBoolean("disable_photo_side_action", true)
        set(value) = prefs.edit().putBoolean("disable_photo_side_action", value).apply()

    var disableSwipeToNext: Boolean
        get() = prefs.getBoolean("disable_swipe_to_next", true)
        set(value) = prefs.edit().putBoolean("disable_swipe_to_next", value).apply()

    var autoPauseVideo: Boolean
        get() = prefs.getBoolean("auto_pause_video", false)
        set(value) = prefs.edit().putBoolean("auto_pause_video", value).apply()

    var mediaPreview: Boolean
        get() = prefs.getBoolean("media_preview", true)
        set(value) = prefs.edit().putBoolean("media_preview", value).apply()

    var rearVideoMessages: Boolean
        get() = prefs.getBoolean("rear_video_messages", false)
        set(value) = prefs.edit().putBoolean("rear_video_messages", value).apply()

    var takeGIFasVideo: Boolean
        get() = prefs.getBoolean("take_gif_as_video", false)
        set(value) = prefs.edit().putBoolean("take_gif_as_video", value).apply()

    var openArchiveOnPull: Boolean
        get() = prefs.getBoolean("open_archive_on_pull", false)
        set(value) = prefs.edit().putBoolean("open_archive_on_pull", value).apply()

    // Download settings
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

    var enhancedVideoBitrate: Boolean
        get() = prefs.getBoolean("enhanced_video_bitrate", false)
        set(value) = prefs.edit().putBoolean("enhanced_video_bitrate", value).apply()

    var alwaysShowDownloadIcon: Boolean
        get() = prefs.getBoolean("always_show_download_icon", false)
        set(value) = prefs.edit().putBoolean("always_show_download_icon", value).apply()

    var showDeleteDownloadedFile: Boolean
        get() = prefs.getBoolean("show_delete_downloaded_file", true)
        set(value) = prefs.edit().putBoolean("show_delete_downloaded_file", value).apply()

    // Sticker settings
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

    var hideGroupSticker: Boolean
        get() = prefs.getBoolean("hide_group_sticker", false)
        set(value) = prefs.edit().putBoolean("hide_group_sticker", value).apply()

    var externalStickerCache: Boolean
        get() = prefs.getBoolean("external_sticker_cache", false)
        set(value) = prefs.edit().putBoolean("external_sticker_cache", value).apply()

    var removeFavouriteStickersInRecentStickers: Boolean
        get() = prefs.getBoolean("remove_favourite_stickers_in_recent_stickers", false)
        set(value) = prefs.edit().putBoolean("remove_favourite_stickers_in_recent_stickers", value).apply()

    var disableFeaturedStickers: Boolean
        get() = prefs.getBoolean("disable_featured_stickers", false)
        set(value) = prefs.edit().putBoolean("disable_featured_stickers", value).apply()

    var disableFeaturedGifs: Boolean
        get() = prefs.getBoolean("disable_featured_gifs", false)
        set(value) = prefs.edit().putBoolean("disable_featured_gifs", value).apply()

    var disableFeaturedEmojis: Boolean
        get() = prefs.getBoolean("disable_featured_emojis", false)
        set(value) = prefs.edit().putBoolean("disable_featured_emojis", value).apply()

    var showSmallGIF: Boolean
        get() = prefs.getBoolean("show_small_gif", false)
        set(value) = prefs.edit().putBoolean("show_small_gif", value).apply()

    var sendMp4DocumentAsVideo: Boolean
        get() = prefs.getBoolean("send_mp4_document_as_video", false)
        set(value) = prefs.edit().putBoolean("send_mp4_document_as_video", value).apply()

    var playerDecoder: Int
        get() = prefs.getInt("player_decoder", 0)
        set(value) = prefs.edit().putInt("player_decoder", value).apply()

    var defaultHlsVideoQuality: Int
        get() = prefs.getInt("default_hls_video_quality", 0)
        set(value) = prefs.edit().putInt("default_hls_video_quality", value).apply()
}
