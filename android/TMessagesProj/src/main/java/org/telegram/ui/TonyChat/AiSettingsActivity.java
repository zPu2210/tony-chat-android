package org.telegram.ui.TonyChat;

import android.content.Context;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.Cells.TextCheckCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.EditTextBoldCursor;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;

import com.tonychat.ai.AiFeatureType;
import com.tonychat.ai.AiManager;
import com.tonychat.ai.config.AiConfig;
import com.tonychat.ai.consent.AiConsentManager;

/** AI Features settings screen accessible from the navigation drawer. */
public class AiSettingsActivity extends BaseFragment {

    private RecyclerListView listView;
    private ListAdapter listAdapter;

    // Row indices
    private int featuresHeaderRow;
    private int smartReplyRow;
    private int summaryRow;
    private int toneRewriteRow;
    private int translateRow;
    private int featuresSectionRow;
    private int apiKeysHeaderRow;
    private int openAiKeyRow;
    private int anthropicKeyRow;
    private int removeBgKeyRow;
    private int apiKeysSectionRow;
    private int preferencesHeaderRow;
    private int preferOnDeviceRow;
    private int preferencesSectionRow;
    private int cacheHeaderRow;
    private int clearCacheRow;
    private int cacheSectionRow;
    private int rowCount;

    @Override
    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        updateRows();
        return true;
    }

    private void updateRows() {
        rowCount = 0;
        featuresHeaderRow = rowCount++;
        smartReplyRow = rowCount++;
        summaryRow = rowCount++;
        toneRewriteRow = rowCount++;
        translateRow = rowCount++;
        featuresSectionRow = rowCount++;
        apiKeysHeaderRow = rowCount++;
        openAiKeyRow = rowCount++;
        anthropicKeyRow = rowCount++;
        removeBgKeyRow = rowCount++;
        apiKeysSectionRow = rowCount++;
        preferencesHeaderRow = rowCount++;
        preferOnDeviceRow = rowCount++;
        preferencesSectionRow = rowCount++;
        cacheHeaderRow = rowCount++;
        clearCacheRow = rowCount++;
        cacheSectionRow = rowCount++;
    }

    @Override
    public View createView(Context context) {
        actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        actionBar.setAllowOverlayTitle(true);
        actionBar.setTitle("AI Features");
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
        if (position == smartReplyRow) {
            toggleFeature(AiFeatureType.SMART_REPLY, view);
        } else if (position == summaryRow) {
            toggleFeature(AiFeatureType.SUMMARY, view);
        } else if (position == toneRewriteRow) {
            toggleFeature(AiFeatureType.TONE_REWRITE, view);
        } else if (position == translateRow) {
            toggleFeature(AiFeatureType.TRANSLATE, view);
        } else if (position == openAiKeyRow) {
            showApiKeyDialog("OpenAI API Key", AiConfig.INSTANCE.getOpenAiApiKey(), key -> {
                AiConfig.INSTANCE.setOpenAiApiKey(key);
                AiManager.INSTANCE.refreshProviders();
                if (listAdapter != null) listAdapter.notifyItemChanged(openAiKeyRow);
            });
        } else if (position == anthropicKeyRow) {
            showApiKeyDialog("Anthropic API Key", AiConfig.INSTANCE.getAnthropicApiKey(), key -> {
                AiConfig.INSTANCE.setAnthropicApiKey(key);
                AiManager.INSTANCE.refreshProviders();
                if (listAdapter != null) listAdapter.notifyItemChanged(anthropicKeyRow);
            });
        } else if (position == removeBgKeyRow) {
            showApiKeyDialog("Remove.bg API Key", AiConfig.INSTANCE.getRemoveBgApiKey(), key -> {
                AiConfig.INSTANCE.setRemoveBgApiKey(key);
                AiManager.INSTANCE.refreshProviders();
                if (listAdapter != null) listAdapter.notifyItemChanged(removeBgKeyRow);
            });
        } else if (position == preferOnDeviceRow) {
            boolean newValue = !AiConfig.INSTANCE.getPreferOnDevice();
            AiConfig.INSTANCE.setPreferOnDevice(newValue);
            if (view instanceof TextCheckCell) {
                ((TextCheckCell) view).setChecked(newValue);
            }
        } else if (position == clearCacheRow) {
            clearCache();
        }
    }

    private void toggleFeature(AiFeatureType feature, View view) {
        boolean hasConsent = AiConsentManager.INSTANCE.hasConsent(feature);
        if (!hasConsent) {
            boolean isOnDevice = AiConfig.INSTANCE.getPreferOnDevice()
                && (feature == AiFeatureType.SMART_REPLY || feature == AiFeatureType.TONE_REWRITE);
            AiConsentDialog.show(getParentActivity(), feature, isOnDevice, granted -> {
                if (granted) {
                    AiConfig.INSTANCE.setFeatureEnabled(feature, true);
                    if (view instanceof TextCheckCell) {
                        ((TextCheckCell) view).setChecked(true);
                    }
                }
            });
        } else {
            boolean enabled = AiConfig.INSTANCE.isFeatureEnabled(feature);
            boolean newValue = !enabled;
            AiConfig.INSTANCE.setFeatureEnabled(feature, newValue);
            if (!newValue) {
                AiConsentManager.INSTANCE.revokeConsent(feature);
            }
            if (view instanceof TextCheckCell) {
                ((TextCheckCell) view).setChecked(newValue);
            }
        }
    }

    private void showApiKeyDialog(String title, String currentKey, ApiKeyCallback callback) {
        Context context = getParentActivity();
        if (context == null) return;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);

        EditTextBoldCursor editText = new EditTextBoldCursor(context);
        editText.setTextSize(18);

        if (currentKey != null && currentKey.length() > 4) {
            editText.setHint("****" + currentKey.substring(currentKey.length() - 4));
        } else {
            editText.setHint("sk-...");
        }

        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        editText.setSingleLine(true);
        editText.setPadding(AndroidUtilities.dp(24), AndroidUtilities.dp(8), AndroidUtilities.dp(24), AndroidUtilities.dp(8));
        builder.setView(editText);

        builder.setPositiveButton(LocaleController.getString(R.string.OK), (dialog, which) -> {
            String key = editText.getText().toString().trim();
            if (key.isEmpty() && currentKey != null) {
                callback.onKey(currentKey);
            } else {
                callback.onKey(key.isEmpty() ? null : key);
            }
        });
        builder.setNegativeButton(LocaleController.getString(R.string.Cancel), null);
        if (currentKey != null && !currentKey.isEmpty()) {
            builder.setNeutralButton("Remove", (dialog, which) -> callback.onKey(null));
        }

        AlertDialog dialog = builder.show();

        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
            );
        }

        editText.requestFocus();
    }

    private void clearCache() {
        Context context = getParentActivity();
        if (context == null) return;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Clear AI Cache");
        builder.setMessage("This will delete all cached AI responses.");
        builder.setPositiveButton("Clear", (dialog, which) -> {
            AiManager.INSTANCE.clearCache(() -> AndroidUtilities.runOnUIThread(() -> {
                if (listAdapter != null) listAdapter.notifyItemChanged(clearCacheRow);
            }));
        });
        builder.setNegativeButton(LocaleController.getString(R.string.Cancel), null);
        builder.show();
    }

    private interface ApiKeyCallback {
        void onKey(String key);
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
            return type != 0 && type != 3; // Not header or shadow
        }

        @Override
        public int getItemViewType(int position) {
            if (position == featuresHeaderRow || position == apiKeysHeaderRow
                || position == preferencesHeaderRow || position == cacheHeaderRow) {
                return 0; // HeaderCell
            } else if (position == featuresSectionRow || position == apiKeysSectionRow
                || position == preferencesSectionRow || position == cacheSectionRow) {
                return 3; // ShadowSectionCell
            } else if (position == smartReplyRow || position == summaryRow
                || position == toneRewriteRow || position == translateRow
                || position == preferOnDeviceRow) {
                return 1; // TextCheckCell
            } else {
                return 2; // TextSettingsCell
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
                    view = new TextSettingsCell(context);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
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
            switch (holder.getItemViewType()) {
                case 0: {
                    HeaderCell cell = (HeaderCell) holder.itemView;
                    if (position == featuresHeaderRow) {
                        cell.setText("AI Features");
                    } else if (position == apiKeysHeaderRow) {
                        cell.setText("API Keys (Advanced)");
                    } else if (position == preferencesHeaderRow) {
                        cell.setText("Preferences");
                    } else if (position == cacheHeaderRow) {
                        cell.setText("Cache");
                    }
                    break;
                }
                case 1: {
                    TextCheckCell cell = (TextCheckCell) holder.itemView;
                    if (position == smartReplyRow) {
                        cell.setTextAndCheck("Smart Replies", isFeatureOn(AiFeatureType.SMART_REPLY), true);
                    } else if (position == summaryRow) {
                        cell.setTextAndCheck("Chat Summary", isFeatureOn(AiFeatureType.SUMMARY), true);
                    } else if (position == toneRewriteRow) {
                        cell.setTextAndCheck("Tone Rewrite", isFeatureOn(AiFeatureType.TONE_REWRITE), true);
                    } else if (position == translateRow) {
                        cell.setTextAndCheck("AI Translation", isFeatureOn(AiFeatureType.TRANSLATE), false);
                    } else if (position == preferOnDeviceRow) {
                        cell.setTextAndCheck("Prefer on-device processing", AiConfig.INSTANCE.getPreferOnDevice(), false);
                    }
                    break;
                }
                case 2: {
                    TextSettingsCell cell = (TextSettingsCell) holder.itemView;
                    if (position == openAiKeyRow) {
                        String key = AiConfig.INSTANCE.getOpenAiApiKey();
                        cell.setTextAndValue("OpenAI", key != null ? "Configured" : "Not set", true);
                    } else if (position == anthropicKeyRow) {
                        String key = AiConfig.INSTANCE.getAnthropicApiKey();
                        cell.setTextAndValue("Anthropic", key != null ? "Configured" : "Not set", true);
                    } else if (position == removeBgKeyRow) {
                        String key = AiConfig.INSTANCE.getRemoveBgApiKey();
                        cell.setTextAndValue("Remove.bg", key != null ? "Configured" : "Not set", false);
                    } else if (position == clearCacheRow) {
                        cell.setText("Clear AI Cache", false);
                    }
                    break;
                }
            }
        }

        private boolean isFeatureOn(AiFeatureType feature) {
            return AiConsentManager.INSTANCE.hasConsent(feature) && AiConfig.INSTANCE.isFeatureEnabled(feature);
        }
    }
}
