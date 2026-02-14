package tw.nekomimi.nekogram.utils;
import java.io.File;
import android.os.Environment;
public class EnvUtil {
    public static File getTelegramPath() {
        return new File(Environment.getExternalStorageDirectory(), "Telegram");
    }
    public static String[] getAvailableDirectories() { return new String[]{"", "", ""}; }
}
