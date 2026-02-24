package com.tonychat.ai.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transcript_cache")
data class TranscriptCacheEntity(
    @PrimaryKey val cacheKey: String,  // "messageId:fileHash"
    val transcript: String,
    val createdAt: Long,
    val ttlHours: Int = 720  // 30 days
)
