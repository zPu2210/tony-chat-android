package org.telegram.ui.bots;

import android.content.Context;
import android.widget.FrameLayout;

/**
 * STUB: Bot Buttons removed from Tony Chat.
 * Bot button UI features are not supported.
 */
public class BotButtons extends FrameLayout {

    public BotButtons(Context context) {
        super(context);
    }

    public void setButtons(Object buttons) {
        // Do nothing
    }

    public void setDelegate(Delegate delegate) {
        // Do nothing
    }

    public void updateColors() {
        // Do nothing
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(GONE);
    }

    /**
     * Stub interface
     */
    public interface Delegate {
        void onButtonPressed(Object button);
    }

    /**
     * Stub inner class
     */
    public static class ButtonsState {
        public boolean isVisible;
        public java.util.List<Object> buttons;
    }
}
