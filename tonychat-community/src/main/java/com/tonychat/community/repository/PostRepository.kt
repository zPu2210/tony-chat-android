package com.tonychat.community.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tonychat.community.SupabaseClient
import com.tonychat.community.SupabaseResult
import com.tonychat.community.model.CreatePostRequest
import com.tonychat.community.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for post CRUD operations via Supabase REST API
 */
class PostRepository {
    private val gson = Gson()

    companion object {
        private const val MAX_CONTENT_LENGTH = 2000

        /**
         * Validate and sanitize post content
         * - Enforce length limit
         * - Strip HTML tags to prevent XSS
         */
        fun validateContent(content: String): String {
            require(content.length <= MAX_CONTENT_LENGTH) {
                "Content exceeds maximum length of $MAX_CONTENT_LENGTH characters"
            }
            // Strip HTML tags and trim whitespace
            return content.replace(Regex("<[^>]*>"), "").trim()
        }
    }

    /**
     * Get nearby posts using Supabase RPC function
     * @param lat Latitude
     * @param lng Longitude
     * @param radiusMeters Radius in meters (default 5km)
     * @param pageOffset Pagination offset (default 0)
     * @param deviceId Current device ID for like status
     * @return List of posts with counts
     */
    suspend fun getNearbyPosts(
        lat: Double,
        lng: Double,
        radiusMeters: Int = 5000,
        pageOffset: Int = 0,
        deviceId: String
    ): List<Post> = withContext(Dispatchers.IO) {
        try {
            // Call nearby_posts RPC function
            val rpcBody = gson.toJson(mapOf(
                "lat" to lat,
                "lng" to lng,
                "radius_meters" to radiusMeters,
                "page_offset" to pageOffset,
                "page_size" to 20,
                "caller_device_id" to deviceId
            ))

            val request = SupabaseClient.post("/rest/v1/rpc/nearby_posts", rpcBody, deviceId)
            when (val result = SupabaseClient.execute(request)) {
                is SupabaseResult.Success -> {
                    val type = object : TypeToken<List<Post>>() {}.type
                    gson.fromJson<List<Post>>(result.data, type) ?: emptyList()
                }
                is SupabaseResult.Error -> {
                    println("Supabase error ${result.code}: ${result.message}")
                    emptyList()
                }
                is SupabaseResult.NetworkError -> {
                    result.exception.printStackTrace()
                    emptyList()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    /**
     * Create a new post
     * @param request Post creation data
     * @return Created post or null on error
     */
    suspend fun createPost(request: CreatePostRequest): Post? = withContext(Dispatchers.IO) {
        try {
            val jsonBody = gson.toJson(request)
            val httpRequest = SupabaseClient.post("/rest/v1/posts", jsonBody, request.deviceId)
            when (val result = SupabaseClient.execute(httpRequest)) {
                is SupabaseResult.Success -> {
                    val type = object : TypeToken<List<Post>>() {}.type
                    val posts = gson.fromJson<List<Post>>(result.data, type)
                    posts?.firstOrNull()
                }
                is SupabaseResult.Error -> {
                    println("Supabase error ${result.code}: ${result.message}")
                    null
                }
                is SupabaseResult.NetworkError -> {
                    result.exception.printStackTrace()
                    null
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Delete a post by ID
     * @param postId Post UUID
     * @param deviceId Device ID for ownership check
     * @return true if deleted successfully
     */
    suspend fun deletePost(postId: String, deviceId: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val request = SupabaseClient.delete(
                path = "/rest/v1/posts",
                queryParams = mapOf("id" to "eq.$postId"),
                deviceId = deviceId
            )
            when (val result = SupabaseClient.execute(request)) {
                is SupabaseResult.Success -> true
                is SupabaseResult.Error -> {
                    println("Supabase error ${result.code}: ${result.message}")
                    false
                }
                is SupabaseResult.NetworkError -> {
                    result.exception.printStackTrace()
                    false
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
