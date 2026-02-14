package org.telegram.messenger;

import android.util.SparseArray;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

// TONY_CHAT: Gift auctions feature removed - stubs only
public class GiftAuctionController extends BaseController {

    private GiftAuctionController(int num) {
        super(num);
    }

    private static volatile SparseArray<GiftAuctionController> Instance = new SparseArray<>();

    public static GiftAuctionController getInstance(int num) {
        GiftAuctionController localInstance = Instance.get(num);
        if (localInstance == null) {
            synchronized (GiftAuctionController.class) {
                localInstance = Instance.get(num);
                if (localInstance == null) {
                    Instance.set(num, localInstance = new GiftAuctionController(num));
                }
            }
        }
        return localInstance;
    }

    public boolean hasActiveAuctions() {
        return false;
    }

    public ArrayList<Object> getActiveAuctions() {
        return new ArrayList<>();
    }

    public void subscribeToActiveAuctionsUpdates(Object listener) {
        // Stub
    }

    public void unsubscribeFromActiveAuctionsUpdates(Object listener) {
        // Stub
    }

    public int requestGiftAuctionBySlug(String slug, Utilities.Callback2<Object, Object> callback) {
        if (callback != null) {
            callback.run(null, "Feature removed");
        }
        return 0;
    }
}
