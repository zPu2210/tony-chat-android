package tw.nekomimi.nekogram.helpers;

import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.Utilities;

import java.util.HashMap;

public class CloudStorageHelper extends AccountInstance {
    public CloudStorageHelper(int num) {
        super(num);
    }

    public static CloudStorageHelper getInstance(int num) {
        return new CloudStorageHelper(num);
    }

    public void setItem(String key, String value, Utilities.Callback2<String, String> callback) {
        if (callback != null) callback.run(null, null);
    }

    public void getItem(String key, Utilities.Callback2<String, String> callback) {
        if (callback != null) callback.run(null, null);
    }

    public void getItems(String[] keys, Utilities.Callback2<HashMap<String, String>, String> callback) {
        if (callback != null) callback.run(new HashMap<>(), null);
    }

    public void removeItem(String key, Utilities.Callback2<String, String> callback) {
        if (callback != null) callback.run(null, null);
    }

    public void removeItems(String[] keys, Utilities.Callback2<String, String> callback) {
        if (callback != null) callback.run(null, null);
    }

    public void getKeys(Utilities.Callback2<String[], String> callback) {
        if (callback != null) callback.run(new String[0], null);
    }
}
