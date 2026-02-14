package org.telegram.ui.Business;

import android.content.Context;
import android.widget.LinearLayout;
import org.telegram.tgnet.tl.TL_account;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;

public class BusinessLinksEmptyView extends LinearLayout {

    public BusinessLinksEmptyView(Context context, BaseFragment fragment, TL_account.TL_businessChatLink businessLink, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        setOrientation(VERTICAL);
    }
}
