package tw.nekomimi.nekogram;

import android.content.Context;
import android.view.View;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.ThemeDescription;
import java.util.ArrayList;

/**
 * Stub: Datacenter activity
 */
public class DatacenterActivity extends BaseFragment {
    public DatacenterActivity() {}

    public DatacenterActivity(int dcToHighlight) {}

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
