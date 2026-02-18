package com.tonychat.ai

data class AiMessage(
    val text: String,
    val isOutgoing: Boolean,
    val senderName: String = ""
)
