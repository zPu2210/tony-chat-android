package org.telegram.ui.Stories;

import android.content.Context;
import android.util.Property;
import android.view.View;

/**
 * ProfileStoriesView stub - Stories removed in Tony Chat
 */
public class ProfileStoriesView extends View {

    // Tony Chat: Stub property for fragment transition animations
    public static final Property<ProfileStoriesView, Float> FRAGMENT_TRANSITION_PROPERTY =
        new Property<ProfileStoriesView, Float>(Float.class, "fragmentTransition") {
            @Override
            public Float get(ProfileStoriesView object) {
                return 0f;
            }

            @Override
            public void set(ProfileStoriesView object, Float value) {
                // No-op stub
            }
        };

    public ProfileStoriesView(Context context, int currentAccount, long dialogId, boolean isTopic,
                             View avatarContainer, Object avatarImage, Object resourcesProvider) {
        super(context);
    }

    protected void onTap(StoryViewer.PlaceProvider provider) {
        // Tony Chat: Stories removed - no-op
    }

    protected void onLongPress() {
        // Tony Chat: Stories removed - no-op
    }

    public void setStories(Object stories) {
        // Tony Chat: Stories removed - no-op
    }

    public void setExpandProgress(float progress) {
        // Tony Chat: Stories removed - no-op
    }

    public void setActionBarActionMode(float value) {
        // Tony Chat: Stories removed - no-op
    }

    public void setExpandCoords(float x, boolean writeButtonVisible, float y) {
        // Tony Chat: Stories removed - no-op
    }

    public boolean isEmpty() {
        return true; // Tony Chat: Stories removed - always empty
    }

    public void setFragmentTransitionProgress(float progress) {
        // Tony Chat: Stories removed - no-op
    }

    public void setBounds(float left, float right, float centerY, boolean instant) {
        // Tony Chat: Stories removed - no-op
    }

    public void setProgressToStoriesInsets(float progress) {
        // Tony Chat: Stories removed - no-op
    }

    public void update() {
        // Tony Chat: Stories removed - no-op
    }
}
