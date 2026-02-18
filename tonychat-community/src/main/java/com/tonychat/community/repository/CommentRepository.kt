package com.tonychat.community.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tonychat.community.SupabaseClient
import com.tonychat.community.model.Comment
import com.tonychat.community.model.CreateCommentRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for comment operations via Supabase REST API
 */
class CommentRepository {
    private val gson = Gson()

    /**
     * Get comments for a post
     * @param postId Post UUID
     * @return List of comments sorted by creation time
     */
    suspend fun getComments(postId: String): List<Comment> = withContext(Dispatchers.IO) {
        try {
            val request = SupabaseClient.get(
                path = "/rest/v1/comments",
                queryParams = mapOf(
                    "post_id" to "eq.$postId",
                    "order" to "created_at.asc",
                    "select" to "*"
                )
            )
            val response = SupabaseClient.execute(request)

            if (response != null) {
                val type = object : TypeToken<List<Comment>>() {}.type
                gson.fromJson<List<Comment>>(response, type) ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    /**
     * Create a new comment
     * @param request Comment creation data
     * @return Created comment or null on error
     */
    suspend fun createComment(request: CreateCommentRequest): Comment? = withContext(Dispatchers.IO) {
        try {
            val jsonBody = gson.toJson(request)
            val httpRequest = SupabaseClient.post("/rest/v1/comments", jsonBody, request.deviceId)
            val response = SupabaseClient.execute(httpRequest)

            if (response != null) {
                val type = object : TypeToken<List<Comment>>() {}.type
                val comments = gson.fromJson<List<Comment>>(response, type)
                comments?.firstOrNull()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
