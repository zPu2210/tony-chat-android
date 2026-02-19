package com.tonychat.community.repository

import android.util.Log
import com.google.gson.Gson
import com.tonychat.community.SupabaseClient
import com.tonychat.community.SupabaseResult
import com.tonychat.community.model.CreateReportRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository for reporting posts via Supabase REST API
 */
class ReportRepository {
    private val gson = Gson()

    companion object {
        private const val TAG = "ReportRepository"
    }

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
            when (val result = SupabaseClient.execute(httpRequest)) {
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
