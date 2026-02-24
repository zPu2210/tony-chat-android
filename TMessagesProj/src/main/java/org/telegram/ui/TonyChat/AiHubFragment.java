package org.telegram.ui.TonyChat;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.GradientDrawable;
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
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

/**
 * AI Hub v2.0 — Tab 0 in bottom nav.
 * Shows 6 standalone AI tool cards in a 3x2 grid.
 * Each card is a placeholder until Phase 2 wires up the actual tool screens.
 */
public class AiHubFragment extends BaseFragment {

    private static final ToolItem[] TOOLS = {
            new ToolItem("AI Writer", "Fix grammar, adjust tone, write emails",
                    R.drawable.msg_edit, 0xFF3B82F6),
            new ToolItem("AI Translator", "Translate any text instantly",
                    R.drawable.msg_translate, 0xFF10B981),
            new ToolItem("Remove BG", "Remove photo backgrounds",
                    R.drawable.msg_photos, 0xFFF97316),
            new ToolItem("Upscale", "Enhance photo resolution",
                    R.drawable.msg_photo_settings, 0xFF8B5CF6),
            new ToolItem("Remove Text", "Erase text from images",
                    R.drawable.msg_clear, 0xFFF43F5E),
            new ToolItem("AI Generate", "Create images from text",
                    R.drawable.msg_addphoto, 0xFF06B6D4),
    };

    @Override
    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        return true;
    }

    @Override
    public View createView(Context context) {
        actionBar.setTitle("Tony AI");
        actionBar.setAllowOverlayTitle(true);

        fragmentView = new FrameLayout(context);
        FrameLayout root = (FrameLayout) fragmentView;
        root.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));

        ScrollView scrollView = new ScrollView(context);
        root.addView(scrollView, LayoutHelper.createFrame(
                LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT));

        LinearLayout content = new LinearLayout(context);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(20), dp(16), dp(20), dp(100));
        scrollView.addView(content, new ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // Hero subtitle
        TextView subtitle = new TextView(context);
        subtitle.setText("What can I help you with?");
        subtitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        subtitle.setTextColor(TonyColors.textSecondary());
        content.addView(subtitle, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 20));

        // 3 rows x 2 cards
        for (int i = 0; i < TOOLS.length; i += 2) {
            addToolRow(context, content, TOOLS[i], TOOLS[i + 1]);
        }

        return fragmentView;
    }

    private void addToolRow(Context context, LinearLayout parent,
                            ToolItem left, ToolItem right) {
        LinearLayout row = new LinearLayout(context);
        row.setOrientation(LinearLayout.HORIZONTAL);

        row.addView(buildToolCard(context, left),
                new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        View spacer = new View(context);
        row.addView(spacer, new LinearLayout.LayoutParams(dp(12), 0));

        row.addView(buildToolCard(context, right),
                new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.bottomMargin = dp(12);
        parent.addView(row, lp);
    }

    private View buildToolCard(Context context, ToolItem tool) {
        RoundedCardView card = new RoundedCardView(context);
        card.setCardColor(TonyColors.backgroundSecondary());

        LinearLayout inner = new LinearLayout(context);
        inner.setOrientation(LinearLayout.VERTICAL);
        inner.setPadding(dp(16), dp(16), dp(16), dp(16));

        // Colored icon circle
        FrameLayout iconCircle = new FrameLayout(context);
        GradientDrawable circleBg = new GradientDrawable();
        circleBg.setShape(GradientDrawable.OVAL);
        circleBg.setColor(tool.color);
        iconCircle.setBackground(circleBg);

        ImageView icon = new ImageView(context);
        icon.setImageResource(tool.iconRes);
        icon.setColorFilter(new PorterDuffColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_IN));
        icon.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
        iconCircle.addView(icon, LayoutHelper.createFrame(22, 22, Gravity.CENTER));
        inner.addView(iconCircle, LayoutHelper.createLinear(40, 40, 0, 0, 0, 12));

        // Title
        TextView title = new TextView(context);
        title.setText(tool.title);
        title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        title.setTextColor(TonyColors.textPrimary());
        title.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        title.setSingleLine(true);
        inner.addView(title, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, 0, 0, 0, 4));

        // Description
        TextView desc = new TextView(context);
        desc.setText(tool.description);
        desc.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 13);
        desc.setTextColor(TonyColors.textSecondary());
        desc.setMaxLines(2);
        inner.addView(desc, LayoutHelper.createLinear(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        card.addView(inner, LayoutHelper.createFrame(
                LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT));

        card.setOnClickListener(v -> {
            LoginPromptSheet.checkAndRun(AiHubFragment.this, () -> {
                if (getParentActivity() != null) {
                    android.widget.Toast.makeText(getParentActivity(),
                            tool.title + " \u2014 Coming soon!",
                            android.widget.Toast.LENGTH_SHORT).show();
                }
            });
        });

        return card;
    }

    private static int dp(float value) {
        return AndroidUtilities.dp(value);
    }

    private static class ToolItem {
        final String title;
        final String description;
        final int iconRes;
        final int color;

        ToolItem(String title, String description, int iconRes, int color) {
            this.title = title;
            this.description = description;
            this.iconRes = iconRes;
            this.color = color;
        }
    }
}
