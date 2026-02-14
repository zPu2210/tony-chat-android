package org.telegram.ui.Stories.recorder;

import android.graphics.Bitmap;
import org.telegram.messenger.Utilities;

/**
 * DominantColors stub - Stories removed in Tony Chat
 */
public class DominantColors {
    public int getColor() { return 0; }

    public static int[] getColorsSync(boolean param1, Bitmap bitmap, boolean param2) {
        return new int[]{0xFF000000, 0xFFFFFFFF};
    }

    // Tony Chat: Stories removed - async color extraction stub
    public static void getColors(boolean param1, Bitmap bitmap, boolean dark, Utilities.Callback<int[]> callback) {
        if (callback != null) {
            callback.run(new int[]{0xFF000000, 0xFFFFFFFF});
        }
    }
}
