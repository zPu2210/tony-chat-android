package org.telegram.ui.Components;

import android.content.Context;
import android.widget.FrameLayout;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;

/**
 * STUB: Profile gifts container removed from Tony Chat.
 */
public class ProfileGiftsContainer extends FrameLayout {
    public ProfileGiftsContainer(Context context) { super(context); }
    public ProfileGiftsContainer(BaseFragment fragment, Context context, int account, long dialogId, Theme.ResourcesProvider rp) { super(context); }

    public void setUserId(long userId) {}
    public void load() {}
    public void update() {}
    public boolean canFilter() { return false; }
    public boolean canScroll(boolean forward) { return true; }
    public boolean isReordering() { return false; }
    public void resetReordering() {}
    public RecyclerListView getCurrentListView() { return null; }
    public int getLastEmojisHash() { return 0; }
    public CharSequence getLastEmojis(Object param) { return ""; }
    public void setVisibleHeight(int h) {}
    public void setExpandProgress(float progress) {}
    public void setCollapseProgress(float progress, boolean animated) {}
    public void setExpandCoords(float y) {}
    public void setActionBarActionMode(float progress) {}
    public void setProgressToStoriesInsets(float progress) {}
    public void setBounds(float a, float b, float c, boolean d, int e) {}
    public void setActive(boolean active) {}
    public void scrollToCollectionId(int id) {}
    public void updateColors() {}
    public int getGiftsCount() { return 0; }
    public float getBottomOffset() { return 0; }
    public float expandProgress;
    public float collapseProgress;
    public boolean isOpening;
    public Object findUserStarGift(long id) { return null; }
    protected void onGiftsUpdate() {}
    protected void onFilterUpdate() {}
    protected int processColor(int color) { return color; }
    protected void updatedReordering(boolean reordering) {}
}
