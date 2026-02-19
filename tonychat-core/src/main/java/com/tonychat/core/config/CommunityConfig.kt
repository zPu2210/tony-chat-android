package com.tonychat.core.config

import android.content.SharedPreferences

/**
 * Community Board configuration
 */
class CommunityConfig(private val prefs: SharedPreferences) {
    var communityEnabled: Boolean
        get() = prefs.getBoolean("community_enabled", true)
        set(value) = prefs.edit().putBoolean("community_enabled", value).apply()

    var postRadius: Float
        get() = prefs.getFloat("post_radius", 5000f)
        set(value) = prefs.edit().putFloat("post_radius", value).apply()

    var feedSort: String
        get() = prefs.getString("feed_sort", "recent") ?: "recent"
        set(value) = prefs.edit().putString("feed_sort", value).apply()

    var imageQuality: Int
        get() = prefs.getInt("image_quality", 85)
        set(value) = prefs.edit().putInt("image_quality", value).apply()

    var maxImageSizeMb: Int
        get() = prefs.getInt("max_image_size_mb", 5)
        set(value) = prefs.edit().putInt("max_image_size_mb", value).apply()
}
