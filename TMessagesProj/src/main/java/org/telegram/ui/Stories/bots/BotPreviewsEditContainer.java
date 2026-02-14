package org.telegram.ui.Stories.bots;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * BotPreviewsEditContainer stub - Stories removed in Tony Chat
 */
public class BotPreviewsEditContainer extends FrameLayout {
    public BotPreviewsEditContainer(Context context) {
        super(context);
    }

    public BotPreviewsEditContainer(Context context, Object fragment, long dialogId) {
        super(context);
    }

    public boolean checkPinchToZoom(MotionEvent ev) {
        return false;
    }

    public int getItemsCount() {
        return 0;
    }

    public String getCurrentLang() {
        return "";
    }

    public boolean isSelectedAll() {
        return false;
    }

    public void selectAll() {}
    public void unselectAll() {}
    public void deleteLang(String lang) {}

    public void onSelectedTabChanged() {}
    public void closeActionMode() {}

    public org.telegram.ui.Stories.StoriesController.BotPreviewsList getCurrentList() { return null; }

    protected boolean isSelected(org.telegram.messenger.MessageObject messageObject) { return false; }
    protected boolean select(org.telegram.messenger.MessageObject messageObject) { return false; }
    protected boolean unselect(org.telegram.messenger.MessageObject messageObject) { return false; }
    public void updateSelection(boolean animated) {}
}
