package org.telegram.ui.TonyChat;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;

import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.Theme;

import com.tonychat.ai.AiFeatureType;
import com.tonychat.ai.AiManagerBridge;
import com.tonychat.ai.ImageEditResponse;
import com.tonychat.ai.config.AiConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/** UI helper for AI image editing features (background removal). */
public class ImageEditHelper {

    public interface ResultCallback {
        void onSuccess();
    }

    /**
     * Show background removal flow: consent → loading → preview → save/share.
     */
    public static void removeBackground(Context context, File imageFile, ResultCallback callback) {
        if (context == null || imageFile == null || !imageFile.exists()) {
            Toast.makeText(context, "Image not found", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!AiConfig.INSTANCE.isFeatureEnabled(AiFeatureType.IMAGE_EDIT)) {
            boolean isOnDevice = false;
            AiConsentDialog.show(context, AiFeatureType.IMAGE_EDIT, isOnDevice, granted -> {
                if (granted) {
                    AiConfig.INSTANCE.setFeatureEnabled(AiFeatureType.IMAGE_EDIT, true);
                    executeRemoveBackground(context, imageFile, callback);
                }
            });
            return;
        }

        executeRemoveBackground(context, imageFile, callback);
    }

    private static void executeRemoveBackground(Context context, File imageFile, ResultCallback callback) {
        AlertDialog loadingDialog = showLoadingDialog(context);

        AiManagerBridge.INSTANCE.removeBackground(imageFile, response -> {
            loadingDialog.dismiss();

            if (response instanceof ImageEditResponse.Success) {
                File resultFile = ((ImageEditResponse.Success) response).getResultFile();
                boolean fromCache = ((ImageEditResponse.Success) response).getFromCache();
                showPreviewDialog(context, imageFile, resultFile, fromCache, callback);
            } else if (response instanceof ImageEditResponse.ConsentRequired) {
                Toast.makeText(context, "Consent required", Toast.LENGTH_SHORT).show();
            } else if (response instanceof ImageEditResponse.RateLimited) {
                Toast.makeText(context, "Rate limit reached. Try again later.", Toast.LENGTH_SHORT).show();
            } else if (response instanceof ImageEditResponse.Error) {
                String errorMsg = ((ImageEditResponse.Error) response).getMessage();
                Toast.makeText(context, "Error: " + errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    private static AlertDialog showLoadingDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(AndroidUtilities.dp(24), AndroidUtilities.dp(24),
            AndroidUtilities.dp(24), AndroidUtilities.dp(24));
        layout.setGravity(Gravity.CENTER);

        ProgressBar progress = new ProgressBar(context);
        layout.addView(progress, new LinearLayout.LayoutParams(
            AndroidUtilities.dp(48), AndroidUtilities.dp(48)
        ));

        TextView text = new TextView(context);
        text.setText("Removing background...");
        text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        text.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        text.setPadding(0, AndroidUtilities.dp(16), 0, 0);
        layout.addView(text);

        builder.setView(layout);
        return builder.show();
    }

    private static void showPreviewDialog(Context context, File original, File edited, boolean fromCache, ResultCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(AndroidUtilities.dp(16), AndroidUtilities.dp(16),
            AndroidUtilities.dp(16), AndroidUtilities.dp(16));

        TextView title = new TextView(context);
        title.setText("Background Removed" + (fromCache ? " (cached)" : ""));
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        title.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        title.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        title.setPadding(0, 0, 0, AndroidUtilities.dp(12));
        layout.addView(title);

        FrameLayout imageContainer = new FrameLayout(context);
        imageContainer.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            AndroidUtilities.dp(300)
        ));

        ImageView editedView = new ImageView(context);
        editedView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        try {
            // Downsample large images to prevent OOM
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(edited.getAbsolutePath(), opts);
            int maxDim = 1920;
            int inSampleSize = 1;
            while (opts.outWidth / inSampleSize > maxDim || opts.outHeight / inSampleSize > maxDim) {
                inSampleSize *= 2;
            }
            opts.inJustDecodeBounds = false;
            opts.inSampleSize = inSampleSize;
            Bitmap editedBitmap = BitmapFactory.decodeFile(edited.getAbsolutePath(), opts);
            if (editedBitmap != null) {
                editedView.setImageBitmap(editedBitmap);
            }
        } catch (Exception e) {
            Toast.makeText(context, "Failed to load preview", Toast.LENGTH_SHORT).show();
        }
        imageContainer.addView(editedView, new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        ));

        layout.addView(imageContainer);

        LinearLayout buttonRow = new LinearLayout(context);
        buttonRow.setOrientation(LinearLayout.HORIZONTAL);
        buttonRow.setPadding(0, AndroidUtilities.dp(16), 0, 0);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
            0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
        );
        buttonParams.setMargins(0, 0, AndroidUtilities.dp(8), 0);

        TextView btnSave = new TextView(context);
        btnSave.setText("Save to Gallery");
        btnSave.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        btnSave.setTextColor(Theme.getColor(Theme.key_featuredStickers_buttonText));
        btnSave.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.dp(6),
            Theme.getColor(Theme.key_featuredStickers_addButton),
            Theme.getColor(Theme.key_featuredStickers_addButtonPressed)));
        btnSave.setPadding(AndroidUtilities.dp(12), AndroidUtilities.dp(10),
            AndroidUtilities.dp(12), AndroidUtilities.dp(10));
        btnSave.setGravity(Gravity.CENTER);
        buttonRow.addView(btnSave, buttonParams);

        TextView btnShare = new TextView(context);
        btnShare.setText("Share");
        btnShare.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        btnShare.setTextColor(Theme.getColor(Theme.key_featuredStickers_buttonText));
        btnShare.setBackground(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.dp(6),
            Theme.getColor(Theme.key_featuredStickers_addButton),
            Theme.getColor(Theme.key_featuredStickers_addButtonPressed)));
        btnShare.setPadding(AndroidUtilities.dp(12), AndroidUtilities.dp(10),
            AndroidUtilities.dp(12), AndroidUtilities.dp(10));
        btnShare.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams shareParams = new LinearLayout.LayoutParams(
            0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
        );
        buttonRow.addView(btnShare, shareParams);

        layout.addView(buttonRow);

        builder.setView(layout);
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();

        btnSave.setOnClickListener(v -> {
            saveToGallery(context, edited);
            dialog.dismiss();
            if (callback != null) callback.onSuccess();
        });

        btnShare.setOnClickListener(v -> {
            shareImage(context, edited);
            dialog.dismiss();
        });

        dialog.show();
    }

    private static void saveToGallery(Context context, File imageFile) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DISPLAY_NAME, "edited_" + System.currentTimeMillis() + ".png");
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
                values.put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/TonyChat/Edited");

                Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                if (uri != null) {
                    try (OutputStream out = context.getContentResolver().openOutputStream(uri);
                         FileInputStream in = new FileInputStream(imageFile)) {
                        byte[] buffer = new byte[8192];
                        int read;
                        while ((read = in.read(buffer)) != -1) {
                            out.write(buffer, 0, read);
                        }
                        Toast.makeText(context, "Saved to Gallery", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(context, "Save requires Android 10+", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Toast.makeText(context, "Save failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private static void shareImage(Context context, File imageFile) {
        try {
            Uri uri = FileProvider.getUriForFile(context,
                ApplicationLoader.applicationContext.getPackageName() + ".provider", imageFile);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/png");
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(Intent.createChooser(shareIntent, "Share Image"));
        } catch (Exception e) {
            Toast.makeText(context, "Share failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
