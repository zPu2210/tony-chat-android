package tw.nekomimi.nekogram.helpers.remote;
public class UpdateHelper {
    private static final UpdateHelper instance = new UpdateHelper();
    public static UpdateHelper getInstance() { return instance; }
    public void checkNewVersion(Object cb) {}
    public void checkNewVersionAvailable(Object activity, Object cb) {}
    public void checkNewVersionAvailable(Object cb) {}
    public boolean hasSupport() { return false; }
}
