package org.telegram.ui.Gifts;

import java.util.ArrayList;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.tl.TL_stars;
import org.telegram.ui.ActionBar.BaseFragment;

/**
 * STUB: Resale gifts feature removed from Tony Chat.
 */
public class ResaleGiftsFragment extends BaseFragment {

    public ResaleGiftsFragment() {}

    public static void show(Object... args) {}

    public static class ResaleGiftsList {
        public long gift_id;
        public final ArrayList<TL_stars.TL_starGiftUnique> gifts = new ArrayList<>();
        public boolean loading = false;
        public boolean endReached = true;

        public ResaleGiftsList() {}
        public ResaleGiftsList(int account, long giftId, Utilities.Callback<Boolean> callback) {
            this.gift_id = giftId;
        }

        public void load() {}
        public void cancel() {}
        public int getCount() { return 0; }
    }

    public static class ResaleBuyTransferAlert {
        public ResaleBuyTransferAlert(Object... args) {}
        public void show() {}
    }

    public static class PaymentFormState {
        public static final int STATE_LOADING = 0;
        public static final int STATE_LOADED = 1;
        public static final int STATE_ERROR = 2;
    }
}
