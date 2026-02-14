package org.telegram.ui.Components.Premium;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.telegram.messenger.NotificationCenter;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.Components.BottomSheetWithRecyclerListView;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.PremiumPreviewFragment;

/**
 * STUB: Doubled limits bottom sheet removed.
 * This stub prevents compilation errors in callers.
 */
public class DoubledLimitsBottomSheet extends BottomSheetWithRecyclerListView implements NotificationCenter.NotificationCenterDelegate {

    // Constructors used by callers
    public DoubledLimitsBottomSheet(BaseFragment fragment, int currentAccount) {
        super(fragment, false, false, null);
    }

    public DoubledLimitsBottomSheet(BaseFragment fragment, int currentAccount, PremiumPreviewFragment.SubscriptionTier subscriptionTier) {
        super(fragment, false, false, null);
    }

    @Override
    public void didReceivedNotification(int id, int account, Object... args) {
        // No-op
    }

    @Override
    public void show() {
        // No-op: don't show doubled limits upsells
    }

    @Override
    public CharSequence getTitle() {
        return "";
    }

    @Override
    protected RecyclerListView.SelectionAdapter createAdapter(RecyclerListView listView) {
        return new Adapter();
    }

    // Inner classes that may be referenced
    private static class LimitCell extends LinearLayout {
        public LimitCell(Context context) {
            super(context);
        }
    }

    private static class Limit {
        public int icon;
        public String description;
        public int defaultCount;
        public int premiumCount;
    }

    public static class Adapter extends RecyclerListView.SelectionAdapter {
        public View containerView;

        public Adapter() {}
        public Adapter(int account, boolean flag, Object resourcesProvider) {}

        public void measureGradient(Context context, int width, int height) {}

        @Override
        public boolean isEnabled(RecyclerView.ViewHolder holder) {
            return false;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull android.view.ViewGroup parent, int viewType) {
            return new RecyclerView.ViewHolder(new View(parent.getContext())) {};
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
