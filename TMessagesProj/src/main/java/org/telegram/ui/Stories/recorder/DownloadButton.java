package org.telegram.ui.Stories.recorder;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * DownloadButton stub - Stories removed in Tony Chat
 */
public class DownloadButton extends ImageView {
    public DownloadButton(Context context) {
        super(context);
    }

    public static class PreparingVideoToast extends FrameLayout {
        private Runnable onCancelListener;

        public PreparingVideoToast(Context context) {
            super(context);
        }

        public void setOnCancelListener(Runnable listener) {
            this.onCancelListener = listener;
        }

        public void setProgress(float progress) {
            // Stub - no-op
        }

        public void show() {
            setVisibility(VISIBLE);
        }

        public void hide() {
            setVisibility(GONE);
            if (onCancelListener != null) {
                onCancelListener.run();
            }
        }
    }
}
