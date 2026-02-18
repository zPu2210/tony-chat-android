package com.tonychat.ai

import org.junit.Assert.*
import org.junit.Test

class AiResponseTest {

    @Test
    fun `Success holds data`() {
        val response = AiResponse.Success("hello")
        assertEquals("hello", response.data)
        assertFalse(response.fromCache)
        assertEquals("", response.provider)
    }

    @Test
    fun `Success with cache and provider metadata`() {
        val response = AiResponse.Success("cached", fromCache = true, provider = "openai")
        assertTrue(response.fromCache)
        assertEquals("openai", response.provider)
    }

    @Test
    fun `Success can hold list data`() {
        val response = AiResponse.Success(listOf("a", "b", "c"))
        assertEquals(3, response.data.size)
        assertEquals("b", response.data[1])
    }

    @Test
    fun `Error holds message and code`() {
        val response = AiResponse.Error<String>("rate limited", 429)
        assertEquals("rate limited", response.message)
        assertEquals(429, response.code)
    }

    @Test
    fun `Error default code is -1`() {
        val response = AiResponse.Error<String>("unknown")
        assertEquals(-1, response.code)
    }

    @Test
    fun `ConsentRequired is distinct sealed subtype`() {
        val response: AiResponse<String> = AiResponse.ConsentRequired()
        assertTrue(response is AiResponse.ConsentRequired)
        assertFalse(response is AiResponse.Success)
        assertFalse(response is AiResponse.Error)
    }

    @Test
    fun `RateLimited is distinct sealed subtype`() {
        val response: AiResponse<String> = AiResponse.RateLimited()
        assertTrue(response is AiResponse.RateLimited)
        assertFalse(response is AiResponse.Unavailable)
    }

    @Test
    fun `Unavailable is distinct sealed subtype`() {
        val response: AiResponse<String> = AiResponse.Unavailable()
        assertTrue(response is AiResponse.Unavailable)
        assertFalse(response is AiResponse.RateLimited)
    }
}
