package com.tonychat.community.model

import com.google.gson.annotations.SerializedName

data class NewsArticle(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("summary") val summary: String? = null,
    @SerializedName("source_url") val sourceUrl: String,
    @SerializedName("source_name") val sourceName: String,
    @SerializedName("image_url") val imageUrl: String? = null,
    @SerializedName("category") val category: String = "general",
    @SerializedName("published_at") val publishedAt: String,
    @SerializedName("created_at") val createdAt: String? = null
)
