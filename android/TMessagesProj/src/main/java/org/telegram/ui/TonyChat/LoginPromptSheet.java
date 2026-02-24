package org.telegram.ui.TonyChat;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.R;
import org.telegram.messenger.UserConfig;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.LoginActivity;

/**
 * Login gate bottom sheet — shown when unauthenticated user taps any gated action.
 * Displays a prompt with "Sign in" button that opens Telegram phone number login.
 */
public class LoginPromptSheet {

    /**
     * Returns true if user is logged in to Telegram, false otherwise.
     */
    public static boolean isLoggedIn() {
        int account = UserConfig.selectedAccount;
        return UserConfig.getInstance(account).isClientActivated();
    }

    /**
     * Show the login prompt bottom sheet. If already logged in, runs the action immediately.
     *
     * @param fragment The current BaseFragment context
     * @param action   Runnable to execute if already logged in (or after login success)
     */
    public static void checkAndRun(BaseFragment fragment, Runnable action) {
        if (isLoggedIn()) {
            action.run();
            return;
        }
        show(fragment);
    }

    /**
     * Show the login prompt bottom sheet.
     */
    public static void show(BaseFragment fragment) {
        if (fragment == null || fragment.getParentActivity() == null) return;
        Context context = fragment.getParentActivity();

        BottomSheet.Builder builder = new BottomSheet.Builder(context);

        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setGravity(Gravity.CENTER_HORIZONTAL);
        int pad = AndroidUtilities.dp(24);
        container.setPadding(pad, AndroidUtilities.dp(32), pad, AndroidUtilities.dp(32));

        // Lock/login icon
        ImageView icon = new ImageView(context);
        icon.setImageResource(R.drawable.msg_permissions);
        icon.setColorFilter(0xFFF9E000);
        icon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        LinearLayout.LayoutParams iconLp = new LinearLayout.LayoutParams(
                AndroidUtilities.dp(64), AndroidUtilities.dp(64));
        iconLp.bottomMargin = AndroidUtilities.dp(16);
        container.addView(icon, iconLp);

        // Title
        TextView title = new TextView(context);
        title.setText("Sign in to continue");
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        title.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        title.setTextColor(0xFF111111);
        title.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams titleLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleLp.bottomMargin = AndroidUtilities.dp(8);
        container.addView(title, titleLp);

        // Description
        TextView desc = new TextView(context);
        desc.setText("Connect your Telegram account to\nuse this feature.");
        desc.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        desc.setTextColor(0xFF666666);
        desc.setGravity(Gravity.CENTER);
        desc.setLineSpacing(AndroidUtilities.dp(2), 1f);
        LinearLayout.LayoutParams descLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        descLp.bottomMargin = AndroidUtilities.dp(24);
        container.addView(desc, descLp);

        // Sign in button (yellow)
        TextView signInBtn = new TextView(context);
        signInBtn.setText("Sign In");
        signInBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        signInBtn.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        signInBtn.setTextColor(0xFF111111);
        signInBtn.setGravity(Gravity.CENTER);
        signInBtn.setPadding(AndroidUtilities.dp(48), AndroidUtilities.dp(14),
                AndroidUtilities.dp(48), AndroidUtilities.dp(14));

        GradientDrawable btnBg = new GradientDrawable();
        btnBg.setShape(GradientDrawable.RECTANGLE);
        btnBg.setCornerRadius(AndroidUtilities.dp(24));
        btnBg.setColor(0xFFF9E000);
        signInBtn.setBackground(btnBg);

        LinearLayout.LayoutParams btnLp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btnLp.bottomMargin = AndroidUtilities.dp(12);
        container.addView(signInBtn, btnLp);

        // Cancel text button
        TextView cancelBtn = new TextView(context);
        cancelBtn.setText("Not now");
        cancelBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        cancelBtn.setTextColor(0xFF999999);
        cancelBtn.setGravity(Gravity.CENTER);
        cancelBtn.setPadding(0, AndroidUtilities.dp(8), 0, AndroidUtilities.dp(8));
        container.addView(cancelBtn, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        builder.setCustomView(container);
        BottomSheet sheet = builder.create();

        signInBtn.setOnClickListener(v -> {
            sheet.dismiss();
            fragment.presentFragment(new LoginActivity());
        });

        cancelBtn.setOnClickListener(v -> sheet.dismiss());

        sheet.show();
    }
}
