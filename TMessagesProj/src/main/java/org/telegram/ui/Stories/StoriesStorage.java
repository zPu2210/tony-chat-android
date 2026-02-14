package org.telegram.ui.Stories;

import org.telegram.tgnet.tl.TL_stories;

/**
 * StoriesStorage stub - Stories removed in Tony Chat
 */
public class StoriesStorage {
    public StoriesStorage(int account) {}

    public void saveStory(long dialogId, TL_stories.StoryItem story) {}
    public TL_stories.StoryItem getStory(long dialogId, int storyId) { return null; }
    public void deleteStories(long dialogId) {}

    // Tony Chat: Stories removed - additional stub methods
    public static TL_stories.StoryItem checkExpiredStateLocal(int currentAccount, long dialogId, TL_stories.StoryItem storyItem) {
        return storyItem;
    }

    // Tony Chat: ChatMessagesMetadataController static call
    public static void applyStory(int currentAccount, long dialogId, Object messageObject, TL_stories.StoryItem storyItem) {}

    public void updateStoryItem(long dialogId, TL_stories.StoryItem storyItem) {}
}
