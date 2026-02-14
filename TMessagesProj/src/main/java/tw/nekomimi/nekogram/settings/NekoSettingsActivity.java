package tw.nekomimi.nekogram.settings;

import android.content.Context;
import android.view.View;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.ThemeDescription;
import java.util.ArrayList;

/**
 * Stub: NekoX settings activity
 */
public class NekoSettingsActivity extends BaseFragment {
    public static final int PAGE_TYPE = 0;
    public static final int PAGE_ABOUT = 1;

    @Override
    public boolean onFragmentCreate() {
        return true;
    }

    @Override
    public View createView(Context context) {
        return null;
    }

    @Override
    public ArrayList<ThemeDescription> getThemeDescriptions() {
        return new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
    }

    public static void importSettings(android.app.Activity activity, java.io.File file) {
        // No-op stub
    }
}
