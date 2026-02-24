package com.tonychat.ai.consent

import android.content.Context
import android.content.SharedPreferences
import com.tonychat.ai.AiFeatureType

/** Tracks per-feature AI consent state. All features opt-in by default. */
object AiConsentManager {
    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences("ai_consent", Context.MODE_PRIVATE)
    }

    fun hasConsent(feature: AiFeatureType): Boolean =
        prefs.getBoolean("consent_${feature.name}", false)

    fun grantConsent(feature: AiFeatureType) =
        prefs.edit().putBoolean("consent_${feature.name}", true).apply()

    fun revokeConsent(feature: AiFeatureType) =
        prefs.edit().putBoolean("consent_${feature.name}", false).apply()

    fun hasAnyConsent(): Boolean =
        AiFeatureType.entries.any { hasConsent(it) }

    fun revokeAll() {
        val editor = prefs.edit()
        AiFeatureType.entries.forEach { editor.remove("consent_${it.name}") }
        editor.apply()
    }
}
