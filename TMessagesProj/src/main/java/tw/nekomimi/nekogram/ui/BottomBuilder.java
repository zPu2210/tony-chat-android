package tw.nekomimi.nekogram.ui;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.LinearLayout;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.Components.EditTextBoldCursor;
import org.telegram.ui.Cells.HeaderCell;
public class BottomBuilder {
    private BottomSheet.Builder builder;
    private LinearLayout rootView;
    public BottomBuilder(Context context) {
        builder = new BottomSheet.Builder(context);
        rootView = new LinearLayout(context);
        rootView.setOrientation(LinearLayout.VERTICAL);
    }
    public BottomBuilder(Activity activity, boolean needFocus) { this((Context) activity); }
    public BottomBuilder(Activity activity, boolean needFocus, int theme) { this((Context) activity); }
    public BottomBuilder(Context context, int theme) { this(context); }
    public BottomBuilder(Context context, boolean needFocus, int theme) { this(context); }
    public BottomBuilder(Context context, BaseFragment fragment) { this(context); }
    public BottomBuilder(Context context, boolean needFocus) { this(context); }
    public BottomSheet.Builder getBuilder() { return builder; }
    public LinearLayout getRootView() { return rootView; }
    public void addTitle(CharSequence title) {}
    public View addTitle(CharSequence title, String subtitle) { return null; }
    public View addTitle(String title, boolean big, String subtitle) { return null; }
    public HeaderCell addTitle(CharSequence title, boolean big) { return null; }
    public void addTitle(CharSequence title, CharSequence subtitle) {}
    // addItem variants
    public void addItem(String text, int icon, Runnable onClick) {}
    public void addItem(String text, int icon, boolean red, Runnable onClick) {}
    public void addItem(CharSequence text, int icon, boolean red, Runnable onClick) {}
    public void addItem(String text, int icon, kotlin.jvm.functions.Function1 onClick) {}
    public void addItem(String text, int icon, boolean red, kotlin.jvm.functions.Function1 onClick) {}
    // More add methods
    public void addItems(String[] texts, int[] icons, ItemClickListener listener) {}
    public void addItems(CharSequence[] texts, int[] icons, ItemClickListener listener) {}
    public void addRadioItem(String text, boolean checked, kotlin.jvm.functions.Function1 onClick) {}
    public void doRadioCheck(Object cell) {}
    public Object addButton(String text, boolean red, kotlin.jvm.functions.Function1 onClick) { return null; }
    public Object addButton(String text, kotlin.jvm.functions.Function1 onClick) { return null; }
    public Object addCheckItem(String text, boolean checked, boolean red, kotlin.jvm.functions.Function1 onClick) { return null; }
    public void addCancelItem() {}
    public void addCancelButton() {}
    public EditTextBoldCursor addEditText(String hint) { return null; }
    public void setOnPreDismissListener(DialogInterface.OnDismissListener listener) {}
    public BottomSheet create() { return builder.create(); }
    public BottomSheet show() { return builder.create(); }
    public void dismiss() {}
    public interface ItemClickListener { Object onClick(int which, CharSequence text, View cell); }
}
