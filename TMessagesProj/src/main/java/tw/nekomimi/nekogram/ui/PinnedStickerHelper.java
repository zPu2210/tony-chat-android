package tw.nekomimi.nekogram.ui;

import org.telegram.tgnet.TLRPC;
import java.util.ArrayList;
import java.util.List;

/**
 * Stub: Pinned sticker helper
 */
public class PinnedStickerHelper {
    public List<Long> pinnedList = new ArrayList<>();
    public final int accountNum;

    private PinnedStickerHelper(int accountNum) {
        this.accountNum = accountNum;
    }

    public static PinnedStickerHelper getInstance(int accountNum) {
        return new PinnedStickerHelper(accountNum);
    }

    public boolean isPinned(long sticker_id) {
        return false;
    }

    public List<Long> pinNewSticker(long stickerID) {
        return pinnedList;
    }

    public void removePinnedStickerLocal(long stickerID) {}

    public synchronized boolean reorderPinnedStickersForSS(List<TLRPC.StickerSet> stickerSets, boolean removeLocal) {
        return false;
    }

    public synchronized boolean reorderPinnedStickers(List<TLRPC.TL_messages_stickerSet> stickerSets, boolean removeLocal) {
        return false;
    }

    public boolean reorderPinnedStickers(List<TLRPC.TL_messages_stickerSet> stickerSets) {
        return false;
    }

    public void swap(int index1, int index2) {}

    public void sendOrderSyncForSS(List<TLRPC.StickerSet> stickerSetIDs) {}

    public void sendOrderSync(List<TLRPC.TL_messages_stickerSet> stickerSetIDs) {}
}
