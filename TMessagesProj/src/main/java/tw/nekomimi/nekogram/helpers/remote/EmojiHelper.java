package tw.nekomimi.nekogram.helpers.remote;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import java.io.File;
public class EmojiHelper {
    private static EmojiHelper instance;
    public static EmojiHelper getInstance() { if (instance == null) instance = new EmojiHelper(); return instance; }
    public boolean isUseCustomEmoji() { return false; }
    public String getSelectedPackName() { return ""; }
    public String getSelectedEmojiPackId() { return ""; }
    public static void reloadEmoji() {}
    public static boolean isValidEmojiPack(File file) { return false; }
    public EmojiPackBase installEmoji(File file, boolean system) { return null; }
    public void checkEmojiPacks() {}
    public int getEmojiSize() { return 0; }
    public String getEmojiPack() { return "default"; }
    public void setEmojiPack(String packId) {}
    public File getCurrentEmojiPackOffline() { return null; }
    public Typeface getCurrentTypeface() { return null; }
    public void deleteAll() {}
    public static void drawEmojiFont(Canvas canvas, float x, float y, Typeface typeface, String emoji, int size) {}
    public static class EmojiPackBase {
        public String packId = "";
        public String packName = "";
        public String packVersion = "";
        public String getPackId() { return packId; }
    }
    public static class EmojiSetBulletinLayout extends android.widget.FrameLayout {
        public EmojiSetBulletinLayout(Context c, String title, Object doc, Object set) { super(c); }
    }
}
