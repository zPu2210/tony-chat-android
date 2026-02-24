package com.tonychat.ai.ratelimit

import android.content.Context
import android.content.SharedPreferences
import com.tonychat.ai.AiFeatureType
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

/**
 * Thread-safe, persistent rate limiter per AI feature.
 * Uses ReentrantLock for thread safety and SharedPreferences for persistence across app restarts.
 */
class RateLimiter(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("tonychat_rate_limits", Context.MODE_PRIVATE)
    private val lock = ReentrantLock()

    private data class Config(
        val maxTokens: Int,
        val refillPerMinute: Int
    )

    private val configs = mapOf(
        AiFeatureType.SMART_REPLY to Config(60, 60),
        AiFeatureType.SUMMARY to Config(10, 10),
        AiFeatureType.TONE_REWRITE to Config(30, 30),
        AiFeatureType.TRANSLATE to Config(30, 30),
        AiFeatureType.IMAGE_EDIT to Config(5, 5),
        AiFeatureType.EMOJI_REMIX to Config(250, 250),
        AiFeatureType.TRANSCRIBE to Config(30, 30),
        AiFeatureType.AI_WRITER to Config(50, 50),         // 50/day standalone writer
        AiFeatureType.CLIPDROP_IMAGE to Config(5, 5)        // 5/day shared pool for all ClipDrop tools
    )

    fun tryAcquire(feature: AiFeatureType): Boolean = lock.withLock {
        val config = configs[feature] ?: return false
        val key = feature.name
        val tokensKey = "${key}_tokens"
        val timestampKey = "${key}_timestamp"

        val now = System.currentTimeMillis()
        val lastRefill = prefs.getLong(timestampKey, now)
        val currentTokens = prefs.getFloat(tokensKey, config.maxTokens.toFloat())

        // Refill tokens based on elapsed time
        val elapsedMinutes = (now - lastRefill) / 60_000.0
        val refillAmount = elapsedMinutes * config.refillPerMinute
        val newTokens = (currentTokens + refillAmount).coerceAtMost(config.maxTokens.toDouble())

        return if (newTokens >= 1.0) {
            // Consume 1 token
            prefs.edit()
                .putFloat(tokensKey, (newTokens - 1.0).toFloat())
                .putLong(timestampKey, now)
                .apply()
            true
        } else {
            // Not enough tokens, but update timestamp to prevent drift
            prefs.edit()
                .putFloat(tokensKey, newTokens.toFloat())
                .putLong(timestampKey, now)
                .apply()
            false
        }
    }

    /** Reset all rate limits (for testing or admin purposes). */
    fun reset() = lock.withLock {
        prefs.edit().clear().apply()
    }
}
