package com.tonychat.ai.provider

import android.content.Context
import com.tonychat.ai.*

/**
 * On-device AI via Google AI Edge SDK (Gemini Nano).
 * Supports SMART_REPLY and TONE_REWRITE only - Nano not strong enough for summarize/translate.
 * Requires Pixel 6+ or Samsung S24+ with AI Core installed.
 *
 * NOTE: Actual Google AI Edge SDK integration deferred until we can test on a physical device.
 * This is a structural placeholder that returns Unavailable until SDK is wired.
 */
class GeminiNanoProvider(private val context: Context) : AiProvider {
    override val name = "gemini-nano"
    override val isOnDevice = true
    override val isAvailable: Boolean get() = checkAvailability(context)

    override suspend fun generateReply(context: List<AiMessage>, count: Int): AiResponse<List<String>> {
        if (!isAvailable) return AiResponse.Unavailable()
        // TODO: Wire Google AI Edge SDK when testing on physical device
        return AiResponse.Unavailable()
    }

    override suspend fun rewriteTone(text: String, tone: ToneStyle): AiResponse<String> {
        if (!isAvailable) return AiResponse.Unavailable()
        // TODO: Wire Google AI Edge SDK when testing on physical device
        return AiResponse.Unavailable()
    }

    override suspend fun summarize(messages: List<AiMessage>, maxLength: Int): AiResponse<String> =
        AiResponse.Unavailable() // Nano not capable enough

    override suspend fun translate(text: String, sourceLang: String, targetLang: String): AiResponse<String> =
        AiResponse.Unavailable() // Nano not capable enough

    companion object {
        fun checkAvailability(context: Context): Boolean {
            // TODO: Check com.google.android.aicore package presence + GenerativeModel.isAvailable()
            return false
        }
    }
}
