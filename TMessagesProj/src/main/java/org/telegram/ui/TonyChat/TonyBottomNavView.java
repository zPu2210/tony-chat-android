package org.telegram.ui.TonyChat;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

/**
 * Floating pill bottom navigation for Tony Chat v2.0.
 * 3 tabs: Chats (default) | Community | Settings
 * Active = yellow #F9E000 icon+label, inactive = #777777.
 * Pill: white bg, cornerRadius 33dp, elevation shadow.
 */
public class TonyBottomNavView extends FrameLayout {

    public interface OnTabSelectedListener {
        void onTabSelected(int index);
    }

    public static final int TAB_CHATS = 0;
    public static final int TAB_COMMUNITY = 1;
    public static final int TAB_SETTINGS = 2;
    public static final int TAB_COUNT = 3;

    private static final int ANIM_DURATION = 200;
    private static final int ACTIVE_COLOR = 0xFF111111;
    private static final int INACTIVE_COLOR = 0xFF777777;
    private static final int PILL_COLOR = 0xFFF9E000;

    private static final int[] ICON_RES = {
            R.drawable.ic_tony_chats,
            R.drawable.ic_tony_community,
            R.drawable.ic_tony_settings
    };

    private static final String[] TAB_LABELS = {
            "Chats", "Community", "Settings"
    };

    private static final String[] CONTENT_DESCRIPTIONS = {
            "Chats tab", "Community tab", "Settings tab"
    };

    private final ImageView[] icons = new ImageView[TAB_COUNT];
    private final TextView[] labels = new TextView[TAB_COUNT];
    private final int[] currentColors = new int[TAB_COUNT];
    private final Paint badgePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint badgeTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint pillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final RectF pillRect = new RectF();

    private int activeTab = TAB_CHATS;
    private int unreadCount = 0;
    private OnTabSelectedListener listener;

    public TonyBottomNavView(Context context) {
        super(context);
        setWillNotDraw(false);

        // Yellow pill behind active tab
        pillPaint.setColor(PILL_COLOR);

        // Red badge for unread count
        badgePaint.setColor(0xFFFF3B30);
        badgeTextPaint.setColor(0xFFFFFFFF);
        badgeTextPaint.setTextSize(AndroidUtilities.dp(10));
        badgeTextPaint.setTextAlign(Paint.Align.CENTER);

        // Floating pill shape
        setOutlineProvider(new android.view.ViewOutlineProvider() {
            @Override
            public void getOutline(View view, android.graphics.Outline outline) {
                outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(),
                        AndroidUtilities.dp(33));
            }
        });
        setClipToOutline(true);
        setBackgroundColor(0xFFFFFFFF);
        setElevation(AndroidUtilities.dp(15));

        // Tab row
        LinearLayout tabRow = new LinearLayout(context);
        tabRow.setOrientation(LinearLayout.HORIZONTAL);
        tabRow.setGravity(Gravity.CENTER);

        for (int i = 0; i < TAB_COUNT; i++) {
            final int tabIndex = i;

            // Each tab: vertical stack of icon + label
            LinearLayout tabContainer = new LinearLayout(context);
            tabContainer.setOrientation(LinearLayout.VERTICAL);
            tabContainer.setGravity(Gravity.CENTER_HORIZONTAL);
            tabContainer.setPadding(0, AndroidUtilities.dp(10), 0, AndroidUtilities.dp(8));

            ImageView icon = new ImageView(context);
            icon.setImageResource(ICON_RES[i]);
            icon.setScaleType(ImageView.ScaleType.CENTER);
            icon.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
            tabContainer.addView(icon, new LinearLayout.LayoutParams(
                    AndroidUtilities.dp(24), AndroidUtilities.dp(24)));

            TextView label = new TextView(context);
            label.setText(TAB_LABELS[i]);
            label.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            label.setGravity(Gravity.CENTER);
            label.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
            LinearLayout.LayoutParams labelParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            labelParams.topMargin = AndroidUtilities.dp(4);
            tabContainer.addView(label, labelParams);

            // Initial colors
            int color = (i == activeTab) ? ACTIVE_COLOR : INACTIVE_COLOR;
            currentColors[i] = color;
            icon.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
            label.setTextColor(color);
            if (i == activeTab) {
                label.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            }

            // Accessibility
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

            icons[i] = icon;
            labels[i] = label;
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
        animateTabTransition(prevTab, index);
        updateAccessibility();
    }

    public int getActiveTab() {
        return activeTab;
    }

    public int getSelectedTab() {
        return activeTab;
    }

    public void selectTab(int index) {
        setActiveTab(index);
        if (listener != null) {
            listener.onTabSelected(index);
        }
    }

    public void setUnreadCount(int count) {
        if (unreadCount != count) {
            unreadCount = count;
            invalidate();
        }
    }

    private void animateTabTransition(int fromTab, int toTab) {
        // Deactivate old tab
        if (fromTab >= 0 && fromTab < TAB_COUNT && fromTab != toTab) {
            animateSingleTab(fromTab, currentColors[fromTab], INACTIVE_COLOR, false);
        }
        // Activate new tab
        if (toTab >= 0 && toTab < TAB_COUNT) {
            animateSingleTab(toTab, currentColors[toTab], ACTIVE_COLOR, true);
        }
        // Repaint pill position
        invalidate();
    }

    private void animateSingleTab(int tabIndex, int fromColor, int toColor, boolean active) {
        if (fromColor == toColor) {
            // Just update typeface
            labels[tabIndex].setTypeface(active
                    ? AndroidUtilities.getTypeface("fonts/rmedium.ttf") : Typeface.DEFAULT);
            return;
        }
        ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), fromColor, toColor);
        animator.setDuration(ANIM_DURATION);
        final boolean setActive = active;
        animator.addUpdateListener(anim -> {
            int color = (int) anim.getAnimatedValue();
            currentColors[tabIndex] = color;
            icons[tabIndex].setColorFilter(
                    new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
            labels[tabIndex].setTextColor(color);
        });
        animator.start();
        labels[tabIndex].setTypeface(active
                ? AndroidUtilities.getTypeface("fonts/rmedium.ttf") : Typeface.DEFAULT);
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
    protected void dispatchDraw(Canvas canvas) {
        // Draw yellow pill behind active tab BEFORE children
        View tabRow = getChildAt(0);
        if (tabRow instanceof LinearLayout) {
            View activeChild = ((LinearLayout) tabRow).getChildAt(activeTab);
            if (activeChild != null) {
                float left = activeChild.getLeft() + tabRow.getLeft() + AndroidUtilities.dp(8);
                float top = activeChild.getTop() + tabRow.getTop() + AndroidUtilities.dp(6);
                float right = activeChild.getRight() + tabRow.getLeft() - AndroidUtilities.dp(8);
                float bottom = activeChild.getBottom() + tabRow.getTop() - AndroidUtilities.dp(4);
                float radius = AndroidUtilities.dp(16);
                pillRect.set(left, top, right, bottom);
                canvas.drawRoundRect(pillRect, radius, radius, pillPaint);
            }
        }

        super.dispatchDraw(canvas);

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
        int height = AndroidUtilities.dp(66);
        super.onMeasure(widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
    }

    public void updateColors() {
        setBackgroundColor(0xFFFFFFFF);
        pillPaint.setColor(PILL_COLOR);
        badgePaint.setColor(0xFFFF3B30);
        for (int i = 0; i < TAB_COUNT; i++) {
            int color = (i == activeTab) ? ACTIVE_COLOR : INACTIVE_COLOR;
            currentColors[i] = color;
            icons[i].setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
            labels[i].setTextColor(color);
            labels[i].setTypeface(i == activeTab
                    ? AndroidUtilities.getTypeface("fonts/rmedium.ttf") : Typeface.DEFAULT);
        }
        invalidate();
    }
}
