package tw.nekomimi.nekogram.helpers.remote;
import org.telegram.tgnet.TLRPC;
public class PeerColorHelper {
    private static PeerColorHelper instance = new PeerColorHelper();
    public static PeerColorHelper getInstance() { return instance; }
    public int getColorId(TLRPC.User user) { return -1; }
    public int getColorId(TLRPC.Chat chat) { return -1; }
    public long getEmojiId(TLRPC.User user) { return 0; }
    public long getEmojiId(TLRPC.Chat chat) { return 0; }
    public int getProfileColorId(TLRPC.User user) { return -1; }
    public int getProfileColorId(TLRPC.Chat chat) { return -1; }
    public long getProfileEmojiId(TLRPC.User user) { return 0; }
    public long getProfileEmojiId(TLRPC.Chat chat) { return 0; }
    public long getBackgroundEmojiId(TLRPC.Chat chat) { return 0; }
}
