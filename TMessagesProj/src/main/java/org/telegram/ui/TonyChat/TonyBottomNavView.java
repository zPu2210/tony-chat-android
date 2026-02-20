package org.telegram.ui.TonyChat;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.view.Gravity;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

/**
 * Icon-only bottom navigation for Tony Chat.
 * 4 tabs: Community | AI Assist | Chats (default) | Settings
 * Active tab = Indigo icon (animated), inactive = gray.
 */
public class TonyBottomNavView extends FrameLayout {

    public interface OnTabSelectedListener {
        void onTabSelected(int index);
    }

    public static final int TAB_COMMUNITY = 0;
    public static final int TAB_AI_ASSIST = 1;
    public static final int TAB_CHATS = 2;
    public static final int TAB_SETTINGS = 3;
    private static final int TAB_COUNT = 4;
    private static final int ANIM_DURATION = 200;

    private static final int[] ICON_RES = {
        R.drawable.ic_tony_community,
        R.drawable.ic_tony_ai,
        R.drawable.ic_tony_chats,
        R.drawable.ic_tony_settings
    };

    private static final String[] CONTENT_DESCRIPTIONS = {
        "Community Board tab", "AI Assist tab", "Chats tab", "Settings tab"
    };

    private final ImageView[] icons = new ImageView[TAB_COUNT];
    private final int[] currentIconColors = new int[TAB_COUNT];
    private final Paint separatorPaint = new Paint();
    private final Paint badgePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint badgeTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int activeTab = TAB_CHATS;
    private int unreadCount = 0;
    private OnTabSelectedListener listener;

    public TonyBottomNavView(Context context) {
        super(context);
        setWillNotDraw(false);

        separatorPaint.setColor(Theme.getColor(Theme.key_divider));
        separatorPaint.setStrokeWidth(1);

        badgePaint.setColor(TonyColors.primary());
        badgeTextPaint.setColor(0xFFFFFFFF);
        badgeTextPaint.setTextSize(AndroidUtilities.dp(10));
        badgeTextPaint.setTextAlign(Paint.Align.CENTER);

        setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));

        LinearLayout tabRow = new LinearLayout(context);
        tabRow.setOrientation(LinearLayout.HORIZONTAL);
        tabRow.setGravity(Gravity.CENTER_VERTICAL);

        int activeColor = TonyColors.primary();
        int inactiveColor = Theme.getColor(Theme.key_windowBackgroundWhiteGrayText);

        for (int i = 0; i < TAB_COUNT; i++) {
            final int tabIndex = i;
            FrameLayout tabContainer = new FrameLayout(context);

            ImageView icon = new ImageView(context);
            icon.setImageResource(ICON_RES[i]);
            icon.setScaleType(ImageView.ScaleType.CENTER);
            icon.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
            tabContainer.addView(icon, LayoutHelper.createFrame(24, 24, Gravity.CENTER));

            tabContainer.setContentDescription(CONTENT_DESCRIPTIONS[tabIndex]
                + (tabIndex == activeTab ? ", selected" : ""));
            tabContainer.setAccessibilityDelegate(new View.AccessibilityDelegate() {
                @Override
                public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfo info) {
                    super.onInitializeAccessibilityNodeInfo(host, info);
                    info.setClassName("android.widget.TabWidget");
                    info.setSelected(tabIndex == activeTab);
                }
            });

            tabContainer.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onTabSelected(tabIndex);
                }
            });

            int color = (i == activeTab) ? activeColor : inactiveColor;
            currentIconColors[i] = color;
            icon.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));

            icons[i] = icon;
            tabRow.addView(tabContainer, new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.MATCH_PARENT, 1f));
        }

        addView(tabRow, LayoutHelper.createFrame(
            LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT, Gravity.CENTER));
    }

    public void setOnTabSelectedListener(OnTabSelectedListener listener) {
        this.listener = listener;
    }

    public void setActiveTab(int index) {
        if (index < 0 || index >= TAB_COUNT) return;
        int prevTab = activeTab;
        activeTab = index;
        animateIconColors(prevTab, index);
        updateAccessibility();
    }

    public int getActiveTab() {
        return activeTab;
    }

    public void setUnreadCount(int count) {
        if (unreadCount != count) {
            unreadCount = count;
            invalidate();
        }
    }

    /** Animate color transition for old and new active tab icons. */
    private void animateIconColors(int fromTab, int toTab) {
        int activeColor = TonyColors.primary();
        int inactiveColor = Theme.getColor(Theme.key_windowBackgroundWhiteGrayText);

        // Deactivate old tab
        if (fromTab >= 0 && fromTab < TAB_COUNT && fromTab != toTab) {
            animateSingleIcon(fromTab, currentIconColors[fromTab], inactiveColor);
        }
        // Activate new tab
        if (toTab >= 0 && toTab < TAB_COUNT) {
            animateSingleIcon(toTab, currentIconColors[toTab], activeColor);
        }
    }

    private void animateSingleIcon(int tabIndex, int fromColor, int toColor) {
        if (fromColor == toColor) {
            return;
        }
        ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), fromColor, toColor);
        animator.setDuration(ANIM_DURATION);
        animator.addUpdateListener(anim -> {
            int color = (int) anim.getAnimatedValue();
            currentIconColors[tabIndex] = color;
            icons[tabIndex].setColorFilter(
                new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
        });
        animator.start();
    }

    private void updateAccessibility() {
        View tabRow = getChildAt(0);
        if (tabRow instanceof LinearLayout) {
            for (int i = 0; i < TAB_COUNT; i++) {
                View tab = ((LinearLayout) tabRow).getChildAt(i);
                if (tab != null) {
                    tab.setSelected(i == activeTab);
                    tab.setContentDescription(CONTENT_DESCRIPTIONS[i]
                        + (i == activeTab ? ", selected" : ""));
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Top separator line
        canvas.drawLine(0, 0, getWidth(), 0, separatorPaint);

        // Badge on Chats tab
        if (unreadCount > 0 && icons[TAB_CHATS] != null) {
            View chatIcon = icons[TAB_CHATS];
            int[] loc = new int[2];
            chatIcon.getLocationInWindow(loc);
            int[] myLoc = new int[2];
            getLocationInWindow(myLoc);

            float iconCenterX = loc[0] - myLoc[0] + chatIcon.getWidth() / 2f;
            float iconTop = loc[1] - myLoc[1];

            float badgeX = iconCenterX + AndroidUtilities.dp(8);
            float badgeY = iconTop + AndroidUtilities.dp(2);
            float radius = AndroidUtilities.dp(8);

            String text = unreadCount > 99 ? "99+" : String.valueOf(unreadCount);
            float textWidth = badgeTextPaint.measureText(text);
            float minWidth = radius * 2;
            float badgeWidth = Math.max(minWidth, textWidth + AndroidUtilities.dp(8));

            canvas.drawRoundRect(
                badgeX - badgeWidth / 2, badgeY - radius,
                badgeX + badgeWidth / 2, badgeY + radius,
                radius, radius, badgePaint);

            Paint.FontMetrics fm = badgeTextPaint.getFontMetrics();
            float textY = badgeY - (fm.ascent + fm.descent) / 2;
            canvas.drawText(text, badgeX, textY, badgeTextPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = AndroidUtilities.dp(56);
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }

    /** Update colors when theme changes. */
    public void updateColors() {
        setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        separatorPaint.setColor(Theme.getColor(Theme.key_divider));
        badgePaint.setColor(TonyColors.primary());

        int activeColor = TonyColors.primary();
        int inactiveColor = Theme.getColor(Theme.key_windowBackgroundWhiteGrayText);
        for (int i = 0; i < TAB_COUNT; i++) {
            int color = (i == activeTab) ? activeColor : inactiveColor;
            currentIconColors[i] = color;
            icons[i].setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
        }
        invalidate();
    }
}
