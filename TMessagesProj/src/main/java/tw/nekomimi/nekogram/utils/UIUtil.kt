package tw.nekomimi.nekogram.utils

object UIUtil {

    @JvmStatic
    fun runOnUIThread(runnable: Runnable) {
        runnable.run()
    }

    fun runOnUIThread(runnable: () -> Unit) {
        runnable()
    }

    @JvmStatic
    @JvmOverloads
    fun runOnIoDispatcher(runnable: Runnable, delay: Long = 0) {
        runnable.run()
    }

    fun runOnIoDispatcher(runnable: suspend () -> Unit) {}
}
