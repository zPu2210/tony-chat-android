package xyz.nextalone.nagram.helper

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import android.view.View
import org.telegram.messenger.AndroidUtilities

/**
 * ColorOS helper stub
 * Feature removed - all methods are no-ops
 */
object ColorOsHelper {
    val isColorOS: Boolean = !TextUtils.isEmpty(AndroidUtilities.getSystemProperty("ro.build.version.oplusrom"))
    val colorOSVersion: Int = try {
        AndroidUtilities.getSystemProperty("ro.build.version.release").toInt()
    } catch (_: Exception) { 0 }

    fun isColorOSAiAvailable(): Boolean = false

    fun startColorOsAiService(view: View, text: String) {
        // No-op: ColorOS AI service feature removed
    }

    fun startColorOsAiService(context: Context, uri: Uri): Boolean = false
}
