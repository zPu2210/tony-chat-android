package tw.nekomimi.nekogram;

import org.telegram.ui.ActionBar.ActionBarPopupWindow;
import java.util.ArrayList;
import java.util.List;

/**
 * Stub: Back button menu recent items
 */
public class BackButtonMenuRecent {
    public static List<Long> getRecentDialogs() {
        return new ArrayList<>();
    }

    public static void addToRecentDialogs(int accountNum, long dialogId) {}

    public static void removeFromRecentDialogs(long dialogId) {}

    public static void clearRecentDialogs() {}

    public static void clearRecentDialogs(int accountNum) {}

    public static ActionBarPopupWindow.ActionBarPopupWindowLayout createMenu(android.content.Context context) {
        return null;
    }

    public static void show(int accountNum, org.telegram.ui.ActionBar.BaseFragment fragment, android.view.View anchorView) {
        // No-op stub
    }
}
