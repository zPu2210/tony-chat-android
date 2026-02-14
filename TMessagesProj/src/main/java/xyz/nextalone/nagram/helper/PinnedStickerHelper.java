package xyz.nextalone.nagram.helper;
import org.telegram.tgnet.TLRPC;
import java.util.ArrayList;
public class PinnedStickerHelper {
    public static PinnedStickerHelper INSTANCE = new PinnedStickerHelper();
    public static PinnedStickerHelper getInstance(int account) { return INSTANCE; }
    public ArrayList<Long> pinnedList = new ArrayList<>();
    public boolean isPinned(long id) { return false; }
    public void pin(long id) {}
    public void unpin(long id) {}
    public void swap(int from, int to) {}
    public void reorderPinnedStickers(ArrayList<TLRPC.TL_messages_stickerSet> sets) {}
    public void removePinnedStickerLocal(long id) {}
}
