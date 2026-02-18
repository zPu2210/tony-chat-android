package org.telegram.ui.TonyChat;

import android.os.Handler;
import android.os.Looper;

import org.telegram.messenger.MessageObject;

import com.tonychat.ai.AiFeatureType;
import com.tonychat.ai.AiManagerBridge;
import com.tonychat.ai.AiMessage;
import com.tonychat.ai.AiResponse;
import com.tonychat.ai.chat.ChatContextExtractor;
import com.tonychat.ai.config.AiConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Bridges ChatActivity's message data to the AI smart reply system.
 * Handles debouncing, consent checks, and background execution.
 */
public class SmartReplyHelper {

    public interface Callback {
        void onLoading();
        void onSuggestions(List<String> suggestions);
        void onHide();
    }

    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable pendingRequest;
    private Callback callback;
    private volatile boolean cancelled;

    private static final long DEBOUNCE_MS = 800L;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    /**
     * Called when a new message arrives in the open chat.
     * Only triggers for incoming (non-outgoing) text messages.
     */
    public void onNewMessage(MessageObject message, List<MessageObject> allMessages) {
        if (message == null || callback == null) return;

        // Only trigger on received text messages
        if (message.isOut()) return;
        if (message.messageOwner == null || message.messageOwner.message == null) return;
        if (message.messageOwner.message.trim().isEmpty()) return;

        // Check if feature is enabled + has consent
        if (!AiConfig.INSTANCE.isFeatureEnabled(AiFeatureType.SMART_REPLY)) return;

        // Debounce: cancel previous pending request
        cancelPending();

        pendingRequest = () -> generateReplies(allMessages);
        handler.postDelayed(pendingRequest, DEBOUNCE_MS);
    }

    /** Called when user starts typing - hide suggestions. */
    public void onTypingStarted() {
        cancelPending();
        if (callback != null) callback.onHide();
    }

    public void cancelPending() {
        cancelled = true;
        if (pendingRequest != null) {
            handler.removeCallbacks(pendingRequest);
            pendingRequest = null;
        }
    }

    public void destroy() {
        cancelPending();
        callback = null;
    }

    private void generateReplies(List<MessageObject> allMessages) {
        if (callback == null) return;
        cancelled = false;

        // Extract recent messages
        List<ChatContextExtractor.SimpleMsg> simpleMsgs = new ArrayList<>();
        int count = Math.min(allMessages.size(), 10);
        for (int i = 0; i < count; i++) {
            MessageObject msg = allMessages.get(i);
            if (msg.messageOwner == null) continue;
            String text = msg.messageOwner.message;
            if (text == null || text.trim().isEmpty()) continue;
            boolean isService = msg.messageOwner.action != null;
            simpleMsgs.add(new ChatContextExtractor.SimpleMsg(text, msg.isOut(), isService));
        }

        List<AiMessage> context = ChatContextExtractor.INSTANCE.extract(simpleMsgs, 5);
        if (context.isEmpty()) return;

        callback.onLoading();

        AiManagerBridge.INSTANCE.generateReply(context, 3, result -> {
            if (cancelled || callback == null) return;
            if (result instanceof AiResponse.Success) {
                @SuppressWarnings("unchecked")
                List<String> suggestions = ((AiResponse.Success<List<String>>) result).getData();
                if (suggestions != null && !suggestions.isEmpty()) {
                    callback.onSuggestions(suggestions);
                } else {
                    callback.onHide();
                }
            } else {
                callback.onHide();
            }
        });
    }
}
