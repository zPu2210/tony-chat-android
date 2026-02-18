package com.tonychat.ai.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TranscriptCacheDao {
    @Query("SELECT * FROM transcript_cache WHERE cacheKey = :key AND (createdAt + ttlHours * 3600000) > :now LIMIT 1")
    fun get(key: String, now: Long = System.currentTimeMillis()): TranscriptCacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: TranscriptCacheEntity)

    @Query("DELETE FROM transcript_cache WHERE (createdAt + ttlHours * 3600000) < :now")
    fun evictExpired(now: Long = System.currentTimeMillis())

    @Query("DELETE FROM transcript_cache")
    fun clearAll()
}
