package org.telegram.ui.Components.Premium;

import android.content.Context;

import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.PremiumPreviewFragment;

/**
 * STUB: Premium feature preview bottom sheet removed.
 * This stub prevents compilation errors in callers.
 */
public class PremiumFeatureBottomSheet extends BottomSheet {

    // Multiple constructor signatures used by callers
    public PremiumFeatureBottomSheet(Context context, int startType, boolean onlySelectedType, Theme.ResourcesProvider resourcesProvider) {
        super(context, false, resourcesProvider);
    }

    public PremiumFeatureBottomSheet(BaseFragment fragment, int startType, boolean onlySelectedType) {
        super(fragment.getParentActivity(), false);
    }

    public PremiumFeatureBottomSheet(BaseFragment fragment, int startType, boolean onlySelectedType, PremiumPreviewFragment.SubscriptionTier subscriptionTier) {
        super(fragment.getParentActivity(), false);
    }

    public PremiumFeatureBottomSheet(BaseFragment fragment, Context context, int currentAccount, int startType, boolean onlySelectedType) {
        super(context, false);
    }

    public PremiumFeatureBottomSheet(BaseFragment fragment, Context context, int currentAccount, boolean business, int startType, boolean onlySelectedType, PremiumPreviewFragment.SubscriptionTier subscriptionTier) {
        super(context, false);
    }

    public PremiumFeatureBottomSheet(BaseFragment fragment, Context context, int currentAccount, boolean business, int startType, boolean onlySelectedType, PremiumPreviewFragment.SubscriptionTier subscriptionTier, Theme.ResourcesProvider resourcesProvider) {
        super(context, false, resourcesProvider);
    }

    // Builder-pattern method
    public PremiumFeatureBottomSheet setForceAbout() {
        return this;
    }

    @Override
    public void show() {
        // No-op: don't show Premium upsells
    }
}
