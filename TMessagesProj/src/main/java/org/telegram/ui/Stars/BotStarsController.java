package org.telegram.ui.Stars;

import android.content.Context;
import android.util.SparseArray;

import org.telegram.messenger.Utilities;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.tl.TL_payments;
import org.telegram.tgnet.tl.TL_stars;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * STUB: Bot Stars feature removed from Tony Chat.
 * All methods return empty/zero values.
 */
public class BotStarsController {

    private static volatile SparseArray<BotStarsController> Instance = new SparseArray<>();
    private static final Object lockObject = new Object();

    public static BotStarsController getInstance(int num) {
        BotStarsController localInstance = Instance.get(num);
        if (localInstance == null) {
            synchronized (lockObject) {
                localInstance = Instance.get(num);
                if (localInstance == null) {
                    Instance.put(num, localInstance = new BotStarsController(num));
                }
            }
        }
        return localInstance;
    }

    public final int currentAccount;

    private BotStarsController(int account) {
        currentAccount = account;
    }

    // Balance methods - all return 0/null/false
    public TL_stars.StarsAmount getBotStarsBalance(long did) {
        return TL_stars.StarsAmount.ofStars(0);
    }

    public void invalidateStarsBalance(long did) {}

    public long getTONBalance(long did) {
        return 0;
    }

    public long getAvailableBalance(long did) {
        return 0;
    }

    public boolean isStarsBalanceAvailable(long did) {
        return false;
    }

    public boolean isTONBalanceAvailable(long did) {
        return false;
    }

    public TLRPC.TL_payments_starsRevenueStats getStarsRevenueStats(long did) {
        return null;
    }

    public TLRPC.TL_payments_starsRevenueStats getStarsRevenueStats(long did, boolean force) {
        return null;
    }

    public boolean botHasStars(long did) {
        return false;
    }

    public boolean botHasTON(long did) {
        return false;
    }

    public void preloadStarsStats(long did) {}

    public void preloadTonStats(long did) {}

    public TLRPC.TL_payments_starsRevenueStats getTONRevenueStats(long did, boolean force) {
        return null;
    }

    public void onUpdate(TLRPC.TL_updateStarsRevenueStatus update) {}

    // Transaction methods - all return false/empty
    public void invalidateTransactions(long did, boolean load) {}

    public void preloadTransactions(long did) {}

    public void loadTransactions(long did, int type) {}

    public boolean isLoadingTransactions(long did, int type) {
        return false;
    }

    public boolean didFullyLoadTransactions(long did, int type) {
        return true;
    }

    public boolean hasTransactions(long did) {
        return false;
    }

    public boolean hasTransactions(long did, int type) {
        return false;
    }

    // Connected bots stubs
    public class ChannelConnectedBots {
        public final ArrayList<TL_payments.connectedBotStarRef> bots = new ArrayList<>();
        public int count = 0;
        public boolean endReached = true;

        public void clear() {}
        public void check() {}
        public void cancel() {}
        public boolean isLoading() { return false; }
        public void load() {}
        public void apply(TL_payments.connectedStarRefBots res) {}
        public void applyEdit(TL_payments.connectedStarRefBots res) {}
    }

    private final ChannelConnectedBots emptyConnectedBots = new ChannelConnectedBots();
    public ChannelConnectedBots getChannelConnectedBots(long dialogId) {
        return emptyConnectedBots;
    }

    public boolean channelHasConnectedBots(long dialogId) {
        return false;
    }

    // Sort enum needs to be static
    public enum Sort { BY_REVENUE, BY_DATE }

    // Suggested bots stubs
    public class ChannelSuggestedBots {
        public final ArrayList<TL_payments.connectedBotStarRef> bots = new ArrayList<>();
        public int count = 0;
        public boolean endReached = true;

        public void clear() {}
        public void check() {}
        public void cancel() {}
        public boolean isLoading() { return false; }
        public int getCount() { return 0; }
        public void setSort(Sort sort) {}
        public Sort getSort() { return Sort.BY_REVENUE; }
        public void load() {}
        public void remove(long did) {}
        public void reload() {}
    }

    private final ChannelSuggestedBots emptySuggestedBots = new ChannelSuggestedBots();
    public ChannelSuggestedBots getChannelSuggestedBots(long dialogId) {
        return emptySuggestedBots;
    }

    public boolean channelHasSuggestedBots(long dialogId) {
        return false;
    }

    public void loadAdminedBots() {}

    public void loadAdminedChannels() {}

    public void getConnectedBot(Context context, long dialogId, long botId, Utilities.Callback<TL_payments.connectedBotStarRef> whenDone) {
        if (whenDone != null) whenDone.run(null);
    }

    public ArrayList<TLObject> getAdmined() {
        return new ArrayList<>();
    }

    public final HashMap<Long, Object> bots = new HashMap<>();
}
