package org.telegram.ui;

import android.text.SpannableStringBuilder;

import org.telegram.ui.ActionBar.BaseFragment;

/**
 * STUB: Premium upsell screen removed.
 * This stub prevents compilation errors in callers.
 */
public class PremiumPreviewFragment extends BaseFragment {

    // Feature type constants referenced by callers
    public static final int PREMIUM_FEATURE_TRANSLATIONS = 0;
    public static final int PREMIUM_FEATURE_BUSINESS_QUICK_REPLIES = 1;
    public static final int PREMIUM_FEATURE_SAVED_TAGS = 2;
    public static final int PREMIUM_FEATURE_DOWNLOAD_SPEED = 3;
    public static final int PREMIUM_FEATURE_BUSINESS = 4;
    public static final int PREMIUM_FEATURE_TODO = 5;
    public static final int PREMIUM_FEATURE_ADS = 6;
    public static final int PREMIUM_FEATURE_VOICE_TO_TEXT = 7;
    public static final int PREMIUM_FEATURE_EMOJI_STATUS = 8;
    public static final int PREMIUM_FEATURE_APPLICATION_ICONS = 9;
    public static final int PREMIUM_FEATURE_ADVANCED_CHAT_MANAGEMENT = 10;
    public static final int PREMIUM_FEATURE_REACTIONS = 11;
    public static final int PREMIUM_FEATURE_ANIMATED_EMOJI = 12;
    public static final int PREMIUM_FEATURE_STORIES = 13;
    public static final int PREMIUM_FEATURE_ANIMATED_AVATARS = 14;
    public static final int PREMIUM_FEATURE_MESSAGE_PRIVACY = 15;
    public static final int PREMIUM_FEATURE_NAME_COLOR = 16;
    public static final int PREMIUM_FEATURE_STICKERS = 17;
    public static final int PREMIUM_FEATURE_WALLPAPER = 18;
    public static final int PREMIUM_FEATURE_FOLDER_TAGS = 19;
    public static final int PREMIUM_FEATURE_PROFILE_BADGE = 20;
    public static final int PREMIUM_FEATURE_LIMITS = 21;
    public static final int PREMIUM_FEATURE_UPLOAD_LIMIT = 22;

    // Feature page constants (different from PREMIUM_FEATURE_ above)
    public static final int FEATURES_BUSINESS = 100;
    public static final int FEATURES_PREMIUM = 101;

    public static final String TRANSACTION_PATTERN = ".*";

    // Constructors used by callers
    public PremiumPreviewFragment(String source) {
        // No-op stub
    }

    public PremiumPreviewFragment(int type, String source) {
        // No-op stub
    }

    // Builder-pattern methods
    public PremiumPreviewFragment setForcePremium() {
        return this;
    }

    public PremiumPreviewFragment setSelectAnnualByDefault() {
        return this;
    }

    // Static utility methods used by callers
    public static String featureTypeToServerString(int feature) {
        return "";
    }

    public static SpannableStringBuilder applyNewSpan(CharSequence text) {
        return new SpannableStringBuilder(text);
    }

    public static SpannableStringBuilder applyNewSpan(CharSequence text, int offset) {
        return new SpannableStringBuilder(text);
    }

    public static void applyNewSpan(SpannableStringBuilder builder) {
        // No-op stub
    }

    public static int serverStringToFeatureType(String s) {
        return 0;
    }

    public static void sentPremiumBuyCanceled() {
        // No-op stub
    }

    // Inner classes referenced by other Premium components
    public static class PremiumFeatureData {
        public int type;
        public int icon;
        public String title;
        public String description;

        public PremiumFeatureData(int type, int icon, String title, String description) {
            this.type = type;
            this.icon = icon;
            this.title = title;
            this.description = description;
        }
    }

    public static class SubscriptionTier {
        public String currency;
        public long amount;
        public int months;
        public SubscriptionOption subscriptionOption = new SubscriptionOption();

        public SubscriptionTier() {
        }

        public int getMonths() { return months; }
        public int getDiscount() { return 0; }
        public String getFormattedPricePerYear() { return ""; }
        public String getFormattedPricePerMonth() { return ""; }
        public String getFormattedPricePerYearRegular() { return ""; }

        public static class SubscriptionOption {
            public boolean current;
        }
    }
}
