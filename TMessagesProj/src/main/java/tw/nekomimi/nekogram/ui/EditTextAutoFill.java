package tw.nekomimi.nekogram.ui;

import android.content.Context;
import org.telegram.ui.Components.EditTextBoldCursor;

/**
 * Stub: EditText with autofill hints
 */
public class EditTextAutoFill extends EditTextBoldCursor {
    public EditTextAutoFill(Context context) {
        super(context);
    }

    @Override
    public int getAutofillType() {
        return AUTOFILL_TYPE_TEXT;
    }
}
