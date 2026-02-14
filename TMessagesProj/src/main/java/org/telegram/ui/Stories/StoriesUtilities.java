package org.telegram.ui.Stories;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import org.telegram.messenger.ImageReceiver;
import org.telegram.tgnet.tl.TL_stories;

/**
 * StoriesUtilities stub - Stories removed in Tony Chat
 */
public class StoriesUtilities {
    public static void drawAvatarWithStory(long dialogId, Canvas canvas, float x, float y, float radius, Paint paint) {}
    public static void drawAvatarWithStory(long dialogId, Canvas canvas, float x, float y, float radius, Paint paint, boolean hasStory) {}
    public static void drawAvatarWithStory(long dialogId, Canvas canvas, ImageReceiver receiver, AvatarStoryParams params) {}
    public static int getPredictiveUnreadState(Object storiesController, long dialogId) { return STATE_EMPTY; }

    public static CharSequence createExpiredStoryString() {
        return "Expired Story";
    }

    public static CharSequence createExpiredStoryString(boolean makeClickable, int stringRes, Object... formatArgs) {
        return "Expired Story";
    }

    public static CharSequence createReplyStoryString() {
        return "Story";
    }

    public static Drawable getExpiredStoryDrawable() {
        return null;
    }

    public static void setImage(ImageReceiver receiver, TL_stories.StoryItem item) {}
    public static void setStoryMiniImage(ImageReceiver receiver, TL_stories.StoryItem item) {}

    public static final int STATE_EMPTY = 0;
    public static final int STATE_HAS_UNREAD = 1;
    public static final int STATE_READ = 2;

    public static class AvatarStoryParams {
        public RectF originalAvatarRect = new RectF();
        public float alpha = 1f;
        public boolean drawHiddenStoriesAsSegments;
        public boolean allowLongress;
        public boolean drawnLive;
        public int currentState = STATE_EMPTY;
        public boolean drawSegments;
        public boolean animate;
        public int forceState = -1;
        public boolean drawInside;
        public Object resourcesProvider;
        public TL_stories.StoryItem storyItem;
        public int storyId;
        public boolean isArchive; // Tony Chat: Stories removed

        public AvatarStoryParams(boolean param) {}

        // Tony Chat: ReactedUserHolderView constructor
        public AvatarStoryParams(boolean param, Object resourcesProvider) {
            this.resourcesProvider = resourcesProvider;
        }

        public void drawSegments(Canvas canvas, RectF rect, float alpha) {}
        public void onDetachFromWindow() {}
        public boolean isStoryCell() { return false; }
        public boolean isEmpty() { return currentState == STATE_EMPTY; }
        public boolean checkOnTouchEvent(Object event, Object view) { return false; }
        public void openStory(long dialogId, Runnable onDone) {} // Tony Chat: ReactedUserHolderView override
    }

    public static class StoryGradientTools {
        public Paint paint = new Paint();

        // Tony Chat: PeerColorActivity constructor
        public StoryGradientTools(Object view, boolean emojiStatus) {}

        public void setBounds(float left, float top, float right, float bottom) {}
        public void setColor(Object color, boolean animated) {} // Tony Chat: PeerColorActivity - accepts PeerColor or int
        public Paint getPaint(android.graphics.RectF bounds) { return paint; } // Tony Chat: PeerColorActivity
    }

    public static boolean hasExpiredViews(TL_stories.StoryItem storyItem) {
        return false;
    }
}
