package com.tonychat.ai.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Cache entry for generated emoji images.
 * Key = MD5 hash of normalized prompt (lowercase, trimmed).
 * TTL = 720 hours (30 days) - prompts unlikely to change meaning.
 */
@Entity(tableName = "emoji_cache")
data class EmojiCacheEntity(
    @PrimaryKey val promptHash: String,
    val prompt: String,              // Original prompt for debugging
    val imagePath: String,            // Absolute path to PNG file
    val provider: String,             // "Gemini 2.5 Flash Image"
    val createdAt: Long,
    val ttlHours: Int = 720
)
