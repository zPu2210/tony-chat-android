package com.tonychat.ai.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EmojiCacheDao {
    @Query("SELECT * FROM emoji_cache WHERE promptHash = :hash AND (createdAt + ttlHours * 3600000) > :now LIMIT 1")
    suspend fun get(hash: String, now: Long = System.currentTimeMillis()): EmojiCacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: EmojiCacheEntity)

    @Query("DELETE FROM emoji_cache WHERE (createdAt + ttlHours * 3600000) <= :now")
    suspend fun evictExpired(now: Long = System.currentTimeMillis())

    @Query("DELETE FROM emoji_cache")
    suspend fun clearAll()
}
