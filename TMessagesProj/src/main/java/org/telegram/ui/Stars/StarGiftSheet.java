package org.telegram.ui.Stars;

import android.content.Context;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet;

/**
 * STUB: Star gifts feature removed from Tony Chat.
 * Minimal stub to prevent compilation errors.
 */
public class StarGiftSheet extends BottomSheet {

    public StarGiftSheet(Context context) {
        super(context, false);
    }

    public StarGiftSheet(Context context, int account, long dialogId, Object resourcesProvider) {
        super(context, false);
    }

    public static void show(BaseFragment fragment, Object... args) {
        // No-op stub
    }

    public static void showSavedGift(BaseFragment fragment, Object... args) {
        // No-op stub
    }

    public static void showUniqueGift(BaseFragment fragment, Object... args) {
        // No-op stub
    }

    public void show() {
        // No-op stub - override parent to prevent actual showing
    }

    public StarGiftSheet set(Object messageObject) {
        return this;
    }

    public StarGiftSheet set(Object gift, Object param) {
        return this;
    }

    public StarGiftSheet set(String slug, Object gift, Object param) {
        return this;
    }

    public StarGiftSheet setupWearPage() {
        return this;
    }

    public static CharSequence replaceSingleTagToLink(String text, Object runnable) {
        return text;
    }

    public interface Delegate {
        void onGiftSent();
    }

    public static class PaymentFormState {
        public String currency;
        public org.telegram.tgnet.TLRPC.TL_payments_paymentFormStarGift form;
        public PaymentFormState(Object... args) {}
        public PaymentFormState(String currency, Object form) {
            this.currency = currency;
            this.form = (org.telegram.tgnet.TLRPC.TL_payments_paymentFormStarGift) form;
        }
    }

    public static class ResaleBuyTransferAlert {
        public final org.telegram.ui.ActionBar.AlertDialog alertDialog;

        public static class Progress {
            public void init() {}
            public void end() {}
        }

        public ResaleBuyTransferAlert(Context context, Object resourceProvider, Object gift, PaymentFormState state, int account, long to, String name, org.telegram.messenger.Utilities.Callback2<PaymentFormState, Progress> callback) {
            alertDialog = new org.telegram.ui.ActionBar.AlertDialog(context, 0);
        }

        public ResaleBuyTransferAlert(Object... args) {
            alertDialog = null;
        }
        public void show() {}
    }

    public interface ProgressCallback {
        void init();
        void end();
    }
}
