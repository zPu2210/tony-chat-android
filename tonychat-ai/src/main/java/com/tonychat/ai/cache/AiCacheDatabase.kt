package com.tonychat.ai.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [AiCacheEntity::class, ImageEditCacheEntity::class, EmojiCacheEntity::class, TranscriptCacheEntity::class], version = 4, exportSchema = false)
abstract class AiCacheDatabase : RoomDatabase() {
    abstract fun cacheDao(): AiCacheDao
    abstract fun imageEditCacheDao(): ImageEditCacheDao
    abstract fun emojiCacheDao(): EmojiCacheDao
    abstract fun transcriptCacheDao(): TranscriptCacheDao

    companion object {
        @Volatile
        private var INSTANCE: AiCacheDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `image_edit_cache` (
                        `imageHash` TEXT NOT NULL,
                        `resultPath` TEXT NOT NULL,
                        `createdAt` INTEGER NOT NULL,
                        `ttlHours` INTEGER NOT NULL,
                        PRIMARY KEY(`imageHash`)
                    )
                    """.trimIndent()
                )
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `emoji_cache` (
                        `promptHash` TEXT NOT NULL,
                        `prompt` TEXT NOT NULL,
                        `imagePath` TEXT NOT NULL,
                        `provider` TEXT NOT NULL,
                        `createdAt` INTEGER NOT NULL,
                        `ttlHours` INTEGER NOT NULL,
                        PRIMARY KEY(`promptHash`)
                    )
                    """.trimIndent()
                )
            }
        }

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `transcript_cache` (
                        `cacheKey` TEXT NOT NULL,
                        `transcript` TEXT NOT NULL,
                        `createdAt` INTEGER NOT NULL,
                        `ttlHours` INTEGER NOT NULL,
                        PRIMARY KEY(`cacheKey`)
                    )
                    """.trimIndent()
                )
            }
        }

        fun get(context: Context): AiCacheDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AiCacheDatabase::class.java,
                    "tonychat_ai_cache.db"
                )
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
                .build().also { INSTANCE = it }
            }
        }
    }
}
