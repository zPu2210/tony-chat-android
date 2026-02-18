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
    private const val SUPABASE_URL = "https://omuajrrvkhzeruupwjot.supabase.co"
    private const val ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im9tdWFqcnJ2a2h6ZXJ1dXB3am90Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzEyMzM0NDUsImV4cCI6MjA4NjgwOTQ0NX0.6v5fA9uxYxQ89AskrJQoYb5BwOgttt5x9z9VQFaIe3g"

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
    fun get(path: String, queryParams: Map<String, String> = emptyMap()): Request {
        val url = buildUrl(path, queryParams)
        return Request.Builder()
            .url(url)
            .addHeader("apikey", ANON_KEY)
            .addHeader("Authorization", "Bearer $ANON_KEY")
            .get()
            .build()
    }

    /**
     * POST request to Supabase REST API
     */
    fun post(path: String, jsonBody: String, deviceId: String? = null): Request {
        val url = "$SUPABASE_URL$path"
        val builder = Request.Builder()
            .url(url)
            .addHeader("apikey", ANON_KEY)
            .addHeader("Authorization", "Bearer $ANON_KEY")
            .addHeader("Content-Type", "application/json")
            .addHeader("Prefer", "return=representation")

        // Add device ID header for RLS policies
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
    fun delete(path: String, queryParams: Map<String, String> = emptyMap(), deviceId: String? = null): Request {
        val url = buildUrl(path, queryParams)
        val builder = Request.Builder()
            .url(url)
            .addHeader("apikey", ANON_KEY)
            .addHeader("Authorization", "Bearer $ANON_KEY")

        if (deviceId != null) {
            builder.addHeader("x-device-id", deviceId)
        }

        return builder.delete().build()
    }

    /**
     * Execute request and return response body as string
     */
    fun execute(request: Request): String? {
        return httpClient.newCall(request).execute().use { response ->
            if (response.isSuccessful) {
                response.body?.string()
            } else {
                null
            }
        }
    }

    /**
     * Get OkHttpClient instance for custom requests (e.g., Storage upload)
     */
    fun getClient(): OkHttpClient = httpClient

    fun getAnonKey(): String = ANON_KEY
    fun getStorageUrl(): String = "$SUPABASE_URL/storage/v1"

    private fun buildUrl(path: String, queryParams: Map<String, String>): String {
        val base = "$SUPABASE_URL$path"
        if (queryParams.isEmpty()) return base

        val query = queryParams.entries.joinToString("&") { (key, value) ->
            "$key=${java.net.URLEncoder.encode(value, "UTF-8")}"
        }
        return "$base?$query"
    }
}
