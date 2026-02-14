package tw.nekomimi.nekogram.utils;
import android.widget.TextView;
public class StrUtil {
    public static void setText(Object fragment, TextView tv, String text) {
        if (tv != null && text != null) tv.setText(text);
    }
}
