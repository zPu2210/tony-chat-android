package tw.nekomimi.nekogram.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import org.telegram.ui.ActionBar.AlertDialog

object ProxyUtil {

    @JvmStatic
    fun isVPNEnabled(): Boolean = false

    @JvmStatic
    fun registerNetworkCallback() {}

    @JvmStatic
    fun getOwnerActivity(ctx: Context): Activity {
        error("ProxyUtil stub")
    }

    @JvmStatic
    @JvmOverloads
    fun showQrDialog(ctx: Context, text: String, icon: ((Int) -> Bitmap)? = null): AlertDialog {
        return AlertDialog.Builder(ctx).create()
    }

    @JvmStatic
    fun createQRCode(text: String, size: Int = 768, icon: ((Int) -> Bitmap)? = null): Bitmap {
        return Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    }

    @JvmStatic
    fun tryReadQR(ctx: Activity, bitmap: Bitmap) {}

    @JvmStatic
    @JvmOverloads
    fun showLinkAlert(ctx: Activity, text: String, tryInternal: Boolean = true) {}

    @JvmStatic
    fun importFromClipboard(ctx: Activity) {}

    @JvmStatic
    fun isIpv6Address(value: String): Boolean = false
}
