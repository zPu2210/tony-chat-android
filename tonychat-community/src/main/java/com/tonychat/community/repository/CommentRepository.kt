package com.tonychat.community.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tonychat.community.SupabaseClient
import com.tonychat.community.SupabaseResult
import com.tonychat.community.model.Comment
import com.tonychat.community.model.CreateCommentRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for comment operations via Supabase REST API
 */
class CommentRepository {
    private val gson = Gson()

    companion object {
        private const val TAG = "CommentRepository"
        private const val MAX_CONTENT_LENGTH = 2000

        /**
         * Validate and sanitize comment content
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
            when (val result = SupabaseClient.execute(request)) {
                is SupabaseResult.Success -> {
                    val type = object : TypeToken<List<Comment>>() {}.type
                    gson.fromJson<List<Comment>>(result.data, type) ?: emptyList()
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
     * Create a new comment
     * @param request Comment creation data
     * @return Created comment or null on error
     */
    suspend fun createComment(request: CreateCommentRequest): Comment? = withContext(Dispatchers.IO) {
        try {
            // Validate content before submission
            val sanitized = try {
                validateContent(request.content)
            } catch (e: IllegalArgumentException) {
                Log.w(TAG, "Content validation failed: ${e.message}")
                return@withContext null
            }

            val jsonBody = gson.toJson(request.copy(content = sanitized))
            val httpRequest = SupabaseClient.post("/rest/v1/comments", jsonBody, request.deviceId)
            when (val result = SupabaseClient.execute(httpRequest)) {
                is SupabaseResult.Success -> {
                    val type = object : TypeToken<List<Comment>>() {}.type
                    val comments = gson.fromJson<List<Comment>>(result.data, type)
                    comments?.firstOrNull()
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
}
