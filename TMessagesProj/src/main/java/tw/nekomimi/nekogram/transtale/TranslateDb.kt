package tw.nekomimi.nekogram.transtale

import java.util.*

class TranslateDb(val code: String) {
    companion object {
        @JvmStatic
        fun getChatLanguage(chatId: Long, default: Locale): Locale = default

        @JvmStatic
        fun saveChatLanguage(chatId: Long, locale: Locale) { /* no-op */ }

        @JvmStatic
        fun getChatCCTarget(chatId: Long, default: String?): String? = default

        @JvmStatic
        fun saveChatCCTarget(chatId: Long, target: String) { /* no-op */ }

        @JvmStatic
        fun currentTarget(): TranslateDb = TranslateDb("en")

        @JvmStatic
        fun forLocale(locale: Locale): TranslateDb = TranslateDb(locale.language)

        @JvmStatic
        fun currentInputTarget(): TranslateDb = TranslateDb("en")

        @JvmStatic
        fun clearAll() { /* no-op */ }
    }

    fun clear() { /* no-op */ }
    fun contains(text: String): Boolean = false
    fun query(text: String): String? = null
    fun save(text: String, trans: String) { /* no-op */ }
}

// Extension kept for compatibility
val Locale.transDb: TranslateDb get() = TranslateDb.forLocale(this)
val String.transDbByCode: TranslateDb get() = TranslateDb.forLocale(code2Locale)
