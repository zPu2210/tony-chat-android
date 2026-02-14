package org.telegram.ui.Business;

import android.content.Context;
import android.widget.LinearLayout;
import org.telegram.ui.ActionBar.Theme;

public class QuickRepliesEmptyView extends LinearLayout {

    public QuickRepliesEmptyView(Context context, int chatMode, long type, long topic_id, String quickReplyName, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        setOrientation(VERTICAL);
    }
}
