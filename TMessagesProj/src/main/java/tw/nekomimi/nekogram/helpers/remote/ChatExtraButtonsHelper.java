package tw.nekomimi.nekogram.helpers.remote;

import java.util.ArrayList;

public class ChatExtraButtonsHelper {
    public static final int CHAT_BUTTON_TYPE_LINK = 1;
    public static final int CHAT_BUTTON_TYPE_SEARCH = 2;

    public static ChatExtraButtonsHelper getInstance() {
        return new ChatExtraButtonsHelper();
    }

    public ArrayList<ChatExtraButtonInfo> getChatExtraButtons(long chatId) {
        return null;
    }

    public void loadPagePreviewRules() {
    }

    public void saveChatExtraButtons() {
    }

    public boolean needUpdate() {
        return false;
    }

    public void checkChatExtraButtons() {
    }

    public static class ChatExtraButtonInfo {
        public int type;
        public long chatId;
        public String name;
        public String url;

        public ChatExtraButtonInfo() {
        }

        public ChatExtraButtonInfo(int type, long chatId, String name, String url) {
            this.type = type;
            this.chatId = chatId;
            this.name = name;
            this.url = url;
        }
    }
}
