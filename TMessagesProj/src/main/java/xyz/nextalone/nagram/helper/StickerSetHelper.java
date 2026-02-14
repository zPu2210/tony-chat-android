package xyz.nextalone.nagram.helper;
import org.telegram.tgnet.TLRPC;
import java.io.File;
import java.util.ArrayList;
public class StickerSetHelper {
    public static final StickerSetHelper INSTANCE = new StickerSetHelper();
    public boolean sendOrderSyncForSS(ArrayList<TLRPC.StickerSet> sets) { return false; }
    public boolean reorderPinnedStickersForSS(ArrayList<TLRPC.StickerSet> sets, boolean notify) { return false; }
    public void removePinnedStickerLocal(long id) {}
    public void copyStickerSet(Object set, File dir) {}
    public void copyStickerSet(String shortName, String packName, Object stickerSet, int account) {}
}
