package com.tonychat.ai.provider

import com.tonychat.ai.*

/** Fallback provider when AI is disabled or no provider configured. */
class NoOpProvider : AiProvider {
    override val name = "none"
    override val isOnDevice = false
    override val isAvailable = false

    override suspend fun generateReply(context: List<AiMessage>, count: Int) =
        AiResponse.Unavailable<List<String>>()

    override suspend fun summarize(messages: List<AiMessage>, maxLength: Int) =
        AiResponse.Unavailable<String>()

    override suspend fun rewriteTone(text: String, tone: ToneStyle) =
        AiResponse.Unavailable<String>()

    override suspend fun translate(text: String, sourceLang: String, targetLang: String) =
        AiResponse.Unavailable<String>()
}
