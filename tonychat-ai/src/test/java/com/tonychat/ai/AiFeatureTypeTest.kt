package com.tonychat.ai

import org.junit.Assert.*
import org.junit.Test

class AiFeatureTypeTest {

    @Test
    fun `SMART_REPLY has zero TTL - not cached`() {
        assertEquals(0, AiFeatureType.SMART_REPLY.ttlHours)
    }

    @Test
    fun `SUMMARY has 24h TTL`() {
        assertEquals(24, AiFeatureType.SUMMARY.ttlHours)
    }

    @Test
    fun `TONE_REWRITE has 1h TTL`() {
        assertEquals(1, AiFeatureType.TONE_REWRITE.ttlHours)
    }

    @Test
    fun `TRANSLATE has 72h TTL`() {
        assertEquals(72, AiFeatureType.TRANSLATE.ttlHours)
    }

    @Test
    fun `IMAGE_EDIT has 168h TTL`() {
        assertEquals(168, AiFeatureType.IMAGE_EDIT.ttlHours)
    }

    @Test
    fun `EMOJI_REMIX has 720h TTL`() {
        assertEquals(720, AiFeatureType.EMOJI_REMIX.ttlHours)
    }

    @Test
    fun `TRANSCRIBE has 720h TTL`() {
        assertEquals(720, AiFeatureType.TRANSCRIBE.ttlHours)
    }

    @Test
    fun `exactly 7 feature types exist`() {
        assertEquals(7, AiFeatureType.entries.size)
    }
}
