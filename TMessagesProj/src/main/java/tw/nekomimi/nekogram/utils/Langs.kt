package tw.nekomimi.nekogram.utils

import org.telegram.ui.ActionBar.AlertDialog

fun <T> T.applyIf(boolean: Boolean, block: (T.() -> Unit)?): T {
    if (boolean) block?.invoke(this)
    return this
}

fun <T> T.applyIfNot(boolean: Boolean, block: (T.() -> Unit)?): T {
    if (!boolean) block?.invoke(this)
    return this
}

fun String.input(vararg params: Any): String = this

fun AlertDialog.uUpdate(message: String) {}
fun AlertDialog.uDismiss() {}
