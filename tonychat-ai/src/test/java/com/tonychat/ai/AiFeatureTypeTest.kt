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
    fun `exactly 4 feature types exist`() {
        assertEquals(4, AiFeatureType.entries.size)
    }
}
