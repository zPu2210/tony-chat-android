package org.telegram.ui.Stories;

import android.content.Context;
import android.view.View;
import org.telegram.ui.Components.AnimatedEmojiDrawable;

/**
 * StoryReactionWidgetView stub - Stories removed in Tony Chat
 */
public class StoryReactionWidgetView extends View {
    public MediaArea mediaArea = new MediaArea();

    public StoryReactionWidgetView(Context context) {
        super(context);
    }

    public AnimatedEmojiDrawable getAnimatedEmojiDrawable() {
        return null;
    }

    public static class MediaArea {
        public Object reaction;
    }
}
