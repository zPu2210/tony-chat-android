package tw.nekomimi.nekogram.cc

/**
 * Stub: Chinese character converter - returns input unchanged
 */
class CCConverter {
    companion object {
        @JvmStatic
        fun get(target: String): CCConverter {
            return CCConverter()
        }

        @JvmStatic
        fun get(target: CCTarget): CCConverter {
            return CCConverter()
        }
    }

    fun convert(text: String): String = text
}
