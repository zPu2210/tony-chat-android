package tw.nekomimi.nekogram.ui;
import org.telegram.tgnet.TLRPC;
import java.util.ArrayList;
public class PinnedStickerHelper {
    public static PinnedStickerHelper INSTANCE = new PinnedStickerHelper();
    public static PinnedStickerHelper getInstance(int account) { return INSTANCE; }
    public ArrayList<Long> pinnedList = new ArrayList<>();
    public boolean isPinned(long id) { return false; }
    public void removePinnedStickerLocal(long id) {}
    public boolean reorderPinnedStickersForSS(ArrayList<TLRPC.TL_messages_stickerSet> sets, boolean notify) { return false; }
    public boolean sendOrderSyncForSS(ArrayList<TLRPC.TL_messages_stickerSet> sets) { return false; }
}
