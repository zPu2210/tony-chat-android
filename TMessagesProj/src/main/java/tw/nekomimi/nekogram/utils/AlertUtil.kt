package tw.nekomimi.nekogram.utils

import android.content.Context
import org.telegram.tgnet.TLRPC
import org.telegram.ui.ActionBar.AlertDialog

object AlertUtil {

    @JvmStatic
    fun copyAndAlert(text: String) {}

    @JvmStatic
    fun copyLinkAndAlert(text: String) {}

    @JvmStatic
    fun call(number: String) {}

    @JvmStatic
    fun showToast(e: Throwable) {}

    @JvmStatic
    fun showToast(e: TLRPC.TL_error?) {}

    @JvmStatic
    fun showToast(text: String) {}

    @JvmStatic
    fun showSimpleAlert(ctx: Context?, error: Throwable) {}

    @JvmStatic
    @JvmOverloads
    fun showSimpleAlert(ctx: Context?, text: String, listener: ((AlertDialog.Builder) -> Unit)? = null) {}

    @JvmStatic
    @JvmOverloads
    fun showSimpleAlert(ctx: Context?, title: String?, text: String, listener: ((AlertDialog.Builder) -> Unit)? = null) {}

    @JvmStatic
    fun showCopyAlert(ctx: Context, text: String) {}

    @JvmOverloads
    @JvmStatic
    fun showProgress(ctx: Context, text: String = "Loading"): AlertDialog {
        return AlertDialog.Builder(ctx).create()
    }

    fun showInput(ctx: Context, title: String, hint: String, onInput: (AlertDialog.Builder, String) -> String) {}

    @JvmStatic
    @JvmOverloads
    fun showConfirm(ctx: Context, title: String, text: String? = null, icon: Int, button: String, red: Boolean, listener: Runnable) {}

    @JvmStatic
    @JvmOverloads
    fun showTransFailedDialog(ctx: Context, noRetry: Boolean, message: String, retryRunnable: Runnable) {}

    fun showTimePicker(ctx: Context, title: String, callback: (Long) -> Unit) {}
}
