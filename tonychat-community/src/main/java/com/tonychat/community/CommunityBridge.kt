package com.tonychat.community

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.tonychat.community.model.Comment
import com.tonychat.community.model.CreateCommentRequest
import com.tonychat.community.model.CreatePostRequest
import com.tonychat.community.model.Post
import com.tonychat.community.repository.CommentRepository
import com.tonychat.community.repository.LikeRepository
import com.tonychat.community.repository.LocationHelper
import com.tonychat.community.repository.PostRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Java-friendly bridge for calling Community repository suspend functions from Java code.
 * Each method runs on IO dispatcher and delivers results on the main thread via callback.
 */
object CommunityBridge {
    private const val TAG = "CommunityBridge"

    private val fallbackScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val scope: CoroutineScope
        get() = try {
            ProcessLifecycleOwner.get().lifecycleScope
        } catch (e: Exception) {
            Log.w(TAG, "ProcessLifecycleOwner not ready, using fallback scope", e)
            fallbackScope
        }

    private val postRepository = PostRepository()
    private val commentRepository = CommentRepository()
    private val likeRepository = LikeRepository()

    fun interface PostListCallback {
        fun onResult(posts: List<Post>)
    }

    fun interface PostCallback {
        fun onResult(post: Post?)
    }

    fun interface CommentListCallback {
        fun onResult(comments: List<Comment>)
    }

    fun interface CommentCallback {
        fun onResult(comment: Comment?)
    }

    fun interface BooleanCallback {
        fun onResult(success: Boolean)
    }

    fun interface LocationCallback {
        fun onResult(location: Location?)
    }

    /**
     * Get current location (bridge for suspend function)
     */
    @JvmStatic
    fun getCurrentLocation(context: Context, callback: LocationCallback) {
        scope.launch(Dispatchers.IO) {
            val result = try {
                val locationHelper = LocationHelper(context)
                locationHelper.getCurrentLocation()
            } catch (e: Exception) {
                Log.w(TAG, "getCurrentLocation failed", e)
                null
            }
            withContext(Dispatchers.Main) {
                callback.onResult(result)
            }
        }
    }

    /**
     * Get nearby posts using location coordinates
     */
    @JvmStatic
    fun getNearbyPosts(
        lat: Double,
        lng: Double,
        radiusMeters: Int,
        pageOffset: Int,
        deviceId: String,
        callback: PostListCallback
    ) {
        scope.launch(Dispatchers.IO) {
            val result = try {
                postRepository.getNearbyPosts(lat, lng, radiusMeters, pageOffset, deviceId)
            } catch (e: Exception) {
                Log.w(TAG, "getNearbyPosts failed", e)
                emptyList()
            }
            withContext(Dispatchers.Main) {
                callback.onResult(result)
            }
        }
    }

    /**
     * Create a new post
     */
    @JvmStatic
    fun createPost(request: CreatePostRequest, callback: PostCallback) {
        scope.launch(Dispatchers.IO) {
            val result = try {
                postRepository.createPost(request)
            } catch (e: Exception) {
                Log.w(TAG, "createPost failed", e)
                null
            }
            withContext(Dispatchers.Main) {
                callback.onResult(result)
            }
        }
    }

    /**
     * Delete a post by ID
     */
    @JvmStatic
    fun deletePost(postId: String, deviceId: String, callback: BooleanCallback) {
        scope.launch(Dispatchers.IO) {
            val result = try {
                postRepository.deletePost(postId, deviceId)
            } catch (e: Exception) {
                Log.w(TAG, "deletePost failed", e)
                false
            }
            withContext(Dispatchers.Main) {
                callback.onResult(result)
            }
        }
    }

    /**
     * Get comments for a post
     */
    @JvmStatic
    fun getComments(postId: String, callback: CommentListCallback) {
        scope.launch(Dispatchers.IO) {
            val result = try {
                commentRepository.getComments(postId)
            } catch (e: Exception) {
                Log.w(TAG, "getComments failed", e)
                emptyList()
            }
            withContext(Dispatchers.Main) {
                callback.onResult(result)
            }
        }
    }

    /**
     * Create a new comment
     */
    @JvmStatic
    fun createComment(request: CreateCommentRequest, callback: CommentCallback) {
        scope.launch(Dispatchers.IO) {
            val result = try {
                commentRepository.createComment(request)
            } catch (e: Exception) {
                Log.w(TAG, "createComment failed", e)
                null
            }
            withContext(Dispatchers.Main) {
                callback.onResult(result)
            }
        }
    }

    /**
     * Like a post
     */
    @JvmStatic
    fun likePost(postId: String, deviceId: String, callback: BooleanCallback) {
        scope.launch(Dispatchers.IO) {
            val result = try {
                likeRepository.likePost(postId, deviceId)
            } catch (e: Exception) {
                Log.w(TAG, "likePost failed", e)
                false
            }
            withContext(Dispatchers.Main) {
                callback.onResult(result)
            }
        }
    }

    /**
     * Unlike a post
     */
    @JvmStatic
    fun unlikePost(postId: String, deviceId: String, callback: BooleanCallback) {
        scope.launch(Dispatchers.IO) {
            val result = try {
                likeRepository.unlikePost(postId, deviceId)
            } catch (e: Exception) {
                Log.w(TAG, "unlikePost failed", e)
                false
            }
            withContext(Dispatchers.Main) {
                callback.onResult(result)
            }
        }
    }
}
