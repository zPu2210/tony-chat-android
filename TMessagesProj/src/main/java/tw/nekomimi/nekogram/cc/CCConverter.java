package tw.nekomimi.nekogram.cc;
public class CCConverter {
    public static final int PILLS = 0;
    public static final int PURE = 1;
    public static CCConverter get(CCTarget target) { return new CCConverter(); }
    public static String convert(String text, CCTarget target) { return text; }
    public static String convert(String text, int mode) { return text; }
    public String convert(String text) { return text; }
}
