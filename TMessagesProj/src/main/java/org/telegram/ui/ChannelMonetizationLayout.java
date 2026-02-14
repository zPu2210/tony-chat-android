package org.telegram.ui;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import org.telegram.tgnet.tl.TL_stars;
import org.telegram.tgnet.tl.TL_stats;
import org.telegram.tgnet.TLRPC;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.messenger.R;

/**
 * Stub for removed ChannelMonetizationLayout - monetization/sponsored features removed.
 * Provides minimal no-op implementations to avoid breaking existing code.
 */
public class ChannelMonetizationLayout extends FrameLayout {

    public static ChannelMonetizationLayout instance;
    public long dialogId;

    public ChannelMonetizationLayout(Context context) {
        super(context);
    }

    public ChannelMonetizationLayout(Context context, BaseFragment fragment, int currentAccount, long chatId, Theme.ResourcesProvider resourcesProvider, boolean canViewRevenue, boolean canViewStarsRevenue) {
        super(context);
        // No-op stub constructor
    }

    public void setupBalances(boolean hasTon, Object status) {
        // No-op stub
    }

    public void reloadTransactions() {
        // No-op stub
    }

    public void setActionBar(Object actionBar) {
        // No-op stub
    }

    public static AlertDialog makeLearnSheet(Context context, boolean isBot, Theme.ResourcesProvider resourceProvider) {
        // Return a simple dismissible alert
        return new AlertDialog.Builder(context).create();
    }

    public static void showTransactionSheet(Context context, int currentAccount, TL_stats.BroadcastRevenueTransaction transaction, long botId, Theme.ResourcesProvider resourceProvider) {
        // No-op stub
    }

    public static class ProceedOverviewCell extends LinearLayout {
        public ProceedOverviewCell(Context context, Theme.ResourcesProvider resourcesProvider) {
            super(context);
        }

        public void set(ProceedOverview overview) {
            // No-op stub
        }
    }

    public static class ProceedOverview {
        public boolean contains1 = true;
        public String crypto_currency;
        public CharSequence text;
        public long crypto_amount;
        public long amount;
        public String currency;

        public boolean contains2;
        public String crypto_currency2;
        public TL_stars.StarsAmount crypto_amount2 = TL_stars.StarsAmount.ofStars(0);
        public long amount2;

        public static ProceedOverview as(String cryptoCurrency, CharSequence text) {
            ProceedOverview o = new ProceedOverview();
            o.crypto_currency = cryptoCurrency;
            o.text = text;
            return o;
        }

        public static ProceedOverview as(String cryptoCurrency, String cryptoCurrency2, CharSequence text) {
            ProceedOverview o = new ProceedOverview();
            o.contains1 = false;
            o.crypto_currency = cryptoCurrency;
            o.crypto_currency2 = cryptoCurrency2;
            o.text = text;
            return o;
        }
    }

    public static class TransactionCell extends FrameLayout {
        public TransactionCell(Context context, Theme.ResourcesProvider resourcesProvider) {
            super(context);
        }

        public void set(TL_stats.BroadcastRevenueTransaction transaction, boolean divider) {
            // No-op stub
        }
    }
}
