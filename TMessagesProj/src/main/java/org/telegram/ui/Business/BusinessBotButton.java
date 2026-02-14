package org.telegram.ui.Business;

import android.content.Context;
import android.widget.FrameLayout;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;

public class BusinessBotButton extends FrameLayout {

    public BusinessBotButton(Context context, BaseFragment fragment, Theme.ResourcesProvider resourcesProvider) {
        super(context);
    }

    public void setLeftMargin(int margin) {}

    public void set(long botId, long dialogId, String link, int currentAccount) {}
}
