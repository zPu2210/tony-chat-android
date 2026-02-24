package org.telegram.ui.TonyChat;

import android.content.Context;
import android.widget.Toast;

import org.telegram.messenger.MessageObject;

import com.tonychat.ai.AiFeatureType;
import com.tonychat.ai.AiManagerBridge;
import com.tonychat.ai.AiMessage;
import com.tonychat.ai.AiResponse;
import com.tonychat.ai.chat.ChatContextExtractor;
import com.tonychat.ai.config.AiConfig;
import com.tonychat.ai.consent.AiConsentManager;

import java.util.ArrayList;
import java.util.List;

/** Orchestrates the chat summarization flow: consent → extract → API → display. */
public class SummaryHelper {

    private static final int DEFAULT_MSG_COUNT = 50;
    private static final int MIN_MESSAGES = 3;
    private static final int MAX_LENGTH = 200;

    public static void showSummary(
        org.telegram.ui.ActionBar.BaseFragment fragment,
        List<MessageObject> allMessages,
        Context context
    ) {
        if (context == null) return;

        // Check consent
        if (!AiConsentManager.INSTANCE.hasConsent(AiFeatureType.SUMMARY)) {
            boolean isOnDevice = AiConfig.INSTANCE.getPreferOnDevice();
            AiConsentDialog.show(context, AiFeatureType.SUMMARY, isOnDevice, granted -> {
                if (granted) {
                    AiConfig.INSTANCE.setFeatureEnabled(AiFeatureType.SUMMARY, true);
                    doSummarize(context, allMessages);
                }
            });
            return;
        }

        if (!AiConfig.INSTANCE.isFeatureEnabled(AiFeatureType.SUMMARY)) {
            Toast.makeText(context, "Enable Chat Summary in AI Settings", Toast.LENGTH_SHORT).show();
            return;
        }

        doSummarize(context, allMessages);
    }

    private static void doSummarize(Context context, List<MessageObject> allMessages) {
        // Extract messages
        List<ChatContextExtractor.SimpleMsg> simpleMsgs = new ArrayList<>();
        int count = Math.min(allMessages.size(), DEFAULT_MSG_COUNT);
        for (int i = 0; i < count; i++) {
            MessageObject msg = allMessages.get(i);
            if (msg.messageOwner == null) continue;
            String text = msg.messageOwner.message;
            if (text == null || text.trim().isEmpty()) continue;
            boolean isService = msg.messageOwner.action != null;
            simpleMsgs.add(new ChatContextExtractor.SimpleMsg(text, msg.isOut(), isService));
        }

        List<AiMessage> aiMessages = ChatContextExtractor.INSTANCE.extract(simpleMsgs, DEFAULT_MSG_COUNT);
        if (aiMessages.size() < MIN_MESSAGES) {
            Toast.makeText(context, "Not enough messages to summarize", Toast.LENGTH_SHORT).show();
            return;
        }

        SummaryBottomSheet sheet = new SummaryBottomSheet(context);
        sheet.showLoading();
        sheet.show();

        Runnable doRequest = () -> AiManagerBridge.INSTANCE.summarize(aiMessages, MAX_LENGTH, result -> {
            if (result instanceof AiResponse.Success) {
                AiResponse.Success<String> success = (AiResponse.Success<String>) result;
                sheet.showSummary(success.getData(), success.getFromCache());
            } else if (result instanceof AiResponse.Error) {
                sheet.showError(((AiResponse.Error<String>) result).getMessage());
            } else if (result instanceof AiResponse.ConsentRequired) {
                sheet.dismiss();
            } else {
                sheet.showError("Summary unavailable. Check your API key in AI Settings.");
            }
        });

        sheet.setRetryAction(doRequest);
        doRequest.run();
    }
}
