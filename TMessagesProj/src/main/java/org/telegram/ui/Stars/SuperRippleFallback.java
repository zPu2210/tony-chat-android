package org.telegram.ui.Stars;

import android.content.Context;
import android.view.View;

/**
 * STUB: SuperRippleFallback removed from Tony Chat.
 */
public class SuperRippleFallback implements ISuperRipple {
    public final View view;

    public SuperRippleFallback(View parent) {
        this.view = parent;
    }

    public SuperRippleFallback(Context context) {
        this.view = null;
    }

    @Override
    public void animate(float x, float y, float radius) {
        // No-op stub
    }

    @Override
    public void setCallback(Runnable callback) {
        // No-op stub
    }

    @Override
    public void start() {}

    @Override
    public void stop() {}

    @Override
    public boolean isRunning() { return false; }

    @Override
    public View getView() { return view; }
}
