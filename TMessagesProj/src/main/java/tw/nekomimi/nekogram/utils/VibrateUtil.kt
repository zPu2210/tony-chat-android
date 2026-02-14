package tw.nekomimi.nekogram.utils

import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View

object VibrateUtil {

    lateinit var vibrator: Vibrator

    @JvmStatic
    @JvmOverloads
    fun vibrate(time: Long = 200L, effect: VibrationEffect? = null) {}

    @JvmStatic
    fun disableHapticFeedback(view: View?) {}

    @JvmStatic
    fun vibrate(longs: LongArray, repeat: Int) {}
}
