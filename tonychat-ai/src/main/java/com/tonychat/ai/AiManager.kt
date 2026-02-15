package com.tonychat.ai

import android.content.Context
import com.tonychat.ai.cache.AiCacheDao
import com.tonychat.ai.cache.AiCacheDatabase
import com.tonychat.ai.cache.AiCacheEntity
import com.tonychat.ai.config.AiConfig
import com.tonychat.ai.consent.AiConsentManager
import com.tonychat.ai.provider.*
import com.tonychat.ai.ratelimit.RateLimiter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.MessageDigest

/** Singleton orchestrator: consent check → rate limit → cache → provider selection → execute. */
object AiManager {
    private lateinit var cache: AiCacheDao
    private val rateLimiter = RateLimiter()
    private var onDeviceProvider: AiProvider = NoOpProvider()
    private var cloudProvider: AiProvider = NoOpProvider()

    fun init(appContext: Context) {
        AiConfig.init(appContext)
        AiConsentManager.init(appContext)
        cache = AiCacheDatabase.get(appContext).cacheDao()

        refreshProviders()

        // Evict expired cache entries on startup
        CoroutineScope(Dispatchers.IO).launch {
            try { cache.evictExpired() } catch (_: Exception) {}
        }
    }

    /** Clear all cached AI responses. */
    fun clearCache(onDone: Runnable? = null) {
        CoroutineScope(Dispatchers.IO).launch {
            try { cache.clearAll() } catch (_: Exception) {}
            onDone?.run()
        }
    }

    /** Re-read API keys and rebuild providers. Call after settings change. */
    fun refreshProviders() {
        onDeviceProvider = NoOpProvider() // GeminiNano wired later with device testing
        val openAiKey = AiConfig.openAiApiKey
        val anthropicKey = AiConfig.anthropicApiKey
        cloudProvider = when {
            !openAiKey.isNullOrBlank() -> OpenAiProvider(openAiKey)
            !anthropicKey.isNullOrBlank() -> AnthropicProvider(anthropicKey)
            else -> NoOpProvider()
        }
    }

    suspend fun generateReply(context: List<AiMessage>, count: Int = 3): AiResponse<List<String>> {
        return execute(AiFeatureType.SMART_REPLY) { provider ->
            provider.generateReply(context, count)
        }
    }

    suspend fun summarize(messages: List<AiMessage>, maxLength: Int = 150): AiResponse<String> {
        val cacheKey = cacheHash(AiFeatureType.SUMMARY, messages.joinToString("\n") { it.text })
        return executeWithCache(AiFeatureType.SUMMARY, cacheKey) { provider ->
            provider.summarize(messages, maxLength)
        }
    }

    suspend fun rewriteTone(text: String, tone: ToneStyle): AiResponse<String> {
        val cacheKey = cacheHash(AiFeatureType.TONE_REWRITE, "$text|${tone.name}")
        return executeWithCache(AiFeatureType.TONE_REWRITE, cacheKey) { provider ->
            provider.rewriteTone(text, tone)
        }
    }

    suspend fun translate(text: String, sourceLang: String, targetLang: String): AiResponse<String> {
        val cacheKey = cacheHash(AiFeatureType.TRANSLATE, "$text|$sourceLang|$targetLang")
        return executeWithCache(AiFeatureType.TRANSLATE, cacheKey) { provider ->
            provider.translate(text, sourceLang, targetLang)
        }
    }

    private suspend fun <T> execute(
        feature: AiFeatureType,
        request: suspend (AiProvider) -> AiResponse<T>
    ): AiResponse<T> {
        if (!AiConsentManager.hasConsent(feature)) return AiResponse.ConsentRequired()
        if (!rateLimiter.tryAcquire(feature)) return AiResponse.RateLimited()
        val provider = pickProvider(feature)
        if (provider is NoOpProvider) return AiResponse.Unavailable()
        return request(provider)
    }

    private suspend fun executeWithCache(
        feature: AiFeatureType,
        cacheKey: String,
        request: suspend (AiProvider) -> AiResponse<String>
    ): AiResponse<String> {
        if (!AiConsentManager.hasConsent(feature)) return AiResponse.ConsentRequired()
        if (!rateLimiter.tryAcquire(feature)) return AiResponse.RateLimited()

        // Check cache
        if (feature.ttlHours > 0) {
            try {
                cache.getCached(cacheKey)?.let {
                    return AiResponse.Success(it.responseText, fromCache = true)
                }
            } catch (_: Exception) {}
        }

        val provider = pickProvider(feature)
        if (provider is NoOpProvider) return AiResponse.Unavailable()

        val result = request(provider)

        // Store in cache on success
        if (result is AiResponse.Success && feature.ttlHours > 0) {
            try {
                cache.insert(AiCacheEntity(
                    queryHash = cacheKey,
                    feature = feature.name,
                    responseText = result.data,
                    createdAt = System.currentTimeMillis(),
                    ttlHours = feature.ttlHours
                ))
            } catch (_: Exception) {}
        }

        return result
    }

    private fun pickProvider(feature: AiFeatureType): AiProvider {
        if (AiConfig.preferOnDevice && onDeviceProvider.isAvailable) {
            if (feature in listOf(AiFeatureType.SMART_REPLY, AiFeatureType.TONE_REWRITE)) {
                return onDeviceProvider
            }
        }
        return if (cloudProvider.isAvailable) cloudProvider else NoOpProvider()
    }

    private fun cacheHash(feature: AiFeatureType, input: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes = digest.digest("${feature.name}|$input".toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}
