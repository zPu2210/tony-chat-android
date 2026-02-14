package org.telegram.ui.Stories.recorder;

import android.content.Context;
import android.view.View;
import org.telegram.tgnet.TLRPC;
import org.telegram.ui.ActionBar.BottomSheet;
import java.util.ArrayList;

/**
 * StoryPrivacyBottomSheet stub - Stories removed in Tony Chat
 */
public class StoryPrivacyBottomSheet extends BottomSheet {
    // Tony Chat: Stories removed - privacy type constants
    public static final int TYPE_EVERYONE = 0;
    public static final int TYPE_CONTACTS = 1;
    public static final int TYPE_CLOSE_FRIENDS = 2;
    public static final int TYPE_SELECTED_CONTACTS = 3;

    public StoryPrivacyBottomSheet(int account) {
        super(null, false);
    }

    public static class StoryPrivacy {
        public int type;
        public ArrayList<Long> selectedUserIds = new ArrayList<>();
        public ArrayList<Long> selectedUserIdsCopy = new ArrayList<>();
        public TLRPC.InputPeer sendAs;
        public boolean isCloseFriends;
        public boolean isContact;
        public boolean isSelectedContacts;

        // Tony Chat: Stories removed - default constructor
        public StoryPrivacy() {
            this.type = TYPE_EVERYONE;
        }

        // Tony Chat: Stories removed - constructor overload
        public StoryPrivacy(int currentAccount, Object privacy) {
            this.type = TYPE_EVERYONE;
        }

        public static StoryPrivacy fromValue(int value) {
            return new StoryPrivacy();
        }

        public int getValue() { return 0; }
    }

    public static class UserCell extends View {
        public long dialogId;
        public View checkBox;
        public View radioButton;

        public UserCell(Context context, Object resourcesProvider) {
            super(context);
            checkBox = new View(context);
            radioButton = new View(context);
        }

        public void set(Object user) {}
        public void setChecked(boolean checked, boolean animated) {}
        public void setIsSendAs(boolean isSendAs, boolean animated) {}
        public void setDivider(boolean divider) {}
    }
}
