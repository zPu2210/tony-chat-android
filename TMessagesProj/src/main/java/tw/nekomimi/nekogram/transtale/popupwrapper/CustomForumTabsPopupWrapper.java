package tw.nekomimi.nekogram.transtale.popupwrapper;

import android.content.Context;
import org.telegram.ui.ActionBar.ActionBarPopupWindow;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.PopupSwipeBackLayout;

public class CustomForumTabsPopupWrapper {
    public ActionBarPopupWindow.ActionBarPopupWindowLayout windowLayout;

    public CustomForumTabsPopupWrapper(BaseFragment fragment, PopupSwipeBackLayout swipeBackLayout, long dialogId, Theme.ResourcesProvider resourcesProvider) {
        Context context = fragment.getParentActivity();
        windowLayout = new ActionBarPopupWindow.ActionBarPopupWindowLayout(context, 0, resourcesProvider);
        windowLayout.setFitItems(true);
    }

    public void updateItems() {
        // No-op
    }
}
