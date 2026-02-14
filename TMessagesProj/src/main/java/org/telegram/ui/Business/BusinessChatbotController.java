package org.telegram.ui.Business;

import android.util.SparseArray;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.tl.TL_account;

public class BusinessChatbotController {

    private static volatile SparseArray<BusinessChatbotController> Instance = new SparseArray<>();
    private static final Object lockObject = new Object();

    public static BusinessChatbotController getInstance(int num) {
        BusinessChatbotController localInstance = Instance.get(num);
        if (localInstance == null) {
            synchronized (lockObject) {
                localInstance = Instance.get(num);
                if (localInstance == null) {
                    Instance.put(num, localInstance = new BusinessChatbotController(num));
                }
            }
        }
        return localInstance;
    }

    private final int currentAccount;
    private BusinessChatbotController(int account) {
        this.currentAccount = account;
    }

    public void load(Utilities.Callback<TL_account.connectedBots> callback) {
        if (callback != null) {
            callback.run(null);
        }
    }

    public void invalidate(boolean reload) {}
}
