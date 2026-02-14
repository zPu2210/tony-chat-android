package org.telegram.ui.Stories;

import org.telegram.tgnet.tl.TL_stories;
import java.util.ArrayList;

/**
 * PublicStoriesList stub - Stories removed in Tony Chat
 */
public class PublicStoriesList {
    public String query = "";
    public String username = null;

    public ArrayList<TL_stories.StoryItem> getStories() {
        return new ArrayList<>();
    }

    public int getCount() {
        return 0;
    }
}
