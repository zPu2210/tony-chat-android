package com.tonychat.community.repository

import com.google.gson.Gson
import com.tonychat.community.SupabaseClient
import com.tonychat.community.model.CreateReportRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for reporting posts via Supabase REST API
 */
class ReportRepository {
    private val gson = Gson()

    /**
     * Report a post
     * @param postId Post UUID
     * @param deviceId Device ID
     * @param reason Report reason
     * @return true if reported successfully
     */
    suspend fun reportPost(postId: String, deviceId: String, reason: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val request = CreateReportRequest(postId = postId, deviceId = deviceId, reason = reason)
            val jsonBody = gson.toJson(request)
            val httpRequest = SupabaseClient.post("/rest/v1/reports", jsonBody, deviceId)
            val response = SupabaseClient.execute(httpRequest)
            response != null
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
