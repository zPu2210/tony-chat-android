package tw.nekomimi.nekogram.transtale

import android.view.View
import org.telegram.messenger.LocaleController
import java.util.*

// Extension properties kept for compatibility
val String.code2Locale: Locale
    get() {
        if (this.isBlank()) return LocaleController.getInstance().currentLocale
        val args = replace('-', '_').split('_')
        return if (args.size == 1) Locale(args[0]) else Locale(args[0], args[1])
    }

val Locale.locale2code: String
    get() = if (country.isNullOrBlank()) language else "$language-$country"

interface Translator {
    suspend fun doTranslate(from: String, to: String, query: String): String

    companion object {
        const val providerGoogle = 1
        const val providerGoogleCN = 2
        const val providerYandex = 3
        const val providerLingo = 4
        const val providerMicrosoft = 5
        const val providerYouDao = 6
        const val providerDeepL = 7
        const val providerTelegram = 8
        const val providerTranSmart = 9

        @JvmStatic
        @JvmOverloads
        fun translate(to: Locale = LocaleController.getInstance().currentLocale, query: String, translateCallBack: TranslateCallBack) {
            // Translation disabled in Tony Chat
            translateCallBack.onFailed(true, "Translation not available")
        }

        @JvmStatic
        @JvmOverloads
        fun showTargetLangSelect(anchor: View, input: Boolean = false, full: Boolean = false, callback: (Locale) -> Unit = {}) {
            // No-op
        }

        // Overload for 3 argument calls from Java
        @JvmStatic
        fun showTargetLangSelect(anchor: View, input: Boolean, callback: (Locale) -> Unit) {
            // No-op
        }

        @JvmStatic
        @JvmOverloads
        fun showCCTargetSelect(anchor: View, input: Boolean = true, callback: (String) -> Unit = {}) {
            // No-op
        }

        // Overload for 2 argument calls from Java
        @JvmStatic
        fun showCCTargetSelect(anchor: View, callback: (String) -> Unit) {
            // No-op
        }

        interface TranslateCallBack {
            fun onSuccess(translation: String)
            fun onFailed(unsupported: Boolean, message: String)
        }
    }
}
