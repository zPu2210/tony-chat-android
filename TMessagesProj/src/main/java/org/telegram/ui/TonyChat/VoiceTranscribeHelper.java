package org.telegram.ui.TonyChat;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.MessageObject;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.Theme;

import com.tonychat.ai.AiFeatureType;
import com.tonychat.ai.AiManagerBridge;
import com.tonychat.ai.AiResponse;
import com.tonychat.ai.config.AiConfig;

import java.io.File;

/** UI helper for voice message transcription. */
public class VoiceTranscribeHelper {

    /**
     * Transcribe voice message to text.
     */
    public static void transcribe(Context context, File audioFile) {
        if (context == null || audioFile == null || !audioFile.exists()) {
            Toast.makeText(context, "Audio file not found", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!AiConfig.INSTANCE.isFeatureEnabled(AiFeatureType.TRANSCRIBE)) {
            boolean isOnDevice = false;
            AiConsentDialog.show(context, AiFeatureType.TRANSCRIBE, isOnDevice, granted -> {
                if (granted) {
                    AiConfig.INSTANCE.setFeatureEnabled(AiFeatureType.TRANSCRIBE, true);
                    executeTranscribe(context, audioFile);
                }
            });
            return;
        }

        executeTranscribe(context, audioFile);
    }

    private static void executeTranscribe(Context context, File audioFile) {
        AlertDialog loadingDialog = showLoadingDialog(context);

        AiManagerBridge.INSTANCE.transcribeVoice(audioFile, response -> {
            loadingDialog.dismiss();

            if (response instanceof AiResponse.Success) {
                String transcript = ((AiResponse.Success<String>) response).getData();
                boolean fromCache = ((AiResponse.Success<String>) response).getFromCache();
                showTranscriptDialog(context, transcript, fromCache);
            } else if (response instanceof AiResponse.ConsentRequired) {
                Toast.makeText(context, "Consent required", Toast.LENGTH_SHORT).show();
            } else if (response instanceof AiResponse.RateLimited) {
                Toast.makeText(context, "Rate limit reached. Try again later.", Toast.LENGTH_SHORT).show();
            } else if (response instanceof AiResponse.Error) {
                String errorMsg = ((AiResponse.Error<String>) response).getMessage();
                Toast.makeText(context, "Transcription failed: " + errorMsg, Toast.LENGTH_LONG).show();
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
        text.setText("Transcribing...");
        text.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        text.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        text.setPadding(0, AndroidUtilities.dp(16), 0, 0);
        layout.addView(text);

        builder.setView(layout);
        return builder.show();
    }

    private static void showTranscriptDialog(Context context, String transcript, boolean fromCache) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Voice Transcript" + (fromCache ? " (cached)" : ""));
        builder.setMessage(transcript);
        builder.setPositiveButton("Copy", (dialog, which) -> {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboard != null) {
                clipboard.setPrimaryClip(ClipData.newPlainText("transcript", transcript));
                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Close", null);
        builder.show();
    }
}
