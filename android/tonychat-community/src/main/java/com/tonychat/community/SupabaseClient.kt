package com.tonychat.community

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit

/**
 * Simplified Supabase REST API client using OkHttp + Gson.
 * Avoids adding Supabase SDK and Ktor dependencies.
 */
object SupabaseClient {
    private val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()

    private val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    /**
     * GET request to Supabase REST API
     */
    suspend fun get(path: String, queryParams: Map<String, String> = emptyMap()): Request {
        val url = buildUrl(path, queryParams)
        val jwt = com.tonychat.community.auth.SupabaseAuthManager.getToken()
            ?: BuildConfig.SUPABASE_ANON_KEY

        return Request.Builder()
            .url(url)
            .addHeader("apikey", BuildConfig.SUPABASE_ANON_KEY)
            .addHeader("Authorization", "Bearer $jwt")
            .get()
            .build()
    }

    /**
     * POST request to Supabase REST API
     */
    suspend fun post(path: String, jsonBody: String, deviceId: String? = null): Request {
        val url = "${BuildConfig.SUPABASE_URL}$path"
        val jwt = com.tonychat.community.auth.SupabaseAuthManager.getToken()
            ?: BuildConfig.SUPABASE_ANON_KEY

        val builder = Request.Builder()
            .url(url)
            .addHeader("apikey", BuildConfig.SUPABASE_ANON_KEY)
            .addHeader("Authorization", "Bearer $jwt")
            .addHeader("Content-Type", "application/json")
            .addHeader("Prefer", "return=representation")

        // Keep device ID header for backward compatibility during migration
        if (deviceId != null) {
            builder.addHeader("x-device-id", deviceId)
        }

        return builder
            .post(jsonBody.toRequestBody(JSON_MEDIA_TYPE))
            .build()
    }

    /**
     * DELETE request to Supabase REST API
     */
    suspend fun delete(path: String, queryParams: Map<String, String> = emptyMap(), deviceId: String? = null): Request {
        val url = buildUrl(path, queryParams)
        val jwt = com.tonychat.community.auth.SupabaseAuthManager.getToken()
            ?: BuildConfig.SUPABASE_ANON_KEY

        val builder = Request.Builder()
            .url(url)
            .addHeader("apikey", BuildConfig.SUPABASE_ANON_KEY)
            .addHeader("Authorization", "Bearer $jwt")

        // Keep device ID header for backward compatibility during migration
        if (deviceId != null) {
            builder.addHeader("x-device-id", deviceId)
        }

        return builder.delete().build()
    }

    /**
     * Execute request and return response body as string (wrapped in Result type)
     */
    fun execute(request: Request): SupabaseResult<String> {
        return try {
            httpClient.newCall(request).execute().use { response ->
                val body = response.body?.string()
                if (response.isSuccessful) {
                    SupabaseResult.Success(body ?: "")
                } else {
                    SupabaseResult.Error(
                        code = response.code,
                        message = response.message,
                        body = body
                    )
                }
            }
        } catch (e: Exception) {
            SupabaseResult.NetworkError(e)
        }
    }

    /**
     * Get OkHttpClient instance for custom requests (e.g., Storage upload)
     */
    fun getClient(): OkHttpClient = httpClient

    fun getAnonKey(): String = BuildConfig.SUPABASE_ANON_KEY
    fun getStorageUrl(): String = "${BuildConfig.SUPABASE_URL}/storage/v1"

    private fun buildUrl(path: String, queryParams: Map<String, String>): String {
        val base = "${BuildConfig.SUPABASE_URL}$path"
        if (queryParams.isEmpty()) return base

        val query = queryParams.entries.joinToString("&") { (key, value) ->
            "$key=${java.net.URLEncoder.encode(value, "UTF-8")}"
        }
        return "$base?$query"
    }
}
