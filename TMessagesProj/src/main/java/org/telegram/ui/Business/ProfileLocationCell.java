package org.telegram.ui.Business;

import android.content.Context;
import android.widget.LinearLayout;
import org.telegram.tgnet.TLRPC;
import org.telegram.ui.ActionBar.Theme;

public class ProfileLocationCell extends LinearLayout {

    public ProfileLocationCell(Context context, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        setOrientation(VERTICAL);
    }

    public void set(TLRPC.TL_businessLocation location, boolean divider) {}
}
