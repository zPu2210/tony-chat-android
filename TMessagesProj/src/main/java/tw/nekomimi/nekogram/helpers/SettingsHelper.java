package tw.nekomimi.nekogram.helpers;

import android.app.Activity;
import android.net.Uri;

import org.telegram.ui.ActionBar.BaseFragment;

import java.util.ArrayList;

public class SettingsHelper {
    public static void processDeepLink(Activity activity, Uri uri, Callback callback, Runnable unknown) {
        if (unknown != null) unknown.run();
    }

    public interface Callback {
        void presentFragment(BaseFragment fragment);
    }

    public static ArrayList<SettingsSearchResult> onCreateSearchArray(Callback callback) {
        return new ArrayList<>();
    }
}
