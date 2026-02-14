package org.telegram.ui.Stories;

/**
 * ViewsForPeerStoriesRequester stub - Stories removed in Tony Chat
 */
public abstract class ViewsForPeerStoriesRequester {
    public ViewsForPeerStoriesRequester(StoriesController controller, long dialogId, int account) {}

    public void request(long dialogId) {}
    public void requestNext() {}
    public void start(boolean enabled) {}

    protected abstract void getStoryIds(java.util.ArrayList<Integer> ids);
    protected abstract boolean updateStories(java.util.ArrayList<Integer> reqIds, org.telegram.tgnet.tl.TL_stories.TL_stories_storyViews storyViews);
}
