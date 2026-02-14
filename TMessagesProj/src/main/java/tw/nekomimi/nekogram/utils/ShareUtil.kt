package tw.nekomimi.nekogram.utils

import android.content.Context
import java.io.File

object ShareUtil {

    @JvmStatic
    @JvmOverloads
    fun shareText(ctx: Context, text: String, choose: Boolean = false) {}

    @JvmOverloads
    @JvmStatic
    fun shareFile(ctx: Context, fileToShare: File, caption: String = "") {}

    @JvmOverloads
    @JvmStatic
    fun openFile(ctx: Context, fileToOpen: File) {}
}
