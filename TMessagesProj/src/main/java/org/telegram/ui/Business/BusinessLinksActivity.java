package org.telegram.ui.Business;

import android.content.Context;
import android.view.View;
import org.telegram.tgnet.tl.TL_account;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;

public class BusinessLinksActivity extends BaseFragment {

    public BusinessLinksActivity() {
        super();
    }

    public static void openRenameAlert(Context context, int currentAccount, TL_account.TL_businessChatLink businessLink, Theme.ResourcesProvider resourcesProvider, boolean flag) {
        // No-op stub
    }

    public static boolean closeRenameAlert() {
        return false;
    }

    public static class BusinessLinkView extends View {
        public BusinessLinkView(Context context, Theme.ResourcesProvider resourcesProvider) {
            super(context);
        }

        public void set(BusinessLinkWrapper wrapper, boolean divider) {}
    }

    public static class BusinessLinkWrapper {
        public TL_account.TL_businessChatLink link;
    }
}
