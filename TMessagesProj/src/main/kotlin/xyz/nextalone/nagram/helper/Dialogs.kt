package xyz.nextalone.nagram.helper

import android.content.Context

/**
 * Dialog utilities stub
 * Nagram-specific dialogs removed
 */
object Dialogs {

    @JvmStatic
    fun CreateVoiceCaptionAlert(
        context: Context,
        timestamps: ArrayList<String>,
        finish: (String) -> Unit
    ) {
        // No-op: voice caption alert feature removed
    }

    @JvmStatic
    fun createNeedChangeNekoSettingsAlert(context: Context) {
        // No-op: neko settings alert feature removed
    }
}
