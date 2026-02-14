package tw.nekomimi.nekogram.helpers.remote;

import android.graphics.Bitmap;
import android.graphics.Typeface;

import org.telegram.ui.ActionBar.BaseFragment;

import java.io.File;
import java.util.ArrayList;

public class EmojiHelper {
    public static EmojiHelper getInstance() {
        return new EmojiHelper();
    }

    public static File getSystemEmojiFontPath() {
        return null;
    }

    public static void mkDirs() {
    }

    public static boolean isValidEmojiPack(File path) {
        return false;
    }

    public static void reloadEmoji() {
    }

    public static void drawEmojiFont(android.graphics.Canvas canvas, float x, float y, android.graphics.Typeface typeface, String emoji, int size) {
        // No-op stub
    }

    public Long getEmojiSize() {
        return 0L;
    }

    public void deleteAll() {
    }

    public String getEmojiPack() {
        return "";
    }

    public void setEmojiPack(String pack) {
    }

    public void setEmojiPack(String pack, boolean manually) {
    }

    public void addListener(EmojiPackLoadListener listener) {
    }

    public void removeListener(EmojiPackLoadListener listener) {
    }

    public Typeface getCurrentTypeface() {
        return null;
    }

    public Typeface getSystemEmojiTypeface() {
        return null;
    }

    public String getSelectedPackName() {
        return "";
    }

    public String getSelectedEmojiPackId() {
        return "";
    }

    public void loadEmojisInfo(EmojiPacksLoadedListener listener) {
    }

    public void loadEmojiPackInfo() {
    }

    public ArrayList<EmojiPackBase> getEmojiPacks() {
        return new ArrayList<>();
    }

    public ArrayList<EmojiPackBase> getEmojiPacksInfoAll() {
        return new ArrayList<>();
    }

    public boolean isPackDownloaded(EmojiPackInfo pack) {
        return false;
    }

    public boolean isPackInstalled(EmojiPackInfo pack) {
        return false;
    }

    public EmojiPackBase getCurrentEmojiPackInfo() {
        return null;
    }

    public EmojiPackInfo getEmojiPackInfo(String emojiPackId) {
        return null;
    }

    public boolean isEmojiPackDownloading(EmojiPackInfo pack) {
        return false;
    }

    public void installDownloadedEmoji(EmojiPackInfo pack, boolean update) {
    }

    public EmojiPackBase installEmoji(File emojiFile, boolean update) {
        return null;
    }

    public static File getEmojiDir() {
        return null;
    }

    public File getCurrentEmojiPackOffline() {
        return null;
    }

    public static class EmojiSetBulletinLayout extends org.telegram.ui.Components.Bulletin.TwoLineLayout {
        public EmojiSetBulletinLayout(android.content.Context context, String title, String description, EmojiPackBase pack, org.telegram.ui.ActionBar.Theme.ResourcesProvider resourcesProvider) {
            super(context, resourcesProvider);
        }
    }

    public Bitmap getSystemEmojiPreview() {
        return null;
    }

    public boolean isSelectedCustomEmojiPack() {
        return false;
    }

    public void cancelableDelete(BaseFragment fragment, EmojiPackBase emojiPackBase, OnBulletinAction onUndoBulletinAction) {
    }

    public void deleteEmojiPack(EmojiPackBase emojiPackBase) {
    }

    public void downloadPack(EmojiPackInfo pack, boolean update, boolean tried) {
    }

    public void checkEmojiPacks() {
    }

    public interface EmojiPackLoadListener {
        void progressChanged(EmojiPackInfo pack, boolean finished, float progress, long bytesLoaded);
    }

    public interface EmojiPacksLoadedListener {
        void emojiPacksLoaded(String error);
    }

    public interface OnBulletinAction {
        void onPreStart();
        void onUndo();
    }

    public static class EmojiPackBase {
        protected String packName;
        protected String packId;
        protected String fileLocation;

        public EmojiPackBase() {
        }

        public void loadFromFile(File file) {
        }

        public String getPackName() {
            return "";
        }

        public String getPackId() {
            return "";
        }

        public String getFileLocation() {
            return "";
        }

        public String getPreview() {
            return "";
        }

        public Long getFileSize() {
            return 0L;
        }
    }

    public static class EmojiPackInfo extends EmojiPackBase {
        public EmojiPackInfo() {
        }

        public int getPackVersion() {
            return 0;
        }

        public Object getPreviewDocument() {
            return null;
        }

        public Object getFileDocument() {
            return null;
        }
    }
}
