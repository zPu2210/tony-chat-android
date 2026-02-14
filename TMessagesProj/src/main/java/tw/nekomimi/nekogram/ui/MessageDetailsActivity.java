package tw.nekomimi.nekogram.ui;

import android.content.Context;
import android.view.View;
import org.telegram.messenger.MessageObject;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.ThemeDescription;
import java.util.ArrayList;

/**
 * Stub: Message details activity
 */
public class MessageDetailsActivity extends BaseFragment {
    public MessageDetailsActivity(MessageObject messageObject) {}

    @Override
    public boolean onFragmentCreate() {
        return true;
    }

    @Override
    public View createView(Context context) {
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
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
