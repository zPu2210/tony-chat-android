package com.tonychat.ai

import android.content.Context
import android.util.Log
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.tonychat.ai.cache.AiCacheDao
import com.tonychat.ai.cache.AiCacheDatabase
import com.tonychat.ai.cache.AiCacheEntity
import com.tonychat.ai.cache.EmojiCacheDao
import com.tonychat.ai.cache.EmojiCacheEntity
import com.tonychat.ai.cache.ImageEditCacheDao
import com.tonychat.ai.cache.ImageEditCacheEntity
import com.tonychat.ai.cache.TranscriptCacheDao
import com.tonychat.ai.cache.TranscriptCacheEntity
import com.tonychat.ai.config.AiConfig
import com.tonychat.ai.consent.AiConsentManager
import com.tonychat.ai.provider.*
import com.tonychat.ai.ratelimit.RateLimiter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.security.MessageDigest

/** Singleton orchestrator: consent check → rate limit → cache → provider selection → execute. */
object AiManager {
    private const val TAG = "AiManager"

    private lateinit var cache: AiCacheDao
    private lateinit var imageEditCache: ImageEditCacheDao
    private lateinit var imageEditCacheDir: File
    private lateinit var emojiCache: EmojiCacheDao
    private lateinit var emojiCacheDir: File
    private lateinit var transcriptCache: TranscriptCacheDao
    private lateinit var rateLimiter: RateLimiter
    private var onDeviceProvider: AiProvider = NoOpProvider()
    private var cloudProvider: AiProvider = NoOpProvider()
    private var removeBgProvider: ImageEditProvider? = null
    private var emojiProvider: ImageGenerationProvider? = null

    fun init(appContext: Context) {
        AiConfig.init(appContext)
        AiConsentManager.init(appContext)
        val db = AiCacheDatabase.get(appContext)
        cache = db.cacheDao()
        imageEditCache = db.imageEditCacheDao()
        emojiCache = db.emojiCacheDao()
        transcriptCache = db.transcriptCacheDao()
        imageEditCacheDir = File(appContext.cacheDir, "image_edits")
        emojiCacheDir = File(appContext.cacheDir, "emoji_gen").apply { mkdirs() }
        rateLimiter = RateLimiter(appContext)

        refreshProviders()

        // Evict expired cache entries on startup
        ProcessLifecycleOwner.get().lifecycleScope.launch(Dispatchers.IO) {
            try {
                cache.evictExpired()
                imageEditCache.evictExpired()
                emojiCache.evictExpired()
                transcriptCache.evictExpired()
            } catch (e: Exception) {
                Log.w(TAG, "Cache eviction failed", e)
            }
        }
    }

    /** Clear all cached AI responses. */
    fun clearCache(onDone: Runnable? = null) {
        ProcessLifecycleOwner.get().lifecycleScope.launch(Dispatchers.IO) {
            try {
                cache.clearAll()
            } catch (e: Exception) {
                Log.w(TAG, "Cache clear failed", e)
            }
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

        val removeBgKey = AiConfig.removeBgApiKey
        removeBgProvider = if (!removeBgKey.isNullOrBlank()) {
            RemoveBgProvider(removeBgKey, imageEditCacheDir)
        } else {
            null
        }

        val geminiKey = AiConfig.geminiApiKey
        emojiProvider = if (!geminiKey.isNullOrBlank()) {
            GeminiImageProvider(geminiKey, emojiCacheDir)
        } else {
            null
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
            } catch (e: Exception) {
                Log.w(TAG, "Cache read failed for $feature", e)
            }
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
            } catch (e: Exception) {
                Log.w(TAG, "Cache write failed for $feature", e)
            }
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

    /** Remove background from image file. */
    suspend fun removeBackground(imageFile: File): ImageEditResponse {
        if (!AiConsentManager.hasConsent(AiFeatureType.IMAGE_EDIT)) {
            return ImageEditResponse.ConsentRequired()
        }
        if (!rateLimiter.tryAcquire(AiFeatureType.IMAGE_EDIT)) {
            return ImageEditResponse.RateLimited()
        }

        val hash = imageFile.hashMD5()
        try {
            val cached = imageEditCache.get(hash)
            if (cached != null) {
                val cachedFile = File(cached.resultPath)
                if (cachedFile.exists()) {
                    return ImageEditResponse.Success(cachedFile, fromCache = true)
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "ImageEdit cache read failed", e)
        }

        val provider = removeBgProvider ?: return ImageEditResponse.Error("No provider available. Set API key in AI Settings.")
        val response = provider.removeBackground(imageFile)

        if (response is ImageEditResponse.Success) {
            try {
                imageEditCache.insert(
                    ImageEditCacheEntity(
                        imageHash = hash,
                        resultPath = response.resultFile.absolutePath,
                        createdAt = System.currentTimeMillis(),
                        ttlHours = 168
                    )
                )
            } catch (e: Exception) {
                Log.w(TAG, "ImageEdit cache write failed", e)
            }
        }

        return response
    }

    private fun File.hashMD5(): String {
        val md = MessageDigest.getInstance("MD5")
        return this.inputStream().use { input ->
            val buffer = ByteArray(8192)
            var read: Int
            while (input.read(buffer).also { read = it } > 0) {
                md.update(buffer, 0, read)
            }
            md.digest().joinToString("") { "%02x".format(it) }
        }
    }

    /** Generate emoji from text prompt. */
    suspend fun generateEmoji(prompt: String): ImageGenerationResponse {
        if (!AiConsentManager.hasConsent(AiFeatureType.EMOJI_REMIX)) {
            return ImageGenerationResponse.ConsentRequired()
        }
        if (!rateLimiter.tryAcquire(AiFeatureType.EMOJI_REMIX)) {
            return ImageGenerationResponse.RateLimited()
        }

        // Check cache by prompt hash
        val hash = prompt.trim().lowercase().hashString()
        try {
            val cached = emojiCache.get(hash)
            if (cached != null) {
                val cachedFile = File(cached.imagePath)
                if (cachedFile.exists()) {
                    return ImageGenerationResponse.Success(cachedFile, fromCache = true, provider = cached.provider)
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "Emoji cache read failed", e)
        }

        val provider = emojiProvider ?: return ImageGenerationResponse.Error("No provider available. Set Gemini API key in AI Settings.")
        val response = provider.generateEmoji(prompt)

        if (response is ImageGenerationResponse.Success) {
            try {
                emojiCache.insert(
                    EmojiCacheEntity(
                        promptHash = hash,
                        prompt = prompt,
                        imagePath = response.imageFile.absolutePath,
                        provider = response.provider,
                        createdAt = System.currentTimeMillis(),
                        ttlHours = 720
                    )
                )
            } catch (e: Exception) {
                Log.w(TAG, "Emoji cache write failed", e)
            }
        }

        return response
    }

    private fun String.hashString(): String {
        val md = MessageDigest.getInstance("MD5")
        val bytes = md.digest(this.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    /** Transcribe voice message to text using OpenAI Whisper. */
    suspend fun transcribeVoice(audioFile: File): AiResponse<String> {
        if (!AiConsentManager.hasConsent(AiFeatureType.TRANSCRIBE)) {
            return AiResponse.ConsentRequired()
        }
        if (!rateLimiter.tryAcquire(AiFeatureType.TRANSCRIBE)) {
            return AiResponse.RateLimited()
        }

        // Check cache by file hash
        val hash = audioFile.hashMD5()
        try {
            val cached = transcriptCache.get(hash)
            if (cached != null) {
                return AiResponse.Success(cached.transcript, fromCache = true)
            }
        } catch (e: Exception) {
            Log.w(TAG, "Transcript cache read failed", e)
        }

        val provider = cloudProvider
        if (provider !is OpenAiProvider) {
            return AiResponse.Error("OpenAI API key required. Set in AI Settings.")
        }

        val response = provider.transcribe(audioFile)

        if (response is AiResponse.Success) {
            try {
                transcriptCache.insert(
                    TranscriptCacheEntity(
                        cacheKey = hash,
                        transcript = response.data,
                        createdAt = System.currentTimeMillis(),
                        ttlHours = 720
                    )
                )
            } catch (e: Exception) {
                Log.w(TAG, "Transcript cache write failed", e)
            }
        }

        return response
    }
}
