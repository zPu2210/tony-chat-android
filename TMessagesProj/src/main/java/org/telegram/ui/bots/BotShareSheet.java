package org.telegram.ui.bots;

import android.content.Context;

import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.BottomSheetWithRecyclerListView;
import org.telegram.ui.Components.RecyclerListView;

/**
 * STUB: Bot Share Sheet removed from Tony Chat.
 * Bot sharing UI features are not supported.
 */
public class BotShareSheet extends BottomSheetWithRecyclerListView {

    public BotShareSheet(Context context, BaseFragment fragment, Theme.ResourcesProvider resourcesProvider) {
        super(fragment, false, false, false, resourcesProvider);
    }

    public void show(String url, String text) {
        // Do nothing
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    protected CharSequence getTitle() {
        return "";
    }

    @Override
    protected RecyclerListView.SelectionAdapter createAdapter(RecyclerListView listView) {
        return new RecyclerListView.SelectionAdapter() {
            @Override
            public boolean isEnabled(androidx.recyclerview.widget.RecyclerView.ViewHolder holder) {
                return false;
            }

            @Override
            public androidx.recyclerview.widget.RecyclerView.ViewHolder onCreateViewHolder(android.view.ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onBindViewHolder(androidx.recyclerview.widget.RecyclerView.ViewHolder holder, int position) {
            }

            @Override
            public int getItemCount() {
                return 0;
            }
        };
    }
}
