package org.telegram.ui.Stars;

import android.content.Context;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet;

/**
 * STUB: Explain stars feature removed from Tony Chat.
 */
public class ExplainStarsSheet extends BottomSheet {
    public ExplainStarsSheet(Context context) {
        super(context, false);
    }

    public static void show(BaseFragment fragment, Object... args) {
        // No-op stub
    }

    public static class FeatureCell extends android.widget.FrameLayout {
        public static final int STYLE_SHEET = 0;
        public final android.widget.TextView subtitleView;

        public FeatureCell(Context context) {
            super(context);
            subtitleView = new android.widget.TextView(context);
        }

        public FeatureCell(Context context, int style, Object resourcesProvider) {
            super(context);
            subtitleView = new android.widget.TextView(context);
        }

        public void set(int icon, CharSequence title, CharSequence text) {
            // No-op stub
        }
    }
}
