package tw.nekomimi.nekogram.transtale;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import tw.nekomimi.nekogram.cc.CCTarget;
public class Translator {
    public static void translate(String text, TranslateCallBack callback) {}
    public static void translate(String text, String to, TranslateCallBack callback) {}
    public static void translate(String text, String to, String from, TranslateCallBack callback) {}
    public static void translate(String text, Locale to, TranslateCallBack callback) {}
    public static void translate(String text, Locale to, Locale from, TranslateCallBack callback) {}
    // Overloads accepting Companion.TranslateCallBack
    public static void translate(String text, Companion.TranslateCallBack callback) {}
    public static void translate(String text, String to, Companion.TranslateCallBack callback) {}
    public static void translate(String text, String to, String from, Companion.TranslateCallBack callback) {}
    public static void translate(String text, Locale to, Companion.TranslateCallBack callback) {}
    public static void translate(String text, Locale to, Locale from, Companion.TranslateCallBack callback) {}
    public static void translate(Locale to, String text, Companion.TranslateCallBack callback) {}
    public static void translate(Locale to, Locale from, Companion.TranslateCallBack callback) {}
    public static String getMessagePlainText(Object msg) { return ""; }
    public static Locale forLocale(Locale locale) { return locale; }
    public static Locale getChatLanguage(long chatId, Locale defaultLocale) { return defaultLocale; }
    public static void saveChatLanguage(long chatId, Locale locale) {}
    public static CCTarget getChatCCTarget(long chatId, String lang) { return null; }
    public static void showTargetLangSelect(Object menuItem, boolean b, Object callback) {}
    public static void showCCTargetSelect(Object menuItem, Object callback) {}
    public static void showTransFailedDialog(Object context, boolean unsupported, String message, Runnable onRetry) {}
    public static int currentAppId() { return 0; }
    public static class Companion {
        public interface TranslateCallBack {
            void onSuccess(@NotNull String translation);
            void onFailed(boolean unsupported, @NotNull String message);
        }
    }
    public interface TranslateCallBack {
        void onSuccess(@NotNull String translation);
        void onFailed(boolean unsupported, @NotNull String message);
    }
}
