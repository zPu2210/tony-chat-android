package com.tonychat.ai.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Cache entity for image editing results.
 * Stores file path references, not the actual image bytes.
 */
@Entity(tableName = "image_edit_cache")
data class ImageEditCacheEntity(
    @PrimaryKey val imageHash: String,
    val resultPath: String,
    val createdAt: Long,
    val ttlHours: Int = 168
)
