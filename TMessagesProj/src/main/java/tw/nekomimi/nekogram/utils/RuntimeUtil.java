package tw.nekomimi.nekogram.utils;
public class RuntimeUtil {
    public static Process exec(String... cmd) throws Exception {
        return Runtime.getRuntime().exec(cmd);
    }
}
