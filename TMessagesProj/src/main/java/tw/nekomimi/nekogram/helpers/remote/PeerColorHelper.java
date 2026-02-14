package tw.nekomimi.nekogram.helpers.remote;

import org.telegram.tgnet.TLRPC;

public class PeerColorHelper {
    public static PeerColorHelper getInstance() {
        return new PeerColorHelper();
    }

    public void loadPeerColorInfo() {
    }

    public void savePeerColorInfo() {
    }

    public boolean needUpdate() {
        return false;
    }

    public void checkPeerColor() {
    }

    public Integer getColorId(long chatId) {
        return null;
    }

    public Integer getColorId(TLRPC.User user) {
        return null;
    }

    public Integer getColorId(TLRPC.Chat chat) {
        return null;
    }

    public Long getEmojiId(long chatId) {
        return null;
    }

    public Long getEmojiId(TLRPC.User user) {
        return null;
    }

    public Long getEmojiId(TLRPC.Chat chat) {
        return null;
    }

    public Integer getProfileColorId(long chatId) {
        return null;
    }

    public Integer getProfileColorId(TLRPC.User user) {
        return null;
    }

    public Integer getProfileColorId(TLRPC.Chat chat) {
        return null;
    }

    public Long getProfileEmojiId(long chatId) {
        return null;
    }

    public Long getProfileEmojiId(TLRPC.User user) {
        return null;
    }

    public Long getProfileEmojiId(TLRPC.Chat chat) {
        return null;
    }

    public static class PeerColorInfo {
        public long chatId;
        public int version;
        public int colorId;
        public long emojiId;
        public int profileColorId;
        public long profileEmojiId;

        public PeerColorInfo(long chatId, int version) {
            this.chatId = chatId;
            this.version = version;
        }

        public PeerColorInfo() {
        }
    }
}
