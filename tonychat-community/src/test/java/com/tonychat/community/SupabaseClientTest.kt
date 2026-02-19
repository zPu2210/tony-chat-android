package com.tonychat.community

import com.tonychat.community.auth.SupabaseAuthManager
import io.mockk.*
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SupabaseClientTest {

    private lateinit var mockServer: MockWebServer

    @Before
    fun setup() {
        mockServer = MockWebServer()
        mockServer.start()

        // Mock auth manager
        mockkObject(SupabaseAuthManager)
        coEvery { SupabaseAuthManager.getToken() } returns null
    }

    @After
    fun teardown() {
        mockServer.shutdown()
        unmockkAll()
    }

    @Test
    fun `get request builds correct URL`() = runBlocking {
        val request = SupabaseClient.get("/rest/v1/posts")

        assertTrue(request.url.toString().contains("/rest/v1/posts"))
        assertEquals("GET", request.method)
    }

    @Test
    fun `get request includes API key headers`() = runBlocking {
        val request = SupabaseClient.get("/rest/v1/posts")

        assertNotNull(request.header("apikey"))
        assertNotNull(request.header("Authorization"))
        assertTrue(request.header("Authorization")!!.startsWith("Bearer "))
    }

    @Test
    fun `get request appends query parameters`() = runBlocking {
        val params = mapOf("id" to "eq.123", "limit" to "10")
        val request = SupabaseClient.get("/rest/v1/posts", params)

        val url = request.url.toString()
        assertTrue(url.contains("id=eq.123"))
        assertTrue(url.contains("limit=10"))
    }

    @Test
    fun `post request includes device ID header`() = runBlocking {
        val deviceId = "test-device-456"
        val request = SupabaseClient.post("/rest/v1/posts", "{}", deviceId)

        assertEquals(deviceId, request.header("x-device-id"))
    }

    @Test
    fun `post request includes Prefer header for representation`() = runBlocking {
        val request = SupabaseClient.post("/rest/v1/posts", "{}", "device-id")

        assertEquals("return=representation", request.header("Prefer"))
    }

    @Test
    fun `post request has correct content type`() = runBlocking {
        val request = SupabaseClient.post("/rest/v1/posts", "{}", "device-id")

        assertEquals("application/json", request.header("Content-Type"))
        assertEquals("POST", request.method)
    }

    @Test
    fun `delete request includes query parameters`() = runBlocking {
        val params = mapOf("id" to "eq.post-123")
        val request = SupabaseClient.delete("/rest/v1/posts", params, "device-id")

        val url = request.url.toString()
        assertTrue(url.contains("id=eq.post-123"))
        assertEquals("DELETE", request.method)
    }

    @Test
    fun `delete request includes device ID header when provided`() = runBlocking {
        val deviceId = "test-device-789"
        val request = SupabaseClient.delete("/rest/v1/posts", emptyMap(), deviceId)

        assertEquals(deviceId, request.header("x-device-id"))
    }

    @Test
    fun `execute returns Success on 200 response`() {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""{"success": true}""")
        mockServer.enqueue(mockResponse)

        val request = okhttp3.Request.Builder()
            .url(mockServer.url("/test"))
            .get()
            .build()

        val result = SupabaseClient.execute(request)

        assertTrue(result is SupabaseResult.Success)
        assertEquals("""{"success": true}""", (result as SupabaseResult.Success).data)
    }

    @Test
    fun `execute returns Success on 201 created`() {
        val mockResponse = MockResponse()
            .setResponseCode(201)
            .setBody("""[{"id": "new-post"}]""")
        mockServer.enqueue(mockResponse)

        val request = okhttp3.Request.Builder()
            .url(mockServer.url("/test"))
            .post(okhttp3.RequestBody.create(null, "{}"))
            .build()

        val result = SupabaseClient.execute(request)

        assertTrue(result is SupabaseResult.Success)
    }

    @Test
    fun `execute returns Error on 400 bad request`() {
        val mockResponse = MockResponse()
            .setResponseCode(400)
            .setBody("""{"error": "Invalid request"}""")
        mockServer.enqueue(mockResponse)

        val request = okhttp3.Request.Builder()
            .url(mockServer.url("/test"))
            .get()
            .build()

        val result = SupabaseClient.execute(request)

        assertTrue(result is SupabaseResult.Error)
        val error = result as SupabaseResult.Error
        assertEquals(400, error.code)
        assertNotNull(error.body)
    }

    @Test
    fun `execute returns Error on 401 unauthorized`() {
        val mockResponse = MockResponse()
            .setResponseCode(401)
            .setBody("""{"error": "Unauthorized"}""")
        mockServer.enqueue(mockResponse)

        val request = okhttp3.Request.Builder()
            .url(mockServer.url("/test"))
            .get()
            .build()

        val result = SupabaseClient.execute(request)

        assertTrue(result is SupabaseResult.Error)
        assertEquals(401, (result as SupabaseResult.Error).code)
    }

    @Test
    fun `execute returns Error on 403 forbidden`() {
        val mockResponse = MockResponse()
            .setResponseCode(403)
            .setBody("""{"error": "Forbidden"}""")
        mockServer.enqueue(mockResponse)

        val request = okhttp3.Request.Builder()
            .url(mockServer.url("/test"))
            .get()
            .build()

        val result = SupabaseClient.execute(request)

        assertTrue(result is SupabaseResult.Error)
        assertEquals(403, (result as SupabaseResult.Error).code)
    }

    @Test
    fun `execute returns Error on 500 server error`() {
        val mockResponse = MockResponse()
            .setResponseCode(500)
            .setBody("""{"error": "Internal server error"}""")
        mockServer.enqueue(mockResponse)

        val request = okhttp3.Request.Builder()
            .url(mockServer.url("/test"))
            .get()
            .build()

        val result = SupabaseClient.execute(request)

        assertTrue(result is SupabaseResult.Error)
        assertEquals(500, (result as SupabaseResult.Error).code)
    }

    @Test
    fun `execute returns NetworkError on connection failure`() {
        // Don't enqueue any response, causing connection failure
        val request = okhttp3.Request.Builder()
            .url("http://invalid-host-that-does-not-exist-12345.com")
            .get()
            .build()

        val result = SupabaseClient.execute(request)

        assertTrue(result is SupabaseResult.NetworkError)
        assertNotNull((result as SupabaseResult.NetworkError).exception)
    }

    @Test
    fun `execute handles empty response body on success`() {
        val mockResponse = MockResponse()
            .setResponseCode(204)
            .setBody("")
        mockServer.enqueue(mockResponse)

        val request = okhttp3.Request.Builder()
            .url(mockServer.url("/test"))
            .delete()
            .build()

        val result = SupabaseClient.execute(request)

        assertTrue(result is SupabaseResult.Success)
        assertEquals("", (result as SupabaseResult.Success).data)
    }

    @Test
    fun `getClient returns OkHttpClient instance`() {
        val client = SupabaseClient.getClient()

        assertNotNull(client)
        assertTrue(client.connectTimeoutMillis > 0)
    }

    @Test
    fun `getAnonKey returns configured anon key`() {
        val anonKey = SupabaseClient.getAnonKey()

        assertNotNull(anonKey)
        assertFalse(anonKey.isEmpty())
    }

    @Test
    fun `getStorageUrl returns correct storage URL`() {
        val storageUrl = SupabaseClient.getStorageUrl()

        assertTrue(storageUrl.contains("/storage/v1"))
        assertTrue(storageUrl.startsWith("http"))
    }

    @Test
    fun `post request uses JWT token when available`() = runBlocking {
        val mockJwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.test.signature"
        coEvery { SupabaseAuthManager.getToken() } returns mockJwt

        val request = SupabaseClient.post("/rest/v1/posts", "{}", "device-id")

        assertEquals("Bearer $mockJwt", request.header("Authorization"))
    }

    @Test
    fun `get request uses anon key when no JWT available`() = runBlocking {
        coEvery { SupabaseAuthManager.getToken() } returns null

        val request = SupabaseClient.get("/rest/v1/posts")

        val authHeader = request.header("Authorization")
        assertTrue(authHeader!!.startsWith("Bearer "))
        assertTrue(authHeader.contains("eyJ")) // JWT format
    }

    @Test
    fun `buildUrl encodes special characters in query params`() = runBlocking {
        val params = mapOf("name" to "Test & Co.", "city" to "San Francisco")
        val request = SupabaseClient.get("/rest/v1/posts", params)

        val url = request.url.toString()
        assertTrue(url.contains("Test+%26+Co.") || url.contains("Test%20%26%20Co."))
        assertTrue(url.contains("San+Francisco") || url.contains("San%20Francisco"))
    }
}
