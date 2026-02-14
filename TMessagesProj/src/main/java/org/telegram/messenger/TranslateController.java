package org.telegram.messenger;

import android.util.LongSparseArray;
import androidx.annotation.Nullable;
import org.telegram.tgnet.TLRPC;
import org.telegram.ui.Components.Bulletin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TranslateController extends BaseController {

    public static final String UNKNOWN_LANGUAGE = "und";

    private final LongSparseArray<Boolean> translatingDialogs = new LongSparseArray<>();
    private final Set<Long> translatableDialogs = new HashSet<>();
    private final HashMap<Long, HashMap<Integer, MessageObject>> keptReplyMessageObjects = new HashMap<>();
    private final Set<Long> hideTranslateDialogs = new HashSet<>();

    private MessagesController messagesController;

    public TranslateController(MessagesController messagesController) {
        super(messagesController.currentAccount);
        this.messagesController = messagesController;
    }

    public boolean isFeatureAvailable() {
        return false; // Translation disabled in Tony Chat
    }

    public boolean isFeatureAvailable(long dialogId) {
        return false;
    }

    public boolean isChatTranslateEnabled() {
        return false;
    }

    public boolean isContextTranslateEnabled() {
        return false;
    }

    public void setContextTranslateEnabled(boolean enable) {
        // No-op
    }

    public void setChatTranslateEnabled(boolean enable) {
        // No-op
    }

    public static boolean isTranslatable(MessageObject messageObject) {
        return false;
    }

    public boolean isDialogTranslatable(long dialogId) {
        return false;
    }

    public boolean isTranslateDialogHidden(long dialogId) {
        return false;
    }

    public boolean isTranslatingDialog(long dialogId) {
        return false;
    }

    public void toggleTranslatingDialog(long dialogId) {
        // No-op
    }

    public boolean toggleTranslatingDialog(long dialogId, boolean value) {
        return false;
    }

    public String getDialogTranslateTo(long dialogId) {
        return null;
    }

    public void setDialogTranslateTo(long dialogId, String language) {
        // No-op
    }

    public void updateDialogFull(long dialogId) {
        // No-op
    }

    public void setHideTranslateDialog(long dialogId, boolean hide) {
        // No-op
    }

    public void setHideTranslateDialog(long dialogId, boolean hide, boolean doNotNotify) {
        // No-op
    }

    public static class Language {
        public String code;
        public String displayName;
        public String ownDisplayName;
        public String q;
    }

    public static ArrayList<Language> getLanguages() {
        return new ArrayList<>();
    }

    public static void invalidateSuggestedLanguageCodes() {
        // No-op
    }

    public static void analyzeSuggestedLanguageCodes() {
        // No-op
    }

    public static ArrayList<Language> getSuggestedLanguages(String except) {
        return new ArrayList<>();
    }

    public static ArrayList<LocaleController.LocaleInfo> getLocales() {
        return new ArrayList<>();
    }

    public static ArrayList<LocaleController.LocaleInfo> getLocales(boolean firstSystem) {
        return new ArrayList<>();
    }

    public void checkRestrictedLanguagesUpdate() {
        // No-op
    }

    public void cleanup() {
        // No-op
    }

    public void reset() {
        // No-op
    }

    public void toggleTranslateStory(@Nullable org.telegram.tgnet.tl.TL_stories.StoryItem storyItem) {
        // No-op
    }

    public boolean canTranslateStory(org.telegram.tgnet.tl.TL_stories.StoryItem storyItem) {
        return false;
    }

    public void translateStory(org.telegram.tgnet.tl.TL_stories.StoryItem storyItem, Runnable callback) {
        // No-op
    }

    public void detectStoryLanguage(org.telegram.tgnet.tl.TL_stories.StoryItem storyItem) {
        // No-op
    }

    public void translateMessages(long dialogId, ArrayList<Integer> messageIds) {
        // No-op
    }

    public void detectLanguage(MessageObject messageObject) {
        // No-op
    }

    public void checkTranslation(MessageObject messageObject, boolean onScreen, int hash) {
        // No-op
    }

    public void checkTranslation(MessageObject messageObject, boolean onScreen) {
        // No-op
    }

    public MessageObject findReplyMessageObject(long dialogId, int messageId) {
        return null;
    }

    public boolean isTranslating(MessageObject messageObject) {
        return false;
    }

    public void translatePhoto(MessageObject messageObject, Runnable callback) {
        // No-op
    }

    public void detectPhotoLanguage(MessageObject messageObject, java.util.function.Consumer<String> callback) {
        // No-op
    }

    public boolean canTranslatePhoto(MessageObject messageObject, String detectedLanguage) {
        return false;
    }

    public static void invalidateRestrictedLanguages() {
        // No-op
    }

    public void invalidateTranslation(MessageObject messageObject) {
        // No-op
    }

    public HashMap<Integer, MessageObject> getKeptReplyMessageObjects(long dialogId) {
        return keptReplyMessageObjects.get(dialogId);
    }

    public void keepReplyMessage(MessageObject messageObject) {
        // No-op
    }

    public void checkDialogTranslatable(MessageObject messageObject) {
        // No-op
    }

    public void checkDialogMessage(long dialogId) {
        // No-op
    }

    public Bulletin showTranslationFailedBulletin(MessageObject messageObject, Runnable onUndo) {
        return null;
    }

    public void showTranslationFailedAlert(Throwable exception) {
        // No-op
    }

    public void checkDialogMessageSure(long dialogId, int scheduled) {
        // No-op
    }

    public void checkDialogMessageSure(long dialogId) {
        checkDialogMessageSure(dialogId, 0);
    }

    public void checkTranslationFailure(MessageObject messageObject, int type, Runnable onUndo) {
        // No-op
    }

    public void resetTranslatingDialog(long dialogId) {
        // No-op
    }

    public void invalidateTranslations() {
        // No-op
    }

    public void loadRestrictedLanguages() {
        // No-op
    }

    public void toggleRestrictedLanguage(String language, boolean restricted) {
        // No-op
    }

    public Set<String> getRestrictedLanguages() {
        return new HashSet<>();
    }

    public void pushDialogLanguage(long dialogId, String language) {
        // No-op
    }

    public String getDialogDetectedLanguage(long dialogId) {
        return null;
    }

    private void saveTranslatingDialogsCached() {
        // No-op
    }

    private void loadTranslatingDialogsCached() {
        // No-op
    }

    // Helper methods for language name display (used by LanguageSelectActivity)
    public static String languageName(String language) {
        return languageName(language, false);
    }

    public static String languageName(String language, boolean accusative) {
        if (language == null) return null;
        try {
            java.util.Locale locale = new java.util.Locale(language);
            return locale.getDisplayLanguage();
        } catch (Exception e) {
            return language;
        }
    }

    public static String languageName(String language, boolean[] accusative) {
        return languageName(language, accusative != null && accusative.length > 0 && accusative[0]);
    }

    public static String languageNameCapital(String language) {
        return capitalFirst(languageName(language));
    }

    public static String systemLanguageName(String language, boolean own) {
        if (language == null) return null;
        try {
            java.util.Locale locale = new java.util.Locale(language);
            return own ? locale.getDisplayLanguage(locale) : locale.getDisplayLanguage();
        } catch (Exception e) {
            return language;
        }
    }

    public static String capitalFirst(String str) {
        if (str == null || str.length() == 0) return str;
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    public static String getToLanguage() {
        return "en";
    }

    // Static method for restricted languages (called from LoginActivity, LaunchActivity)
    public static void checkRestrictedLanguages(boolean doRequest) {
        // No-op - translation disabled
    }

    public static Set<String> getRestrictedLanguagesStatic() {
        return new HashSet<>();
    }

    public static void updateRestrictedLanguages(HashSet<String> languages, boolean showToast) {
        // No-op
    }

    public static void showAlert(android.app.Activity activity, org.telegram.ui.ActionBar.BaseFragment fragment,
                                 int account, String fromLang, String toLang, CharSequence text,
                                 Object param1, boolean param2, Object param3, Runnable onDismiss) {
        // No-op - translation disabled
    }

    // PollText class stub for serialization compatibility
    public static class PollText extends org.telegram.tgnet.TLObject {
        public TLRPC.TL_textWithEntities question;
        public ArrayList<TLRPC.PollAnswer> answers = new ArrayList<>();
        public TLRPC.TL_textWithEntities solution;

        public static PollText TLdeserialize(org.telegram.tgnet.InputSerializedData stream, int constructor, boolean exception) {
            return new PollText();
        }

        @Override
        public void serializeToStream(org.telegram.tgnet.OutputSerializedData stream) {
            // No-op
        }
    }
}
