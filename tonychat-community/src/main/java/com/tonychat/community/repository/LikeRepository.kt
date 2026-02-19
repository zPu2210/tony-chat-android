package com.tonychat.community.repository

import com.google.gson.Gson
import com.tonychat.community.SupabaseClient
import com.tonychat.community.SupabaseResult
import com.tonychat.community.model.CreateLikeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for like/unlike operations via Supabase REST API
 */
class LikeRepository {
    private val gson = Gson()

    /**
     * Like a post
     * @param postId Post UUID
     * @param deviceId Device ID
     * @return true if liked successfully
     */
    suspend fun likePost(postId: String, deviceId: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val request = CreateLikeRequest(postId = postId, deviceId = deviceId)
            val jsonBody = gson.toJson(request)
            val httpRequest = SupabaseClient.post("/rest/v1/likes", jsonBody, deviceId)
            when (val result = SupabaseClient.execute(httpRequest)) {
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

    /**
     * Unlike a post
     * @param postId Post UUID
     * @param deviceId Device ID
     * @return true if unliked successfully
     */
    suspend fun unlikePost(postId: String, deviceId: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val request = SupabaseClient.delete(
                path = "/rest/v1/likes",
                queryParams = mapOf(
                    "post_id" to "eq.$postId",
                    "device_id" to "eq.$deviceId"
                ),
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
