package org.telegram.ui.Stories.recorder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.FrameLayout;

/**
 * PreviewView stub - Stories removed in Tony Chat
 */
public class PreviewView extends FrameLayout {
    public PreviewView(Context context) {
        super(context);
    }

    public static class TextureViewHolder {
        public android.view.TextureView textureView;
        public boolean active;
        public boolean textureViewActive;

        // Tony Chat: MessageEntityView compatibility - 2 callbacks
        public void takeTextureView(
                org.telegram.messenger.Utilities.Callback<android.view.TextureView> onTextureView,
                org.telegram.messenger.Utilities.Callback2<Integer, Integer> onSize
        ) {
            if (onTextureView != null) {
                onTextureView.run(textureView);
            }
        }
    }

    public static Drawable getBackgroundDrawableFromTheme(int currentAccount, String emoticon, boolean isDark, boolean preview) {
        return null;
    }

    // Tony Chat: ThemePreviewActivity compatibility - 3-arg version
    public static Drawable getBackgroundDrawableFromTheme(int currentAccount, String emoticon, boolean isDark) {
        return null;
    }

    public static Drawable getBackgroundDrawable(Drawable current, int currentAccount, Object wallpaper, boolean isDark) {
        return current;
    }
}
