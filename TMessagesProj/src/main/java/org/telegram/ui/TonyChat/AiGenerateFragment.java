package org.telegram.ui.TonyChat;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.Components.LayoutHelper;

import com.tonychat.ai.AiFeatureType;
import com.tonychat.ai.AiManagerBridge;
import com.tonychat.ai.ImageGenerationResponse;
import com.tonychat.ai.consent.AiConsentManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * AI Generate — text prompt input -> ClipDrop text-to-image -> result -> Save.
 * Different from BaseImageToolFragment: no photo picker, uses text prompt instead.
 */
public class AiGenerateFragment extends BaseFragment {

    private EditText promptField;
    private ImageView resultImage;
    private LinearLayout resultContainer;
    private ProgressBar progressBar;
    private TextView generateBtn;
    private File resultFile;
    private boolean isLoading = false;

    @Override
    public View createView(Context context) {
        actionBar.setBackButtonImage(org.telegram.messenger.R.drawable.ic_ab_back);
        actionBar.setTitle("AI Generate");
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

        // Prompt label
        TextView promptLabel = new TextView(context);
        promptLabel.setText("Describe the image you want");
        promptLabel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        promptLabel.setTextColor(TonyColors.textSecondary());
        promptLabel.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        content.addView(promptLabel, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 8));

        // Prompt input
        promptField = new EditText(context);
        promptField.setHint("A sunset over mountains with a lake...");
        promptField.setHintTextColor(TonyColors.textTertiary());
        promptField.setTextColor(TonyColors.textPrimary());
        promptField.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        promptField.setMinLines(3);
        promptField.setMaxLines(6);
        promptField.setGravity(Gravity.TOP | Gravity.START);
        promptField.setPadding(dp(14), dp(12), dp(14), dp(12));
        GradientDrawable inputBg = new GradientDrawable();
        inputBg.setShape(GradientDrawable.RECTANGLE);
        inputBg.setCornerRadius(dp(12));
        inputBg.setColor(TonyColors.backgroundSecondary());
        promptField.setBackground(inputBg);
        content.addView(promptField, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 4));

        // Character counter hint
        TextView charHint = new TextView(context);
        charHint.setText("Max 1000 characters");
        charHint.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        charHint.setTextColor(TonyColors.textTertiary());
        content.addView(charHint, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 16));

        // Generate button
        generateBtn = new TextView(context);
        generateBtn.setText("Generate Image");
        generateBtn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        generateBtn.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        generateBtn.setTextColor(TonyColors.onPrimary());
        generateBtn.setGravity(Gravity.CENTER);
        generateBtn.setPadding(0, dp(14), 0, dp(14));
        GradientDrawable btnBg = new GradientDrawable();
        btnBg.setShape(GradientDrawable.RECTANGLE);
        btnBg.setCornerRadius(dp(12));
        btnBg.setColor(TonyColors.primary());
        generateBtn.setBackground(btnBg);
        generateBtn.setOnClickListener(v -> onGenerate());
        content.addView(generateBtn, LayoutHelper.createLinear(
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
        resultContainer.setGravity(Gravity.CENTER_HORIZONTAL);
        content.addView(resultContainer, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        TextView resultLabel = new TextView(context);
        resultLabel.setText("Generated Image");
        resultLabel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        resultLabel.setTextColor(TonyColors.textSecondary());
        resultLabel.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        resultContainer.addView(resultLabel, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 8));

        resultImage = new ImageView(context);
        resultImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        GradientDrawable rBg = new GradientDrawable();
        rBg.setShape(GradientDrawable.RECTANGLE);
        rBg.setCornerRadius(dp(12));
        rBg.setColor(TonyColors.backgroundSecondary());
        resultImage.setBackground(rBg);
        resultImage.setClipToOutline(true);
        resultContainer.addView(resultImage, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, 300, 0, 0, 0, 12));

        // Save button
        TextView saveBtn = new TextView(context);
        saveBtn.setText("Save to Gallery");
        saveBtn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        saveBtn.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        saveBtn.setTextColor(TonyColors.onPrimary());
        saveBtn.setGravity(Gravity.CENTER);
        saveBtn.setPadding(dp(32), dp(12), dp(32), dp(12));
        GradientDrawable saveBg = new GradientDrawable();
        saveBg.setShape(GradientDrawable.RECTANGLE);
        saveBg.setCornerRadius(dp(10));
        saveBg.setColor(TonyColors.primary());
        saveBtn.setBackground(saveBg);
        saveBtn.setOnClickListener(v -> saveToGallery());
        resultContainer.addView(saveBtn, LayoutHelper.createLinear(
                LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL));

        // Attribution
        TextView attribution = new TextView(context);
        attribution.setText("Powered by ClipDrop");
        attribution.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 11);
        attribution.setTextColor(TonyColors.textTertiary());
        attribution.setGravity(Gravity.CENTER);
        content.addView(attribution, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 16, 0, 0));

        return fragmentView;
    }

    private void onGenerate() {
        String prompt = promptField.getText().toString().trim();
        if (TextUtils.isEmpty(prompt)) {
            Toast.makeText(getParentActivity(), "Enter a description first", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isLoading) return;

        InputMethodManager imm = (InputMethodManager) getParentActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) imm.hideSoftInputFromWindow(promptField.getWindowToken(), 0);

        if (!AiConsentManager.INSTANCE.hasConsent(AiFeatureType.CLIPDROP_IMAGE)) {
            AiConsentDialog.show(getParentActivity(), AiFeatureType.CLIPDROP_IMAGE, false, granted -> {
                if (granted) executeGenerate(prompt);
            });
            return;
        }
        executeGenerate(prompt);
    }

    private void executeGenerate(String prompt) {
        setLoading(true);
        AiManagerBridge.clipDropGenerate(prompt, response -> {
            setLoading(false);
            if (response instanceof ImageGenerationResponse.Success) {
                resultFile = ((ImageGenerationResponse.Success) response).getImageFile();
                showResult(resultFile);
            } else if (response instanceof ImageGenerationResponse.RateLimited) {
                Toast.makeText(getParentActivity(),
                        "Daily limit reached. Try again tomorrow.", Toast.LENGTH_LONG).show();
            } else if (response instanceof ImageGenerationResponse.Error) {
                Toast.makeText(getParentActivity(),
                        ((ImageGenerationResponse.Error) response).getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setLoading(boolean loading) {
        isLoading = loading;
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        generateBtn.setEnabled(!loading);
        generateBtn.setAlpha(loading ? 0.5f : 1f);
    }

    private void showResult(File file) {
        resultContainer.setVisibility(View.VISIBLE);
        Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
        resultImage.setImageBitmap(bm);
    }

    private void saveToGallery() {
        if (resultFile == null || !resultFile.exists() || getParentActivity() == null) return;
        try {
            String fileName = "TonyChat_gen_" + System.currentTimeMillis() + ".png";
            if (Build.VERSION.SDK_INT >= 29) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
                values.put(MediaStore.Images.Media.RELATIVE_PATH,
                        Environment.DIRECTORY_PICTURES + "/TonyChat");
                Uri uri = getParentActivity().getContentResolver()
                        .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                if (uri != null) {
                    OutputStream os = getParentActivity().getContentResolver().openOutputStream(uri);
                    if (os != null) {
                        Bitmap bm = BitmapFactory.decodeFile(resultFile.getAbsolutePath());
                        bm.compress(Bitmap.CompressFormat.PNG, 100, os);
                        os.close();
                        bm.recycle();
                    }
                }
            } else {
                File dir = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES), "TonyChat");
                dir.mkdirs();
                File outFile = new File(dir, fileName);
                Bitmap bm = BitmapFactory.decodeFile(resultFile.getAbsolutePath());
                FileOutputStream fos = new FileOutputStream(outFile);
                bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
                bm.recycle();
                Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                scanIntent.setData(Uri.fromFile(outFile));
                getParentActivity().sendBroadcast(scanIntent);
            }
            Toast.makeText(getParentActivity(), "Saved to Gallery", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getParentActivity(),
                    "Save failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private static int dp(float v) {
        return AndroidUtilities.dp(v);
    }
}
