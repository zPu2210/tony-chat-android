package tw.nekomimi.nekogram.folder;

import org.telegram.ui.ActionBar.BaseFragment;

public class IconSelectorAlert {
    public static void show(BaseFragment fragment, OnIconSelectedListener onIconSelectedListener) {
    }

    public interface OnIconSelectedListener {
        void onIconSelected(String emoticon);
    }
}
