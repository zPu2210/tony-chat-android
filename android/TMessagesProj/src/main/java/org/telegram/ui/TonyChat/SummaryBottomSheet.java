package org.telegram.ui.TonyChat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.Theme;

/** Bottom sheet displaying an AI-generated summary of the current chat. */
public class SummaryBottomSheet extends BottomSheet {

    private final TextView titleView;
    private final TextView bodyView;
    private final TextView footerView;
    private final ProgressBar progressBar;
    private final LinearLayout contentLayout;
    private final TextView copyButton;
    private final TextView retryButton;
    private Runnable retryAction;

    public SummaryBottomSheet(Context context) {
        super(context, false);

        LinearLayout root = new LinearLayout(context);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(AndroidUtilities.dp(20), AndroidUtilities.dp(16),
            AndroidUtilities.dp(20), AndroidUtilities.dp(16));

        // Title
        titleView = new TextView(context);
        titleView.setText("\u2728 Chat Summary");
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        titleView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        titleView.setTypeface(titleView.getTypeface(), android.graphics.Typeface.BOLD);
        root.addView(titleView, new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // Progress
        progressBar = new ProgressBar(context);
        progressBar.setPadding(0, AndroidUtilities.dp(24), 0, AndroidUtilities.dp(24));
        root.addView(progressBar, new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // Scrollable body
        ScrollView scrollView = new ScrollView(context);
        scrollView.setVisibility(View.GONE);

        contentLayout = new LinearLayout(context);
        contentLayout.setOrientation(LinearLayout.VERTICAL);

        bodyView = new TextView(context);
        bodyView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        bodyView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        bodyView.setLineSpacing(AndroidUtilities.dp(4), 1f);
        bodyView.setPadding(0, AndroidUtilities.dp(12), 0, AndroidUtilities.dp(8));
        contentLayout.addView(bodyView, new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        footerView = new TextView(context);
        footerView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        footerView.setTextColor(Theme.getColor(Theme.key_dialogTextGray3));
        contentLayout.addView(footerView, new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        scrollView.addView(contentLayout, new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams scrollLp = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f);
        root.addView(scrollView, scrollLp);

        // Retry button (hidden by default)
        retryButton = new TextView(context);
        retryButton.setText("Tap to retry");
        retryButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        retryButton.setTextColor(Theme.getColor(Theme.key_dialogButton));
        retryButton.setGravity(Gravity.CENTER);
        retryButton.setPadding(0, AndroidUtilities.dp(16), 0, AndroidUtilities.dp(8));
        retryButton.setVisibility(View.GONE);
        retryButton.setOnClickListener(v -> {
            if (retryAction != null) retryAction.run();
        });
        root.addView(retryButton, new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // Copy button
        copyButton = new TextView(context);
        copyButton.setText("Copy");
        copyButton.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        copyButton.setTextColor(Theme.getColor(Theme.key_dialogButton));
        copyButton.setGravity(Gravity.CENTER);
        copyButton.setPadding(0, AndroidUtilities.dp(12), 0, 0);
        copyButton.setVisibility(View.GONE);
        copyButton.setOnClickListener(v -> {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboard != null && bodyView.getText() != null) {
                clipboard.setPrimaryClip(ClipData.newPlainText("Summary", bodyView.getText()));
                Toast.makeText(context, "Summary copied", Toast.LENGTH_SHORT).show();
            }
        });
        root.addView(copyButton, new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        setCustomView(root);
    }

    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        ((View) bodyView.getParent().getParent()).setVisibility(View.GONE);
        copyButton.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
    }

    public void showSummary(String summary, boolean fromCache) {
        progressBar.setVisibility(View.GONE);
        ((View) bodyView.getParent().getParent()).setVisibility(View.VISIBLE);
        bodyView.setText(summary);
        footerView.setText(fromCache ? "Cached summary" : "Fresh summary");
        copyButton.setVisibility(View.VISIBLE);
        retryButton.setVisibility(View.GONE);
    }

    public void showError(String message) {
        progressBar.setVisibility(View.GONE);
        ((View) bodyView.getParent().getParent()).setVisibility(View.VISIBLE);
        bodyView.setText(!TextUtils.isEmpty(message) ? message : "Couldn't generate summary.");
        footerView.setText("");
        copyButton.setVisibility(View.GONE);
        retryButton.setVisibility(View.VISIBLE);
    }

    public void setRetryAction(Runnable action) {
        this.retryAction = action;
    }
}
