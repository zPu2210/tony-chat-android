package org.telegram.ui.TonyChat;

import com.tonychat.ai.AiManagerBridge;

import java.io.File;

/** Remove Text from images tool — extends BaseImageToolFragment. */
public class RemoveTextToolFragment extends BaseImageToolFragment {
    @Override protected String getToolTitle() { return "Remove Text"; }
    @Override protected String getButtonText() { return "Remove Text"; }

    @Override
    protected void processImage(File imageFile, ImageEditCallback callback) {
        AiManagerBridge.clipDropRemoveText(imageFile, callback::onResult);
    }
}
