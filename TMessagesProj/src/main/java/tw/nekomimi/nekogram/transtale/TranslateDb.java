package tw.nekomimi.nekogram.transtale;
import java.util.Locale;
import tw.nekomimi.nekogram.cc.CCTarget;
public class TranslateDb {
    public static TranslateDb currentTarget() { return new TranslateDb(); }
    public static TranslateDb currentInputTarget() { return new TranslateDb(); }
    public static TranslateDb forLocale(Locale locale) { return new TranslateDb(); }
    public static Locale getChatLanguage(long chatId, Locale defaultLocale) { return defaultLocale; }
    public static String getChatCCTarget(long chatId, String lang) { return null; }
    public static void clearAll() {}
    public boolean contains(String key) { return false; }
    public String query(String key) { return null; }
    public TranslateDb get(CCTarget target) { return this; }
    public String convert(String text) { return text; }
    public void save(String key, String val) {}
    public void clear() {}
}
