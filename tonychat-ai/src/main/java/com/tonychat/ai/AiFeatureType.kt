package com.tonychat.ai

enum class AiFeatureType(val ttlHours: Int) {
    SMART_REPLY(0),
    SUMMARY(24),
    TONE_REWRITE(1),
    TRANSLATE(72),
    IMAGE_EDIT(168),
    EMOJI_REMIX(720),  // 30 days - generated emojis stable per prompt
    TRANSCRIBE(720)    // 30 days - voice transcripts don't change
}
