package tw.nekomimi.nekogram.helpers;
public class UserHelper {
    private static final UserHelper instance = new UserHelper();
    public static UserHelper getInstance(int account) { return instance; }
    public static String getDC(Object u) { return ""; }
    public static String getIdAndDc(Object u) { return ""; }
}
