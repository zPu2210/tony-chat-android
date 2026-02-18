package com.tonychat.ai.ratelimit

import com.tonychat.ai.AiFeatureType
import org.junit.Assert.*
import org.junit.Test

class RateLimiterTest {

    @Test
    fun `tryAcquire succeeds when tokens available`() {
        val limiter = RateLimiter()
        assertTrue(limiter.tryAcquire(AiFeatureType.SMART_REPLY))
    }

    @Test
    fun `smart reply bucket has 60 tokens`() {
        val limiter = RateLimiter()
        repeat(60) {
            assertTrue("Token $it should succeed", limiter.tryAcquire(AiFeatureType.SMART_REPLY))
        }
        assertFalse("61st token should fail", limiter.tryAcquire(AiFeatureType.SMART_REPLY))
    }

    @Test
    fun `summary bucket has 10 tokens`() {
        val limiter = RateLimiter()
        repeat(10) {
            assertTrue(limiter.tryAcquire(AiFeatureType.SUMMARY))
        }
        assertFalse(limiter.tryAcquire(AiFeatureType.SUMMARY))
    }

    @Test
    fun `tone rewrite bucket has 30 tokens`() {
        val limiter = RateLimiter()
        repeat(30) {
            assertTrue(limiter.tryAcquire(AiFeatureType.TONE_REWRITE))
        }
        assertFalse(limiter.tryAcquire(AiFeatureType.TONE_REWRITE))
    }

    @Test
    fun `translate bucket has 30 tokens`() {
        val limiter = RateLimiter()
        repeat(30) {
            assertTrue(limiter.tryAcquire(AiFeatureType.TRANSLATE))
        }
        assertFalse(limiter.tryAcquire(AiFeatureType.TRANSLATE))
    }

    @Test
    fun `different features have independent buckets`() {
        val limiter = RateLimiter()
        // Exhaust SUMMARY
        repeat(10) { limiter.tryAcquire(AiFeatureType.SUMMARY) }
        assertFalse(limiter.tryAcquire(AiFeatureType.SUMMARY))
        // SMART_REPLY unaffected
        assertTrue(limiter.tryAcquire(AiFeatureType.SMART_REPLY))
    }

    @Test
    fun `exhausted bucket stays exhausted without time passing`() {
        val limiter = RateLimiter()
        repeat(10) { limiter.tryAcquire(AiFeatureType.SUMMARY) }
        // Multiple attempts all fail
        assertFalse(limiter.tryAcquire(AiFeatureType.SUMMARY))
        assertFalse(limiter.tryAcquire(AiFeatureType.SUMMARY))
        assertFalse(limiter.tryAcquire(AiFeatureType.SUMMARY))
    }

    @Test
    fun `each limiter instance is independent`() {
        val limiter1 = RateLimiter()
        val limiter2 = RateLimiter()
        repeat(10) { limiter1.tryAcquire(AiFeatureType.SUMMARY) }
        assertFalse(limiter1.tryAcquire(AiFeatureType.SUMMARY))
        assertTrue(limiter2.tryAcquire(AiFeatureType.SUMMARY))
    }
}
