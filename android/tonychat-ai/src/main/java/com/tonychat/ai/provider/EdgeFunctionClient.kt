package com.tonychat.ai.provider

import com.tonychat.ai.AiResponse
import com.tonychat.ai.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.util.concurrent.TimeUnit

/**
 * HTTP client for Supabase Edge Function text endpoints:
 * - POST /ai-rewrite  → {text, style} → {result, remaining}
 * - POST /ai-translate → {text, targetLang, sourceLang?} → {result, detectedLang?, remaining}
 */
object EdgeFunctionClient {
    private const val BASE_URL = BuildConfig.SUPABASE_URL + "/functions/v1"
    private val JSON_TYPE = "application/json; charset=utf-8".toMediaType()

    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    suspend fun rewrite(
        text: String, style: String, deviceId: String
    ): AiResponse<String> = withContext(Dispatchers.IO) {
        try {
            val json = JSONObject().apply {
                put("text", text)
                put("style", style)
            }
            val request = Request.Builder()
                .url("$BASE_URL/ai-rewrite")
                .header("X-Device-Id", deviceId)
                .header("Authorization", "Bearer ${BuildConfig.SUPABASE_ANON_KEY}")
                .post(json.toString().toRequestBody(JSON_TYPE))
                .build()

            val response = client.newCall(request).execute()
            response.use { resp ->
                when (resp.code) {
                    200 -> {
                        val body = JSONObject(resp.body?.string() ?: "{}")
                        val result = body.optString("result", "")
                        if (result.isNotBlank()) {
                            AiResponse.Success(result, provider = "edge-function")
                        } else {
                            AiResponse.Error("Empty response from server")
                        }
                    }
                    429 -> AiResponse.RateLimited()
                    else -> {
                        val errorBody = resp.body?.string() ?: ""
                        AiResponse.Error("Server error (${resp.code}): $errorBody")
                    }
                }
            }
        } catch (e: Exception) {
            AiResponse.Error(e.message ?: "Network error")
        }
    }

    suspend fun translate(
        text: String, targetLang: String, sourceLang: String?, deviceId: String
    ): AiResponse<String> = withContext(Dispatchers.IO) {
        try {
            val json = JSONObject().apply {
                put("text", text)
                put("targetLang", targetLang)
                if (!sourceLang.isNullOrBlank()) put("sourceLang", sourceLang)
            }
            val request = Request.Builder()
                .url("$BASE_URL/ai-translate")
                .header("X-Device-Id", deviceId)
                .header("Authorization", "Bearer ${BuildConfig.SUPABASE_ANON_KEY}")
                .post(json.toString().toRequestBody(JSON_TYPE))
                .build()

            val response = client.newCall(request).execute()
            response.use { resp ->
                when (resp.code) {
                    200 -> {
                        val body = JSONObject(resp.body?.string() ?: "{}")
                        val result = body.optString("result", "")
                        if (result.isNotBlank()) {
                            AiResponse.Success(result, provider = "edge-function")
                        } else {
                            AiResponse.Error("Empty response from server")
                        }
                    }
                    429 -> AiResponse.RateLimited()
                    else -> {
                        val errorBody = resp.body?.string() ?: ""
                        AiResponse.Error("Server error (${resp.code}): $errorBody")
                    }
                }
            }
        } catch (e: Exception) {
            AiResponse.Error(e.message ?: "Network error")
        }
    }
}
