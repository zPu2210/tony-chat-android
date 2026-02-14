package org.telegram.ui.Business;

import android.content.Context;
import android.view.View;
import org.telegram.messenger.Utilities;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;

public class QuickRepliesActivity extends BaseFragment {

    public QuickRepliesActivity() {
        super();
    }

    public static void openRenameReplyAlert(Context context, int currentAccount, String quickReplyShortcut, Object currentQuickReply, Theme.ResourcesProvider resourcesProvider, boolean flag, Utilities.Callback<String> callback) {
        // No-op stub
    }

    public static class QuickReplyView extends View {
        public QuickReplyView(Context context, boolean flag, Theme.ResourcesProvider resourcesProvider) {
            super(context);
        }

        public void invalidateEmojis() {}
        public void setReorder(boolean allowReorder) {}
        public void setChecked(boolean checked, boolean animated) {}
        public void set(QuickRepliesController.QuickReply reply, String query, boolean divider) {}
    }

    public static class LargeQuickReplyView extends View {
        public LargeQuickReplyView(Context context, Theme.ResourcesProvider resourcesProvider) {
            super(context);
        }

        public void setChecked(boolean checked, boolean animated) {}
        public void set(QuickRepliesController.QuickReply reply, boolean divider) {}
    }
}
