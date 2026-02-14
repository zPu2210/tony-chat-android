package org.telegram.ui.Business;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import org.telegram.tgnet.tl.TL_account;
import org.telegram.ui.ActionBar.Theme;

public class ProfileHoursCell extends LinearLayout {

    public ProfileHoursCell(Context context, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        setOrientation(VERTICAL);
    }

    protected int processColor(int color) {
        return color;
    }

    public void updateColors() {}

    public void setOnTimezoneSwitchClick(OnClickListener listener) {}

    public void set(TL_account.TL_businessWorkHours hours, boolean expanded, boolean showMine, boolean divider) {}
}
