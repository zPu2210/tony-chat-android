package com.tonychat.community.model

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("id") val id: String,
    @SerializedName("device_id") val deviceId: String? = null,
    @SerializedName("content") val content: String,
    @SerializedName("image_url") val imageUrl: String? = null,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double,
    @SerializedName("anonymous") val anonymous: Boolean = false,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("expires_at") val expiresAt: String,

    // Transient fields from nearby_posts RPC - var generates setters automatically
    @SerializedName("like_count") var likeCount: Long = 0,
    @SerializedName("comment_count") var commentCount: Long = 0,
    @SerializedName("is_liked") var isLiked: Boolean = false
)

// Request model for creating post
data class CreatePostRequest(
    @SerializedName("device_id") val deviceId: String,
    @SerializedName("content") val content: String,
    @SerializedName("image_url") val imageUrl: String? = null,
    @SerializedName("location") val location: LocationPoint,
    @SerializedName("anonymous") val anonymous: Boolean = false
)

data class LocationPoint(
    @SerializedName("type") val type: String = "Point",
    @SerializedName("coordinates") val coordinates: List<Double>  // [lng, lat]
)
