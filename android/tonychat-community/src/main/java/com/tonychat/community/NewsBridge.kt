package com.tonychat.community

import android.util.Log
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.tonychat.community.model.NewsArticle
import com.tonychat.community.repository.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Java-friendly bridge for NewsRepository suspend functions.
 * Runs on IO dispatcher, delivers results on main thread via callback.
 */
object NewsBridge {
    private const val TAG = "NewsBridge"

    private val fallbackScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val scope: CoroutineScope
        get() = try {
            ProcessLifecycleOwner.get().lifecycleScope
        } catch (e: Exception) {
            Log.w(TAG, "ProcessLifecycleOwner not ready, using fallback scope", e)
            fallbackScope
        }

    private val repository = NewsRepository()

    fun interface ArticleListCallback {
        fun onResult(articles: List<NewsArticle>)
    }

    fun interface BooleanCallback {
        fun onResult(value: Boolean)
    }

    @JvmStatic
    fun getArticles(category: String, offset: Int, callback: ArticleListCallback) {
        scope.launch(Dispatchers.IO) {
            val result = try {
                repository.getArticles(category, offset)
            } catch (e: Exception) {
                Log.w(TAG, "getArticles failed", e)
                emptyList()
            }
            withContext(Dispatchers.Main) {
                callback.onResult(result)
            }
        }
    }

    @JvmStatic
    fun getCached(category: String, callback: ArticleListCallback) {
        val cached = repository.getCached(category)
        if (cached != null) {
            callback.onResult(cached)
        } else {
            callback.onResult(emptyList())
        }
    }

    @JvmStatic
    fun isStale(callback: BooleanCallback) {
        callback.onResult(repository.isStale())
    }

    @JvmStatic
    fun clearCache() {
        repository.clearCache()
    }
}
