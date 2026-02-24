package com.tonychat.ai

enum class AiFeatureType(val ttlHours: Int) {
    SMART_REPLY(0),
    SUMMARY(24),
    TONE_REWRITE(1),
    TRANSLATE(72),
    IMAGE_EDIT(168),
    EMOJI_REMIX(720),
    TRANSCRIBE(720),
    AI_WRITER(1),          // Standalone writer: 50/day
    CLIPDROP_IMAGE(168)    // ClipDrop image tools: 5/day shared pool
}
