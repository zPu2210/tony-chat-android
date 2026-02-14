package org.telegram.ui.Components.Premium;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.telegram.messenger.NotificationCenter;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.tl.TL_stars;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.BottomSheetWithRecyclerListView;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.PremiumPreviewFragment;

import java.util.ArrayList;

/**
 * STUB: Premium preview bottom sheet removed.
 * This stub prevents compilation errors in callers.
 */
public class PremiumPreviewBottomSheet extends BottomSheetWithRecyclerListView implements NotificationCenter.NotificationCenterDelegate {

    // Protected fields accessed by subclasses like PremiumPreviewGiftLinkBottomSheet
    protected int paddingRow;
    protected int rowCount;
    protected int additionStartRow;
    protected int additionEndRow;
    protected int featuresStartRow;
    protected int featuresEndRow;
    protected int sectionRow;
    protected int buttonRow;
    protected ArrayList<PremiumPreviewFragment.PremiumFeatureData> premiumFeatures = new ArrayList<>();
    protected TextView subtitleView;
    protected TextView[] titleView = new TextView[1];
    public View overrideTitleIcon;

    // Public fields accessed by ChatActivity and other callers
    public Object emojiStatusCollectible; // TLRPC.TL_emojiStatusCollectible or TL_help_appUpdate
    public boolean isEmojiStatus;
    public float startEnterFromX;
    public float startEnterFromY;
    public float startEnterFromScale;
    public float startEnterFromX1;
    public float startEnterFromY1;
    public View startEnterFromView;
    public Integer accentColor;
    public Object statusStickerSet; // TLRPC.InputStickerSet

    // Constructors used by callers
    public PremiumPreviewBottomSheet(BaseFragment fragment, int currentAccount, TLRPC.User user, Theme.ResourcesProvider resourcesProvider) {
        super(fragment, false, false, false, resourcesProvider);
    }

    public PremiumPreviewBottomSheet(BaseFragment fragment, int currentAccount, TLRPC.User user, GiftPremiumBottomSheet.GiftTier gift, TL_stars.StarGift stargift, Theme.ResourcesProvider resourcesProvider) {
        super(fragment, false, false, false, resourcesProvider);
    }

    // Builder-pattern methods
    public PremiumPreviewBottomSheet setOutboundGift(boolean outboundGift) {
        return this;
    }

    public PremiumPreviewBottomSheet setAnimateConfetti(boolean animateConfetti) {
        return this;
    }

    public PremiumPreviewBottomSheet setAnimateConfettiWithStars(boolean animateConfettiWithStars) {
        return this;
    }

    // Fields accessed by subclasses
    protected int termsRow;
    protected boolean useBackgroundTopPadding = true;

    protected static final int CELL_TYPE_LINK = 100;

    // Methods called by subclasses
    protected void updateRows() {
    }

    private void init() {
    }

    public void setTitle(boolean animated) {
    }

    protected View onCreateAdditionCell(int viewType, android.content.Context context) {
        return null;
    }

    protected void onBindAdditionCell(View view, int pos) {
    }

    protected int getAdditionItemViewType(int position) {
        return 0;
    }

    protected boolean needDefaultPremiumBtn() {
        return true;
    }

    protected void afterCellCreated(int viewType, View view) {
    }

    protected void attachIconContainer(android.widget.LinearLayout container) {
    }

    @Override
    public CharSequence getTitle() {
        return "";
    }

    @Override
    public void didReceivedNotification(int id, int account, Object... args) {
        // No-op
    }

    @Override
    public void show() {
        // No-op: don't show Premium upsells
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
