package org.telegram.ui.TonyChat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

import com.tonychat.ai.AiFeatureType;
import com.tonychat.ai.AiManagerBridge;
import com.tonychat.ai.AiResponse;
import com.tonychat.ai.consent.AiConsentManager;

/**
 * Standalone AI Writer — 8 style pills + text input + result area + Copy.
 * Stateless: back button = blank state on re-entry.
 */
public class AiWriterFragment extends BaseFragment {

    private static final String[][] PILLS = {
            {"Fix Grammar", "quick"},
            {"Professional", "quick"},
            {"Casual", "quick"},
            {"Polite", "quick"},
            {"Email", "template"},
            {"Greeting", "template"},
            {"Meeting", "template"},
            {"Thanks", "template"},
    };

    private static final String[] TEMPLATES = {
            "", "", "", "",
            "Dear [name], I am writing to ",
            "Hi [name], I hope you're doing well. ",
            "I'd like to schedule a meeting to discuss ",
            "Thank you for ",
    };

    private EditText inputField;
    private TextView resultText;
    private ProgressBar progressBar;
    private TextView actionBtn;
    private LinearLayout resultContainer;
    private String selectedPill = "Fix Grammar";
    private LinearLayout pillContainer;
    private boolean isLoading = false;

    @Override
    public View createView(Context context) {
        actionBar.setBackButtonImage(org.telegram.messenger.R.drawable.ic_ab_back);
        actionBar.setTitle("AI Writer");
        actionBar.setAllowOverlayTitle(true);
        actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() {
            @Override
            public void onItemClick(int id) {
                if (id == -1) finishFragment();
            }
        });

        fragmentView = new FrameLayout(context);
        FrameLayout root = (FrameLayout) fragmentView;
        root.setBackgroundColor(TonyColors.background());

        ScrollView scrollView = new ScrollView(context);
        root.addView(scrollView, LayoutHelper.createFrame(
                LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));

        LinearLayout content = new LinearLayout(context);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(16), dp(12), dp(16), dp(100));
        scrollView.addView(content, new ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // Pill toolbar
        HorizontalScrollView pillScroll = new HorizontalScrollView(context);
        pillScroll.setHorizontalScrollBarEnabled(false);
        pillScroll.setClipToPadding(false);
        content.addView(pillScroll, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 16));

        pillContainer = new LinearLayout(context);
        pillContainer.setOrientation(LinearLayout.HORIZONTAL);
        pillScroll.addView(pillContainer);

        for (int i = 0; i < PILLS.length; i++) {
            addPill(context, pillContainer, PILLS[i][0], i);
        }

        // Input label
        TextView inputLabel = new TextView(context);
        inputLabel.setText("Your text");
        inputLabel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        inputLabel.setTextColor(TonyColors.textSecondary());
        inputLabel.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        content.addView(inputLabel, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 8));

        // Input field
        inputField = new EditText(context);
        inputField.setHint("Type or paste your text here...");
        inputField.setHintTextColor(TonyColors.textTertiary());
        inputField.setTextColor(TonyColors.textPrimary());
        inputField.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        inputField.setMinLines(4);
        inputField.setMaxLines(8);
        inputField.setGravity(Gravity.TOP | Gravity.START);
        inputField.setPadding(dp(14), dp(12), dp(14), dp(12));
        GradientDrawable inputBg = new GradientDrawable();
        inputBg.setShape(GradientDrawable.RECTANGLE);
        inputBg.setCornerRadius(dp(12));
        inputBg.setColor(TonyColors.backgroundSecondary());
        inputField.setBackground(inputBg);
        content.addView(inputField, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 16));

        // Action button
        actionBtn = new TextView(context);
        actionBtn.setText("Transform");
        actionBtn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        actionBtn.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        actionBtn.setTextColor(TonyColors.onPrimary());
        actionBtn.setGravity(Gravity.CENTER);
        actionBtn.setPadding(0, dp(14), 0, dp(14));
        GradientDrawable btnBg = new GradientDrawable();
        btnBg.setShape(GradientDrawable.RECTANGLE);
        btnBg.setCornerRadius(dp(12));
        btnBg.setColor(TonyColors.primary());
        actionBtn.setBackground(btnBg);
        actionBtn.setOnClickListener(v -> onTransform());
        content.addView(actionBtn, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 20));

        // Progress
        progressBar = new ProgressBar(context);
        progressBar.setVisibility(android.view.View.GONE);
        content.addView(progressBar, LayoutHelper.createLinear(
                LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                Gravity.CENTER_HORIZONTAL, 0, 0, 0, 12));

        // Result container
        resultContainer = new LinearLayout(context);
        resultContainer.setOrientation(LinearLayout.VERTICAL);
        resultContainer.setVisibility(android.view.View.GONE);
        GradientDrawable resultBg = new GradientDrawable();
        resultBg.setShape(GradientDrawable.RECTANGLE);
        resultBg.setCornerRadius(dp(12));
        resultBg.setColor(TonyColors.backgroundSecondary());
        resultContainer.setBackground(resultBg);
        resultContainer.setPadding(dp(14), dp(12), dp(14), dp(12));
        content.addView(resultContainer, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        // Result label
        TextView resultLabel = new TextView(context);
        resultLabel.setText("Result");
        resultLabel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
        resultLabel.setTextColor(TonyColors.textTertiary());
        resultLabel.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        resultContainer.addView(resultLabel, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 8));

        // Result text
        resultText = new TextView(context);
        resultText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        resultText.setTextColor(TonyColors.textPrimary());
        resultText.setTextIsSelectable(true);
        resultContainer.addView(resultText, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 12));

        // Copy button
        TextView copyBtn = new TextView(context);
        copyBtn.setText("Copy");
        copyBtn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        copyBtn.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        copyBtn.setTextColor(TonyColors.onPrimary());
        copyBtn.setGravity(Gravity.CENTER);
        copyBtn.setPadding(dp(24), dp(10), dp(24), dp(10));
        GradientDrawable copyBg = new GradientDrawable();
        copyBg.setShape(GradientDrawable.RECTANGLE);
        copyBg.setCornerRadius(dp(8));
        copyBg.setColor(TonyColors.primary());
        copyBtn.setBackground(copyBg);
        copyBtn.setOnClickListener(v -> copyResult());
        resultContainer.addView(copyBtn, LayoutHelper.createLinear(
                LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.END));

        return fragmentView;
    }

    private void addPill(Context context, LinearLayout parent, String label, int index) {
        TextView pill = new TextView(context);
        pill.setText(label);
        pill.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
        pill.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        pill.setPadding(dp(14), dp(8), dp(14), dp(8));
        pill.setGravity(Gravity.CENTER);
        pill.setTag(label);

        updatePillStyle(pill, label.equals(selectedPill));

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.rightMargin = dp(8);
        parent.addView(pill, lp);

        pill.setOnClickListener(v -> {
            selectedPill = label;
            refreshPills();
            // If template pill, pre-fill input
            if ("template".equals(PILLS[index][1]) && TextUtils.isEmpty(inputField.getText())) {
                inputField.setText(TEMPLATES[index]);
                inputField.setSelection(inputField.getText().length());
            }
        });
    }

    private void refreshPills() {
        for (int i = 0; i < pillContainer.getChildCount(); i++) {
            TextView pill = (TextView) pillContainer.getChildAt(i);
            updatePillStyle(pill, selectedPill.equals(pill.getTag()));
        }
    }

    private void updatePillStyle(TextView pill, boolean active) {
        GradientDrawable bg = new GradientDrawable();
        bg.setShape(GradientDrawable.RECTANGLE);
        bg.setCornerRadius(dp(16));
        if (active) {
            bg.setColor(TonyColors.primary());
            pill.setTextColor(TonyColors.onPrimary());
        } else {
            bg.setColor(TonyColors.backgroundSecondary());
            pill.setTextColor(TonyColors.textSecondary());
        }
        pill.setBackground(bg);
    }

    private void onTransform() {
        String text = inputField.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(getParentActivity(), "Enter some text first", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isLoading) return;

        // Hide keyboard
        InputMethodManager imm = (InputMethodManager) getParentActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) imm.hideSoftInputFromWindow(inputField.getWindowToken(), 0);

        // Check consent
        if (!AiConsentManager.INSTANCE.hasConsent(AiFeatureType.AI_WRITER)) {
            AiConsentDialog.show(getParentActivity(), AiFeatureType.AI_WRITER, false, granted -> {
                if (granted) executeTransform(text);
            });
            return;
        }
        executeTransform(text);
    }

    private void executeTransform(String text) {
        setLoading(true);
        AiManagerBridge.INSTANCE.standaloneRewrite(text, selectedPill, response -> {
            setLoading(false);
            if (response instanceof AiResponse.Success) {
                String result = ((AiResponse.Success<String>) response).getData();
                showResult(result);
            } else if (response instanceof AiResponse.RateLimited) {
                Toast.makeText(getParentActivity(),
                        "Daily limit reached. Try again tomorrow.", Toast.LENGTH_LONG).show();
            } else if (response instanceof AiResponse.Unavailable) {
                Toast.makeText(getParentActivity(),
                        "AI service unavailable. Check AI Settings.", Toast.LENGTH_LONG).show();
            } else if (response instanceof AiResponse.Error) {
                Toast.makeText(getParentActivity(),
                        ((AiResponse.Error<String>) response).getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setLoading(boolean loading) {
        isLoading = loading;
        progressBar.setVisibility(loading ? android.view.View.VISIBLE : android.view.View.GONE);
        actionBtn.setEnabled(!loading);
        actionBtn.setAlpha(loading ? 0.5f : 1f);
    }

    private void showResult(String text) {
        resultContainer.setVisibility(android.view.View.VISIBLE);
        resultText.setText(text);
    }

    private void copyResult() {
        if (getParentActivity() == null) return;
        CharSequence text = resultText.getText();
        if (TextUtils.isEmpty(text)) return;
        ClipboardManager cm = (ClipboardManager) getParentActivity()
                .getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText("AI Writer", text));
        Toast.makeText(getParentActivity(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    private static int dp(float v) {
        return AndroidUtilities.dp(v);
    }
}
