package tw.nekomimi.nekogram.utils;
import java.io.File;
public class FileUtil {
    public static File extLib(String name) { return new File(name); }
    public static void initDir(File dir) { if (dir != null && !dir.exists()) dir.mkdirs(); }
    public static void saveAsset(String asset, File target) {}
    public static void delete(File f) { if (f != null && f.exists()) f.delete(); }
    public static void deleteDirectory(File dir) {}
    public static long getDirSize(File dir) { return 0; }
}
