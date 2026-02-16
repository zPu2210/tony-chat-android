package com.tonychat.community

import android.content.Context
import android.location.Location
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Java-friendly bridge for calling Community repository suspend functions from Java code.
 * Each method runs on IO dispatcher and delivers results on the main thread via callback.
 */
object CommunityBridge {

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
        CoroutineScope(Dispatchers.IO).launch {
            val result = try {
                val locationHelper = LocationHelper(context)
                locationHelper.getCurrentLocation()
            } catch (e: Exception) {
                e.printStackTrace()
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
        CoroutineScope(Dispatchers.IO).launch {
            val result = try {
                postRepository.getNearbyPosts(lat, lng, radiusMeters, pageOffset, deviceId)
            } catch (e: Exception) {
                e.printStackTrace()
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
        CoroutineScope(Dispatchers.IO).launch {
            val result = try {
                postRepository.createPost(request)
            } catch (e: Exception) {
                e.printStackTrace()
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
        CoroutineScope(Dispatchers.IO).launch {
            val result = try {
                postRepository.deletePost(postId, deviceId)
            } catch (e: Exception) {
                e.printStackTrace()
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
        CoroutineScope(Dispatchers.IO).launch {
            val result = try {
                commentRepository.getComments(postId)
            } catch (e: Exception) {
                e.printStackTrace()
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
        CoroutineScope(Dispatchers.IO).launch {
            val result = try {
                commentRepository.createComment(request)
            } catch (e: Exception) {
                e.printStackTrace()
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
        CoroutineScope(Dispatchers.IO).launch {
            val result = try {
                likeRepository.likePost(postId, deviceId)
            } catch (e: Exception) {
                e.printStackTrace()
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
        CoroutineScope(Dispatchers.IO).launch {
            val result = try {
                likeRepository.unlikePost(postId, deviceId)
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
            withContext(Dispatchers.Main) {
                callback.onResult(result)
            }
        }
    }
}
