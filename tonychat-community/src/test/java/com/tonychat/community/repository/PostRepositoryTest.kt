package com.tonychat.community.repository

import com.tonychat.community.SupabaseClient
import com.tonychat.community.SupabaseResult
import com.tonychat.community.model.CreatePostRequest
import io.mockk.*
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class PostRepositoryTest {

    private lateinit var repository: PostRepository
    private val testDeviceId = "test-device-123"

    @Before
    fun setup() {
        repository = PostRepository()
        mockkObject(SupabaseClient)
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun `getNearbyPosts returns list on success`() = runBlocking {
        val mockRequest = mockk<Request>()
        val jsonResponse = """
            [
                {
                    "id": "post-1",
                    "content": "Test post",
                    "device_id": "device-1",
                    "lat": 37.7749,
                    "lng": -122.4194,
                    "created_at": "2024-01-01T00:00:00Z",
                    "image_url": null,
                    "like_count": 5,
                    "comment_count": 2,
                    "is_liked": false
                }
            ]
        """.trimIndent()

        coEvery { SupabaseClient.post(any(), any(), any()) } returns mockRequest
        every { SupabaseClient.execute(mockRequest) } returns SupabaseResult.Success(jsonResponse)

        val posts = repository.getNearbyPosts(37.7749, -122.4194, 5000, 0, testDeviceId)

        assertEquals(1, posts.size)
        assertEquals("post-1", posts[0].id)
        assertEquals("Test post", posts[0].content)
        assertEquals(5, posts[0].likeCount)
        assertEquals(2, posts[0].commentCount)
    }

    @Test
    fun `getNearbyPosts returns empty list on API error`() = runBlocking {
        val mockRequest = mockk<Request>()
        coEvery { SupabaseClient.post(any(), any(), any()) } returns mockRequest
        every { SupabaseClient.execute(mockRequest) } returns SupabaseResult.Error(500, "Internal error", null)

        val posts = repository.getNearbyPosts(37.7749, -122.4194, 5000, 0, testDeviceId)

        assertTrue(posts.isEmpty())
    }

    @Test
    fun `getNearbyPosts returns empty list on network error`() = runBlocking {
        val mockRequest = mockk<Request>()
        coEvery { SupabaseClient.post(any(), any(), any()) } returns mockRequest
        every { SupabaseClient.execute(mockRequest) } returns SupabaseResult.NetworkError(Exception("Network timeout"))

        val posts = repository.getNearbyPosts(37.7749, -122.4194, 5000, 0, testDeviceId)

        assertTrue(posts.isEmpty())
    }

    @Test
    fun `getNearbyPosts uses correct RPC endpoint`() = runBlocking {
        val mockRequest = mockk<Request>()
        coEvery { SupabaseClient.post(any(), any(), any()) } returns mockRequest
        every { SupabaseClient.execute(mockRequest) } returns SupabaseResult.Success("[]")

        repository.getNearbyPosts(37.7749, -122.4194, 5000, 0, testDeviceId)

        coVerify { SupabaseClient.post(eq("/rest/v1/rpc/nearby_posts"), any(), eq(testDeviceId)) }
    }

    @Test
    fun `createPost returns post on success`() = runBlocking {
        val mockRequest = mockk<Request>()
        val request = CreatePostRequest(
            content = "My new post",
            deviceId = testDeviceId,
            location = com.tonychat.community.model.LocationPoint(
                coordinates = listOf(-122.4194, 37.7749)
            ),
            imageUrl = null
        )
        val jsonResponse = """
            [{
                "id": "new-post-1",
                "content": "My new post",
                "device_id": "test-device-123",
                "lat": 37.7749,
                "lng": -122.4194,
                "created_at": "2024-01-01T00:00:00Z",
                "image_url": null,
                "like_count": 0,
                "comment_count": 0,
                "is_liked": false
            }]
        """.trimIndent()

        coEvery { SupabaseClient.post(any(), any(), any()) } returns mockRequest
        every { SupabaseClient.execute(mockRequest) } returns SupabaseResult.Success(jsonResponse)

        val post = repository.createPost(request)

        assertNotNull(post)
        assertEquals("new-post-1", post?.id)
        assertEquals("My new post", post?.content)
    }

    @Test
    fun `createPost returns null on API error`() = runBlocking {
        val mockRequest = mockk<Request>()
        val request = CreatePostRequest(
            content = "Test",
            deviceId = testDeviceId,
            location = com.tonychat.community.model.LocationPoint(
                coordinates = listOf(-122.4194, 37.7749)
            ),
            imageUrl = null
        )

        coEvery { SupabaseClient.post(any(), any(), any()) } returns mockRequest
        every { SupabaseClient.execute(mockRequest) } returns SupabaseResult.Error(400, "Bad request", null)

        val post = repository.createPost(request)

        assertNull(post)
    }

    @Test
    fun `createPost returns null on network error`() = runBlocking {
        val mockRequest = mockk<Request>()
        val request = CreatePostRequest(
            content = "Test",
            deviceId = testDeviceId,
            location = com.tonychat.community.model.LocationPoint(
                coordinates = listOf(-122.4194, 37.7749)
            ),
            imageUrl = null
        )

        coEvery { SupabaseClient.post(any(), any(), any()) } returns mockRequest
        every { SupabaseClient.execute(mockRequest) } returns SupabaseResult.NetworkError(Exception("Connection failed"))

        val post = repository.createPost(request)

        assertNull(post)
    }

    @Test
    fun `deletePost returns true on success`() = runBlocking {
        val mockRequest = mockk<Request>()
        val postId = "post-to-delete"

        coEvery { SupabaseClient.delete(any(), any(), any()) } returns mockRequest
        every { SupabaseClient.execute(mockRequest) } returns SupabaseResult.Success("")

        val result = repository.deletePost(postId, testDeviceId)

        assertTrue(result)
    }

    @Test
    fun `deletePost returns false on API error`() = runBlocking {
        val mockRequest = mockk<Request>()
        val postId = "post-to-delete"

        coEvery { SupabaseClient.delete(any(), any(), any()) } returns mockRequest
        every { SupabaseClient.execute(mockRequest) } returns SupabaseResult.Error(403, "Forbidden", null)

        val result = repository.deletePost(postId, testDeviceId)

        assertFalse(result)
    }

    @Test
    fun `deletePost uses correct query parameters`() = runBlocking {
        val mockRequest = mockk<Request>()
        val postId = "post-123"

        coEvery { SupabaseClient.delete(any(), any(), any()) } returns mockRequest
        every { SupabaseClient.execute(mockRequest) } returns SupabaseResult.Success("")

        repository.deletePost(postId, testDeviceId)

        coVerify {
            SupabaseClient.delete(
                eq("/rest/v1/posts"),
                eq(mapOf("id" to "eq.$postId")),
                eq(testDeviceId)
            )
        }
    }

    @Test
    fun `validateContent strips HTML tags`() {
        val input = "<script>alert('xss')</script>Hello <b>world</b>!"
        val result = PostRepository.validateContent(input)

        assertEquals("alert('xss')Hello world!", result)
        assertFalse(result.contains("<"))
        assertFalse(result.contains(">"))
    }

    @Test
    fun `validateContent enforces length limit`() {
        val longContent = "a".repeat(2001)

        val exception = assertThrows(IllegalArgumentException::class.java) {
            PostRepository.validateContent(longContent)
        }

        assertTrue(exception.message!!.contains("maximum length"))
    }

    @Test
    fun `validateContent allows content at exactly max length`() {
        val maxContent = "a".repeat(2000)

        val result = PostRepository.validateContent(maxContent)

        assertEquals(2000, result.length)
    }

    @Test
    fun `validateContent trims whitespace`() {
        val input = "  Hello world  \n\t"
        val result = PostRepository.validateContent(input)

        assertEquals("Hello world", result)
    }
}
