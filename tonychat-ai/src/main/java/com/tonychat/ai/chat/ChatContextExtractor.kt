package com.tonychat.ai.chat

import com.tonychat.ai.AiMessage

/**
 * Extracts recent text messages from a Telegram messages list for AI processing.
 * Operates on already-loaded MessageObject instances (no direct Telegram dependency).
 */
object ChatContextExtractor {

    private const val MAX_CHAR_PER_MESSAGE = 500

    /**
     * Converts raw Telegram message data into AiMessage list for AI provider consumption.
     * @param messages list of objects that have getText/isOut/isServiceMessage methods
     *        (uses reflection-free approach: caller maps MessageObject → SimpleMsg)
     * @param count max messages to return
     */
    fun extract(messages: List<SimpleMsg>, count: Int = 5): List<AiMessage> {
        return messages
            .filter { it.text.isNotBlank() && !it.isService }
            .take(count)
            .map { msg ->
                AiMessage(
                    text = msg.text.take(MAX_CHAR_PER_MESSAGE),
                    isOutgoing = msg.isOutgoing
                )
            }
    }

    /** Lightweight message representation to avoid coupling to Telegram's MessageObject. */
    data class SimpleMsg(
        val text: String,
        val isOutgoing: Boolean,
        val isService: Boolean
    )
}
