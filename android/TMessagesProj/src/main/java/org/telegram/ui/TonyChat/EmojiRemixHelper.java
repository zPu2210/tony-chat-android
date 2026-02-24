package org.telegram.ui.TonyChat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.Theme;

import com.tonychat.ai.AiFeatureType;
import com.tonychat.ai.AiManagerBridge;
import com.tonychat.ai.ImageGenerationResponse;
import com.tonychat.ai.config.AiConfig;

import java.io.File;

/** UI helper for AI emoji generation feature. */
public class EmojiRemixHelper {

    public interface SendCallback {
        void onSend(File emojiFile);
    }

    /**
     * Show emoji generation flow: input prompt → consent → loading → preview → send.
     */
    public static void showEmojiRemixDialog(Context context, SendCallback callback) {
        if (context == null) {
            return;
        }

        if (!AiConfig.INSTANCE.isFeatureEnabled(AiFeatureType.EMOJI_REMIX)) {
            boolean isOnDevice = false;
            AiConsentDialog.show(context, AiFeatureType.EMOJI_REMIX, isOnDevice, granted -> {
                if (granted) {
                    AiConfig.INSTANCE.setFeatureEnabled(AiFeatureType.EMOJI_REMIX, true);
                    showInputDialog(context, callback);
                }
            });
            return;
        }

        showInputDialog(context, callback);
    }

    private static void showInputDialog(Context context, SendCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(AndroidUtilities.dp(16), AndroidUtilities.dp(16),
            AndroidUtilities.dp(16), AndroidUtilities.dp(16));

        TextView title = new TextView(context);
        title.setText("AI Emoji Remix");
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        title.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        title.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        title.setPadding(0, 0, 0, AndroidUtilities.dp(8));
        layout.addView(title);

        TextView subtitle = new TextView(context);
        subtitle.setText("Describe your custom emoji");
        subtitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        subtitle.setTextColor(Theme.getColor(Theme.key_dialogTextGray2));
        subtitle.setPadding(0, 0, 0, AndroidUtilities.dp(12));
        layout.addView(subtitle);

        EditText promptInput = new EditText(context);
        promptInput.setHint("E.g., happy coffee cup");
        promptInput.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        promptInput.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        promptInput.setHintTextColor(Theme.getColor(Theme.key_dialogTextHint));
        promptInput.setMaxLines(3);
        promptInput.setPadding(AndroidUtilities.dp(12), AndroidUtilities.dp(10),
            AndroidUtilities.dp(12), AndroidUtilities.dp(10));
        promptInput.setBackground(Theme.createRoundRectDrawable(AndroidUtilities.dp(6),
            Theme.getColor(Theme.key_dialogInputField)));
        layout.addView(promptInput, new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        TextView examples = new TextView(context);
        examples.setText("Examples: dancing robot, sleepy cat, excited pizza");
        examples.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        examples.setTextColor(Theme.getColor(Theme.key_dialogTextGray3));
        examples.setPadding(0, AndroidUtilities.dp(8), 0, AndroidUtilities.dp(16));
        layout.addView(examples);

        builder.setView(layout);
        builder.setPositiveButton("Generate", (dialog, which) -> {
            String prompt = promptInput.getText().toString().trim();
            if (prompt.isEmpty()) {
                Toast.makeText(context, "Please enter a description", Toast.LENGTH_SHORT).show();
                return;
            }
            if (prompt.length() > 200) {
                Toast.makeText(context, "Description too long (max 200 chars)", Toast.LENGTH_SHORT).show();
                return;
            }
            executeGeneration(context, prompt, callback);
        });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private static void executeGeneration(Context context, String prompt, SendCallback callback) {
        AlertDialog loadingDialog = showLoadingDialog(context);
        final boolean[] completed = {false};

        // 30s timeout
        Runnable timeoutRunnable = () -> {
            if (loadingDialog.isShowing()) {
                loadingDialog.dismiss();
                if (!completed[0]) {
                    Toast.makeText(context, "Request timed out", Toast.LENGTH_SHORT).show();
                }
            }
        };
        AndroidUtilities.runOnUIThread(timeoutRunnable, 30000);

        AiManagerBridge.INSTANCE.generateEmoji(prompt, response -> {
            completed[0] = true;
            AndroidUtilities.cancelRunOnUIThread(timeoutRunnable);
            if (loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }

            if (response instanceof ImageGenerationResponse.Success) {
                File emojiFile = ((ImageGenerationResponse.Success) response).getImageFile();
                boolean fromCache = ((ImageGenerationResponse.Success) response).getFromCache();
                String provider = ((ImageGenerationResponse.Success) response).getProvider();
                showPreviewDialog(context, emojiFile, fromCache, provider, callback);
            } else if (response instanceof ImageGenerationResponse.ConsentRequired) {
                Toast.makeText(context, "Consent required", Toast.LENGTH_SHORT).show();
            } else if (response instanceof ImageGenerationResponse.RateLimited) {
                Toast.makeText(context, "Rate limit reached (250/day). Try again later.", Toast.LENGTH_LONG).show();
            } else if (response instanceof ImageGenerationResponse.Error) {
                String errorMsg = ((ImageGenerationResponse.Error) response).getMessage();
                Toast.makeText(context, "Error: " + errorMsg, Toast.LENGTH_LONG).show();
            }
        });

        loadingDialog.setOnCancelListener(dialog -> {
            completed[0] = true;
            AndroidUtilities.cancelRunOnUIThread(timeoutRunnable);
            Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show();
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
        text.setText("Generating emoji...");
        text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        text.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        text.setPadding(0, AndroidUtilities.dp(16), 0, 0);
        layout.addView(text);

        TextView hint = new TextView(context);
        hint.setText("This may take 5-10 seconds");
        hint.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        hint.setTextColor(Theme.getColor(Theme.key_dialogTextGray3));
        hint.setPadding(0, AndroidUtilities.dp(4), 0, 0);
        layout.addView(hint);

        builder.setView(layout);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.show();
        return dialog;
    }

    private static void showPreviewDialog(Context context, File emojiFile, boolean fromCache, String provider, SendCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(AndroidUtilities.dp(16), AndroidUtilities.dp(16),
            AndroidUtilities.dp(16), AndroidUtilities.dp(16));

        TextView title = new TextView(context);
        title.setText("Emoji Generated" + (fromCache ? " (cached)" : ""));
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        title.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        title.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        title.setPadding(0, 0, 0, AndroidUtilities.dp(12));
        layout.addView(title);

        FrameLayout imageContainer = new FrameLayout(context);
        imageContainer.setLayoutParams(new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            AndroidUtilities.dp(256)
        ));
        imageContainer.setBackground(Theme.createRoundRectDrawable(AndroidUtilities.dp(12),
            Theme.getColor(Theme.key_dialogBackground)));

        ImageView previewImage = new ImageView(context);
        previewImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        try {
            // Downsample large images to prevent OOM
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(emojiFile.getAbsolutePath(), opts);
            int maxDim = 1920;
            int inSampleSize = 1;
            while (opts.outWidth / inSampleSize > maxDim || opts.outHeight / inSampleSize > maxDim) {
                inSampleSize *= 2;
            }
            opts.inJustDecodeBounds = false;
            opts.inSampleSize = inSampleSize;
            Bitmap bitmap = BitmapFactory.decodeFile(emojiFile.getAbsolutePath(), opts);
            if (bitmap != null) {
                previewImage.setImageBitmap(bitmap);
            }
        } catch (Exception e) {
            Toast.makeText(context, "Failed to load preview", Toast.LENGTH_SHORT).show();
        }
        imageContainer.addView(previewImage, new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        ));

        layout.addView(imageContainer);

        builder.setView(layout);
        builder.setPositiveButton("Send", (dialog, which) -> {
            if (callback != null) {
                callback.onSend(emojiFile);
            }
        });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();

        // Recycle bitmap on dismiss
        final Bitmap[] bitmapHolder = new Bitmap[1];
        if (previewImage.getDrawable() instanceof android.graphics.drawable.BitmapDrawable) {
            bitmapHolder[0] = ((android.graphics.drawable.BitmapDrawable) previewImage.getDrawable()).getBitmap();
        }
        dialog.setOnDismissListener(d -> {
            if (bitmapHolder[0] != null && !bitmapHolder[0].isRecycled()) {
                previewImage.setImageBitmap(null);
                bitmapHolder[0].recycle();
            }
        });

        dialog.show();
    }
}
