package com.tonychat.community.model

import com.google.gson.annotations.SerializedName

data class Like(
    @SerializedName("post_id") val postId: String,
    @SerializedName("device_id") val deviceId: String,
    @SerializedName("created_at") val createdAt: String
)

data class CreateLikeRequest(
    @SerializedName("post_id") val postId: String,
    @SerializedName("device_id") val deviceId: String
)
