package tw.nekomimi.nekogram;
import android.view.View;
import org.telegram.ui.ActionBar.BaseFragment;
public class BackButtonMenuRecent {
    public static void show(int account, BaseFragment fragment, View anchor) {}
    public static void show(int account, Object fragment, View anchor) {}
    public static void addRecent(Object o) {}
    public static void removeRecent(Object o) {}
    public static void addToRecentDialogs(int account, long dialogId) {}
    public static void clearRecentDialogs(int account) {}
    public static void removeNotificationObservers(int account) {}
    public static void addNotificationObservers(int account) {}
    public static java.util.ArrayList getRecentDialogs() { return new java.util.ArrayList(); }
}
