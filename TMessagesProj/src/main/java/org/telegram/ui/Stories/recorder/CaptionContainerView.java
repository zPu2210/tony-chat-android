package org.telegram.ui.Stories.recorder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.widget.FrameLayout;

/**
 * CaptionContainerView stub - Stories removed in Tony Chat
 */
public class CaptionContainerView extends FrameLayout {
    public EditTextCaption editText;
    public MentionContainer mentionContainer;
    public KeyboardNotifier keyboardNotifier;
    protected int currentAccount;
    protected boolean toKeyboardShow;
    protected boolean keyboardShown;
    protected RectF bounds = new RectF();
    protected BackgroundBlur backgroundBlur = new BackgroundBlur();
    protected Paint backgroundPaint = new Paint();
    protected Object moveButtonText;

    public CaptionContainerView(Context context) {
        super(context);
        init();
    }

    public CaptionContainerView(Context context, View rootView, View sizeNotifierFrameLayout, View containerView, Object resourcesProvider, Object blurManager) {
        super(context);
        init();
    }

    private void init() {
        editText = new EditTextCaption(getContext());
        mentionContainer = new MentionContainer(getContext());
        keyboardNotifier = new KeyboardNotifier(this, () -> {});
    }

    public void setHint(CharSequence hint) {}
    public CharSequence getText() { return ""; }
    public void setAccount(int account) {
        this.currentAccount = account;
    }
    protected boolean isAtTop() { return false; }
    public void updateMentionContainer() {}
    public void applyService(boolean apply) {}
    public void updateKeyboard(int keyboardHeight) {}
    protected boolean customBlur() { return false; }
    protected void drawBlur(Object blur, Canvas canvas, RectF rect, float r, boolean text, float ox, float oy, boolean thisView, float alpha) {}
    protected int getEditTextStyle() { return 0; }
    protected void updateEditTextLeft() {}
    protected int getEditTextLeft() { return 0; }
    protected void updateColors(Object resourcesProvider) {}
    protected int getEditTextHeight() { return 0; }
    public void setOnHeightUpdate(HeightUpdateListener callback) {}

    @FunctionalInterface
    public interface HeightUpdateListener {
        void onHeightUpdate(int height);
    }
    public void setText(CharSequence text) {}
    public void createMentionsContainer() {}
    public void onEmojiButtonClicked(boolean b) {}
    public void setPeriod(int period) {}
    public float getPeriod() { return 0; }
    public void setTimer(int timer) {}
    public void resetTimerToPeriod() {}
    public void setAddPhotoVisible(boolean visible, boolean animated) {}
    public void onAddPhotoClicked(Runnable callback) {}
    public void showRemoveTimerHint() {}
    public void onTimerChange(Runnable callback) {}
    protected void setupMentionContainer() {}
    protected boolean captionLimitToast() { return false; }
    public void updateMentionsLayoutPosition() {}
    protected boolean ignoreTouches(float x, float y) { return false; }
    public void setShowMoveButtonVisible(boolean show, boolean animated) {}
    protected void onTextChange() {}
    protected void onEditHeightChange(int height) {}
    protected boolean clipChild(View child) { return false; }
    protected int getCaptionLimit() { return 2048; }
    protected int getCaptionDefaultLimit() { return 1024; }
    protected int getCaptionPremiumLimit() { return 2048; }
    protected void beforeUpdateShownKeyboard(boolean show) {}
    protected void onUpdateShowKeyboard(float keyboardT) {}
    protected void afterUpdateShownKeyboard(boolean show) {}
    protected int additionalKeyboardHeight() { return 0; }
    public boolean isCaptionOverLimit() { return false; }
    public CharSequence limitTextView = "";
    public int getCodePointCount() { return 0; }
    public void onBackPressed() {}
    public int getSelectionLength() { return 0; }
    public RectF getBounds() { return bounds; }
    public void invalidateBlur() { invalidate(); }
    public void closeKeyboard() {}
    public void setDialogId(long dialogId) {}
    public int getEditTextHeightClosedKeyboard() { return 100; }

    public static class EditTextCaption extends View {
        public EditTextCaption(Context context) {
            super(context);
        }

        public boolean isPopupView(View view) {
            return false;
        }

        public EditText getEditText() {
            return new EditText(getContext());
        }

        public boolean isPopupShowing() { return false; }
        public void hidePopup(boolean animated) {}
        public void closeKeyboard() {}
        public boolean isKeyboardVisible() { return false; }
    }

    public static class EditText extends android.widget.EditText {
        public EditText(Context context) {
            super(context);
        }

        public void setAllowTextEntitiesIntersection(boolean allow) {}
        public boolean getAllowTextEntitiesIntersection() { return false; }
    }

    public static class BackgroundBlur {
        public Paint[] getPaints(float alpha, float x, float y) {
            return null; // Stub returns null
        }
    }

    public static class MentionContainer extends View {
        private MentionAdapter adapter = new MentionAdapter();

        public MentionContainer(Context context) {
            super(context);
        }

        public MentionAdapter getAdapter() {
            return adapter;
        }

        public void setReversed(boolean reversed) {}
    }

    public static class MentionAdapter {
        public void setAllowStickers(boolean allow) {}
        public void setAllowBots(boolean allow) {}
        public void setAllowChats(boolean allow) {}
        public void setSearchInDailogs(boolean search) {}
        public void setChatInfo(Object chatInfo) {}
        public void setNeedUsernames(boolean need) {}
        public void setNeedBotContext(boolean need) {}
    }

    public static class PeriodDrawable extends android.graphics.drawable.Drawable {
        public float diameterDp = 14;
        public float textOffsetX = 0;
        public float textOffsetY = 0;
        public Paint strokePaint = new Paint();

        public PeriodDrawable() {
            strokePaint.setStyle(Paint.Style.STROKE);
        }

        public PeriodDrawable(Object resourcesProvider) {
            strokePaint.setStyle(Paint.Style.STROKE);
        }

        @Override
        public void draw(Canvas canvas) {}

        public void draw(Canvas canvas, float scale) {}

        @Override
        public void setAlpha(int alpha) {}

        @Override
        public void setColorFilter(android.graphics.ColorFilter colorFilter) {}

        @Override
        public int getOpacity() {
            return android.graphics.PixelFormat.TRANSLUCENT;
        }

        public void updateColors(int color1, int color2, int color3) {}
        public void setTextSize(int size) {}
        public void setTextSize(float size) {}
        public void setValue(int value, boolean animated1, boolean animated2) {}
        public void setClear(boolean clear) {}
        public void setCenterXY(float x, float y) {}
    }
}
