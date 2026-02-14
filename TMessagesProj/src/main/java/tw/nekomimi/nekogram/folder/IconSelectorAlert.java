package tw.nekomimi.nekogram.folder;
import org.telegram.ui.ActionBar.BaseFragment;
public class IconSelectorAlert {
    public static void show(BaseFragment fragment, IconSelectorCallback callback) {}
    public interface IconSelectorCallback {
        void onIconSelected(String emoticon);
    }
}
