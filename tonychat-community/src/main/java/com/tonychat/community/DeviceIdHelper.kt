package com.tonychat.community

import android.content.Context
import android.provider.Settings

/**
 * Helper to get unique device ID for anonymous posting
 */
object DeviceIdHelper {
    /**
     * Get device ID using Android Settings.Secure.ANDROID_ID
     * This is unique per device + app installation
     */
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            ?: "unknown-device"
    }
}
