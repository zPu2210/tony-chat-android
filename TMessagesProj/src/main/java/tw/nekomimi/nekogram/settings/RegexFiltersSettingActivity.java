package tw.nekomimi.nekogram.settings;

import android.content.Context;
import android.view.View;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.ThemeDescription;
import java.util.ArrayList;

/**
 * Stub: Regex filters settings activity
 */
public class RegexFiltersSettingActivity extends BaseFragment {
    public RegexFiltersSettingActivity() {}

    public RegexFiltersSettingActivity(long dialogId) {}

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
    public void onFragmentDestroy() {
        super.onFragmentDestroy();
    }
}
