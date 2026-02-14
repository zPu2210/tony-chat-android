package org.telegram.ui.Business;

import android.util.SparseArray;
import org.telegram.tgnet.TLRPC;
import java.util.ArrayList;

public class TimezonesController {

    private static volatile SparseArray<TimezonesController> Instance = new SparseArray<>();
    private static final Object lockObject = new Object();

    public static TimezonesController getInstance(int num) {
        TimezonesController localInstance = Instance.get(num);
        if (localInstance == null) {
            synchronized (lockObject) {
                localInstance = Instance.get(num);
                if (localInstance == null) {
                    Instance.put(num, localInstance = new TimezonesController(num));
                }
            }
        }
        return localInstance;
    }

    private final int currentAccount;
    private TimezonesController(int account) {
        this.currentAccount = account;
    }

    public ArrayList<TLRPC.TL_timezone> getTimezones() {
        return new ArrayList<>();
    }

    public String getTimezoneName(TLRPC.TL_timezone timezone, boolean flag) {
        return "";
    }
}
