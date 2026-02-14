package org.telegram.ui.Stars;

import android.content.Context;
import org.telegram.messenger.MessageSuggestionParams;
import org.telegram.messenger.Utilities;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet;

/**
 * STUB: Message suggestion offer feature removed from Tony Chat.
 */
public class MessageSuggestionOfferSheet extends BottomSheet {
    public static final int MODE_INPUT = 0;
    public static final int MODE_EDIT = 1;

    public MessageSuggestionOfferSheet(Context context) {
        super(context, false);
    }

    public MessageSuggestionOfferSheet(Context context, int account, long dialogId, Object params, Object fragment, Object resourceProvider, int mode, Utilities.Callback<MessageSuggestionParams> callback) {
        super(context, false);
    }

    public static void show(BaseFragment fragment, Object... args) {
        // No-op stub
    }

    public static String formatDateTime(long timestamp) {
        return String.valueOf(timestamp);
    }

    public void show() {
        // No-op stub - override to prevent actual showing
    }
}
