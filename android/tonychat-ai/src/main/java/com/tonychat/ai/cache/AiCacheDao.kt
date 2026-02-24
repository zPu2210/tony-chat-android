package com.tonychat.ai.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AiCacheDao {
    @Query("SELECT * FROM ai_cache WHERE queryHash = :hash AND createdAt + (ttlHours * 3600000) > :now")
    suspend fun getCached(hash: String, now: Long = System.currentTimeMillis()): AiCacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: AiCacheEntity)

    @Query("DELETE FROM ai_cache WHERE createdAt + (ttlHours * 3600000) < :now")
    suspend fun evictExpired(now: Long = System.currentTimeMillis())

    @Query("SELECT COALESCE(SUM(LENGTH(responseText)), 0) FROM ai_cache")
    suspend fun totalCacheSize(): Long

    @Query("DELETE FROM ai_cache")
    suspend fun clearAll()
}
