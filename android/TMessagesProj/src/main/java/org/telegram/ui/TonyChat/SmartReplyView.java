package org.telegram.ui.TonyChat;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.Theme;

import java.util.List;

/** Horizontal chip bar showing AI-generated reply suggestions above the compose area. */
public class SmartReplyView extends LinearLayout {

    public interface OnSuggestionClick {
        void onClick(String text);
    }

    private final LinearLayout chipsContainer;
    private final HorizontalScrollView scrollView;
    private final ProgressBar loadingIndicator;
    private final ImageView dismissButton;
    private OnSuggestionClick listener;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable autoHideRunnable;

    private static final long AUTO_HIDE_DELAY = 30_000L;
    private static final long ANIM_DURATION = 200L;

    public SmartReplyView(Context context) {
        super(context);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        setPadding(AndroidUtilities.dp(8), AndroidUtilities.dp(6), AndroidUtilities.dp(4), AndroidUtilities.dp(6));
        setVisibility(GONE);

        // Scroll view wrapping chips
        scrollView = new HorizontalScrollView(context);
        scrollView.setHorizontalScrollBarEnabled(false);
        scrollView.setOverScrollMode(OVER_SCROLL_NEVER);

        chipsContainer = new LinearLayout(context);
        chipsContainer.setOrientation(HORIZONTAL);
        chipsContainer.setGravity(Gravity.CENTER_VERTICAL);
        scrollView.addView(chipsContainer, new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        addView(scrollView, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        // Loading indicator (shown while generating)
        loadingIndicator = new ProgressBar(context, null, android.R.attr.progressBarStyleSmall);
        loadingIndicator.setVisibility(GONE);
        addView(loadingIndicator, new LinearLayout.LayoutParams(
            AndroidUtilities.dp(24), AndroidUtilities.dp(24)));

        // Dismiss button
        dismissButton = new ImageView(context);
        dismissButton.setScaleType(ImageView.ScaleType.CENTER);
        dismissButton.setColorFilter(Theme.getColor(Theme.key_chat_messagePanelHint));
        dismissButton.setContentDescription("Dismiss suggestions");
        // Use a simple "X" via text drawable
        TextView xText = new TextView(context);
        xText.setText("\u2715");
        xText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        xText.setTextColor(Theme.getColor(Theme.key_chat_messagePanelHint));
        xText.setGravity(Gravity.CENTER);
        xText.setPadding(AndroidUtilities.dp(4), 0, AndroidUtilities.dp(4), 0);
        xText.setOnClickListener(v -> hideSuggestions());
        addView(xText, new LinearLayout.LayoutParams(
            AndroidUtilities.dp(28), ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void setOnSuggestionClick(OnSuggestionClick listener) {
        this.listener = listener;
    }

    public void showLoading() {
        chipsContainer.removeAllViews();
        loadingIndicator.setVisibility(VISIBLE);
        scrollView.setVisibility(GONE);
        animateIn();
    }

    public void showSuggestions(List<String> suggestions) {
        loadingIndicator.setVisibility(GONE);
        scrollView.setVisibility(VISIBLE);
        chipsContainer.removeAllViews();

        if (suggestions == null || suggestions.isEmpty()) {
            hideSuggestions();
            return;
        }

        for (String suggestion : suggestions) {
            if (suggestion == null || suggestion.trim().isEmpty()) continue;
            String trimmed = suggestion.trim();
            // Cap display length
            String display = trimmed.length() > 80 ? trimmed.substring(0, 77) + "..." : trimmed;
            View chip = createChip(getContext(), display, trimmed);
            chipsContainer.addView(chip);
        }

        animateIn();
        scheduleAutoHide();
    }

    public void hideSuggestions() {
        cancelAutoHide();
        if (getVisibility() == VISIBLE) {
            animate().translationY(getHeight())
                .alpha(0f)
                .setDuration(ANIM_DURATION)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        setVisibility(GONE);
                        setTranslationY(0);
                        setAlpha(1f);
                        chipsContainer.removeAllViews();
                    }
                }).start();
        } else {
            setVisibility(GONE);
            chipsContainer.removeAllViews();
        }
    }

    private void animateIn() {
        if (getVisibility() == VISIBLE) return;
        setAlpha(0f);
        setTranslationY(AndroidUtilities.dp(20));
        setVisibility(VISIBLE);
        animate().translationY(0).alpha(1f).setDuration(ANIM_DURATION).setListener(null).start();
    }

    private void scheduleAutoHide() {
        cancelAutoHide();
        autoHideRunnable = this::hideSuggestions;
        handler.postDelayed(autoHideRunnable, AUTO_HIDE_DELAY);
    }

    private void cancelAutoHide() {
        if (autoHideRunnable != null) {
            handler.removeCallbacks(autoHideRunnable);
            autoHideRunnable = null;
        }
    }

    private View createChip(Context context, String displayText, String fullText) {
        TextView chip = new TextView(context);
        chip.setText("\u2728 " + displayText);
        chip.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        chip.setTextColor(Theme.getColor(Theme.key_chat_messagePanelText));
        chip.setSingleLine(true);
        chip.setMaxLines(1);
        chip.setPadding(
            AndroidUtilities.dp(12), AndroidUtilities.dp(8),
            AndroidUtilities.dp(12), AndroidUtilities.dp(8));

        GradientDrawable bg = new GradientDrawable();
        bg.setCornerRadius(AndroidUtilities.dp(16));
        bg.setColor(Theme.getColor(Theme.key_chat_messagePanelBackground));
        bg.setStroke(AndroidUtilities.dp(1), Theme.getColor(Theme.key_chat_messagePanelShadow));
        chip.setBackground(bg);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(AndroidUtilities.dp(4), 0, AndroidUtilities.dp(4), 0);
        chip.setLayoutParams(lp);

        chip.setOnClickListener(v -> {
            if (listener != null) listener.onClick(fullText);
            hideSuggestions();
        });

        return chip;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelAutoHide();
    }
}
