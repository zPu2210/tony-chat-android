package com.tonychat.ai.provider

import com.tonychat.ai.ImageEditResponse
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.File

class RemoveBgProviderTest {
    private lateinit var mockServer: MockWebServer
    private lateinit var provider: RemoveBgProvider
    private lateinit var cacheDir: File
    private val testApiKey = "test-removebg-api-key"

    @Before
    fun setup() {
        mockServer = MockWebServer()
        mockServer.start()
        cacheDir = File.createTempFile("test_cache", ".tmp").apply {
            delete()
            mkdir()
        }
        provider = RemoveBgProvider(testApiKey, cacheDir, mockServer.url("/").toString().removeSuffix("/"))
    }

    @After
    fun teardown() {
        mockServer.shutdown()
        cacheDir.deleteRecursively()
        unmockkAll()
    }

    @Test
    fun `removeBackground returns success with valid image`() {
        runBlocking {
        val fakePngData = byteArrayOf(
            0x89.toByte(), 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A  // PNG header
        )
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(okio.Buffer().write(fakePngData))
        mockServer.enqueue(mockResponse)

        val inputFile = File.createTempFile("test_input", ".jpg")
        inputFile.writeText("fake image data")

        val result = provider.removeBackground(inputFile)

        assertTrue(result is ImageEditResponse.Success)
        val success = result as ImageEditResponse.Success
        assertTrue(success.resultFile.exists())
        assertTrue(success.resultFile.name.startsWith("edited_"))
        assertTrue(success.resultFile.extension == "png")

        inputFile.delete()
        }
    }

    @Test
    fun `removeBackground returns error when file not found`() {
        runBlocking {
        val nonExistentFile = File("/tmp/nonexistent_file_12345.jpg")

        val result = provider.removeBackground(nonExistentFile)

        assertTrue(result is ImageEditResponse.Error)
        val error = result as ImageEditResponse.Error
        assertTrue(error.message.contains("not found"))
        }
    }

    @Test
    fun `removeBackground returns error for insufficient credits`() {
        runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(402)
            .setBody("""{"error": "Insufficient credits"}""")
        mockServer.enqueue(mockResponse)

        val inputFile = File.createTempFile("test_input", ".jpg")
        inputFile.writeText("fake image data")

        val result = provider.removeBackground(inputFile)

        assertTrue(result is ImageEditResponse.Error)
        val error = result as ImageEditResponse.Error
        assertEquals(402, error.code)
        assertTrue(error.message.contains("Insufficient API credits"))

        inputFile.delete()
        }
    }

    @Test
    fun `removeBackground returns rate limited for 429`() {
        runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(429)
            .setBody("""{"error": "Rate limit exceeded"}""")
        mockServer.enqueue(mockResponse)

        val inputFile = File.createTempFile("test_input", ".jpg")
        inputFile.writeText("fake image data")

        val result = provider.removeBackground(inputFile)

        assertTrue(result is ImageEditResponse.RateLimited)

        inputFile.delete()
        }
    }

    @Test
    fun `removeBackground returns error for invalid API key`() {
        runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(403)
            .setBody("""{"error": "Invalid API key"}""")
        mockServer.enqueue(mockResponse)

        val inputFile = File.createTempFile("test_input", ".jpg")
        inputFile.writeText("fake image data")

        val result = provider.removeBackground(inputFile)

        assertTrue(result is ImageEditResponse.Error)
        val error = result as ImageEditResponse.Error
        assertEquals(403, error.code)
        assertTrue(error.message.contains("Invalid API key"))

        inputFile.delete()
        }
    }

    @Test
    fun `removeBackground returns error for bad request`() {
        runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(400)
            .setBody("""{"error": "Invalid image format"}""")
        mockServer.enqueue(mockResponse)

        val inputFile = File.createTempFile("test_input", ".jpg")
        inputFile.writeText("fake image data")

        val result = provider.removeBackground(inputFile)

        assertTrue(result is ImageEditResponse.Error)
        val error = result as ImageEditResponse.Error
        assertEquals(400, error.code)
        assertTrue(error.message.contains("Invalid image"))

        inputFile.delete()
        }
    }

    @Test
    fun `removeBackground returns success with empty response body`() {
        runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("")
        mockServer.enqueue(mockResponse)

        val inputFile = File.createTempFile("test_input", ".jpg")
        inputFile.writeText("fake image data")

        val result = provider.removeBackground(inputFile)

        // 200 with empty body still creates a 0-byte result file
        assertTrue(result is ImageEditResponse.Success)
        val success = result as ImageEditResponse.Success
        assertTrue(success.resultFile.exists())
        assertEquals(0L, success.resultFile.length())

        inputFile.delete()
        }
    }

    @Test
    fun `removeBackground returns error for unknown status code`() {
        runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(503)
            .setBody("""{"error": "Service unavailable"}""")
        mockServer.enqueue(mockResponse)

        val inputFile = File.createTempFile("test_input", ".jpg")
        inputFile.writeText("fake image data")

        val result = provider.removeBackground(inputFile)

        assertTrue(result is ImageEditResponse.Error)
        val error = result as ImageEditResponse.Error
        assertEquals(503, error.code)
        assertTrue(error.message.contains("API error"))

        inputFile.delete()
        }
    }

    @Test
    fun `provider is available when API key is set`() {
        assertTrue(provider.isAvailable)
        assertEquals("remove.bg", provider.name)
    }

    @Test
    fun `provider is unavailable when API key is blank`() {
        val emptyProvider = RemoveBgProvider("", cacheDir, mockServer.url("/").toString().removeSuffix("/"))
        assertFalse(emptyProvider.isAvailable)
    }

    @Test
    fun `removeBackground creates cache directory if not exists`() {
        runBlocking {
        val newCacheDir = File.createTempFile("new_cache", ".tmp").apply {
            delete()  // Directory doesn't exist yet
        }
        val newProvider = RemoveBgProvider(testApiKey, newCacheDir, mockServer.url("/").toString().removeSuffix("/"))

        val fakePngData = byteArrayOf(0x89.toByte(), 0x50, 0x4E, 0x47)
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(okio.Buffer().write(fakePngData))
        mockServer.enqueue(mockResponse)

        val inputFile = File.createTempFile("test_input", ".jpg")
        inputFile.writeText("fake image data")

        val result = newProvider.removeBackground(inputFile)

        assertTrue(result is ImageEditResponse.Success)
        assertTrue(newCacheDir.exists())
        assertTrue(newCacheDir.isDirectory)

        inputFile.delete()
        newCacheDir.deleteRecursively()
        }
    }
}
