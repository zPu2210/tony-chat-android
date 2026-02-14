package xyz.nextalone.nagram.helper

import android.content.Context
import org.telegram.tgnet.TLRPC.TL_messages_stickerSet

/**
 * External sticker cache helper stub
 * Feature removed - all methods are no-ops
 */
object ExternalStickerCacheHelper {
    const val TAG = "ExternalStickerCache"

    @JvmStatic
    fun checkUri(configCell: Any?, context: Context) {
        // No-op: external sticker cache feature removed
    }

    @JvmStatic
    fun cacheStickers(isAutoSync: Boolean = true) {
        // No-op: external sticker cache feature removed
    }

    @JvmStatic
    fun refreshCacheFiles(set: TL_messages_stickerSet) {
        // No-op: external sticker cache feature removed
    }

    @JvmStatic
    fun deleteCacheFiles(set: TL_messages_stickerSet) {
        // No-op: external sticker cache feature removed
    }

    @JvmStatic
    fun syncAllCaches() {
        // No-op: external sticker cache feature removed
    }

    @JvmStatic
    fun deleteAllCaches() {
        // No-op: external sticker cache feature removed
    }

    @JvmStatic
    fun addNotificationObservers(currentAccount: Int) {
        // No-op: external sticker cache feature removed
    }

    @JvmStatic
    fun removeNotificationObservers(currentAccount: Int) {
        // No-op: external sticker cache feature removed
    }
}
