package org.telegram.ui.TonyChat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

/**
 * One-time "What's New in v2.0" bottom sheet.
 * Shown on first launch after bottom nav is enabled.
 * Auto-dismisses after 10 seconds.
 */
public class TonyMigrationSheet {

    private static final String PREF_KEY = "tony_migration_v2_shown";
    private static final long AUTO_DISMISS_MS = 10_000;

    /** Show the sheet if not already shown. Returns true if shown. */
    public static boolean showIfNeeded(Context context) {
        SharedPreferences prefs = ApplicationLoader.applicationContext
            .getSharedPreferences("tony_prefs", Context.MODE_PRIVATE);
        if (prefs.getBoolean(PREF_KEY, false)) {
            return false;
        }

        prefs.edit().putBoolean(PREF_KEY, true).apply();

        BottomSheet.Builder builder = new BottomSheet.Builder(context);
        builder.setTitle("What's New in Tony Chat", true);

        LinearLayout content = new LinearLayout(context);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(AndroidUtilities.dp(24), AndroidUtilities.dp(8),
            AndroidUtilities.dp(24), AndroidUtilities.dp(24));

        // Bullet items
        String[] bullets = {
            "\u2728  Your chats are now in the Chats tab",
            "\uD83E\uDD16  Find AI features in Tony Assist",
            "\u2699\uFE0F  Settings moved to the Settings tab"
        };

        for (String bullet : bullets) {
            TextView item = new TextView(context);
            item.setText(bullet);
            item.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            item.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            item.setPadding(0, AndroidUtilities.dp(6), 0, AndroidUtilities.dp(6));
            content.addView(item, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
        }

        // Dismiss button
        TextView dismissBtn = new TextView(context);
        dismissBtn.setText("Got it");
        dismissBtn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        dismissBtn.setTextColor(0xFFFFFFFF);
        dismissBtn.setBackgroundColor(TonyColors.primary());
        dismissBtn.setGravity(Gravity.CENTER);
        dismissBtn.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        dismissBtn.setMinimumHeight(AndroidUtilities.dp(48));
        dismissBtn.setPadding(AndroidUtilities.dp(16), AndroidUtilities.dp(12),
            AndroidUtilities.dp(16), AndroidUtilities.dp(12));

        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        btnParams.topMargin = AndroidUtilities.dp(16);
        content.addView(dismissBtn, btnParams);

        builder.setCustomView(content);
        BottomSheet sheet = builder.create();

        dismissBtn.setOnClickListener(v -> sheet.dismiss());

        // Auto-dismiss after 10 seconds
        Handler handler = new Handler(Looper.getMainLooper());
        Runnable autoDismiss = () -> {
            if (sheet.isShowing()) {
                sheet.dismiss();
            }
        };
        handler.postDelayed(autoDismiss, AUTO_DISMISS_MS);
        sheet.setOnDismissListener(dialog -> handler.removeCallbacks(autoDismiss));

        sheet.show();
        return true;
    }
}
