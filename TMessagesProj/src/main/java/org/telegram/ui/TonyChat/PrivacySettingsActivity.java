package org.telegram.ui.TonyChat;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.Cells.TextCheckCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;

import com.tonychat.core.TonyConfig;

/**
 * Privacy & Ghost Mode settings screen.
 * Allows toggling ghost mode (master + individual sub-toggles).
 */
public class PrivacySettingsActivity extends BaseFragment {

    private RecyclerListView listView;
    private ListAdapter listAdapter;

    // Row indices
    private int ghostHeaderRow;
    private int ghostMasterRow;
    private int suppressReadReceiptsRow;
    private int suppressOnlineStatusRow;
    private int suppressTypingRow;
    private int suppressUploadProgressRow;
    private int ghostDisclaimerRow;
    private int additionalHeaderRow;
    private int hidePhoneRow;
    private int disableLinkPreviewRow;
    private int additionalSectionRow;
    private int rowCount;

    @Override
    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        updateRows();
        return true;
    }

    private void updateRows() {
        rowCount = 0;
        ghostHeaderRow = rowCount++;
        ghostMasterRow = rowCount++;
        suppressReadReceiptsRow = rowCount++;
        suppressOnlineStatusRow = rowCount++;
        suppressTypingRow = rowCount++;
        suppressUploadProgressRow = rowCount++;
        ghostDisclaimerRow = rowCount++;
        additionalHeaderRow = rowCount++;
        hidePhoneRow = rowCount++;
        disableLinkPreviewRow = rowCount++;
        additionalSectionRow = rowCount++;
    }

    @Override
    public View createView(Context context) {
        actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        actionBar.setAllowOverlayTitle(true);
        actionBar.setTitle("Privacy & Ghost Mode");
        actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() {
            @Override
            public void onItemClick(int id) {
                if (id == -1) finishFragment();
            }
        });

        fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) fragmentView;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));

        listView = new RecyclerListView(context);
        listView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        listView.setVerticalScrollBarEnabled(false);
        frameLayout.addView(listView, LayoutHelper.createFrame(
            LayoutHelper.MATCH_PARENT, LayoutHelper.MATCH_PARENT, Gravity.TOP | Gravity.START
        ));
        listView.setAdapter(listAdapter = new ListAdapter(context));
        listView.setOnItemClickListener((view, position) -> onItemClick(view, position));
        return fragmentView;
    }

    private void onItemClick(View view, int position) {
        TonyConfig config = TonyConfig.INSTANCE;

        if (position == ghostMasterRow) {
            boolean newValue = !config.getPrivacy().isGhostModeActive();
            config.getPrivacy().setGhostMode(newValue);
            // Refresh all rows to show updated sub-toggle states
            if (listAdapter != null) {
                listAdapter.notifyDataSetChanged();
            }
            String msg = newValue ? "Ghost Mode enabled" : "Ghost Mode disabled";
            Toast.makeText(getParentActivity(), msg, Toast.LENGTH_SHORT).show();
        } else if (position == suppressReadReceiptsRow) {
            boolean newValue = !isSuppressReadReceipts();
            config.getPrivacy().setSendReadMessagePackets(!newValue);
            if (view instanceof TextCheckCell) ((TextCheckCell) view).setChecked(newValue);
            syncGhostMaster();
        } else if (position == suppressOnlineStatusRow) {
            boolean newValue = !isSuppressOnlineStatus();
            config.getPrivacy().setSendOnlinePackets(!newValue);
            if (view instanceof TextCheckCell) ((TextCheckCell) view).setChecked(newValue);
            syncGhostMaster();
        } else if (position == suppressTypingRow) {
            boolean newValue = !config.getPrivacy().getSuppressTypingIndicator();
            config.getPrivacy().setSuppressTypingIndicator(newValue);
            if (view instanceof TextCheckCell) ((TextCheckCell) view).setChecked(newValue);
            syncGhostMaster();
        } else if (position == suppressUploadProgressRow) {
            boolean newValue = !isSuppressUploadProgress();
            config.getPrivacy().setSendUploadProgress(!newValue);
            if (view instanceof TextCheckCell) ((TextCheckCell) view).setChecked(newValue);
            syncGhostMaster();
        } else if (position == hidePhoneRow) {
            boolean newValue = !config.getPrivacy().getHidePhone();
            config.getPrivacy().setHidePhone(newValue);
            if (view instanceof TextCheckCell) ((TextCheckCell) view).setChecked(newValue);
        } else if (position == disableLinkPreviewRow) {
            boolean newValue = !config.getChat().getDisableLinkPreviewByDefault();
            config.getChat().setDisableLinkPreviewByDefault(newValue);
            if (view instanceof TextCheckCell) ((TextCheckCell) view).setChecked(newValue);
        }
    }

    /** Sync ghost master toggle state based on individual toggles. */
    private void syncGhostMaster() {
        TonyConfig config = TonyConfig.INSTANCE;
        boolean allOn = isSuppressReadReceipts() && isSuppressOnlineStatus()
            && config.getPrivacy().getSuppressTypingIndicator() && isSuppressUploadProgress();
        boolean anyOn = isSuppressReadReceipts() || isSuppressOnlineStatus()
            || config.getPrivacy().getSuppressTypingIndicator() || isSuppressUploadProgress();

        // Master is active if ALL sub-toggles are on
        config.getPrivacy().setGhostModeActive(allOn);
        if (listAdapter != null) {
            listAdapter.notifyItemChanged(ghostMasterRow);
        }
    }

    private boolean isSuppressReadReceipts() {
        return !TonyConfig.INSTANCE.getPrivacy().getSendReadMessagePackets();
    }

    private boolean isSuppressOnlineStatus() {
        return !TonyConfig.INSTANCE.getPrivacy().getSendOnlinePackets();
    }

    private boolean isSuppressUploadProgress() {
        return !TonyConfig.INSTANCE.getPrivacy().getSendUploadProgress();
    }

    private class ListAdapter extends RecyclerListView.SelectionAdapter {
        private final Context context;

        public ListAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getItemCount() {
            return rowCount;
        }

        @Override
        public boolean isEnabled(RecyclerView.ViewHolder holder) {
            int type = holder.getItemViewType();
            return type == 1; // Only TextCheckCell is clickable
        }

        @Override
        public int getItemViewType(int position) {
            if (position == ghostHeaderRow || position == additionalHeaderRow) {
                return 0; // HeaderCell
            } else if (position == ghostDisclaimerRow) {
                return 2; // TextInfoPrivacyCell
            } else if (position == additionalSectionRow) {
                return 3; // ShadowSectionCell
            } else {
                return 1; // TextCheckCell
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                case 0:
                    view = new HeaderCell(context);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 1:
                    view = new TextCheckCell(context);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 2:
                    view = new TextInfoPrivacyCell(context);
                    break;
                case 3:
                default:
                    view = new ShadowSectionCell(context);
                    break;
            }
            return new RecyclerListView.Holder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            TonyConfig config = TonyConfig.INSTANCE;
            switch (holder.getItemViewType()) {
                case 0: {
                    HeaderCell cell = (HeaderCell) holder.itemView;
                    if (position == ghostHeaderRow) {
                        cell.setText("Ghost Mode");
                    } else if (position == additionalHeaderRow) {
                        cell.setText("Additional Privacy");
                    }
                    break;
                }
                case 1: {
                    TextCheckCell cell = (TextCheckCell) holder.itemView;
                    if (position == ghostMasterRow) {
                        cell.setTextAndValueAndCheck(
                            "Ghost Mode",
                            "Hide all online activity at once",
                            config.getPrivacy().isGhostModeActive(), true, true
                        );
                    } else if (position == suppressReadReceiptsRow) {
                        cell.setTextAndCheck("Hide read receipts", isSuppressReadReceipts(), true);
                    } else if (position == suppressOnlineStatusRow) {
                        cell.setTextAndCheck("Always appear offline", isSuppressOnlineStatus(), true);
                    } else if (position == suppressTypingRow) {
                        cell.setTextAndCheck("Hide typing indicator", config.getPrivacy().getSuppressTypingIndicator(), true);
                    } else if (position == suppressUploadProgressRow) {
                        cell.setTextAndCheck("Hide upload progress", isSuppressUploadProgress(), false);
                    } else if (position == hidePhoneRow) {
                        cell.setTextAndCheck("Hide phone number", config.getPrivacy().getHidePhone(), true);
                    } else if (position == disableLinkPreviewRow) {
                        cell.setTextAndCheck("Disable link previews", config.getChat().getDisableLinkPreviewByDefault(), false);
                    }
                    break;
                }
                case 2: {
                    TextInfoPrivacyCell cell = (TextInfoPrivacyCell) holder.itemView;
                    if (position == ghostDisclaimerRow) {
                        cell.setText("Ghost Mode suppresses outgoing status signals. " +
                            "Telegram servers may still detect your connection. " +
                            "Messages will still be sent and received normally.");
                    }
                    break;
                }
            }
        }
    }
}
