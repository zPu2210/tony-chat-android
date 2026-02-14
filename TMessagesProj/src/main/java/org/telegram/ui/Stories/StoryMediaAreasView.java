package org.telegram.ui.Stories;

import android.content.Context;
import android.view.View;

/**
 * StoryMediaAreasView stub - Stories removed in Tony Chat
 */
public class StoryMediaAreasView extends View {
    public StoryMediaAreasView(Context context) {
        super(context);
    }

    public boolean hasSelected() {
        return false;
    }

    public boolean hasAreaAboveAt(float x, float y) {
        return false;
    }

    public static class AreaView extends View {
        public AreaView(Context context) {
            super(context);
        }
    }
}
