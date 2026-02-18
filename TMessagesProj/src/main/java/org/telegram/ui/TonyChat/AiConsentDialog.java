package org.telegram.ui.TonyChat;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.Theme;

import com.tonychat.ai.AiFeatureType;
import com.tonychat.ai.consent.AiConsentManager;

/** Shows privacy consent dialog before enabling an AI feature. */
public class AiConsentDialog {

    public interface OnConsentResult {
        void onResult(boolean granted);
    }

    public static void show(Context context, AiFeatureType feature, boolean isOnDevice, OnConsentResult callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(getFeatureTitle(feature));

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(
            AndroidUtilities.dp(24), AndroidUtilities.dp(8),
            AndroidUtilities.dp(24), AndroidUtilities.dp(0)
        );

        TextView description = new TextView(context);
        description.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        description.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        description.setGravity(Gravity.START);
        description.setText(getDescription(feature));
        layout.addView(description);

        TextView privacy = new TextView(context);
        privacy.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        privacy.setTextColor(Theme.getColor(Theme.key_dialogTextGray3));
        privacy.setGravity(Gravity.START);
        privacy.setPadding(0, AndroidUtilities.dp(12), 0, 0);
        privacy.setText(isOnDevice
            ? "All processing happens on your device. No data leaves your phone."
            : "Your messages will be sent to a cloud AI service for processing. Tony Chat does not store your messages on any server."
        );
        layout.addView(privacy);

        builder.setView(layout);
        builder.setPositiveButton("Enable", (dialog, which) -> {
            AiConsentManager.INSTANCE.grantConsent(feature);
            if (callback != null) callback.onResult(true);
        });
        builder.setNegativeButton(LocaleController.getString(R.string.Cancel), (dialog, which) -> {
            if (callback != null) callback.onResult(false);
        });
        builder.show();
    }

    private static String getFeatureTitle(AiFeatureType feature) {
        switch (feature) {
            case SMART_REPLY: return "Enable Smart Replies";
            case SUMMARY: return "Enable Chat Summary";
            case TONE_REWRITE: return "Enable Tone Rewrite";
            case TRANSLATE: return "Enable AI Translation";
            case IMAGE_EDIT: return "Enable Background Removal";
            default: return "Enable AI Feature";
        }
    }

    private static String getDescription(AiFeatureType feature) {
        switch (feature) {
            case SMART_REPLY:
                return "Smart Replies suggests quick responses based on the conversation context.";
            case SUMMARY:
                return "Chat Summary creates brief bullet-point summaries of long conversations.";
            case TONE_REWRITE:
                return "Tone Rewrite helps you adjust the tone of your messages (formal, casual, playful).";
            case TRANSLATE:
                return "AI Translation translates messages between languages using AI.";
            case IMAGE_EDIT:
                return "Background Removal removes backgrounds from images using AI. Images are sent to remove.bg API for processing.";
            default:
                return "This feature uses AI to enhance your messaging experience.";
        }
    }
}
