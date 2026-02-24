package com.tonychat.ai.config

import android.content.Context
import android.content.SharedPreferences
import com.tonychat.ai.AiFeatureType

/** AI-specific configuration stored in dedicated SharedPreferences. */
object AiConfig {
    private lateinit var prefs: SharedPreferences
    private lateinit var keyManager: ApiKeyManager

    fun init(context: Context) {
        prefs = context.getSharedPreferences("tonychat_ai", Context.MODE_PRIVATE)
        keyManager = ApiKeyManager(context)
    }

    var preferOnDevice: Boolean
        get() = prefs.getBoolean("prefer_on_device", true)
        set(value) = prefs.edit().putBoolean("prefer_on_device", value).apply()

    var maxCacheSizeMb: Int
        get() = prefs.getInt("max_cache_size_mb", 50)
        set(value) = prefs.edit().putInt("max_cache_size_mb", value).apply()

    fun isFeatureEnabled(feature: AiFeatureType): Boolean =
        prefs.getBoolean("feature_${feature.name}", false)

    fun setFeatureEnabled(feature: AiFeatureType, enabled: Boolean) =
        prefs.edit().putBoolean("feature_${feature.name}", enabled).apply()

    var openAiApiKey: String?
        get() = prefs.getString("openai_key_enc", null)?.let { keyManager.decrypt(it) }
        set(value) {
            if (value == null) {
                prefs.edit().remove("openai_key_enc").apply()
            } else {
                prefs.edit().putString("openai_key_enc", keyManager.encrypt(value)).apply()
            }
        }

    var anthropicApiKey: String?
        get() = prefs.getString("anthropic_key_enc", null)?.let { keyManager.decrypt(it) }
        set(value) {
            if (value == null) {
                prefs.edit().remove("anthropic_key_enc").apply()
            } else {
                prefs.edit().putString("anthropic_key_enc", keyManager.encrypt(value)).apply()
            }
        }

    var removeBgApiKey: String?
        get() = prefs.getString("removebg_key_enc", null)?.let { keyManager.decrypt(it) }
        set(value) {
            if (value == null) {
                prefs.edit().remove("removebg_key_enc").apply()
            } else {
                prefs.edit().putString("removebg_key_enc", keyManager.encrypt(value)).apply()
            }
        }

    var geminiApiKey: String?
        get() = prefs.getString("gemini_key_enc", null)?.let { keyManager.decrypt(it) }
        set(value) {
            if (value == null) {
                prefs.edit().remove("gemini_key_enc").apply()
            } else {
                prefs.edit().putString("gemini_key_enc", keyManager.encrypt(value)).apply()
            }
        }

    var clipDropApiKey: String?
        get() = prefs.getString("clipdrop_key_enc", null)?.let { keyManager.decrypt(it) }
        set(value) {
            if (value == null) {
                prefs.edit().remove("clipdrop_key_enc").apply()
            } else {
                prefs.edit().putString("clipdrop_key_enc", keyManager.encrypt(value)).apply()
            }
        }
}
