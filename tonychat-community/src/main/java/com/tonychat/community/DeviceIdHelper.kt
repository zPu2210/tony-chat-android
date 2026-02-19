package com.tonychat.community

import android.content.Context
import android.provider.Settings
import java.security.MessageDigest
import java.util.UUID

/**
 * Helper to get unique device ID for anonymous posting.
 * Uses hashed ANDROID_ID with app-specific salt to prevent cross-app correlation
 * and spoofing, with UUID fallback for devices with invalid ANDROID_ID.
 */
object DeviceIdHelper {
    private const val PREFS_NAME = "tc_device"
    private const val KEY_DEVICE_ID = "device_id"
    private const val KNOWN_BAD_ANDROID_ID = "9774d56d682e549c" // Default on some devices

    /**
     * Get device ID using hashed ANDROID_ID or fallback UUID.
     * The ID is stable across app restarts.
     */
    fun getDeviceId(context: Context): String {
        val androidId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

        // Check for invalid ANDROID_ID values
        if (androidId.isNullOrEmpty() || androidId == KNOWN_BAD_ANDROID_ID) {
            return getOrCreateFallbackId(context)
        }

        // Hash ANDROID_ID with app-specific salt to prevent cross-app correlation
        return try {
            val salted = "tonychat:$androidId"
            MessageDigest.getInstance("SHA-256")
                .digest(salted.toByteArray(Charsets.UTF_8))
                .joinToString("") { "%02x".format(it) }
                .take(32)
        } catch (e: Exception) {
            // If hashing fails, use fallback
            getOrCreateFallbackId(context)
        }
    }

    /**
     * Generate and persist a random UUID as fallback device ID
     */
    private fun getOrCreateFallbackId(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_DEVICE_ID, null) ?: UUID.randomUUID().toString().also {
            prefs.edit().putString(KEY_DEVICE_ID, it).apply()
        }
    }
}
