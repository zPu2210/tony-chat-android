package com.tonychat.ai

/** Provider-agnostic interface for LLM backends. */
interface AiProvider {
    val name: String
    val isOnDevice: Boolean
    val isAvailable: Boolean

    suspend fun generateReply(
        context: List<AiMessage>,
        count: Int = 3
    ): AiResponse<List<String>>

    suspend fun summarize(
        messages: List<AiMessage>,
        maxLength: Int = 150
    ): AiResponse<String>

    suspend fun rewriteTone(
        text: String,
        tone: ToneStyle
    ): AiResponse<String>

    suspend fun translate(
        text: String,
        sourceLang: String,
        targetLang: String
    ): AiResponse<String>
}
