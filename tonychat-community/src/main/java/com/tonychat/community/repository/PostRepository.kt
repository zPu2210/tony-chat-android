package com.tonychat.community.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tonychat.community.SupabaseClient
import com.tonychat.community.model.CreatePostRequest
import com.tonychat.community.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for post CRUD operations via Supabase REST API
 */
class PostRepository {
    private val gson = Gson()

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
            val response = SupabaseClient.execute(request)

            if (response != null) {
                val type = object : TypeToken<List<Post>>() {}.type
                gson.fromJson<List<Post>>(response, type) ?: emptyList()
            } else {
                emptyList()
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
            val response = SupabaseClient.execute(httpRequest)

            if (response != null) {
                // Response is array with single element
                val type = object : TypeToken<List<Post>>() {}.type
                val posts = gson.fromJson<List<Post>>(response, type)
                posts?.firstOrNull()
            } else {
                null
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
            val response = SupabaseClient.execute(request)
            response != null
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
