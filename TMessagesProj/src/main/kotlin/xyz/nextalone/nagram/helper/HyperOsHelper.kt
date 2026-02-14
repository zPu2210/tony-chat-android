package xyz.nextalone.nagram.helper

import android.content.Context
import android.text.TextUtils
import android.view.View
import org.telegram.messenger.AndroidUtilities

/**
 * HyperOS helper stub
 * Feature removed - all methods are no-ops
 */
object HyperOsHelper {
    val IS_HYPEROS: Boolean = !TextUtils.isEmpty(AndroidUtilities.getSystemProperty("ro.mi.os.version.name"))

    fun isHyperAiAvailable(context: Context): Boolean = false

    fun startHyperOsAiService(view: View, text: String) {
        // No-op: HyperOS AI service feature removed
    }
}
