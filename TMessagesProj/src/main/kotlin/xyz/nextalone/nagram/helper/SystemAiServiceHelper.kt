package xyz.nextalone.nagram.helper

import android.content.Context
import android.net.Uri
import android.view.View

/**
 * System AI service helper stub
 * Feature removed - all methods are no-ops
 */
object SystemAiServiceHelper {
    fun isSystemAiAvailable(context: Context): Boolean = false

    @JvmOverloads
    fun startSystemAiService(view: View, text: String = "") {
        // No-op: system AI service feature removed
    }

    fun startSystemAiService(context: Context, uri: Uri): Boolean = false
}
