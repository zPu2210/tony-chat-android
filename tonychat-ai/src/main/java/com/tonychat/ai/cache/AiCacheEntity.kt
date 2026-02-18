package com.tonychat.ai.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ai_cache")
data class AiCacheEntity(
    @PrimaryKey val queryHash: String,
    val feature: String,
    val responseText: String,
    val createdAt: Long,
    val ttlHours: Int
)
