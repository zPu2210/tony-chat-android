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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.Components.LayoutHelper;

import com.tonychat.ai.AiFeatureType;
import com.tonychat.ai.AiManagerBridge;
import com.tonychat.ai.AiResponse;
import com.tonychat.ai.consent.AiConsentManager;

/**
 * Standalone AI Translator — language pickers + swap + source input + result + Copy.
 * Stateless: back button = blank state on re-entry.
 */
public class AiTranslatorFragment extends BaseFragment {

    private static final String[][] LANGUAGES = {
            {"auto", "Auto-detect"},
            {"en", "English"},
            {"ko", "Korean"},
            {"vi", "Vietnamese"},
            {"ja", "Japanese"},
            {"zh", "Chinese"},
            {"es", "Spanish"},
            {"fr", "French"},
            {"de", "German"},
            {"pt", "Portuguese"},
            {"ru", "Russian"},
            {"ar", "Arabic"},
            {"hi", "Hindi"},
            {"th", "Thai"},
            {"id", "Indonesian"},
            {"it", "Italian"},
    };

    private String sourceLang = "auto";
    private String targetLang = "en";
    private TextView sourceBtn;
    private TextView targetBtn;
    private EditText inputField;
    private TextView resultText;
    private LinearLayout resultContainer;
    private ProgressBar progressBar;
    private TextView translateBtn;
    private boolean isLoading = false;

    @Override
    public View createView(Context context) {
        actionBar.setBackButtonImage(org.telegram.messenger.R.drawable.ic_ab_back);
        actionBar.setTitle("AI Translator");
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

        // Language picker row
        LinearLayout langRow = new LinearLayout(context);
        langRow.setOrientation(LinearLayout.HORIZONTAL);
        langRow.setGravity(Gravity.CENTER_VERTICAL);
        content.addView(langRow, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 16));

        // Source language button
        sourceBtn = createLangButton(context, "Auto-detect");
        sourceBtn.setOnClickListener(v -> showLanguagePicker(true));
        langRow.addView(sourceBtn, new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        // Swap button
        TextView swapBtn = new TextView(context);
        swapBtn.setText("\u21C4"); // ⇄ arrows
        swapBtn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
        swapBtn.setTextColor(TonyColors.primary());
        swapBtn.setGravity(Gravity.CENTER);
        swapBtn.setPadding(dp(12), dp(8), dp(12), dp(8));
        swapBtn.setOnClickListener(v -> swapLanguages());
        langRow.addView(swapBtn, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // Target language button
        targetBtn = createLangButton(context, "English");
        targetBtn.setOnClickListener(v -> showLanguagePicker(false));
        langRow.addView(targetBtn, new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        // Input label
        TextView inputLabel = new TextView(context);
        inputLabel.setText("Text to translate");
        inputLabel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        inputLabel.setTextColor(TonyColors.textSecondary());
        inputLabel.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        content.addView(inputLabel, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 8));

        // Input field
        inputField = new EditText(context);
        inputField.setHint("Enter text to translate...");
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

        // Translate button
        translateBtn = new TextView(context);
        translateBtn.setText("Translate");
        translateBtn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        translateBtn.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        translateBtn.setTextColor(TonyColors.onPrimary());
        translateBtn.setGravity(Gravity.CENTER);
        translateBtn.setPadding(0, dp(14), 0, dp(14));
        GradientDrawable btnBg = new GradientDrawable();
        btnBg.setShape(GradientDrawable.RECTANGLE);
        btnBg.setCornerRadius(dp(12));
        btnBg.setColor(TonyColors.primary());
        translateBtn.setBackground(btnBg);
        translateBtn.setOnClickListener(v -> onTranslate());
        content.addView(translateBtn, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 20));

        // Progress
        progressBar = new ProgressBar(context);
        progressBar.setVisibility(View.GONE);
        content.addView(progressBar, LayoutHelper.createLinear(
                LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT,
                Gravity.CENTER_HORIZONTAL, 0, 0, 0, 12));

        // Result container
        resultContainer = new LinearLayout(context);
        resultContainer.setOrientation(LinearLayout.VERTICAL);
        resultContainer.setVisibility(View.GONE);
        GradientDrawable resultBg = new GradientDrawable();
        resultBg.setShape(GradientDrawable.RECTANGLE);
        resultBg.setCornerRadius(dp(12));
        resultBg.setColor(TonyColors.backgroundSecondary());
        resultContainer.setBackground(resultBg);
        resultContainer.setPadding(dp(14), dp(12), dp(14), dp(12));
        content.addView(resultContainer, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        TextView resultLabel = new TextView(context);
        resultLabel.setText("Translation");
        resultLabel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
        resultLabel.setTextColor(TonyColors.textTertiary());
        resultLabel.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        resultContainer.addView(resultLabel, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 8));

        resultText = new TextView(context);
        resultText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        resultText.setTextColor(TonyColors.textPrimary());
        resultText.setTextIsSelectable(true);
        resultContainer.addView(resultText, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 12));

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

    private TextView createLangButton(Context context, String label) {
        TextView btn = new TextView(context);
        btn.setText(label);
        btn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        btn.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        btn.setTextColor(TonyColors.textPrimary());
        btn.setGravity(Gravity.CENTER);
        btn.setPadding(dp(12), dp(10), dp(12), dp(10));
        GradientDrawable bg = new GradientDrawable();
        bg.setShape(GradientDrawable.RECTANGLE);
        bg.setCornerRadius(dp(10));
        bg.setColor(TonyColors.backgroundSecondary());
        btn.setBackground(bg);
        return btn;
    }

    private void showLanguagePicker(boolean isSource) {
        if (getParentActivity() == null) return;
        int startIdx = isSource ? 0 : 1; // skip "auto" for target
        String[] names = new String[LANGUAGES.length - startIdx];
        for (int i = startIdx; i < LANGUAGES.length; i++) {
            names[i - startIdx] = LANGUAGES[i][1];
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getParentActivity());
        builder.setTitle(isSource ? "From" : "To");
        builder.setItems(names, (dialog, which) -> {
            int idx = which + startIdx;
            if (isSource) {
                sourceLang = LANGUAGES[idx][0];
                sourceBtn.setText(LANGUAGES[idx][1]);
            } else {
                targetLang = LANGUAGES[idx][0];
                targetBtn.setText(LANGUAGES[idx][1]);
            }
        });
        builder.show();
    }

    private void swapLanguages() {
        if ("auto".equals(sourceLang)) return; // can't swap auto-detect
        String tmpCode = sourceLang;
        String tmpName = sourceBtn.getText().toString();
        sourceLang = targetLang;
        sourceBtn.setText(targetBtn.getText());
        targetLang = tmpCode;
        targetBtn.setText(tmpName);
    }

    private void onTranslate() {
        String text = inputField.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(getParentActivity(), "Enter text to translate", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isLoading) return;

        InputMethodManager imm = (InputMethodManager) getParentActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) imm.hideSoftInputFromWindow(inputField.getWindowToken(), 0);

        if (!AiConsentManager.INSTANCE.hasConsent(AiFeatureType.TRANSLATE)) {
            AiConsentDialog.show(getParentActivity(), AiFeatureType.TRANSLATE, false, granted -> {
                if (granted) executeTranslate(text);
            });
            return;
        }
        executeTranslate(text);
    }

    private void executeTranslate(String text) {
        setLoading(true);
        String from = "auto".equals(sourceLang) ? "" : sourceLang;
        AiManagerBridge.INSTANCE.translate(text, from, targetLang, response -> {
            setLoading(false);
            if (response instanceof AiResponse.Success) {
                String result = ((AiResponse.Success<String>) response).getData();
                showResult(result);
            } else if (response instanceof AiResponse.RateLimited) {
                Toast.makeText(getParentActivity(),
                        "Rate limited. Try again later.", Toast.LENGTH_LONG).show();
            } else if (response instanceof AiResponse.Unavailable) {
                Toast.makeText(getParentActivity(),
                        "Translation unavailable. Check AI Settings.", Toast.LENGTH_LONG).show();
            } else if (response instanceof AiResponse.Error) {
                Toast.makeText(getParentActivity(),
                        ((AiResponse.Error<String>) response).getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setLoading(boolean loading) {
        isLoading = loading;
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        translateBtn.setEnabled(!loading);
        translateBtn.setAlpha(loading ? 0.5f : 1f);
    }

    private void showResult(String text) {
        resultContainer.setVisibility(View.VISIBLE);
        resultText.setText(text);
    }

    private void copyResult() {
        if (getParentActivity() == null) return;
        CharSequence text = resultText.getText();
        if (TextUtils.isEmpty(text)) return;
        ClipboardManager cm = (ClipboardManager) getParentActivity()
                .getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText("Translation", text));
        Toast.makeText(getParentActivity(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
    }

    private static int dp(float v) {
        return AndroidUtilities.dp(v);
    }
}
