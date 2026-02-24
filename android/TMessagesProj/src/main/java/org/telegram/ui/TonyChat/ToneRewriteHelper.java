package org.telegram.ui.TonyChat;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.Theme;

import com.tonychat.ai.AiFeatureType;
import com.tonychat.ai.AiManagerBridge;
import com.tonychat.ai.AiResponse;
import com.tonychat.ai.ToneStyle;
import com.tonychat.ai.config.AiConfig;
import com.tonychat.ai.consent.AiConsentManager;

/**
 * Shows a popup with tone options (Formal, Casual, Playful, Brief, Detailed).
 * Calls AiManager to rewrite text, then delivers result via callback.
 */
public class ToneRewriteHelper {

    public interface RewriteCallback {
        void onRewritten(String newText);
    }

    private PopupWindow popup;
    private String originalText;

    public void showTonePopup(Context context, View anchor, String text, RewriteCallback callback) {
        if (context == null || text == null || text.trim().isEmpty()) return;
        originalText = text;

        // Check consent
        if (!AiConsentManager.INSTANCE.hasConsent(AiFeatureType.TONE_REWRITE)) {
            boolean isOnDevice = AiConfig.INSTANCE.getPreferOnDevice();
            AiConsentDialog.show(context, AiFeatureType.TONE_REWRITE, isOnDevice, granted -> {
                if (granted) {
                    AiConfig.INSTANCE.setFeatureEnabled(AiFeatureType.TONE_REWRITE, true);
                    showPopupInternal(context, anchor, text, callback);
                }
            });
            return;
        }

        if (!AiConfig.INSTANCE.isFeatureEnabled(AiFeatureType.TONE_REWRITE)) {
            Toast.makeText(context, "Enable Tone Rewrite in AI Settings", Toast.LENGTH_SHORT).show();
            return;
        }

        showPopupInternal(context, anchor, text, callback);
    }

    private void showPopupInternal(Context context, View anchor, String text, RewriteCallback callback) {
        dismiss();

        LinearLayout root = new LinearLayout(context);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(AndroidUtilities.dp(12), AndroidUtilities.dp(8),
            AndroidUtilities.dp(12), AndroidUtilities.dp(8));

        GradientDrawable bg = new GradientDrawable();
        bg.setCornerRadius(AndroidUtilities.dp(12));
        bg.setColor(Theme.getColor(Theme.key_dialogBackground));
        bg.setStroke(AndroidUtilities.dp(1), Theme.getColor(Theme.key_dialogGrayLine));
        root.setBackground(bg);
        root.setElevation(AndroidUtilities.dp(4));

        // Title
        TextView title = new TextView(context);
        title.setText("\u2728 Rewrite Tone");
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        title.setTextColor(Theme.getColor(Theme.key_dialogTextGray3));
        title.setPadding(AndroidUtilities.dp(4), 0, 0, AndroidUtilities.dp(4));
        root.addView(title, new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        ToneStyle[] tones = ToneStyle.values();
        String[] labels = {"Formal", "Casual", "Playful", "Brief", "Detailed"};

        for (int i = 0; i < tones.length; i++) {
            ToneStyle tone = tones[i];
            String label = i < labels.length ? labels[i] : tone.name();

            LinearLayout row = new LinearLayout(context);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER_VERTICAL);
            row.setPadding(AndroidUtilities.dp(8), AndroidUtilities.dp(10),
                AndroidUtilities.dp(8), AndroidUtilities.dp(10));

            TextView textView = new TextView(context);
            textView.setText(label);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
            row.addView(textView, new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

            ProgressBar spinner = new ProgressBar(context, null, android.R.attr.progressBarStyleSmall);
            spinner.setVisibility(View.GONE);
            row.addView(spinner, new LinearLayout.LayoutParams(
                AndroidUtilities.dp(20), AndroidUtilities.dp(20)));

            row.setOnClickListener(v -> {
                spinner.setVisibility(View.VISIBLE);
                textView.setAlpha(0.5f);
                // Disable all rows while loading
                for (int j = 0; j < root.getChildCount(); j++) {
                    root.getChildAt(j).setEnabled(false);
                }
                AiManagerBridge.INSTANCE.rewriteTone(text, tone, result -> {
                    spinner.setVisibility(View.GONE);
                    textView.setAlpha(1f);
                    dismiss();
                    if (result instanceof AiResponse.Success) {
                        String rewritten = ((AiResponse.Success<String>) result).getData();
                        if (rewritten != null && !rewritten.trim().isEmpty()) {
                            callback.onRewritten(rewritten);
                        }
                    } else if (result instanceof AiResponse.Error) {
                        Toast.makeText(context,
                            "Rewrite failed: " + ((AiResponse.Error<String>) result).getMessage(),
                            Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context,
                            "Rewrite unavailable. Check API key in AI Settings.",
                            Toast.LENGTH_SHORT).show();
                    }
                });
            });

            root.addView(row, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        popup = new PopupWindow(root, AndroidUtilities.dp(180),
            ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popup.setOutsideTouchable(true);
        popup.setAnimationStyle(android.R.style.Animation_Dialog);

        // Show above anchor
        int[] loc = new int[2];
        anchor.getLocationOnScreen(loc);
        popup.showAtLocation(anchor, Gravity.NO_GRAVITY,
            loc[0], loc[1] - AndroidUtilities.dp(250));
    }

    public void dismiss() {
        if (popup != null && popup.isShowing()) {
            popup.dismiss();
        }
        popup = null;
    }

    public String getOriginalText() {
        return originalText;
    }
}
