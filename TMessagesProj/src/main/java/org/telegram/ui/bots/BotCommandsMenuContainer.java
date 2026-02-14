package org.telegram.ui.bots;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import androidx.core.view.NestedScrollingParent;

import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.RecyclerListView;

/**
 * STUB: Bot Commands Menu Container removed from Tony Chat.
 * Bot commands menu features are not supported.
 */
public class BotCommandsMenuContainer extends FrameLayout implements NestedScrollingParent {

    public RecyclerListView listView;

    public BotCommandsMenuContainer(Context context) {
        super(context);
        listView = new RecyclerListView(context);
    }

    public void setResourcesProvider(Theme.ResourcesProvider resourcesProvider) {
        // Do nothing
    }

    public void setListView(View listView) {
        // Do nothing
    }

    public void dismiss() {
        // Do nothing
    }

    protected void onDismiss() {
        // Do nothing
    }

    public void show() {
        // Do nothing
    }

    public boolean isOpened() {
        return false;
    }

    public int clipBottom() {
        return 0;
    }

    public void updateColors() {
        // Do nothing
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return false;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        // Do nothing
    }

    @Override
    public void onStopNestedScroll(View target) {
        // Do nothing
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        // Do nothing
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        // Do nothing
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return 0;
    }

    public void onDestroy() {
        // Do nothing
    }
}
