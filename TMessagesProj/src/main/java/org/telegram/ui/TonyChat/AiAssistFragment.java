package org.telegram.ui.TonyChat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

import com.tonychat.ai.AiFeatureType;
import com.tonychat.ai.config.AiConfig;
import com.tonychat.ai.consent.AiConsentManager;

/**
 * AI hub dashboard — Tab 1 in bottom nav.
 * Shows status card, 2x3 feature grid, voice card, and settings link.
 */
public class AiAssistFragment extends BaseFragment {

    private static final int COLOR_AMBER = 0xFFD97706;       // text-safe amber (WCAG AA)
    private static final int COLOR_INDIGO = 0xFF6366F1;
    private static final int COLOR_INDIGO_SMALL = 0xFF4F46E5; // for small text (<18sp bold)
    private static final int COLOR_AMBER_ICON = 0xFFF59E0B;   // icons/decorative only

    private LinearLayout contentLayout;

    @Override
    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        return true;
    }

    @Override
    public View createView(Context context) {
        actionBar.setTitle("Tony Assist");
        actionBar.setAllowOverlayTitle(true);

        ActionBarMenu menu = actionBar.createMenu();
        menu.addItem(1, R.drawable.msg_settings_old);
        actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() {
            @Override
            public void onItemClick(int id) {
                if (id == 1) {
                    presentFragment(new AiSettingsActivity());
                }
            }
        });

        fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) fragmentView;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));

        ScrollView scrollView = new ScrollView(context);
        frameLayout.addView(scrollView, LayoutHelper.createFrame(
            LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));

        contentLayout = new LinearLayout(context);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setPadding(AndroidUtilities.dp(16), AndroidUtilities.dp(16),
            AndroidUtilities.dp(16), AndroidUtilities.dp(16));
        scrollView.addView(contentLayout, new ScrollView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        buildStatusCard(context);
        buildQuickActionsGrid(context);
        buildVoiceCard(context);
        buildSetupPrompt(context);

        return fragmentView;
    }

    private void buildStatusCard(Context context) {
        boolean hasKeys = hasAnyApiKey();

        RoundedCardView card = new RoundedCardView(context);
        card.setCardColor(Theme.getColor(Theme.key_windowBackgroundWhite));

        LinearLayout inner = new LinearLayout(context);
        inner.setOrientation(LinearLayout.VERTICAL);
        inner.setPadding(AndroidUtilities.dp(16), AndroidUtilities.dp(14),
            AndroidUtilities.dp(16), AndroidUtilities.dp(14));

        // Status line
        LinearLayout statusRow = new LinearLayout(context);
        statusRow.setOrientation(LinearLayout.HORIZONTAL);
        statusRow.setGravity(Gravity.CENTER_VERTICAL);

        View dot = new View(context);
        dot.setBackgroundColor(hasKeys ? 0xFF10B981 : 0xFFF43F5E);
        dot.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
        LinearLayout.LayoutParams dotParams = new LinearLayout.LayoutParams(
            AndroidUtilities.dp(8), AndroidUtilities.dp(8));
        dotParams.rightMargin = AndroidUtilities.dp(8);
        statusRow.addView(dot, dotParams);

        TextView statusText = new TextView(context);
        statusText.setText(hasKeys ? "AI Status: Ready" : "AI Status: Setup needed");
        statusText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        statusText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        statusText.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        statusRow.addView(statusText);

        inner.addView(statusRow, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 4));

        // Usage summary
        TextView usageText = new TextView(context);
        int featureCount = countEnabledFeatures();
        usageText.setText(featureCount + " feature" + (featureCount != 1 ? "s" : "") + " enabled");
        usageText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
        usageText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        inner.addView(usageText, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        card.addView(inner, LayoutHelper.createFrame(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cardParams.bottomMargin = AndroidUtilities.dp(16);
        contentLayout.addView(card, cardParams);
    }

    private void buildQuickActionsGrid(Context context) {
        // Section header
        TextView header = new TextView(context);
        header.setText("Quick Actions");
        header.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        header.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        header.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        setAccessibilityHeading(header);
        contentLayout.addView(header, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 12));

        // 2-column grid: Row 1
        addFeatureRow(context,
            new FeatureItem("Smart Reply", "AI suggestions", R.drawable.msg_reply,
                AiFeatureType.SMART_REPLY, "Long-press a message to see AI reply suggestions"),
            new FeatureItem("Translate", "Any language", R.drawable.msg_translate,
                AiFeatureType.TRANSLATE, "Long-press a message and select 'AI Translate'")
        );

        // Row 2
        addFeatureRow(context,
            new FeatureItem("Summarize", "Chat digest", R.drawable.msg_copy,
                AiFeatureType.SUMMARY, "Long-press a message and select 'Summary'"),
            new FeatureItem("Tone Rewrite", "Change tone", R.drawable.msg_edit,
                AiFeatureType.TONE_REWRITE, "Long-press your message for tone options")
        );

        // Row 3
        addFeatureRow(context,
            new FeatureItem("Remove BG", "Image editing", R.drawable.msg_photos,
                AiFeatureType.IMAGE_EDIT, "Long-press an image and select 'Remove BG'"),
            new FeatureItem("Emoji Remix", "Create emojis", R.drawable.msg_emoji_activities,
                AiFeatureType.EMOJI_REMIX, "Go to emoji picker for remix options")
        );
    }

    private void addFeatureRow(Context context, FeatureItem left, FeatureItem right) {
        LinearLayout row = new LinearLayout(context);
        row.setOrientation(LinearLayout.HORIZONTAL);

        row.addView(createFeatureCard(context, left), new LinearLayout.LayoutParams(
            0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        View spacer = new View(context);
        row.addView(spacer, new LinearLayout.LayoutParams(
            AndroidUtilities.dp(12), ViewGroup.LayoutParams.MATCH_PARENT));

        row.addView(createFeatureCard(context, right), new LinearLayout.LayoutParams(
            0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rowParams.bottomMargin = AndroidUtilities.dp(12);
        contentLayout.addView(row, rowParams);
    }

    private View createFeatureCard(Context context, FeatureItem item) {
        RoundedCardView card = new RoundedCardView(context);
        card.setCardColor(Theme.getColor(Theme.key_windowBackgroundWhite));

        LinearLayout inner = new LinearLayout(context);
        inner.setOrientation(LinearLayout.VERTICAL);
        inner.setPadding(AndroidUtilities.dp(14), AndroidUtilities.dp(14),
            AndroidUtilities.dp(14), AndroidUtilities.dp(14));

        // Icon (decorative — card itself is clickable)
        ImageView icon = new ImageView(context);
        icon.setImageResource(item.iconRes);
        icon.setColorFilter(COLOR_AMBER_ICON);
        icon.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
        inner.addView(icon, LayoutHelper.createLinear(28, 28, Gravity.START, 0, 0, 0, 8));

        // Title
        TextView title = new TextView(context);
        title.setText(item.title);
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        title.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        title.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        title.setSingleLine(true);
        inner.addView(title, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 2));

        // Subtitle
        TextView subtitle = new TextView(context);
        subtitle.setText(item.subtitle);
        subtitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        subtitle.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        subtitle.setSingleLine(true);
        inner.addView(subtitle, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        card.addView(inner, LayoutHelper.createFrame(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        card.setOnClickListener(v -> showFeatureTooltip(item));
        return card;
    }

    private void buildVoiceCard(Context context) {
        // Section header
        TextView header = new TextView(context);
        header.setText("Voice Transcription");
        header.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        header.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        header.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        setAccessibilityHeading(header);
        LinearLayout.LayoutParams headerParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        headerParams.topMargin = AndroidUtilities.dp(4);
        headerParams.bottomMargin = AndroidUtilities.dp(12);
        contentLayout.addView(header, headerParams);

        RoundedCardView card = new RoundedCardView(context);
        card.setCardColor(Theme.getColor(Theme.key_windowBackgroundWhite));

        LinearLayout inner = new LinearLayout(context);
        inner.setOrientation(LinearLayout.HORIZONTAL);
        inner.setPadding(AndroidUtilities.dp(16), AndroidUtilities.dp(16),
            AndroidUtilities.dp(16), AndroidUtilities.dp(16));
        inner.setGravity(Gravity.CENTER_VERTICAL);

        // Mic icon (decorative)
        ImageView mic = new ImageView(context);
        mic.setImageResource(R.drawable.input_mic);
        mic.setColorFilter(COLOR_AMBER_ICON);
        mic.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
        inner.addView(mic, LayoutHelper.createLinear(32, 32, Gravity.CENTER_VERTICAL, 0, 0, 14, 0));

        // Text
        LinearLayout textCol = new LinearLayout(context);
        textCol.setOrientation(LinearLayout.VERTICAL);

        TextView title = new TextView(context);
        title.setText("Transcribe voice messages");
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        title.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        title.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        textCol.addView(title, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 2));

        TextView desc = new TextView(context);
        desc.setText("Long-press a voice message to transcribe");
        desc.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
        desc.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText));
        textCol.addView(desc, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        inner.addView(textCol, new LinearLayout.LayoutParams(
            0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        card.addView(inner, LayoutHelper.createFrame(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cardParams.bottomMargin = AndroidUtilities.dp(16);
        contentLayout.addView(card, cardParams);
    }

    private void buildSetupPrompt(Context context) {
        if (hasAnyApiKey()) return;

        RoundedCardView card = new RoundedCardView(context);
        card.setCardColor(0x1A6366F1); // Indigo 10% alpha

        LinearLayout inner = new LinearLayout(context);
        inner.setOrientation(LinearLayout.VERTICAL);
        inner.setPadding(AndroidUtilities.dp(16), AndroidUtilities.dp(16),
            AndroidUtilities.dp(16), AndroidUtilities.dp(16));
        inner.setGravity(Gravity.CENTER);

        TextView title = new TextView(context);
        title.setText("Set up AI providers to get started");
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        title.setTextColor(COLOR_INDIGO_SMALL); // small text needs darker indigo for WCAG AA
        title.setGravity(Gravity.CENTER);
        title.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        inner.addView(title, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 0, 0, 0, 10));

        // Setup button
        TextView btn = new TextView(context);
        btn.setText("Open AI Settings");
        btn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
        btn.setTextColor(0xFFFFFFFF);
        btn.setBackgroundColor(COLOR_INDIGO);
        btn.setGravity(Gravity.CENTER);
        btn.setMinimumHeight(AndroidUtilities.dp(48));
        btn.setPadding(AndroidUtilities.dp(24), AndroidUtilities.dp(10),
            AndroidUtilities.dp(24), AndroidUtilities.dp(10));
        btn.setOnClickListener(v -> presentFragment(new AiSettingsActivity()));
        inner.addView(btn, LayoutHelper.createLinear(
            LayoutHelper.WRAP_CONTENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER));

        card.addView(inner, LayoutHelper.createFrame(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        contentLayout.addView(card, LayoutHelper.createLinear(
            LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));
    }

    private void showFeatureTooltip(FeatureItem item) {
        Context context = getParentActivity();
        if (context == null) return;
        android.widget.Toast.makeText(context, item.tooltip, android.widget.Toast.LENGTH_LONG).show();
    }

    private boolean hasAnyApiKey() {
        try {
            return AiConfig.INSTANCE.getOpenAiApiKey() != null
                || AiConfig.INSTANCE.getAnthropicApiKey() != null
                || AiConfig.INSTANCE.getRemoveBgApiKey() != null
                || AiConfig.INSTANCE.getGeminiApiKey() != null;
        } catch (Exception e) {
            return false;
        }
    }

    private int countEnabledFeatures() {
        int count = 0;
        try {
            for (AiFeatureType feature : AiFeatureType.values()) {
                if (AiConsentManager.INSTANCE.hasConsent(feature)
                    && AiConfig.INSTANCE.isFeatureEnabled(feature)) {
                    count++;
                }
            }
        } catch (Exception e) {
            // AiConfig may not be initialized
        }
        return count;
    }

    private static void setAccessibilityHeading(View view) {
        if (android.os.Build.VERSION.SDK_INT >= 28) {
            view.setAccessibilityDelegate(new View.AccessibilityDelegate() {
                @Override
                public void onInitializeAccessibilityNodeInfo(View host, android.view.accessibility.AccessibilityNodeInfo info) {
                    super.onInitializeAccessibilityNodeInfo(host, info);
                    info.setHeading(true);
                }
            });
        }
    }

    private static class FeatureItem {
        final String title;
        final String subtitle;
        final int iconRes;
        final AiFeatureType featureType;
        final String tooltip;

        FeatureItem(String title, String subtitle, int iconRes,
                    AiFeatureType featureType, String tooltip) {
            this.title = title;
            this.subtitle = subtitle;
            this.iconRes = iconRes;
            this.featureType = featureType;
            this.tooltip = tooltip;
        }
    }

    /** Simple rounded-corner card wrapper. */
    static class RoundedCardView extends FrameLayout {
        private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private final RectF rect = new RectF();
        private final float radius = AndroidUtilities.dp(12);

        RoundedCardView(Context context) {
            super(context);
            setWillNotDraw(false);
            paint.setColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        }

        void setCardColor(int color) {
            paint.setColor(color);
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            rect.set(0, 0, getWidth(), getHeight());
            canvas.drawRoundRect(rect, radius, radius, paint);
        }
    }
}
