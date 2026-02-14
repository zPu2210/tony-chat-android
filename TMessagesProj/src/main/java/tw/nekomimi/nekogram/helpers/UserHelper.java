package tw.nekomimi.nekogram.helpers;

import org.telegram.messenger.BaseController;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.TLRPC;

public class UserHelper extends BaseController {
    public UserHelper(int num) {
        super(num);
    }

    public static UserHelper getInstance(int num) {
        return new UserHelper(num);
    }

    void resolveUser(String userName, long userId, Utilities.Callback<TLRPC.User> callback) {
        if (callback != null) callback.run(null);
    }
}
