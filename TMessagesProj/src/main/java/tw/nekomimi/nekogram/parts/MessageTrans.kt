package tw.nekomimi.nekogram.parts

import org.telegram.messenger.MessageObject
import org.telegram.ui.ChatActivity
import java.util.Locale

// Stub for MessageTrans - translation disabled in Tony Chat
object MessageTransKt {
    @JvmStatic
    fun translateMessages(activity: ChatActivity) {
        // No-op
    }

    @JvmStatic
    fun translateMessages(activity: ChatActivity, locale: Locale) {
        // No-op
    }

    @JvmStatic
    fun translateMessages(activity: ChatActivity, messages: ArrayList<MessageObject>, showLanguageSelect: Boolean) {
        // No-op
    }

    @JvmStatic
    fun translateMessages(activity: ChatActivity, messages: ArrayList<MessageObject>) {
        translateMessages(activity, messages, false)
    }
}
