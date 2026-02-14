package org.telegram.ui.Stories.recorder;

import android.view.View;
import org.telegram.messenger.AndroidUtilities;

/**
 * KeyboardNotifier stub - Stories removed in Tony Chat
 */
public class KeyboardNotifier {
    public interface Callback {
        void onKeyboardChanged(int keyboardHeight);
    }

    // Tony Chat: Separate constructors for Runnable and Callback to avoid lambda ambiguity
    public KeyboardNotifier(View rootView) {} // Tony Chat: For null callback cases
    public KeyboardNotifier(View rootView, Runnable onKeyboardChange) {}
    public KeyboardNotifier(View rootView, Callback callback) {}
    public KeyboardNotifier(View rootView, boolean param, Callback callback) {}
    public boolean keyboardVisible() { return false; }
    public int getKeyboardHeight() { return 0; }
    public void ignore(boolean ignore) {}
    public void awaitKeyboard() {}
    public void fire() {}
}
