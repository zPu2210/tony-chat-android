package com.tonychat.ai.provider

import com.tonychat.ai.AiMessage
import com.tonychat.ai.AiResponse
import com.tonychat.ai.ToneStyle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class NoOpProviderTest {

    private val provider = NoOpProvider()

    @Test
    fun `name is none`() {
        assertEquals("none", provider.name)
    }

    @Test
    fun `isOnDevice is false`() {
        assertFalse(provider.isOnDevice)
    }

    @Test
    fun `isAvailable is false`() {
        assertFalse(provider.isAvailable)
    }

    @Test
    fun `generateReply returns Unavailable`() = runTest {
        val result = provider.generateReply(listOf(AiMessage("hi", false)))
        assertTrue(result is AiResponse.Unavailable)
    }

    @Test
    fun `summarize returns Unavailable`() = runTest {
        val result = provider.summarize(listOf(AiMessage("test", false)))
        assertTrue(result is AiResponse.Unavailable)
    }

    @Test
    fun `rewriteTone returns Unavailable`() = runTest {
        val result = provider.rewriteTone("hello", ToneStyle.FORMAL)
        assertTrue(result is AiResponse.Unavailable)
    }

    @Test
    fun `translate returns Unavailable`() = runTest {
        val result = provider.translate("hello", "en", "ko")
        assertTrue(result is AiResponse.Unavailable)
    }
}
