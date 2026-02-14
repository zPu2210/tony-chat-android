package tw.nekomimi.nekogram.utils

import org.openintents.openpgp.util.OpenPgpApi
import org.openintents.openpgp.util.OpenPgpServiceConnection

object PGPUtil {

    lateinit var serviceConnection: OpenPgpServiceConnection
    lateinit var api: OpenPgpApi

    @JvmStatic
    fun recreateConnection() {}

    @JvmStatic
    fun post(runnable: Runnable) {}
}
