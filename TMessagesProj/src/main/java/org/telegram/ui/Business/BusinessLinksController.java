package org.telegram.ui.Business;

import android.util.SparseArray;
import org.telegram.tgnet.tl.TL_account;
import java.util.ArrayList;

public class BusinessLinksController {

    private static volatile SparseArray<BusinessLinksController> Instance = new SparseArray<>();
    private static final Object lockObject = new Object();

    public static BusinessLinksController getInstance(int num) {
        BusinessLinksController localInstance = Instance.get(num);
        if (localInstance == null) {
            synchronized (lockObject) {
                localInstance = Instance.get(num);
                if (localInstance == null) {
                    Instance.put(num, localInstance = new BusinessLinksController(num));
                }
            }
        }
        return localInstance;
    }

    public final int currentAccount;
    public final ArrayList<TL_account.TL_businessChatLink> links = new ArrayList<>();

    private BusinessLinksController(int currentAccount) {
        this.currentAccount = currentAccount;
    }

    public static String stripHttps(String link) {
        if (link.startsWith("https://")) {
            return link.substring(8);
        }
        return link;
    }

    public boolean canAddNew() {
        return false;
    }

    public void load(boolean forceReload) {}

    public TL_account.TL_businessChatLink findLink(String slug) {
        return null;
    }

    public void editLinkMessage(String link, String text, ArrayList entities, Runnable callback) {
        if (callback != null) {
            callback.run();
        }
    }
}
