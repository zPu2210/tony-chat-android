package org.telegram.ui.Components.Premium;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.telegram.messenger.NotificationCenter;
import org.telegram.tgnet.TLRPC;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.Components.BottomSheetWithRecyclerListView;
import org.telegram.ui.Components.RecyclerListView;

/**
 * STUB: Gift Premium bottom sheet removed.
 * This stub prevents compilation errors in callers.
 */
public class GiftPremiumBottomSheet extends BottomSheetWithRecyclerListView implements NotificationCenter.NotificationCenterDelegate {

    // Constructor used by callers
    public GiftPremiumBottomSheet(BaseFragment fragment, TLRPC.User user) {
        super(fragment, false, false, null);
    }

    // Inner class referenced by PremiumPreviewBottomSheet and other components
    public static class GiftTier {
        public int months;
        public long amount;
        public String currency;
        public long amountUsd;
        public String googlePlayProductId;
        public String storeProduct;
        public String subscriptionOption;
        public Object store;

        public GiftTier() {
        }

        // Constructor used by PremiumPreviewGiftLinkBottomSheet
        public GiftTier(TLRPC.TL_premiumGiftOption giftOption, Object store) {
            // No-op stub constructor
        }

        // Methods used by PremiumGiftTierCell and other callers
        public int getMonths() {
            return months;
        }

        public Object getGooglePlayProductDetails() {
            return null;
        }

        public int getDiscount() {
            return 0;
        }

        public String getFormattedPricePerMonth() {
            return "";
        }

        public String getFormattedPrice() {
            return "";
        }
    }

    @Override
    public void didReceivedNotification(int id, int account, Object... args) {
        // No-op
    }

    @Override
    public void show() {
        // No-op: don't show Gift Premium upsells
    }

    @Override
    public CharSequence getTitle() {
        return "";
    }

    @Override
    protected RecyclerListView.SelectionAdapter createAdapter(RecyclerListView listView) {
        return new RecyclerListView.SelectionAdapter() {
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
        };
    }
}
