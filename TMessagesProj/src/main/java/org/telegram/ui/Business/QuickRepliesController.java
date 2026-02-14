package org.telegram.ui.Business;

import android.util.SparseArray;
import org.telegram.messenger.MessageObject;
import org.telegram.tgnet.TLRPC;
import java.util.ArrayList;
import java.util.HashSet;

public class QuickRepliesController {

    public static final String GREETING = "hello";
    public static final String AWAY = "away";

    public static boolean isSpecial(String name) {
        return false;
    }

    private static volatile SparseArray<QuickRepliesController> Instance = new SparseArray<>();
    private static final Object lockObject = new Object();

    public static QuickRepliesController getInstance(int num) {
        QuickRepliesController localInstance = Instance.get(num);
        if (localInstance == null) {
            synchronized (lockObject) {
                localInstance = Instance.get(num);
                if (localInstance == null) {
                    Instance.put(num, localInstance = new QuickRepliesController(num));
                }
            }
        }
        return localInstance;
    }

    public final int currentAccount;
    private QuickRepliesController(int currentAccount) {
        this.currentAccount = currentAccount;
    }

    public class QuickReply {
        public int id;
        public String name;
        public int order;
        public int topMessageId;
        public MessageObject topMessage;
        public int messagesCount;
        public boolean local;
        public HashSet<Integer> localIds = new HashSet<>();

        public int getTopMessageId() {
            return topMessage != null ? topMessage.getId() : topMessageId;
        }

        public int getMessagesCount() {
            return local ? localIds.size() : messagesCount;
        }

        public boolean isSpecial() {
            return false;
        }
    }

    public final ArrayList<QuickReply> replies = new ArrayList<>();
    public final ArrayList<QuickReply> localReplies = new ArrayList<>();

    public boolean canAddNew() {
        return false;
    }

    public ArrayList<QuickReply> getFilteredReplies() {
        return new ArrayList<>();
    }

    public QuickReply findReply(int id) {
        return null;
    }

    public QuickReply findReply(String name) {
        return null;
    }

    public boolean hasReplies() {
        return false;
    }

    public void load() {}

    public void renameReply(int id, String name) {}

    public void deleteLocalMessages(ArrayList<Integer> messages) {}

    public boolean processUpdate(TLRPC.Update update, Object param1, int param2) {
        return false;
    }

    public void checkLocalMessages(ArrayList<MessageObject> messages) {}
}
