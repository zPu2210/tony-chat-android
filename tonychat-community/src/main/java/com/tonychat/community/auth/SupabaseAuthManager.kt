package com.tonychat.community.auth

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.tonychat.community.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit

/**
 * Manages Supabase anonymous authentication with JWT token storage
 * Replaces device-ID-only auth for better security
 *
 * Note: Uses regular SharedPreferences for token storage. For production,
 * consider adding androidx.security:security-crypto for encrypted storage
 * (requires minSdk 21+).
 */
object SupabaseAuthManager {
    private const val PREFS_NAME = "supabase_auth_prefs"
    private const val KEY_ACCESS_TOKEN = "access_token"
    private const val KEY_REFRESH_TOKEN = "refresh_token"
    private const val KEY_EXPIRES_AT = "expires_at"

    private val JSON_MEDIA_TYPE = "application/json; charset=utf-8".toMediaType()

    private var prefs: SharedPreferences? = null
    private val gson = Gson()

    private val httpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    /**
     * Initialize with application context
     */
    fun init(context: Context) {
        if (prefs != null) return
        prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Get valid JWT token, refreshing if needed
     */
    suspend fun getToken(): String? {
        val token = prefs?.getString(KEY_ACCESS_TOKEN, null)
        val expiresAt = prefs?.getLong(KEY_EXPIRES_AT, 0L) ?: 0L

        // Token valid for at least 5 minutes
        if (token != null && System.currentTimeMillis() < expiresAt - 300_000) {
            return token
        }

        // Try refresh first
        val refreshToken = prefs?.getString(KEY_REFRESH_TOKEN, null)
        if (refreshToken != null) {
            val refreshed = refreshToken(refreshToken)
            if (refreshed != null) return refreshed
        }

        // Sign in anonymously
        return signInAnonymously()
    }

    /**
     * Sign in anonymously and store JWT
     */
    private suspend fun signInAnonymously(): String? = withContext(Dispatchers.IO) {
        try {
            val requestBody = """{"data":{}}"""
            val request = Request.Builder()
                .url("${BuildConfig.SUPABASE_URL}/auth/v1/signup")
                .addHeader("apikey", BuildConfig.SUPABASE_ANON_KEY)
                .addHeader("Content-Type", "application/json")
                .post(requestBody.toRequestBody(JSON_MEDIA_TYPE))
                .build()

            val response = httpClient.newCall(request).execute()
            val responseBody = response.body?.string() ?: return@withContext null

            if (!response.isSuccessful) {
                return@withContext null
            }

            parseAndStoreTokens(responseBody)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Refresh existing JWT token
     */
    private suspend fun refreshToken(refreshToken: String): String? = withContext(Dispatchers.IO) {
        try {
            val requestBody = """{"refresh_token":"$refreshToken"}"""
            val request = Request.Builder()
                .url("${BuildConfig.SUPABASE_URL}/auth/v1/token?grant_type=refresh_token")
                .addHeader("apikey", BuildConfig.SUPABASE_ANON_KEY)
                .addHeader("Content-Type", "application/json")
                .post(requestBody.toRequestBody(JSON_MEDIA_TYPE))
                .build()

            val response = httpClient.newCall(request).execute()
            val responseBody = response.body?.string() ?: return@withContext null

            if (!response.isSuccessful) {
                return@withContext null
            }

            parseAndStoreTokens(responseBody)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Parse JWT response and store tokens
     */
    private fun parseAndStoreTokens(responseBody: String): String? {
        try {
            val json = gson.fromJson(responseBody, Map::class.java)
            val accessToken = json["access_token"] as? String ?: return null
            val refreshToken = json["refresh_token"] as? String
            val expiresIn = (json["expires_in"] as? Double)?.toLong() ?: 3600L

            val expiresAt = System.currentTimeMillis() + (expiresIn * 1000)

            prefs?.edit()?.apply {
                putString(KEY_ACCESS_TOKEN, accessToken)
                putString(KEY_REFRESH_TOKEN, refreshToken)
                putLong(KEY_EXPIRES_AT, expiresAt)
                apply()
            }

            return accessToken
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    /**
     * Clear stored tokens
     */
    fun signOut() {
        prefs?.edit()?.clear()?.apply()
    }
}
