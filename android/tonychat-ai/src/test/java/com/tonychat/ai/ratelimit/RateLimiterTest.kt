package com.tonychat.ai.ratelimit

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.tonychat.ai.AiFeatureType
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class RateLimiterTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun `tryAcquire succeeds when tokens available`() {
        val limiter = RateLimiter(context)
        limiter.reset()
        assertTrue(limiter.tryAcquire(AiFeatureType.SMART_REPLY))
    }

    @Test
    fun `smart reply bucket has 60 tokens`() {
        val limiter = RateLimiter(context)
        limiter.reset()
        repeat(60) {
            assertTrue("Token $it should succeed", limiter.tryAcquire(AiFeatureType.SMART_REPLY))
        }
        assertFalse("61st token should fail", limiter.tryAcquire(AiFeatureType.SMART_REPLY))
    }

    @Test
    fun `summary bucket has 10 tokens`() {
        val limiter = RateLimiter(context)
        limiter.reset()
        repeat(10) {
            assertTrue(limiter.tryAcquire(AiFeatureType.SUMMARY))
        }
        assertFalse(limiter.tryAcquire(AiFeatureType.SUMMARY))
    }

    @Test
    fun `tone rewrite bucket has 30 tokens`() {
        val limiter = RateLimiter(context)
        limiter.reset()
        repeat(30) {
            assertTrue(limiter.tryAcquire(AiFeatureType.TONE_REWRITE))
        }
        assertFalse(limiter.tryAcquire(AiFeatureType.TONE_REWRITE))
    }

    @Test
    fun `translate bucket has 30 tokens`() {
        val limiter = RateLimiter(context)
        limiter.reset()
        repeat(30) {
            assertTrue(limiter.tryAcquire(AiFeatureType.TRANSLATE))
        }
        assertFalse(limiter.tryAcquire(AiFeatureType.TRANSLATE))
    }

    @Test
    fun `different features have independent buckets`() {
        val limiter = RateLimiter(context)
        limiter.reset()
        // Exhaust SUMMARY
        repeat(10) { limiter.tryAcquire(AiFeatureType.SUMMARY) }
        assertFalse(limiter.tryAcquire(AiFeatureType.SUMMARY))
        // SMART_REPLY unaffected
        assertTrue(limiter.tryAcquire(AiFeatureType.SMART_REPLY))
    }

    @Test
    fun `exhausted bucket stays exhausted without time passing`() {
        val limiter = RateLimiter(context)
        limiter.reset()
        repeat(10) { limiter.tryAcquire(AiFeatureType.SUMMARY) }
        // Multiple attempts all fail
        assertFalse(limiter.tryAcquire(AiFeatureType.SUMMARY))
        assertFalse(limiter.tryAcquire(AiFeatureType.SUMMARY))
        assertFalse(limiter.tryAcquire(AiFeatureType.SUMMARY))
    }

    @Test
    fun `rate limits persist across instances with same context`() {
        val limiter1 = RateLimiter(context)
        limiter1.reset()
        repeat(10) { limiter1.tryAcquire(AiFeatureType.SUMMARY) }
        assertFalse(limiter1.tryAcquire(AiFeatureType.SUMMARY))

        // New instance with same context should see exhausted state
        val limiter2 = RateLimiter(context)
        assertFalse(limiter2.tryAcquire(AiFeatureType.SUMMARY))
    }
}
