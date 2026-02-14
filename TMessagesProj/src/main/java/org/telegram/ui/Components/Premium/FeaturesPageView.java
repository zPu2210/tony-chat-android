package org.telegram.ui.Components.Premium;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.telegram.ui.Components.RecyclerListView;

/**
 * STUB: Features page view for Premium features removed.
 * This stub prevents compilation errors in callers.
 */
public class FeaturesPageView extends BaseListPageView {

    // Feature constants referenced by PremiumFeatureBottomSheet
    public static final int FEATURES_STORIES = 0;
    public static final int FEATURES_BUSINESS = 1;
    public static final int FEATURES_PREMIUM = 2;

    public FeaturesPageView(Context context, int currentAccount, int type) {
        super(context, null);
    }

    @Override
    public RecyclerListView.SelectionAdapter createAdapter() {
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
