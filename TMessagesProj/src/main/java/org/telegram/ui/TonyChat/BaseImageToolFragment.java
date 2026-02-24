package org.telegram.ui.TonyChat;

import android.app.Activity;
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
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
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
import com.tonychat.ai.ImageEditResponse;
import com.tonychat.ai.consent.AiConsentManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Abstract base class for 3 image tool fragments (Remove BG, Upscale, Remove Text).
 * Pattern: Pick photo -> preview -> action button -> loading -> result -> Save to Gallery.
 */
public abstract class BaseImageToolFragment extends BaseFragment {

    private static final int PICK_IMAGE_REQUEST = 9001;

    private ImageView previewImage;
    private ImageView resultImage;
    private LinearLayout resultContainer;
    private ProgressBar progressBar;
    private TextView actionBtn;
    private TextView saveBtn;
    private File selectedImageFile;
    private File resultFile;
    private boolean isLoading = false;

    protected abstract String getToolTitle();
    protected abstract String getButtonText();
    protected abstract void processImage(File imageFile, ImageEditCallback callback);

    public interface ImageEditCallback {
        void onResult(ImageEditResponse response);
    }

    @Override
    public View createView(Context context) {
        actionBar.setBackButtonImage(org.telegram.messenger.R.drawable.ic_ab_back);
        actionBar.setTitle(getToolTitle());
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
        content.setGravity(Gravity.CENTER_HORIZONTAL);
        scrollView.addView(content, new ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // Pick photo area
        FrameLayout pickArea = new FrameLayout(context);
        GradientDrawable pickBg = new GradientDrawable();
        pickBg.setShape(GradientDrawable.RECTANGLE);
        pickBg.setCornerRadius(dp(16));
        pickBg.setColor(TonyColors.backgroundSecondary());
        pickBg.setStroke(dp(2), 0x33999999);
        pickArea.setBackground(pickBg);
        pickArea.setOnClickListener(v -> pickImage());
        content.addView(pickArea, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, 240, 0, 0, 0, 16));

        // Preview image (shows selected photo)
        previewImage = new ImageView(context);
        previewImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        pickArea.addView(previewImage, LayoutHelper.createFrame(
                LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));

        // Placeholder text
        LinearLayout placeholder = new LinearLayout(context);
        placeholder.setOrientation(LinearLayout.VERTICAL);
        placeholder.setGravity(Gravity.CENTER);
        pickArea.addView(placeholder, LayoutHelper.createFrame(
                LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));

        ImageView addIcon = new ImageView(context);
        addIcon.setImageResource(org.telegram.messenger.R.drawable.msg_addphoto);
        addIcon.setColorFilter(TonyColors.textTertiary());
        placeholder.addView(addIcon, LayoutHelper.createLinear(48, 48, Gravity.CENTER, 0, 0, 0, 8));

        TextView pickLabel = new TextView(context);
        pickLabel.setText("Tap to select a photo");
        pickLabel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        pickLabel.setTextColor(TonyColors.textTertiary());
        pickLabel.setGravity(Gravity.CENTER);
        placeholder.addView(pickLabel);

        // tag placeholder for toggling visibility
        placeholder.setTag("placeholder");

        // Action button
        actionBtn = new TextView(context);
        actionBtn.setText(getButtonText());
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
        actionBtn.setEnabled(false);
        actionBtn.setAlpha(0.5f);
        actionBtn.setOnClickListener(v -> onProcess());
        content.addView(actionBtn, LayoutHelper.createLinear(
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
        resultLabel.setText("Result");
        resultLabel.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        resultLabel.setTextColor(TonyColors.textSecondary());
        resultLabel.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        resultContainer.addView(resultLabel, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 8));

        resultImage = new ImageView(context);
        resultImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        GradientDrawable resultImgBg = new GradientDrawable();
        resultImgBg.setShape(GradientDrawable.RECTANGLE);
        resultImgBg.setCornerRadius(dp(12));
        resultImgBg.setColor(TonyColors.backgroundSecondary());
        resultImage.setBackground(resultImgBg);
        resultImage.setClipToOutline(true);
        resultContainer.addView(resultImage, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, 280, 0, 0, 0, 12));

        // Save button
        saveBtn = new TextView(context);
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

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResultFragment(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null && getParentActivity() != null) {
                try {
                    // Copy to temp file
                    File tempFile = new File(getParentActivity().getCacheDir(),
                            "pick_" + System.currentTimeMillis() + ".jpg");
                    InputStream is = getParentActivity().getContentResolver().openInputStream(uri);
                    if (is != null) {
                        FileOutputStream fos = new FileOutputStream(tempFile);
                        byte[] buf = new byte[8192];
                        int len;
                        while ((len = is.read(buf)) > 0) fos.write(buf, 0, len);
                        fos.close();
                        is.close();
                    }
                    selectedImageFile = tempFile;

                    // Show preview
                    Bitmap bm = BitmapFactory.decodeFile(tempFile.getAbsolutePath());
                    previewImage.setImageBitmap(bm);
                    // Hide placeholder
                    View ph = ((FrameLayout) previewImage.getParent()).findViewWithTag("placeholder");
                    if (ph != null) ph.setVisibility(View.GONE);

                    actionBtn.setEnabled(true);
                    actionBtn.setAlpha(1f);
                    resultContainer.setVisibility(View.GONE);
                } catch (Exception e) {
                    Toast.makeText(getParentActivity(), "Failed to load image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void onProcess() {
        if (selectedImageFile == null || isLoading) return;

        if (!AiConsentManager.INSTANCE.hasConsent(AiFeatureType.CLIPDROP_IMAGE)) {
            AiConsentDialog.show(getParentActivity(), AiFeatureType.CLIPDROP_IMAGE, false, granted -> {
                if (granted) executeProcess();
            });
            return;
        }
        executeProcess();
    }

    private void executeProcess() {
        setLoading(true);
        processImage(selectedImageFile, response -> {
            setLoading(false);
            if (response instanceof ImageEditResponse.Success) {
                resultFile = ((ImageEditResponse.Success) response).getResultFile();
                showResult(resultFile);
            } else if (response instanceof ImageEditResponse.RateLimited) {
                Toast.makeText(getParentActivity(),
                        "Daily limit reached. Try again tomorrow.", Toast.LENGTH_LONG).show();
            } else if (response instanceof ImageEditResponse.ConsentRequired) {
                Toast.makeText(getParentActivity(),
                        "Consent required.", Toast.LENGTH_SHORT).show();
            } else if (response instanceof ImageEditResponse.Error) {
                Toast.makeText(getParentActivity(),
                        ((ImageEditResponse.Error) response).getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setLoading(boolean loading) {
        isLoading = loading;
        progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        actionBtn.setEnabled(!loading);
        actionBtn.setAlpha(loading ? 0.5f : 1f);
    }

    private void showResult(File file) {
        resultContainer.setVisibility(View.VISIBLE);
        Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
        resultImage.setImageBitmap(bm);
    }

    private void saveToGallery() {
        if (resultFile == null || !resultFile.exists() || getParentActivity() == null) return;
        try {
            String fileName = "TonyChat_" + System.currentTimeMillis() + ".png";
            if (Build.VERSION.SDK_INT >= 29) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
                values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/TonyChat");
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
                // Trigger media scan
                Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                scanIntent.setData(Uri.fromFile(outFile));
                getParentActivity().sendBroadcast(scanIntent);
            }
            Toast.makeText(getParentActivity(), "Saved to Gallery", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getParentActivity(), "Save failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    protected static int dp(float v) {
        return AndroidUtilities.dp(v);
    }
}
