package org.telegram.ui.Stories.recorder;

import android.content.Context;
import org.telegram.ui.ActionBar.BottomSheet;

/**
 * GallerySheet stub - Stories removed in Tony Chat
 */
public class GallerySheet extends BottomSheet {
    public GallerySheet(Context context) {
        super(context, false);
    }

    // PhotoViewerCoverEditor constructor compatibility
    public GallerySheet(Context context, Object resourcesProvider, String title, boolean videoCover, float aspectRatio) {
        super(context, false);
    }

    public void setOnGalleryImage(Object listener) {}
}
