package org.telegram.ui.bots;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

/**
 * STUB: Bot Commands Menu View removed from Tony Chat.
 * Bot commands UI features are not supported.
 */
public class BotCommandsMenuView extends View {

    public boolean expanded = false;
    public boolean isWebView = false;

    public BotCommandsMenuView(Context context) {
        super(context);
    }

    public void setExpanded(boolean expanded, boolean animated) {
        this.expanded = expanded;
    }

    public void setOpened(boolean opened) {
        // Do nothing
    }

    public boolean isOpened() {
        return false;
    }

    public boolean setMenuText(String text) {
        return false;
    }

    public void setBotWebView(boolean isBotWebView) {
        this.isWebView = isBotWebView;
    }

    public void setWebView(boolean isWebView) {
        this.isWebView = isWebView;
    }

    public void setDrawBackgroundDrawable(boolean draw) {
        // Do nothing
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Do nothing
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(0, 0);
    }

    public void onDestroy() {
        // Do nothing
    }

    /**
     * Stub adapter
     */
    public static class BotCommandsAdapter extends RecyclerView.Adapter {

        public void setBotInfo(androidx.collection.LongSparseArray botInfo) {
            // Do nothing
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            // Do nothing
        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

    /**
     * Stub view
     */
    public static class BotCommandView extends View {
        public BotCommandView(Context context) {
            super(context);
        }

        public String getCommand() {
            return "";
        }
    }
}
