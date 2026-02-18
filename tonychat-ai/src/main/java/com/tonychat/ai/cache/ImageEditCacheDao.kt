package com.tonychat.ai.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ImageEditCacheDao {
    /**
     * Get cached result if not expired.
     * @param hash MD5 hash of source image
     * @param now Current timestamp in milliseconds
     */
    @Query("SELECT * FROM image_edit_cache WHERE imageHash = :hash AND createdAt + (ttlHours * 3600000) > :now LIMIT 1")
    suspend fun get(hash: String, now: Long = System.currentTimeMillis()): ImageEditCacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ImageEditCacheEntity)

    /**
     * Delete expired cache entries.
     */
    @Query("DELETE FROM image_edit_cache WHERE createdAt + (ttlHours * 3600000) < :now")
    suspend fun evictExpired(now: Long = System.currentTimeMillis())

    /**
     * Clear all image edit cache.
     */
    @Query("DELETE FROM image_edit_cache")
    suspend fun clearAll()
}
