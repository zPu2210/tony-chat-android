package tw.nekomimi.nekogram.helpers.remote;

import org.telegram.tgnet.TLRPC;

public class WallpaperHelper {
    public static WallpaperHelper getInstance() {
        return new WallpaperHelper();
    }

    public void loadWallPaperInfo() {
    }

    public void saveWallPaperInfo() {
    }

    public TLRPC.WallPaper getDialogWallpaper(long dialogId) {
        return null;
    }

    public boolean needUpdate() {
        return false;
    }

    public void checkWallPaper() {
    }

    public static class WallPaperInfo {
        public long chatId;
        public int version;
        public String slug;
        public String emoticon;
        public String mode;
        public TLRPC.WallPaper wallPaper;

        public WallPaperInfo(long chatId, int version, String slug, String emoticon, String mode) {
            this.chatId = chatId;
            this.version = version;
            this.slug = slug;
            this.emoticon = emoticon;
            this.mode = mode;
        }

        public WallPaperInfo() {
        }
    }
}
