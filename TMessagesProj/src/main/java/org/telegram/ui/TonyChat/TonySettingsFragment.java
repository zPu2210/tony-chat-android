package org.telegram.ui.TonyChat;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.R;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.PhoneFormat.PhoneFormat;
import org.telegram.tgnet.TLRPC;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.DataSettingsActivity;
import org.telegram.ui.FiltersSetupActivity;
import org.telegram.ui.LanguageSelectActivity;
import org.telegram.ui.LiteModeSettingsActivity;
import org.telegram.ui.NotificationsSettingsActivity;
import org.telegram.ui.ProfileActivity;

import com.tonychat.core.TonyConfig;

/**
 * Settings tab — Tab 3 in bottom nav.
 * Card-based layout replacing the drawer menu.
 */
public class TonySettingsFragment extends BaseFragment {

    private static final int GHOST_ANIM_DURATION = 200;

    private LinearLayout contentLayout;
    private TextView ghostStatusText;
    private View ghostToggleView;

    @Override
    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        return true;
    }

    @Override
    public View createView(Context context) {
        actionBar.setTitle("Settings");
        actionBar.setAllowOverlayTitle(true);

        fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) fragmentView;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));

        ScrollView scrollView = new ScrollView(context);
        frameLayout.addView(scrollView, LayoutHelper.createFrame(
            LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));

        contentLayout = new LinearLayout(context);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setPadding(AndroidUtilities.dp(16), AndroidUtilities.dp(16),
            AndroidUtilities.dp(16), AndroidUtilities.dp(16));
        scrollView.addView(contentLayout, new ScrollView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        buildProfileHeader(context);
        buildGhostModeCard(context);
        buildSettingsGroup1(context);
        buildSettingsGroup2(context);
        buildQuickActionsGroup(context);
        buildSettingsGroup3(context);

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateGhostState(false);
    }

    private void buildProfileHeader(Context context) {
        TLRPC.User user = UserConfig.getInstance(currentAccount).getCurrentUser();

        RoundedCardView card = new RoundedCardView(context);
        card.setCardColor(Theme.getColor(Theme.key_windowBackgroundWhite));

        LinearLayout inner = new LinearLayout(context);
        inner.setOrientation(LinearLayout.HORIZONTAL);
        inner.setPadding(AndroidUtilities.dp(16), AndroidUtilities.dp(16),
            AndroidUtilities.dp(16), AndroidUtilities.dp(16));
        inner.setGravity(Gravity.CENTER_VERTICAL);

        // Avatar
        BackupImageView avatarView = new BackupImageView(context);
        AvatarDrawable avatarDrawable = new AvatarDrawable();
        if (user != null) {
            avatarDrawable.setInfo(user);
            avatarView.setForUserOrChat(user, avatarDrawable);
            String userName = ContactsController.formatName(user.first_name, user.last_name);
            avatarView.setContentDescription(userName + " avatar");
        }
        inner.addView(avatarView, LayoutHelper.createLinear(56, 56, Gravity.CENTER_VERTICAL, 0, 0, 14, 0));

        // Text column
        LinearLayout textCol = new LinearLayout(context);
        textCol.setOrientation(LinearLayout.VERTICAL);

        TextView nameView = new TextView(context);
        String name = user != null ? ContactsController.formatName(user.first_name, user.last_name) : "";
        nameView.setText(name);
        nameView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
        nameView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        nameView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        nameView.setSingleLine(true);
        textCol.addView(nameView, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 2));

        // Phone
        TextView phoneView = new TextView(context);
        String phone = user != null && user.phone != null
            ? PhoneFormat.getInstance().format("+" + user.phone) : "";
        phoneView.setText(phone);
        phoneView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        phoneView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        phoneView.setSingleLine(true);
        textCol.addView(phoneView, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 1));

        // Username
        if (user != null) {
            String username = UserObject.getPublicUsername(user);
            if (username != null && !username.isEmpty()) {
                TextView usernameView = new TextView(context);
                usernameView.setText("@" + username);
                usernameView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
                usernameView.setTextColor(TonyColors.primary());
                usernameView.setSingleLine(true);
                textCol.addView(usernameView, LayoutHelper.createLinear(
                    LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
            }
        }

        inner.addView(textCol, new LinearLayout.LayoutParams(
            0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        // Chevron (decorative)
        ImageView chevron = new ImageView(context);
        chevron.setImageResource(R.drawable.msg_arrowright);
        chevron.setColorFilter(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        chevron.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
        inner.addView(chevron, LayoutHelper.createLinear(24, 24, Gravity.CENTER_VERTICAL));

        card.addView(inner, LayoutHelper.createFrame(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        card.setOnClickListener(v -> {
            android.os.Bundle args = new android.os.Bundle();
            args.putLong("user_id", UserConfig.getInstance(currentAccount).getClientUserId());
            presentFragment(new ProfileActivity(args));
        });

        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cardParams.bottomMargin = AndroidUtilities.dp(16);
        contentLayout.addView(card, cardParams);
    }

    private void buildGhostModeCard(Context context) {
        boolean isActive = TonyConfig.INSTANCE.getPrivacy().isGhostModeActive();

        RoundedCardView card = new RoundedCardView(context);
        card.setCardColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        ghostToggleView = card;

        LinearLayout inner = new LinearLayout(context);
        inner.setOrientation(LinearLayout.HORIZONTAL);
        inner.setPadding(AndroidUtilities.dp(16), AndroidUtilities.dp(14),
            AndroidUtilities.dp(16), AndroidUtilities.dp(14));
        inner.setGravity(Gravity.CENTER_VERTICAL);

        // Ghost icon (decorative)
        TextView ghostIcon = new TextView(context);
        ghostIcon.setText("\uD83D\uDC7B"); // Ghost emoji
        ghostIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
        ghostIcon.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
        inner.addView(ghostIcon, LayoutHelper.createLinear(
            LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_VERTICAL, 0, 0, 12, 0));

        // Text
        LinearLayout textCol = new LinearLayout(context);
        textCol.setOrientation(LinearLayout.VERTICAL);

        TextView title = new TextView(context);
        title.setText("Ghost Mode");
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        title.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        title.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        textCol.addView(title, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 2));

        ghostStatusText = new TextView(context);
        ghostStatusText.setText(isActive ? "Active \u2014 hiding all activity" : "Off \u2014 normal visibility");
        ghostStatusText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
        ghostStatusText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        textCol.addView(ghostStatusText, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        inner.addView(textCol, new LinearLayout.LayoutParams(
            0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        // Toggle indicator
        TextView toggleText = new TextView(context);
        toggleText.setText(isActive ? "ON" : "OFF");
        toggleText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        toggleText.setTextColor(isActive ? TonyColors.primary()
            : Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        toggleText.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        toggleText.setTag("ghost_toggle_label");
        inner.addView(toggleText, LayoutHelper.createLinear(
            LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_VERTICAL));

        card.addView(inner, LayoutHelper.createFrame(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        // Switch accessibility semantics
        card.setAccessibilityDelegate(new View.AccessibilityDelegate() {
            @Override
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
                info.setClassName("android.widget.Switch");
                info.setCheckable(true);
                info.setChecked(TonyConfig.INSTANCE.getPrivacy().isGhostModeActive());
                info.setContentDescription("Ghost Mode, hide online status and read receipts");
            }
        });

        card.setOnClickListener(v -> {
            boolean current = TonyConfig.INSTANCE.getPrivacy().isGhostModeActive();
            TonyConfig.INSTANCE.getPrivacy().setGhostMode(!current);
            updateGhostState(true);
        });

        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cardParams.bottomMargin = AndroidUtilities.dp(16);
        contentLayout.addView(card, cardParams);
    }

    private void updateGhostState(boolean animate) {
        if (ghostStatusText == null || ghostToggleView == null) return;
        boolean isActive = TonyConfig.INSTANCE.getPrivacy().isGhostModeActive();
        ghostStatusText.setText(isActive ? "Active \u2014 hiding all activity" : "Off \u2014 normal visibility");

        // Update toggle label with optional color animation
        View inner = ((ViewGroup) ghostToggleView).getChildAt(0);
        if (inner instanceof ViewGroup) {
            TextView toggleLabel = inner.findViewWithTag("ghost_toggle_label");
            if (toggleLabel != null) {
                toggleLabel.setText(isActive ? "ON" : "OFF");

                int targetColor = isActive ? TonyColors.primary()
                    : Theme.getColor(Theme.key_windowBackgroundWhiteGrayText);

                if (animate) {
                    int fromColor = toggleLabel.getCurrentTextColor();
                    ValueAnimator anim = ValueAnimator.ofObject(new ArgbEvaluator(), fromColor, targetColor);
                    anim.setDuration(GHOST_ANIM_DURATION);
                    anim.addUpdateListener(a -> toggleLabel.setTextColor((int) a.getAnimatedValue()));
                    anim.start();
                } else {
                    toggleLabel.setTextColor(targetColor);
                }
            }
        }
    }

    private void buildSettingsGroup1(Context context) {
        RoundedCardView card = new RoundedCardView(context);
        card.setCardColor(Theme.getColor(Theme.key_windowBackgroundWhite));

        LinearLayout inner = new LinearLayout(context);
        inner.setOrientation(LinearLayout.VERTICAL);

        addSettingsItem(inner, context, R.drawable.msg_settings_old, TonyColors.aiAccent(),
            "AI Settings", "Providers, keys, features",
            v -> presentFragment(new AiSettingsActivity()), true);
        addSettingsItem(inner, context, R.drawable.msg_secret, TonyColors.success(),
            "Privacy & Ghost Mode", "Control your visibility",
            v -> presentFragment(new PrivacySettingsActivity()), true);
        addSettingsItem(inner, context, R.drawable.msg_notifications, TonyColors.error(),
            "Notifications", "Sounds, alerts, badges",
            v -> presentFragment(new NotificationsSettingsActivity()), false);

        card.addView(inner, LayoutHelper.createFrame(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cardParams.bottomMargin = AndroidUtilities.dp(12);
        contentLayout.addView(card, cardParams);
    }

    private void buildSettingsGroup2(Context context) {
        RoundedCardView card = new RoundedCardView(context);
        card.setCardColor(Theme.getColor(Theme.key_windowBackgroundWhite));

        LinearLayout inner = new LinearLayout(context);
        inner.setOrientation(LinearLayout.VERTICAL);

        addSettingsItem(inner, context, R.drawable.msg_discussion, TonyColors.blue(),
            "Chat Settings", "Themes, wallpapers, bubbles",
            v -> presentFragment(new org.telegram.ui.ThemeActivity(org.telegram.ui.ThemeActivity.THEME_TYPE_BASIC)), true);
        addSettingsItem(inner, context, R.drawable.msg_filled_datausage, TonyColors.purple(),
            "Data & Storage", "Network, cache, downloads",
            v -> presentFragment(new DataSettingsActivity()), true);
        addSettingsItem(inner, context, R.drawable.msg_folders, TonyColors.cyan(),
            "Chat Folders", "Organize conversations",
            v -> presentFragment(new FiltersSetupActivity()), false);

        card.addView(inner, LayoutHelper.createFrame(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cardParams.bottomMargin = AndroidUtilities.dp(12);
        contentLayout.addView(card, cardParams);
    }

    private void buildQuickActionsGroup(Context context) {
        RoundedCardView card = new RoundedCardView(context);
        card.setCardColor(Theme.getColor(Theme.key_windowBackgroundWhite));

        LinearLayout inner = new LinearLayout(context);
        inner.setOrientation(LinearLayout.VERTICAL);

        addSettingsItem(inner, context, R.drawable.msg_saved, TonyColors.blue(),
            "Saved Messages", "Your cloud storage",
            v -> {
                android.os.Bundle args = new android.os.Bundle();
                args.putLong("user_id", UserConfig.getInstance(currentAccount).getClientUserId());
                presentFragment(new org.telegram.ui.ChatActivity(args));
            }, true);
        addSettingsItem(inner, context, R.drawable.msg_invite, TonyColors.success(),
            "Invite Friends", "Share Tony Chat",
            v -> presentFragment(new org.telegram.ui.InviteContactsActivity()), false);

        card.addView(inner, LayoutHelper.createFrame(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cardParams.bottomMargin = AndroidUtilities.dp(12);
        contentLayout.addView(card, cardParams);
    }

    private void buildSettingsGroup3(Context context) {
        RoundedCardView card = new RoundedCardView(context);
        card.setCardColor(Theme.getColor(Theme.key_windowBackgroundWhite));

        LinearLayout inner = new LinearLayout(context);
        inner.setOrientation(LinearLayout.VERTICAL);

        addSettingsItem(inner, context, R.drawable.msg_language, TonyColors.violet(),
            "Language", "App display language",
            v -> presentFragment(new LanguageSelectActivity()), true);
        addSettingsItem(inner, context, R.drawable.msg_premium_speed, TonyColors.orange(),
            "Power Saving", "Reduce animations, data",
            v -> presentFragment(new LiteModeSettingsActivity()), true);
        addSettingsItem(inner, context, R.drawable.msg_info, TonyColors.primary(),
            "About Tony Chat", "Version, licenses, links",
            v -> presentFragment(new TonyAboutActivity()), false);

        card.addView(inner, LayoutHelper.createFrame(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cardParams.bottomMargin = AndroidUtilities.dp(12);
        contentLayout.addView(card, cardParams);
    }

    private void addSettingsItem(LinearLayout parent, Context context,
                                  int iconRes, int iconColor,
                                  String title, String subtitle,
                                  View.OnClickListener onClick, boolean showDivider) {
        LinearLayout row = new LinearLayout(context);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setPadding(AndroidUtilities.dp(14), AndroidUtilities.dp(12),
            AndroidUtilities.dp(14), AndroidUtilities.dp(12));
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setMinimumHeight(AndroidUtilities.dp(48));

        // Ripple on each row
        TonyColors.applyRipple(row, context, 0);

        // Colored icon (decorative)
        ImageView icon = new ImageView(context);
        icon.setImageResource(iconRes);
        icon.setColorFilter(new PorterDuffColorFilter(iconColor, PorterDuff.Mode.SRC_IN));
        icon.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
        row.addView(icon, LayoutHelper.createLinear(24, 24, Gravity.CENTER_VERTICAL, 0, 0, 14, 0));

        // Text column
        LinearLayout textCol = new LinearLayout(context);
        textCol.setOrientation(LinearLayout.VERTICAL);

        TextView titleView = new TextView(context);
        titleView.setText(title);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        titleView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        titleView.setSingleLine(true);
        textCol.addView(titleView, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 1));

        TextView subtitleView = new TextView(context);
        subtitleView.setText(subtitle);
        subtitleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        subtitleView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        subtitleView.setSingleLine(true);
        textCol.addView(subtitleView, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        row.addView(textCol, new LinearLayout.LayoutParams(
            0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        // Chevron (decorative)
        ImageView chevron = new ImageView(context);
        chevron.setImageResource(R.drawable.msg_arrowright);
        chevron.setColorFilter(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        chevron.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
        row.addView(chevron, LayoutHelper.createLinear(16, 16, Gravity.CENTER_VERTICAL));

        row.setOnClickListener(onClick);

        // Wrap in container with optional divider
        LinearLayout wrapper = new LinearLayout(context);
        wrapper.setOrientation(LinearLayout.VERTICAL);
        wrapper.addView(row, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        if (showDivider) {
            View divider = new View(context);
            divider.setBackgroundColor(Theme.getColor(Theme.key_divider));
            LinearLayout.LayoutParams divParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 1);
            divParams.leftMargin = AndroidUtilities.dp(52);
            wrapper.addView(divider, divParams);
        }

        parent.addView(wrapper, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
    }
}
