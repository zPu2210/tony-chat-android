package org.telegram.ui.Stories.recorder;

import org.telegram.ui.ActionBar.BottomSheet;

/**
 * EmojiBottomSheet stub - Stories removed in Tony Chat
 */
public class EmojiBottomSheet extends BottomSheet {
    public static final int WIDGET_PHOTO = 1;

    public EmojiBottomSheet(android.content.Context context) {
        super(context, false);
    }

    public EmojiBottomSheet(android.content.Context context, boolean needFocus, Object resourcesProvider, boolean includeStickers) {
        super(context, needFocus);
    }

    public EmojiBottomSheet whenDocumentSelected(DocumentSelectedListener listener) {
        return this;
    }

    public EmojiBottomSheet whenWidgetSelected(WidgetSelectedListener listener) {
        return this;
    }

    public interface DocumentSelectedListener {
        // Tony Chat: Changed document to TLRPC.Document for StickersDialogs (lines 281, 288)
        void onDocumentSelected(Object parentObject, org.telegram.tgnet.TLRPC.Document document, boolean isGif);
    }

    public interface WidgetSelectedListener {
        void onWidgetSelected(int widget);
    }
}
