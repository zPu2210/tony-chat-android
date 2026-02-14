package tw.nekomimi.nekogram.utils

import java.io.File

object EnvUtil {

    @JvmStatic
    val rootDirectories: List<File> by lazy { emptyList<File>() }

    @JvmStatic
    val availableDirectories
        get() = emptyArray<String>()

    @JvmStatic
    fun getTelegramPath(): File = File("")

    @JvmStatic
    fun getShareCachePath(): File = File("")

    @JvmStatic
    fun doTest() {}
}
