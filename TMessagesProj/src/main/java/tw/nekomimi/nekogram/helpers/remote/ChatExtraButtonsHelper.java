package tw.nekomimi.nekogram.helpers.remote;
import java.util.ArrayList;
public class ChatExtraButtonsHelper {
    public static final int CHAT_BUTTON_TYPE_SEARCH = 0;
    public static final int CHAT_BUTTON_TYPE_LINK = 1;
    private static final ChatExtraButtonsHelper instance = new ChatExtraButtonsHelper();
    public static ChatExtraButtonsHelper getInstance() { return instance; }
    public void checkChatExtraButtons() {}
    public ArrayList<ChatExtraButtonInfo> getChatExtraButtons(long chatId) { return new ArrayList<>(); }
    public static class ChatExtraButtonInfo {
        public int type;
        public String name = "";
        public String text = "";
        public String url = "";
    }
}
