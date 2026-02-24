package com.tonychat.community.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tonychat.community.SupabaseClient
import com.tonychat.community.SupabaseResult
import com.tonychat.community.model.NewsArticle

/**
 * Fetches news articles from Supabase REST API.
 * In-memory cache only — no Room DB, no SharedPrefs for articles.
 * Re-fetch if cache >30min stale or pull-to-refresh.
 */
class NewsRepository {
    companion object {
        private const val TAG = "NewsRepository"
        private const val STALE_THRESHOLD_MS = 30 * 60 * 1000L // 30 minutes
        private const val PAGE_SIZE = 20
    }

    private val gson = Gson()

    // In-memory cache per category
    private val cache = mutableMapOf<String, List<NewsArticle>>()
    private var lastFetchTime = 0L

    fun isStale(): Boolean {
        return System.currentTimeMillis() - lastFetchTime > STALE_THRESHOLD_MS
    }

    fun getCached(category: String): List<NewsArticle>? {
        if (isStale()) return null
        return if (category == "all") {
            cache.values.flatten().sortedByDescending { it.publishedAt }
        } else {
            cache[category]
        }
    }

    fun clearCache() {
        cache.clear()
        lastFetchTime = 0L
    }

    suspend fun getArticles(category: String, offset: Int): List<NewsArticle> {
        val path = buildPath(category, offset)
        val request = SupabaseClient.get(path)
        return when (val result = SupabaseClient.execute(request)) {
            is SupabaseResult.Success -> {
                val type = object : TypeToken<List<NewsArticle>>() {}.type
                val articles: List<NewsArticle> = try {
                    gson.fromJson(result.data, type) ?: emptyList()
                } catch (e: Exception) {
                    Log.e(TAG, "Parse error", e)
                    emptyList()
                }
                // Update cache
                if (offset == 0) {
                    if (category == "all") {
                        // "all" fetches without category filter; cache as "all"
                        cache["all"] = articles
                    } else {
                        cache[category] = articles
                    }
                    lastFetchTime = System.currentTimeMillis()
                } else {
                    // Append to existing cache
                    val key = if (category == "all") "all" else category
                    cache[key] = (cache[key] ?: emptyList()) + articles
                }
                articles
            }
            is SupabaseResult.Error -> {
                Log.e(TAG, "API error ${result.code}: ${result.message}")
                emptyList()
            }
            is SupabaseResult.NetworkError -> {
                Log.e(TAG, "Network error", result.exception)
                emptyList()
            }
        }
    }

    private fun buildPath(category: String, offset: Int): String {
        val sb = StringBuilder("/rest/v1/news_articles?select=*")
        if (category != "all") {
            sb.append("&category=eq.$category")
        }
        sb.append("&order=published_at.desc")
        sb.append("&limit=$PAGE_SIZE")
        sb.append("&offset=$offset")
        return sb.toString()
    }
}
