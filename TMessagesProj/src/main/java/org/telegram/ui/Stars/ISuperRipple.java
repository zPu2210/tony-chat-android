package org.telegram.ui.Stars;

/**
 * STUB: Super ripple interface removed from Tony Chat.
 */
public interface ISuperRipple {
    void start();
    void stop();
    boolean isRunning();
    void setCallback(Runnable callback);
    android.view.View getView();
    void animate(float x, float y, float intensity);
}
