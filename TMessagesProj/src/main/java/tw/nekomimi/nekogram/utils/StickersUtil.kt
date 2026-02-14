package tw.nekomimi.nekogram.utils

import com.google.gson.JsonObject
import org.telegram.tgnet.TLRPC
import org.telegram.ui.ActionBar.AlertDialog
import org.telegram.ui.ActionBar.BaseFragment

object StickersUtil {

    @JvmStatic
    fun importStickers(stickerObj: JsonObject, f: BaseFragment, progress: AlertDialog) {}

    @JvmStatic
    fun exportStickers(account: Int, exportSets: Boolean, exportArchived: Boolean): JsonObject {
        return JsonObject()
    }

    @JvmStatic
    fun exportStickers(exportSets: Collection<TLRPC.StickerSet>): JsonObject {
        return JsonObject()
    }
}
