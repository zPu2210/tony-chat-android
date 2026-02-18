package com.tonychat.community.model

import com.google.gson.annotations.SerializedName

data class Report(
    @SerializedName("id") val id: String,
    @SerializedName("post_id") val postId: String,
    @SerializedName("device_id") val deviceId: String,
    @SerializedName("reason") val reason: String,
    @SerializedName("created_at") val createdAt: String
)

data class CreateReportRequest(
    @SerializedName("post_id") val postId: String,
    @SerializedName("device_id") val deviceId: String,
    @SerializedName("reason") val reason: String
)
