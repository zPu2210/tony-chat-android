package org.telegram.ui.TonyChat;

import android.content.Context;
import android.widget.Toast;

import org.telegram.messenger.MessageObject;

import com.tonychat.ai.AiFeatureType;
import com.tonychat.ai.AiManagerBridge;
import com.tonychat.ai.AiResponse;
import com.tonychat.ai.config.AiConfig;
import com.tonychat.ai.consent.AiConsentManager;

import java.util.Locale;

/** Handles AI translation of individual messages in the chat. */
public class TranslateHelper {

    public interface TranslateCallback {
        void onTranslated(String translatedText, String langPair);
    }

    public static void translateMessage(
        Context context,
        MessageObject message,
        TranslateCallback callback
    ) {
        if (context == null || message == null) return;
        if (message.messageOwner == null || message.messageOwner.message == null) return;

        String text = message.messageOwner.message.trim();
        if (text.isEmpty()) return;

        // Check consent
        if (!AiConsentManager.INSTANCE.hasConsent(AiFeatureType.TRANSLATE)) {
            boolean isOnDevice = AiConfig.INSTANCE.getPreferOnDevice();
            AiConsentDialog.show(context, AiFeatureType.TRANSLATE, isOnDevice, granted -> {
                if (granted) {
                    AiConfig.INSTANCE.setFeatureEnabled(AiFeatureType.TRANSLATE, true);
                    doTranslate(context, text, callback);
                }
            });
            return;
        }

        if (!AiConfig.INSTANCE.isFeatureEnabled(AiFeatureType.TRANSLATE)) {
            Toast.makeText(context, "Enable AI Translation in AI Settings", Toast.LENGTH_SHORT).show();
            return;
        }

        doTranslate(context, text, callback);
    }

    private static void doTranslate(Context context, String text, TranslateCallback callback) {
        String targetLang = Locale.getDefault().getLanguage();

        Toast.makeText(context, "Translating...", Toast.LENGTH_SHORT).show();

        AiManagerBridge.INSTANCE.translate(text, "auto", targetLang, result -> {
            if (result instanceof AiResponse.Success) {
                String translated = ((AiResponse.Success<String>) result).getData();
                if (translated != null && !translated.trim().isEmpty()) {
                    String langPair = "? \u2192 " + targetLang.toUpperCase();
                    callback.onTranslated(translated, langPair);
                }
            } else if (result instanceof AiResponse.Error) {
                Toast.makeText(context,
                    "Translation failed: " + ((AiResponse.Error<String>) result).getMessage(),
                    Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context,
                    "Translation unavailable. Check API key in AI Settings.",
                    Toast.LENGTH_SHORT).show();
            }
        });
    }
}
