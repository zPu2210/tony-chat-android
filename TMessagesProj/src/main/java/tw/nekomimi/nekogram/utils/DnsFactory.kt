package tw.nekomimi.nekogram.utils

import java.net.InetAddress

object DnsFactory {

    fun providers() = emptyArray<String>()

    @JvmStatic
    @JvmOverloads
    fun lookup(domain: String, fallback: Boolean = false): List<InetAddress> = emptyList()

    @JvmStatic
    fun getTxts(domain: String): List<String> = emptyList()
}
