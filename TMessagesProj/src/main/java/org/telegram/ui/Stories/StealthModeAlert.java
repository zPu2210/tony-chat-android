package org.telegram.ui.Stories;

import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.Theme;

/**
 * StealthModeAlert stub - Stories removed in Tony Chat
 */
public class StealthModeAlert extends BottomSheet {
    public static final int TYPE_FROM_DIALOGS = 0;

    public StealthModeAlert(android.content.Context context) {
        super(context, false);
    }

    public StealthModeAlert(android.content.Context context, int type, int typeFromDialogs, Theme.ResourcesProvider resourceProvider) {
        super(context, false);
    }

    public interface StealthModeListener {
        void onResult(boolean isStealthModeEnabled);
    }

    public void setListener(StealthModeListener listener) {}

    public static void showStealthModeEnabledBulletin() {}
}
