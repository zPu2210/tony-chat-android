package org.telegram.ui.TonyChat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.net.Uri;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

/** About screen showing Tony Chat branding, version, credits, and links. */
public class TonyAboutActivity extends BaseFragment {

    @Override
    public View createView(Context context) {
        actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        actionBar.setAllowOverlayTitle(true);
        actionBar.setTitle("About Tony Chat");
        actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() {
            @Override
            public void onItemClick(int id) {
                if (id == -1) finishFragment();
            }
        });

        fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) fragmentView;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));

        ScrollView scrollView = new ScrollView(context);
        frameLayout.addView(scrollView, LayoutHelper.createFrame(
            LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT
        ));

        LinearLayout content = new LinearLayout(context);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setGravity(Gravity.CENTER_HORIZONTAL);
        content.setPadding(AndroidUtilities.dp(24), AndroidUtilities.dp(32),
            AndroidUtilities.dp(24), AndroidUtilities.dp(32));
        scrollView.addView(content, new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        // Logo
        ImageView logo = new ImageView(context);
        logo.setImageResource(R.drawable.msg_secret);
        logo.setColorFilter(Theme.getColor(Theme.key_chats_actionBackground));
        content.addView(logo, LayoutHelper.createLinear(80, 80, Gravity.CENTER, 0, 0, 0, 16));

        // App name
        TextView appName = new TextView(context);
        appName.setText("Tony Chat");
        appName.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
        appName.setTypeface(AndroidUtilities.bold());
        appName.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        appName.setGravity(Gravity.CENTER);
        content.addView(appName, LayoutHelper.createLinear(
            LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 0, 0, 0, 4
        ));

        // Tagline
        TextView tagline = new TextView(context);
        tagline.setText("Privacy + AI Messaging");
        tagline.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        tagline.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText2));
        tagline.setGravity(Gravity.CENTER);
        content.addView(tagline, LayoutHelper.createLinear(
            LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 0, 0, 0, 8
        ));

        // Version
        TextView version = new TextView(context);
        version.setText(getVersionString());
        version.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        version.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText3));
        version.setGravity(Gravity.CENTER);
        content.addView(version, LayoutHelper.createLinear(
            LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 0, 0, 0, 24
        ));

        // Links section
        addSectionHeader(content, context, "Links");
        addLinkRow(content, context, "Source Code", "https://github.com/zPu2210/tony-chat-android");
        addLinkRow(content, context, "Report Issue", "https://github.com/zPu2210/tony-chat-android/issues");
        addLinkRow(content, context, "Send Feedback", "mailto:tonychat.feedback@gmail.com");

        addSpacer(content, context, 16);

        // Credits section
        addSectionHeader(content, context, "Credits");
        addInfoRow(content, context, "Based on Telegram for Android");
        addInfoRow(content, context, "Originally forked from Nagram");
        addInfoRow(content, context, "Licensed under GPL-3.0");

        addSpacer(content, context, 16);

        // Disclaimer
        TextView disclaimer = new TextView(context);
        disclaimer.setText("Tony Chat is not affiliated with Telegram.");
        disclaimer.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        disclaimer.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText3));
        disclaimer.setGravity(Gravity.CENTER);
        content.addView(disclaimer, LayoutHelper.createLinear(
            LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 0, 16, 0, 0
        ));

        return fragmentView;
    }

    private String getVersionString() {
        try {
            PackageInfo info = ApplicationLoader.applicationContext.getPackageManager()
                .getPackageInfo(ApplicationLoader.applicationContext.getPackageName(), 0);
            return "v" + info.versionName + " (build " + info.versionCode + ")";
        } catch (Exception e) {
            return "v1.0.0";
        }
    }

    private void addSectionHeader(LinearLayout parent, Context context, String text) {
        TextView header = new TextView(context);
        header.setText(text);
        header.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
        header.setTypeface(AndroidUtilities.bold());
        header.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueHeader));
        header.setPadding(AndroidUtilities.dp(4), 0, 0, AndroidUtilities.dp(8));
        parent.addView(header, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 0
        ));
    }

    private void addLinkRow(LinearLayout parent, Context context, String label, String url) {
        TextView row = new TextView(context);
        row.setText(label);
        row.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        row.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4));
        row.setPadding(AndroidUtilities.dp(4), AndroidUtilities.dp(10),
            AndroidUtilities.dp(4), AndroidUtilities.dp(10));
        row.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(intent);
            } catch (Exception ignored) {}
        });
        parent.addView(row, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 0
        ));
    }

    private void addInfoRow(LinearLayout parent, Context context, String text) {
        TextView row = new TextView(context);
        row.setText(text);
        row.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        row.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        row.setPadding(AndroidUtilities.dp(4), AndroidUtilities.dp(8),
            AndroidUtilities.dp(4), AndroidUtilities.dp(8));
        parent.addView(row, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 0
        ));
    }

    private void addSpacer(LinearLayout parent, Context context, int heightDp) {
        View spacer = new View(context);
        parent.addView(spacer, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, heightDp, 0, 0, 0, 0
        ));
    }
}
