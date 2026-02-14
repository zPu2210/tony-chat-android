package tw.nekomimi.nekogram;
import java.util.ArrayList;
public class DialogConfig {
    public static boolean isAutoTranslateEnable(long d, int t) { return false; }
    public static boolean isAutoTranslateEnable(long d, int t, int a) { return false; }
    public static void setAutoTranslateEnable(long d, int t, boolean e, int a) {}
    public static boolean hasAutoTranslateConfig(long d, int t, int a) { return false; }
    public static String getTranslateTo(long d, int t, int a) { return ""; }
    public static void setTranslateTo(long d, int t, String l, int a) {}
    public static void modifyShareTarget(ArrayList<?> list) {}
}
