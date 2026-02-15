package com.tonychat.ai.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AiCacheEntity::class], version = 1, exportSchema = false)
abstract class AiCacheDatabase : RoomDatabase() {
    abstract fun cacheDao(): AiCacheDao

    companion object {
        @Volatile
        private var INSTANCE: AiCacheDatabase? = null

        fun get(context: Context): AiCacheDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AiCacheDatabase::class.java,
                    "tonychat_ai_cache.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
