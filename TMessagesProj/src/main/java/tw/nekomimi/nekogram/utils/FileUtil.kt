package tw.nekomimi.nekogram.utils

import java.io.File

object FileUtil {

    @JvmStatic
    fun deleteDirectory(directoryToBeDeleted: File): Boolean = false

    @JvmStatic
    fun initDir(dir: File) {}

    @JvmStatic
    @JvmOverloads
    fun delete(file: File?, filter: (File) -> Boolean = { true }) {}

    @JvmStatic
    fun initFile(file: File) {}

    @JvmStatic
    fun readUtf8String(file: File) = ""

    @JvmStatic
    fun writeUtf8String(text: String, save: File) {}

    @JvmStatic
    fun saveAsset(path: String, saveTo: File) {}

    @JvmStatic
    val abi by lazy { "" }

    @JvmStatic
    fun extLib(name: String): File = File("")

    @JvmStatic
    fun saveNonAsset(path: String, saveTo: File) {}
}
