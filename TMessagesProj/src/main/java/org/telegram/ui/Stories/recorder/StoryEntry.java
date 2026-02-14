package org.telegram.ui.Stories.recorder;

import android.graphics.Bitmap;
import org.telegram.tgnet.TLRPC;
import java.io.File;
import java.util.ArrayList;

/**
 * StoryEntry stub - Stories removed in Tony Chat
 */
public class StoryEntry {
    public static final int TYPE_PHOTO = 0;
    public static final int TYPE_VIDEO = 1;

    public long durationMillis;
    public int width;
    public int height;
    public String audioPath;
    public File file;
    public Bitmap thumbBitmap;
    public boolean isVideo;
    public boolean isEdit;
    public ArrayList<Object> mediaEntries;
    public Object videoEditedInfo;
    public TLRPC.MessageMedia media;
    public CharSequence caption;
    public HDRInfo hdrInfo;

    // Tony Chat: Stories removed - additional fields for VideoEditedInfo compatibility
    public boolean muted;
    public float videoVolume = 1.0f;
    public boolean videoLoop;
    public long videoOffset;
    public float videoLeft;
    public float videoRight;
    public long duration;
    public ArrayList<StoryEntry> collageContent;
    public CollageLayout collage;
    public boolean isDark; // Tony Chat: MessageEntityView theme support

    public static File makeCacheFile(int currentAccount, String ext) {
        return null;
    }

    public static File makeCacheFile(int currentAccount, boolean isVideo) {
        return null;
    }

    // Tony Chat: Stories removed - bitmap scaling stub
    public static Bitmap getScaledBitmap(java.util.function.Function<android.graphics.BitmapFactory.Options, Bitmap> loader, int maxWidth, int maxHeight, boolean exact, boolean noAlpha) {
        if (loader == null) return null;
        try {
            android.graphics.BitmapFactory.Options opts = new android.graphics.BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            loader.apply(opts);
            opts.inJustDecodeBounds = false;
            opts.inSampleSize = calculateInSampleSize(opts, maxWidth, maxHeight);
            return loader.apply(opts);
        } catch (Exception e) {
            return null;
        }
    }

    public static int calculateInSampleSize(android.graphics.BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static class HDRInfo {
        public int colorStandard;
        public int colorTransfer;
        public int colorRange;
        public int maxlum;
        public float minlum;

        // Tony Chat: Stories removed - HDR type detection stub
        public int getHDRType() {
            return 0;
        }
    }

    // Tony Chat: Stories removed - collage layout stub
    public static class CollageLayout {
        public ArrayList<Object> parts = new ArrayList<>();

        public CollageLayout(String serialized) {
            // Stub constructor
        }
    }

    // Tony Chat: MessageEntityView compatibility
    public static Boolean useForwardForRepost(Object messageObject) {
        return false;
    }

    // Tony Chat: ThemePreviewMessagesCell compatibility
    public static void drawBackgroundDrawable(android.graphics.Canvas canvas, android.graphics.drawable.Drawable drawable, int width, int height) {
        if (drawable != null) {
            drawable.setBounds(0, 0, width, height);
            drawable.draw(canvas);
        }
    }
}
