package com.tonychat.ai

import android.content.Context
import android.provider.Settings

/**
 * Provides a stable device identifier for Edge Function rate limiting.
 * Uses ANDROID_ID: unique per app installation + device combo.
 */
object DeviceIdentifier {
    private var cachedId: String? = null

    fun getId(context: Context): String {
        cachedId?.let { return it }
        val id = Settings.Secure.getString(
            context.contentResolver, Settings.Secure.ANDROID_ID
        ) ?: "unknown"
        cachedId = id
        return id
    }
}
