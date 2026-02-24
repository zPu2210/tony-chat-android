package com.tonychat.community.model

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("id") val id: String,
    @SerializedName("post_id") val postId: String,
    @SerializedName("device_id") val deviceId: String,
    @SerializedName("content") val content: String,
    @SerializedName("created_at") val createdAt: String
)

data class CreateCommentRequest(
    @SerializedName("post_id") val postId: String,
    @SerializedName("device_id") val deviceId: String,
    @SerializedName("content") val content: String
)
