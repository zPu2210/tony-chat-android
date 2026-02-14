package tw.nekomimi.nekogram.helpers;
import android.content.Context;
import java.io.File;
public class EmojiHelper {
    private static final EmojiHelper instance = new EmojiHelper();
    public static EmojiHelper getInstance() { return instance; }
    public static void reloadEmoji() {}
    public static boolean isValidEmojiPack(File file) { return false; }
    public EmojiPackBase installEmoji(File file, boolean system) { return null; }
    public void checkEmojiPacks() {}
    public int getEmojiSize() { return 0; }
    public String getEmojiPack() { return ""; }
    public static class EmojiPackBase {
        public String packId;
        public String packName;
        public String packVersion;
        public String getPackId() { return packId != null ? packId : ""; }
    }
    public static class EmojiSetBulletinLayout extends android.widget.FrameLayout {
        public EmojiSetBulletinLayout(Context c, String title, Object doc, Object set) { super(c); }
    }
}
