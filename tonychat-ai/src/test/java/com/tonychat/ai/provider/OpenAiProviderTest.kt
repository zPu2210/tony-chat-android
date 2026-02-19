package com.tonychat.ai.provider

import com.tonychat.ai.*
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.File

class OpenAiProviderTest {
    private lateinit var mockServer: MockWebServer
    private lateinit var provider: OpenAiProvider
    private val testApiKey = "sk-test-key-1234567890"

    @Before
    fun setup() {
        mockServer = MockWebServer()
        mockServer.start()
        provider = OpenAiProvider(testApiKey, mockServer.url("/").toString().removeSuffix("/"))
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
                  "choices": [{
                    "message": {
                      "content": "[\"Sure!\", \"Sounds good\", \"Let's do it\"]"
                    }
                  }]
                }
            """.trimIndent())
        mockServer.enqueue(mockResponse)

        val context = listOf(
            AiMessage("Hello, how are you?", isOutgoing = false),
            AiMessage("I'm doing great!", isOutgoing = true)
        )

        val result = provider.generateReply(context, 3)

        assertTrue(result is AiResponse.Success)
        val data = (result as AiResponse.Success).data
        assertEquals(3, data.size)
        assertEquals("Sure!", data[0])
    }

    @Test
    fun `generateReply handles malformed JSON gracefully`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""
                {
                  "choices": [{
                    "message": {
                      "content": "Reply 1\nReply 2\nReply 3"
                    }
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
    fun `summarize returns success with valid response`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""
                {
                  "choices": [{
                    "message": {
                      "content": "• User greeted\n• Assistant responded\n• Conversation ended"
                    }
                  }]
                }
            """.trimIndent())
        mockServer.enqueue(mockResponse)

        val messages = listOf(
            AiMessage("Hello!", isOutgoing = true),
            AiMessage("Hi there!", isOutgoing = false)
        )
        val result = provider.summarize(messages, 100)

        assertTrue(result is AiResponse.Success)
        val summary = (result as AiResponse.Success).data
        assertTrue(summary.contains("greeted"))
    }

    @Test
    fun `rewriteTone returns success with formal tone`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""
                {
                  "choices": [{
                    "message": {
                      "content": "I would appreciate your assistance with this matter."
                    }
                  }]
                }
            """.trimIndent())
        mockServer.enqueue(mockResponse)

        val result = provider.rewriteTone("Help me with this", ToneStyle.FORMAL)

        assertTrue(result is AiResponse.Success)
        val rewritten = (result as AiResponse.Success).data
        assertTrue(rewritten.contains("appreciate"))
    }

    @Test
    fun `translate returns success with valid translation`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""
                {
                  "choices": [{
                    "message": {
                      "content": "Bonjour"
                    }
                  }]
                }
            """.trimIndent())
        mockServer.enqueue(mockResponse)

        val result = provider.translate("Hello", "English", "French")

        assertTrue(result is AiResponse.Success)
        assertEquals("Bonjour", (result as AiResponse.Success).data)
    }

    @Test
    fun `API error returns error response`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(429)
            .setBody("""{"error": {"message": "Rate limit exceeded"}}""")
        mockServer.enqueue(mockResponse)

        val result = provider.summarize(listOf(AiMessage("Test", true)), 100)

        assertTrue(result is AiResponse.Error)
        val error = result as AiResponse.Error
        assertEquals(429, error.code)
    }

    @Test
    fun `empty response body returns error`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("")
        mockServer.enqueue(mockResponse)

        val result = provider.summarize(listOf(AiMessage("Test", true)), 100)

        assertTrue(result is AiResponse.Error)
    }

    @Test
    fun `transcribe returns success with valid audio file`() {
        runBlocking {
            val mockResponse = MockResponse()
                .setResponseCode(200)
                .setBody("""{"text": "This is a transcribed audio message"}""")
            mockServer.enqueue(mockResponse)

            val tempFile = File.createTempFile("test_audio", ".ogg")
            tempFile.writeText("fake audio data")

            val result = provider.transcribe(tempFile)

            assertTrue(result is AiResponse.Success)
            assertEquals("This is a transcribed audio message", (result as AiResponse.Success).data)

            tempFile.delete()
        }
    }

    @Test
    fun `transcribe returns error for file too large`() {
        runBlocking {
            val mockResponse = MockResponse()
                .setResponseCode(413)
                .setBody("""{"error": "File too large"}""")
            mockServer.enqueue(mockResponse)

            val tempFile = File.createTempFile("test_audio", ".ogg")
            tempFile.writeText("fake audio data")

            val result = provider.transcribe(tempFile)

            assertTrue(result is AiResponse.Error)
            assertEquals(413, (result as AiResponse.Error).code)

            tempFile.delete()
        }
    }

    @Test
    fun `provider is available when API key is set`() {
        assertTrue(provider.isAvailable)
        assertEquals("openai", provider.name)
        assertFalse(provider.isOnDevice)
    }

    @Test
    fun `provider is unavailable when API key is blank`() {
        val emptyProvider = OpenAiProvider("", mockServer.url("/").toString().removeSuffix("/"))
        assertFalse(emptyProvider.isAvailable)
    }
}
