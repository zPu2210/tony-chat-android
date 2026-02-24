package org.telegram.ui.TonyChat;

import com.tonychat.ai.AiManagerBridge;

import java.io.File;

/** Remove Background tool — extends BaseImageToolFragment. */
public class RemoveBgToolFragment extends BaseImageToolFragment {
    @Override protected String getToolTitle() { return "Remove Background"; }
    @Override protected String getButtonText() { return "Remove Background"; }

    @Override
    protected void processImage(File imageFile, ImageEditCallback callback) {
        AiManagerBridge.clipDropRemoveBg(imageFile, callback::onResult);
    }
}
