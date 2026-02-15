package com.tonychat.ai.ratelimit

import com.tonychat.ai.AiFeatureType

/** Token bucket rate limiter per AI feature. */
class RateLimiter {

    private data class Bucket(
        val maxTokens: Int,
        val refillPerMinute: Int,
        var tokens: Double,
        var lastRefill: Long = System.currentTimeMillis()
    )

    private val buckets = mutableMapOf(
        AiFeatureType.SMART_REPLY to Bucket(60, 60, 60.0),
        AiFeatureType.SUMMARY to Bucket(10, 10, 10.0),
        AiFeatureType.TONE_REWRITE to Bucket(30, 30, 30.0),
        AiFeatureType.TRANSLATE to Bucket(30, 30, 30.0)
    )

    fun tryAcquire(feature: AiFeatureType): Boolean {
        val bucket = buckets[feature] ?: return false
        refill(bucket)
        return if (bucket.tokens >= 1.0) {
            bucket.tokens -= 1.0
            true
        } else {
            false
        }
    }

    private fun refill(bucket: Bucket) {
        val now = System.currentTimeMillis()
        val elapsed = (now - bucket.lastRefill) / 60_000.0
        bucket.tokens = (bucket.tokens + elapsed * bucket.refillPerMinute)
            .coerceAtMost(bucket.maxTokens.toDouble())
        bucket.lastRefill = now
    }
}
