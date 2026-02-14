package xyz.nextalone.nagram.helper

import org.telegram.tgnet.TLRPC

/**
 * Local peer color helper stub
 * Feature removed - all methods return null
 */
object LocalPeerColorHelper {
    var loaded: Boolean = false
    var data: LocalQuoteColorData? = null

    @JvmStatic
    fun getColorId(user: TLRPC.User): Int? = null

    @JvmStatic
    fun getEmojiId(user: TLRPC.User?): Long? = null

    @JvmStatic
    fun getProfileColorId(user: TLRPC.User): Int? = null

    @JvmStatic
    fun getProfileEmojiId(user: TLRPC.User?): Long? = null

    @JvmStatic
    fun init(force: Boolean = false) {
        // No-op: local peer color feature removed
    }

    @JvmStatic
    fun apply(colorId: Int, emojiId: Long, profileColorId: Int, profileEmojiId: Long) {
        // No-op: local peer color feature removed
    }
}

data class LocalQuoteColorData(
    var colorId: Int?,
    var emojiId: Long?,
    var profileColorId: Int?,
    var profileEmojiId: Long?
)
