package tw.nekomimi.nekogram.utils

import android.widget.TextView
import org.telegram.ui.ActionBar.BaseFragment

object StrUtil {

    @JvmStatic
    fun setText(fragment: BaseFragment?, textView: TextView, text: String) {
        textView.text = text
    }

    @JvmStatic
    fun getSubString(text: String, left: String?, right: String?): String = ""
}
