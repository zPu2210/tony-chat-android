package org.telegram.ui.Stories;

import android.util.SparseIntArray;
import org.telegram.ui.ActionBar.Theme;

/**
 * DarkThemeResourceProvider stub - Stories removed in Tony Chat
 */
public class DarkThemeResourceProvider implements Theme.ResourcesProvider {
    protected SparseIntArray sparseIntArray = new SparseIntArray();

    @Override
    public int getColor(int key) {
        return Theme.getColor(key);
    }

    public void appendColors() {
        // Override this in subclasses
    }
}
