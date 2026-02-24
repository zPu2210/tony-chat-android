package com.tonychat.ai

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.tonychat.ai.cache.AiCacheDao
import com.tonychat.ai.cache.AiCacheDatabase
import com.tonychat.ai.cache.AiCacheEntity
import com.tonychat.ai.config.AiConfig
import com.tonychat.ai.consent.AiConsentManager
import com.tonychat.ai.provider.NoOpProvider
import com.tonychat.ai.provider.OpenAiProvider
import com.tonychat.ai.ratelimit.RateLimiter
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28], manifest = Config.NONE)
class AiManagerTest {

    private lateinit var context: Context
    private lateinit var mockCacheDao: AiCacheDao
    private lateinit var mockRateLimiter: RateLimiter

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()

        // Reset singleton state
        AiManager.resetForTesting()

        // Mock dependencies
        mockCacheDao = mockk(relaxed = true)
        mockRateLimiter = mockk(relaxed = true)
        coEvery { mockRateLimiter.tryAcquire(any()) } returns true

        // Mock static objects
        mockkObject(AiConsentManager)
        every { AiConsentManager.hasConsent(any()) } returns true

        // Mock AiConfig early — before init() calls refreshProviders()
        mockkObject(AiConfig)
        every { AiConfig.openAiApiKey } returns null
        every { AiConfig.anthropicApiKey } returns null
        every { AiConfig.removeBgApiKey } returns null
        every { AiConfig.geminiApiKey } returns null
        every { AiConfig.preferOnDevice } returns false

        mockkObject(AiCacheDatabase)
        val mockDb = mockk<AiCacheDatabase>(relaxed = true)
        every { AiCacheDatabase.get(any()) } returns mockDb
        every { mockDb.cacheDao() } returns mockCacheDao
        every { mockDb.imageEditCacheDao() } returns mockk(relaxed = true)
        every { mockDb.emojiCacheDao() } returns mockk(relaxed = true)
        every { mockDb.transcriptCacheDao() } returns mockk(relaxed = true)

        // Initialize and inject mocks
        AiManager.init(context)
        AiManager.setCacheForTesting(
            mockCacheDao,
            mockk(relaxed = true),
            mockk(relaxed = true),
            mockk(relaxed = true),
            mockRateLimiter
        )
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun `generateReply returns ConsentRequired when consent not given`() = runBlocking {
        every { AiConsentManager.hasConsent(AiFeatureType.SMART_REPLY) } returns false

        val result = AiManager.generateReply(listOf(AiMessage("Test", true)), 3)

        assertTrue(result is AiResponse.ConsentRequired)
    }

    @Test
    fun `summarize returns cached result when available`() = runBlocking {
        // Mock cache with 2-arg version (hash + now default param)
        val cachedEntity = AiCacheEntity(
            queryHash = "test-hash",
            feature = AiFeatureType.SUMMARY.name,
            responseText = "Cached summary result",
            createdAt = System.currentTimeMillis(),
            ttlHours = 168
        )
        coEvery { mockCacheDao.getCached(any(), any()) } returns cachedEntity

        val messages = listOf(AiMessage("Test message", true))
        val result = AiManager.summarize(messages, 150)

        assertTrue("Expected Success but got ${result::class.simpleName}", result is AiResponse.Success)
        assertEquals("Cached summary result", (result as AiResponse.Success).data)
        assertTrue(result.fromCache)
    }

    @Test
    fun `summarize returns RateLimited when rate limit exceeded`() = runBlocking {
        coEvery { mockRateLimiter.tryAcquire(AiFeatureType.SUMMARY) } returns false

        val messages = listOf(AiMessage("Test", true))
        val result = AiManager.summarize(messages, 150)

        assertTrue(result is AiResponse.RateLimited)
    }

    @Test
    fun `rewriteTone returns Unavailable when no provider configured`() = runBlocking {
        // Ensure no cache hit and no provider
        coEvery { mockCacheDao.getCached(any()) } returns null
        coEvery { mockCacheDao.getCached(any(), any()) } returns null
        AiManager.resetForTesting()

        val result = AiManager.rewriteTone("Test", ToneStyle.FORMAL)

        assertTrue("Expected Unavailable but got ${result::class.simpleName}", result is AiResponse.Unavailable)
    }

    @Test
    fun `translate returns Unavailable when no provider configured`() = runBlocking {
        // Ensure no cache hit and no provider
        coEvery { mockCacheDao.getCached(any()) } returns null
        coEvery { mockCacheDao.getCached(any(), any()) } returns null
        AiManager.resetForTesting()

        val result = AiManager.translate("Hello", "en", "fr")

        assertTrue("Expected Unavailable but got ${result::class.simpleName}", result is AiResponse.Unavailable)
    }

    @Test
    fun `clearCache deletes all cached entries`() = runBlocking {
        coEvery { mockCacheDao.clearAll() } just Runs

        AiManager.clearCache()

        Thread.sleep(100)

        coVerify(timeout = 1000) { mockCacheDao.clearAll() }
    }

    @Test
    fun `refreshProviders updates providers based on API keys`() = runBlocking {
        every { AiConfig.openAiApiKey } returns "sk-test-key"

        AiManager.refreshProviders()

        // After refresh with OpenAI key, translate should not return Unavailable
        val result = AiManager.translate("Hello", "en", "fr")

        // Provider is available but will fail without real API - verify not Unavailable
        assertTrue(result is AiResponse.Error || result is AiResponse.Success)
    }

    @Test
    fun `init evicts expired cache entries on startup`() = runBlocking {
        coEvery { mockCacheDao.evictExpired(any()) } just Runs

        // Init is called in setup(), eviction happens in background
        Thread.sleep(200)

        coVerify(timeout = 1000) { mockCacheDao.evictExpired(any()) }
    }

    @Test
    fun `generateReply bypasses cache with zero TTL`() = runBlocking {
        // Smart reply has ttl of 0, should not attempt cache operations
        val result = AiManager.generateReply(listOf(AiMessage("Test", true)), 3)

        // Should not attempt cache read/write for zero-ttl features
        coVerify(exactly = 0) { mockCacheDao.getCached(any(), any()) }
        coVerify(exactly = 0) { mockCacheDao.insert(any()) }
    }

    @Test
    fun `clearCache invokes cache deletion`() = runBlocking {
        coEvery { mockCacheDao.clearAll() } just Runs

        AiManager.clearCache()

        Thread.sleep(150)

        coVerify(timeout = 1000) { mockCacheDao.clearAll() }
    }
}
