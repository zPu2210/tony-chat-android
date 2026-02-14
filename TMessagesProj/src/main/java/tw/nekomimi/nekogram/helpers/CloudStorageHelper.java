package tw.nekomimi.nekogram.helpers;
public class CloudStorageHelper {
    private static final CloudStorageHelper instance = new CloudStorageHelper();
    public static CloudStorageHelper getInstance(int account) { return instance; }
    public static void init() {}
}
