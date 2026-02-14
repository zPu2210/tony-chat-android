package tw.nekomimi.nekogram.helpers.remote;
import org.telegram.tgnet.TLRPC;
public class WallpaperHelper {
    private static WallpaperHelper instance = new WallpaperHelper();
    public static WallpaperHelper getInstance() { return instance; }
    public TLRPC.WallPaper getDialogWallpaper(long dialogId) { return null; }
}
