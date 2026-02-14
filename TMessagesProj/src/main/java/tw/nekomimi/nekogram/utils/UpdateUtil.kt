package tw.nekomimi.nekogram.utils

import android.content.Context

object UpdateUtil {

    const val channelUsername = "nagram_channel"
    const val channelUsernameTips = "NagramTips"
    const val wikiUrl = "https://na-wiki.xtaolabs.com"

    @JvmStatic
    fun getChannelUrl(): String = "https://t.me/$channelUsername"

    @JvmStatic
    fun getTipsUrl(): String = "https://t.me/$channelUsernameTips"

    @JvmStatic
    fun postCheckFollowChannel(ctx: Context, currentAccount: Int) {}

    @JvmStatic
    fun postCheckFollowTipsChannel(ctx: Context, currentAccount: Int) {}
}
