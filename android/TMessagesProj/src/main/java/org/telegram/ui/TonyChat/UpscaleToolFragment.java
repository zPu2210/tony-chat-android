package org.telegram.ui.TonyChat;

import com.tonychat.ai.AiManagerBridge;

import java.io.File;

/** Upscale 2x tool — extends BaseImageToolFragment. */
public class UpscaleToolFragment extends BaseImageToolFragment {
    @Override protected String getToolTitle() { return "Upscale Image"; }
    @Override protected String getButtonText() { return "Upscale 2x"; }

    @Override
    protected void processImage(File imageFile, ImageEditCallback callback) {
        AiManagerBridge.clipDropUpscale(imageFile, callback::onResult);
    }
}
