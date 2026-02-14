package org.telegram.ui.Stories;

/**
 * StoriesListPlaceProvider stub - Stories removed in Tony Chat
 */
public class StoriesListPlaceProvider {
    public static StoriesListPlaceProvider of(Object view) {
        return new StoriesListPlaceProvider();
    }

    public static StoriesListPlaceProvider of(Object view, boolean hidden) {
        return new StoriesListPlaceProvider();
    }

    public StoriesListPlaceProvider addBottomClip(int clip) {
        return this;
    }

    public interface ClippedView {
        void updateClip(int[] clip);
    }

    public interface AvatarOverlaysView {
        void setStoryParams(Object params);
    }
}
