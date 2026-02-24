package com.tonychat.community.repository

import android.util.Log
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
        private const val TAG = "PostRepository"
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
                    Log.w(TAG, "Supabase error ${result.code}: ${result.message}")
                    emptyList()
                }
                is SupabaseResult.NetworkError -> {
                    Log.w(TAG, "Network error", result.exception)
                    emptyList()
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "Operation failed", e)
            emptyList()
        }
    }

    /**
     * Get all posts (global feed, no location required), chronological
     * @param pageOffset Pagination offset
     * @param callerDeviceId Current device ID for like status
     * @return List of posts with counts
     */
    suspend fun getAllPosts(
        pageOffset: Int = 0,
        callerDeviceId: String
    ): List<Post> = withContext(Dispatchers.IO) {
        try {
            val rpcBody = gson.toJson(mapOf(
                "page_offset" to pageOffset,
                "page_size" to 20,
                "caller_device_id" to callerDeviceId
            ))
            val request = SupabaseClient.post("/rest/v1/rpc/all_posts", rpcBody, callerDeviceId)
            when (val result = SupabaseClient.execute(request)) {
                is SupabaseResult.Success -> {
                    val type = object : TypeToken<List<Post>>() {}.type
                    gson.fromJson<List<Post>>(result.data, type) ?: emptyList()
                }
                is SupabaseResult.Error -> {
                    Log.w(TAG, "Supabase error ${result.code}: ${result.message}")
                    emptyList()
                }
                is SupabaseResult.NetworkError -> {
                    Log.w(TAG, "Network error", result.exception)
                    emptyList()
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "getAllPosts failed", e)
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
            // Validate content before submission
            val sanitized = try {
                validateContent(request.content)
            } catch (e: IllegalArgumentException) {
                Log.w(TAG, "Content validation failed: ${e.message}")
                return@withContext null
            }

            val jsonBody = gson.toJson(request.copy(content = sanitized))
            val httpRequest = SupabaseClient.post("/rest/v1/posts", jsonBody, request.deviceId)
            when (val result = SupabaseClient.execute(httpRequest)) {
                is SupabaseResult.Success -> {
                    val type = object : TypeToken<List<Post>>() {}.type
                    val posts = gson.fromJson<List<Post>>(result.data, type)
                    posts?.firstOrNull()
                }
                is SupabaseResult.Error -> {
                    Log.w(TAG, "Supabase error ${result.code}: ${result.message}")
                    null
                }
                is SupabaseResult.NetworkError -> {
                    Log.w(TAG, "Network error", result.exception)
                    null
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "Operation failed", e)
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
                    Log.w(TAG, "Supabase error ${result.code}: ${result.message}")
                    false
                }
                is SupabaseResult.NetworkError -> {
                    Log.w(TAG, "Network error", result.exception)
                    false
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "Operation failed", e)
            false
        }
    }
}
