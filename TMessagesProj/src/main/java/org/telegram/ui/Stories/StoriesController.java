package org.telegram.ui.Stories;

import android.util.SparseArray;
import androidx.collection.LongSparseArray;
import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.support.LongSparseIntArray;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.tl.TL_stories;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * StoriesController stub - Stories feature removed in Tony Chat.
 * Kept as no-op singleton to prevent breaking references across codebase.
 */
public class StoriesController {

    public static final int STATE_READ = 0;
    public static final int STATE_UNREAD = 1;
    public static final int STATE_UNREAD_CLOSE_FRIEND = 2;
    public static final int STATE_LIVE = 3;

    private final int currentAccount;
    public LongSparseIntArray dialogIdToMaxReadId = new LongSparseIntArray();
    public static final Comparator<TL_stories.StoryItem> storiesComparator = Comparator.comparingInt(o -> o.date);
    public LongSparseArray<SparseArray<Object>> selfViewsModel = new LongSparseArray<>();

    private final LongSparseArray<ArrayList<Object>> uploadingStoriesByDialogId = new LongSparseArray<>();
    private final ArrayList<Long> dialogListIds = new ArrayList<>();
    private final ArrayList<Long> hiddenListIds = new ArrayList<>();
    public boolean hasMoreHidden = false;
    public final ArrayList<StoriesList> attachedSearchLists = new ArrayList<>(); // Tony Chat: HashtagActivity

    // Tony Chat: Stories removed - singleton pattern
    private static final SparseArray<StoriesController> Instance = new SparseArray<>();

    public StoriesController(int currentAccount) {
        this.currentAccount = currentAccount;
    }

    public static StoriesController getInstance(int num) {
        StoriesController localInstance = Instance.get(num);
        if (localInstance == null) {
            synchronized (StoriesController.class) {
                localInstance = Instance.get(num);
                if (localInstance == null) {
                    Instance.put(num, localInstance = new StoriesController(num));
                }
            }
        }
        return localInstance;
    }

    public void loadAllStories() {}
    public Object getDraftsController() { return null; }
    public boolean hasStories(long dialogId) { return false; }
    public boolean hasHiddenStories() { return false; }
    public TL_stories.PeerStories getStoriesFromFullPeer(long dialogId) { return null; }
    public boolean hasStories() { return false; }
    public void loadStories() {}
    public void loadHiddenStories() {}
    public void toggleHidden(long dialogId, boolean hide, boolean request, boolean notify) {}
    public void preloadUserStories(TL_stories.PeerStories userStories) {}
    public void uploadStory(Object entry, boolean count) {}
    public void putUploadingDrafts(ArrayList<Object> entries) {}
    public ArrayList<TL_stories.PeerStories> getDialogListStories() { return new ArrayList<>(); }
    public TL_stories.PeerStories getStories(long peerId) { return null; }
    public ArrayList<UploadingStory> getUploadingStories(long dialogId) { return new ArrayList<>(); }
    public boolean isLastUploadingFailed(long dialogId) { return false; }
    public ArrayList<Object> getUploadingAndEditingStories(long dialogId) { return new ArrayList<>(); }
    public int getMyStoriesCount() { return 0; }
    public int getTotalStoriesCount(boolean hidden) { return 0; }
    public Object findEditingStory(long dialogId, TL_stories.StoryItem storyItem) { return null; }
    public Object getEditingStory(long dialogId) { return null; }

    public static TL_stories.StoryItem applyStoryUpdate(TL_stories.StoryItem oldStoryItem, TL_stories.StoryItem newStoryItem) {
        return newStoryItem;
    }

    public void processUpdate(TL_stories.TL_updateStory updateStory) {}
    public boolean hasSelfStories() { return false; }
    public int getSelfStoriesCount() { return 0; }
    public void deleteStory(long dialogId, TL_stories.StoryItem storyItem) {}
    public void deleteStories(long dialogId, ArrayList<TL_stories.StoryItem> storyItems) {}
    public void updateStoriesPinned(long dialogId, ArrayList<TL_stories.StoryItem> storyItems, boolean pinned, Utilities.Callback<Boolean> whenDone) {
        if (whenDone != null) whenDone.run(false);
    }
    public void updateStoryItem(long dialogId, TL_stories.StoryItem storyItem, boolean edited) {}
    public void updateStoryItem(long dialogId, TL_stories.StoryItem storyItem, boolean force, boolean edited) {}
    public boolean markStoryAsRead(long dialogId, TL_stories.StoryItem storyItem) { return false; }
    public boolean markStoryAsRead(TL_stories.PeerStories userStories, TL_stories.StoryItem storyItem, boolean profile) { return false; }
    public int getMaxStoriesReadId(long dialogId) { return 0; }
    public void markStoriesAsReadFromServer(long dialogId, int maxStoryId) {}
    public boolean hasUnreadStories(long dialogId) { return false; }
    public int getUnreadStoriesCount(long dialogId) { return 0; } // Tony Chat: ContactsAdapter
    public int hasUnreadStoriesLive(long dialogId) { return STATE_READ; }
    public boolean hasLiveStory(long dialogId) { return false; }
    public int getUnreadState(long dialogId) { return STATE_READ; }
    public int getUnreadState(long dialogId, int storyId) { return STATE_READ; }
    public boolean hasUploadingStories(long dialogId) { return false; }
    public void cleanup() {}
    public void pollViewsForSelfStories(long dialogId, boolean start) {}
    public void stopAllPollers() {}
    public void loadNextStories(boolean hidden) {}
    public void fillMessagesWithStories(LongSparseArray messagesWithUnknownStories, Runnable callback, int classGuid, Object timer) {
        if (callback != null) callback.run();
    }
    public void resolveStoryAlbumLink(long peerId, int storyAlbumId, Object consumer) {}
    public void resolveLiveStoryLink(long peerId, Object consumer) {}
    public void resolveStoryLink(long peerId, int storyId, Object consumer) {} // Tony Chat: Stories removed
    public void onPremiumChanged() {}
    public void updateStoriesFromFullPeer(long dialogId, Object stories) {}
    public void updateBlockUser(long userId, boolean blocked, boolean notify) {}
    public void setStealthMode(Object stealthMode) {}
    public void updateStoryReaction(long dialogId, int storyId, Object reaction) {}
    public boolean hasOnlySelfStories() { return false; }
    public boolean canPostStories(long dialogId) { return false; }
    public TL_stories.TL_storiesStealthMode getStealthMode() { return null; }
    public org.telegram.tgnet.tl.TL_stories.StoryItem findStory(long dialogId, int storyId) { return null; }
    public ArrayList<TL_stories.PeerStories> getHiddenList() { return new ArrayList<>(); }
    public boolean canEditStoryAlbums(long dialogId) { return false; }

    // Tony Chat: Stories removed - additional stub methods
    public boolean hasLoadingStories() { return false; }
    public void addStoryToAlbum(long dialogId, long albumId, TL_stories.StoryItem storyItem) {}
    public void removeStoryFromAlbum(long dialogId, long albumId, TL_stories.StoryItem storyItem) {}
    public String getAlbumName(long dialogId, long albumId) { return ""; }
    public boolean canEditStories(long dialogId) { return false; }
    public void renameAlbum(long dialogId, long albumId, String name) {}
    public void removeAlbum(long dialogId, long albumId) {}
    // Tony Chat: Stories removed - single overload to avoid type erasure clash
    public void addStoriesToAlbum(long dialogId, long albumId, Object storyItems) {}
    public void removeContact(long userId) {}
    public void loadSendAs() {} // Tony Chat: Stories removed
    public void invalidateStoryLimit() {}
    public StoriesStorage getStoriesStorage() { return new StoriesStorage(currentAccount); }
    public void removeStoriesFromAlbum(long dialogId, long albumId, Object storyItems) {} // Tony Chat: Stories removed
    public void updateStoriesInLists(long dialogId, Object storyItems) {} // Tony Chat: Stories removed
    public void canSendStoryFor(long dialogId, org.telegram.messenger.Utilities.Callback<Boolean> callback, boolean checkBoosts, Object resourcesProvider) {
        // Tony Chat: Stories removed - always return false via callback
        if (callback != null) {
            callback.run(false);
        }
    }

    // Inner classes as stubs
    public static class UploadingStory {
        public long dialogId;
        public TL_stories.StoryItem storyItem;
        public boolean isEdit;
        public boolean failed;
        public long random_id;
        public String firstFramePath;
        public float progress;
        public org.telegram.messenger.MessageObject sharedMessageObject;
    }

    public StoriesList getStoriesList(long dialogId, int type) { return new StoriesList(currentAccount); }
    public StoriesList getStoriesList(long dialogId, int type, long albumId) { return new StoriesList(currentAccount); }
    public StoriesCollections getStoryAlbumsList(long dialogId) { return new StoriesCollections(); }
    public StoryLimit checkStoryLimit() { return null; }
    public void createAlbum(long dialogId, String name, StoryAlbumCallback callback) {}

    public static class StoryLimit {
        public int currentValue;
        public int defaultValue;
        public int premiumValue;

        public boolean active(int account, int count) { return false; }
        public boolean active(int account) { return false; } // Tony Chat: Stories removed - overload
        public int getLimitReachedType() { return 0; }
    }

    public static class StoriesList {
        public static final int TYPE_PINNED = 0;
        public static final int TYPE_ARCHIVE = 1;
        public static final int TYPE_ALBUMS = 2;
        public static final int TYPE_BOTS = 3;
        public static final int TYPE_STATISTICS = 4; // Tony Chat: Stories removed

        public int currentAccount;
        public long albumId;
        public String username; // Tony Chat: MessagesSearchAdapter
        public ArrayList<org.telegram.messenger.MessageObject> messageObjects = new ArrayList<>();
        public ArrayList<Integer> pinnedIds = new ArrayList<>();

        public StoriesList(int account) { this.currentAccount = account; }

        public ArrayList<TL_stories.PeerStories> getStories() { return new ArrayList<>(); }
        public int getCount() { return 0; }
        public int getLoadedCount() { return 0; }
        public boolean isLoading() { return false; }
        public boolean isFull() { return true; } // Tony Chat: SelectStoriesBottomSheet
        public boolean isOnlyCache() { return false; }
        public void load(boolean force, int count) {}
        public boolean load(ArrayList<Integer> storyIds) { return false; } // Tony Chat: Stories removed - overload
        public boolean isPinned(int id) { return false; }
        public boolean updatePinned(java.util.ArrayList<Integer> ids, boolean pin) { return false; }
        public int link() { return 0; }
        public void unlink(int id) {}
        public void updateStoryViews(java.util.ArrayList<Integer> reqIds, Object views) {}
        public void updatePinnedOrder(java.util.ArrayList<Integer> ids, boolean sync) {}
        public boolean showPhotos() { return true; }
        public boolean showVideos() { return true; }
        public void updateFilters(boolean photos, boolean videos) {}
        public org.telegram.messenger.MessageObject findMessageObject(int storyId) { return null; } // Tony Chat: Stories removed
        public void requestReference(TL_stories.StoryItem storyItem, Utilities.Callback<TL_stories.StoryItem> callback) {} // Tony Chat: Stories removed
    }

    public static class SearchStoriesList extends StoriesList {
        public String query;

        public SearchStoriesList(int account, String username, String hashtag) {
            super(account);
            this.query = hashtag;
        }
        public SearchStoriesList(int account, Object area) {
            super(account);
        }

        public void cancel() {}
    }

    public static class BotPreviewsList extends StoriesList {
        public BotPreviewsList(int account) { super(account); }

        public void delete(java.util.ArrayList<org.telegram.tgnet.TLRPC.MessageMedia> medias) {}
    }

    public static class StoriesCollections {
        public int currentAccount;
        public long dialogId;
        public ArrayList<StoryAlbum> albums = new ArrayList<>();
        public ArrayList<StoryAlbum> collections = new ArrayList<>();

        public boolean canCreateNewAlbum() { return false; }
        public void sendOrder() {}
        public void reorderComplete(boolean animated) {}
        public void reorderStep(java.util.ArrayList<Integer> collectionIds) {}
        public int indexOf(int albumId) { return -1; }
        public StoryAlbum findById(int albumId) { return null; }
    }

    public static class StoryAlbum {
        public long album_id;
        public long albumId;
        public String title;
        public TLRPC.Photo icon_photo; // Tony Chat: Stories removed

        public TL_stories.TL_storyAlbum toTl() { return null; } // Tony Chat: Stories removed
        public static StoryAlbum from(TL_stories.TL_storyAlbum tlAlbum) { return new StoryAlbum(); } // Tony Chat: Stories removed
    }

    public interface StoryAlbumCallback {
        void run(StoryAlbum album);
    }

    public static class BotPreview extends org.telegram.tgnet.tl.TL_stories.StoryItem {
        // Stub for bot preview stories
    }

    // Tony Chat: Stories removed - StoriesStorage stub for compilation
    public static class StoriesStorage {
        private final int currentAccount;

        public StoriesStorage(int currentAccount) {
            this.currentAccount = currentAccount;
        }

        public void applyStory(long dialogId, TL_stories.StoryItem storyItem, boolean edited) {}

        public void updateMessagesWithStories(LongSparseArray messagesWithUnknownStories) {}

        public void updateMessagesWithStories(java.util.ArrayList<org.telegram.messenger.MessageObject> messageObjects) {}

        public void updateStoryItem(long dialogId, TL_stories.StoryItem storyItem) {} // Tony Chat: FileRefController
    }
}
