package tw.nekomimi.nekogram.helpers.remote;
public class PinnedStickerHelper {
    private static PinnedStickerHelper instance;
    public static PinnedStickerHelper getInstance() { if (instance == null) instance = new PinnedStickerHelper(); return instance; }
    public void removePinnedStickerLocal(long id) {}
}
