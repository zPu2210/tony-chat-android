package tw.nekomimi.nekogram.helpers;
public class ExternalStickerCacheHelper {
    private static ExternalStickerCacheHelper instance;
    public static ExternalStickerCacheHelper getInstance() { if (instance == null) instance = new ExternalStickerCacheHelper(); return instance; }
    public void deleteAll() {}
}
