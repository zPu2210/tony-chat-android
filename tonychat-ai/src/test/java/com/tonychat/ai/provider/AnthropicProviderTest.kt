package com.tonychat.ai.provider

import com.tonychat.ai.*
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AnthropicProviderTest {
    private lateinit var mockServer: MockWebServer
    private lateinit var provider: AnthropicProvider
    private val testApiKey = "sk-ant-test-key-1234567890"

    @Before
    fun setup() {
        mockServer = MockWebServer()
        mockServer.start()
        provider = AnthropicProvider(testApiKey, mockServer.url("/").toString().removeSuffix("/"))
    }

    @After
    fun teardown() {
        mockServer.shutdown()
        unmockkAll()
    }

    @Test
    fun `generateReply returns success with valid response`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""
                {
                  "content": [{
                    "text": "[\"Great idea!\", \"I agree\", \"Let's proceed\"]"
                  }]
                }
            """.trimIndent())
        mockServer.enqueue(mockResponse)

        val context = listOf(
            AiMessage("What do you think?", isOutgoing = true)
        )

        val result = provider.generateReply(context, 3)

        assertTrue(result is AiResponse.Success)
        val data = (result as AiResponse.Success).data
        assertEquals(3, data.size)
        assertEquals("Great idea!", data[0])
    }

    @Test
    fun `generateReply handles non-JSON response with fallback`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""
                {
                  "content": [{
                    "text": "First reply\nSecond reply\nThird reply\nFourth reply"
                  }]
                }
            """.trimIndent())
        mockServer.enqueue(mockResponse)

        val context = listOf(AiMessage("Test", isOutgoing = true))
        val result = provider.generateReply(context, 3)

        assertTrue(result is AiResponse.Success)
        val data = (result as AiResponse.Success).data
        assertEquals(3, data.size)
    }

    @Test
    fun `summarize returns success with bullet points`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""
                {
                  "content": [{
                    "text": "• Main topic discussed\n• Key decision made\n• Action items assigned"
                  }]
                }
            """.trimIndent())
        mockServer.enqueue(mockResponse)

        val messages = listOf(
            AiMessage("Let's discuss the project", isOutgoing = true),
            AiMessage("Sure, what aspects?", isOutgoing = false),
            AiMessage("Timeline and budget", isOutgoing = true)
        )
        val result = provider.summarize(messages, 150)

        assertTrue(result is AiResponse.Success)
        val summary = (result as AiResponse.Success).data
        assertTrue(summary.contains("topic"))
    }

    @Test
    fun `rewriteTone returns success with casual tone`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""
                {
                  "content": [{
                    "text": "Hey, can you help me out with this?"
                  }]
                }
            """.trimIndent())
        mockServer.enqueue(mockResponse)

        val result = provider.rewriteTone("Please assist me with this matter", ToneStyle.CASUAL)

        assertTrue(result is AiResponse.Success)
        val rewritten = (result as AiResponse.Success).data
        assertTrue(rewritten.contains("help"))
    }

    @Test
    fun `translate returns success with correct translation`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""
                {
                  "content": [{
                    "text": "Hola"
                  }]
                }
            """.trimIndent())
        mockServer.enqueue(mockResponse)

        val result = provider.translate("Hello", "English", "Spanish")

        assertTrue(result is AiResponse.Success)
        assertEquals("Hola", (result as AiResponse.Success).data)
    }

    @Test
    fun `API error returns error response with code`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(401)
            .setBody("""{"error": {"message": "Invalid API key"}}""")
        mockServer.enqueue(mockResponse)

        val result = provider.summarize(listOf(AiMessage("Test", true)), 100)

        assertTrue(result is AiResponse.Error)
        val error = result as AiResponse.Error
        assertEquals(401, error.code)
    }

    @Test
    fun `rate limit error returns 429 code`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(429)
            .setBody("""{"error": {"message": "Rate limit exceeded"}}""")
        mockServer.enqueue(mockResponse)

        val result = provider.generateReply(listOf(AiMessage("Test", true)), 3)

        assertTrue(result is AiResponse.Error)
        assertEquals(429, (result as AiResponse.Error).code)
    }

    @Test
    fun `empty response body returns error`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("")
        mockServer.enqueue(mockResponse)

        val result = provider.rewriteTone("Test", ToneStyle.FORMAL)

        assertTrue(result is AiResponse.Error)
    }

    @Test
    fun `malformed JSON returns error`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("{invalid json}")
        mockServer.enqueue(mockResponse)

        val result = provider.translate("Hello", "en", "fr")

        assertTrue(result is AiResponse.Error)
    }

    @Test
    fun `provider is available when API key is set`() {
        assertTrue(provider.isAvailable)
        assertEquals("anthropic", provider.name)
        assertFalse(provider.isOnDevice)
    }

    @Test
    fun `provider is unavailable when API key is blank`() {
        val emptyProvider = AnthropicProvider("", mockServer.url("/").toString().removeSuffix("/"))
        assertFalse(emptyProvider.isAvailable)
    }

    @Test
    fun `buildConversation preserves message order and roles`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""{"content": [{"text": "Summary"}]}""")
        mockServer.enqueue(mockResponse)

        val messages = listOf(
            AiMessage("User message 1", isOutgoing = true),
            AiMessage("Assistant response", isOutgoing = false),
            AiMessage("User message 2", isOutgoing = true)
        )

        provider.summarize(messages, 100)

        val request = mockServer.takeRequest()
        val body = request.body.readUtf8()
        assertTrue(body.contains("\"role\":\"user\""))
        assertTrue(body.contains("\"role\":\"assistant\""))
    }
}
