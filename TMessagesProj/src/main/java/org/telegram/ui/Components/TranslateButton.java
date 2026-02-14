package org.telegram.ui.Components;

import android.content.Context;
import android.view.View;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;

// Stub for TranslateButton - translation disabled in Tony Chat
public class TranslateButton extends View {

    public TranslateButton(Context context, BaseFragment fragment, Theme.ResourcesProvider resourcesProvider) {
        super(context);
        setVisibility(GONE);
    }

    protected void onButtonClick() {
        // No-op
    }

    protected void onCloseClick() {
        // No-op
    }

    public void updateText() {
        // No-op
    }

    public void updateColors() {
        // No-op
    }

    public void updateTranslating() {
        // No-op
    }

    public void show(boolean show, boolean animated) {
        setVisibility(GONE); // Always hidden
    }
}
