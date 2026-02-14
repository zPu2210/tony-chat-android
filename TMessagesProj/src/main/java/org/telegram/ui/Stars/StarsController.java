package org.telegram.ui.Stars;

import android.app.Activity;
import android.content.Context;
import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.telegram.messenger.AccountInstance;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.utils.tlutils.AmountUtils;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.tl.TL_stars;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * STUB: Stars & Gifts feature removed from Tony Chat.
 * All methods return empty/zero values.
 */
public class StarsController {

    public static final String currency = "XTR";
    public static final int PERIOD_MONTHLY = 2592000;
    public static final int PERIOD_MINUTE = 60;
    public static final int PERIOD_5MINUTES = 300;

    private static volatile List<SparseArray<StarsController>> Instance = new ArrayList<>(2);
    private static final Object lockObject = new Object();
    static {
        for (int a = 0; a < 2; ++a) {
            Instance.add(new SparseArray<>());
        }
    }

    public static StarsController getTonInstance(int num) {
        return getInstance(num, true);
    }

    public static StarsController getInstance(int num) {
        return getInstance(num, false);
    }

    public static StarsController getInstance(int num, AmountUtils.Currency currency) {
        return getInstance(num, currency == AmountUtils.Currency.TON);
    }

    public static StarsController getInstance(int num, String currency) {
        return getInstance(num, false);
    }

    public static StarsController getInstance(int num, boolean ton) {
        int index = ton ? 1 : 0;
        StarsController localInstance = Instance.get(index).get(num);
        if (localInstance == null) {
            synchronized (lockObject) {
                localInstance = Instance.get(index).get(num);
                if (localInstance == null) {
                    localInstance = new StarsController(num, ton);
                    Instance.get(index).put(num, localInstance);
                }
            }
        }
        return localInstance;
    }

    public final int currentAccount;
    public final boolean ton;
    public final TL_stars.StarsAmount balance = TL_stars.StarsAmount.ofStars(0);
    public final ArrayList<TL_stars.StarsSubscription> insufficientSubscriptions = new ArrayList<>();
    public final ArrayList<TL_stars.StarGift> sortedGifts = new ArrayList<>();

    private StarsController(int account, boolean ton) {
        this.currentAccount = account;
        this.ton = ton;
    }

    // Balance methods - all return 0
    public TL_stars.StarsAmount getBalance() { return TL_stars.StarsAmount.ofStars(0); }
    public TL_stars.StarsAmount getBalance(boolean withMinus) { return TL_stars.StarsAmount.ofStars(0); }
    public void getBalance(boolean force, Runnable loaded, boolean flag) { if (loaded != null) loaded.run(); }
    public boolean canUseTon() { return false; }
    public void invalidateBalance() {}
    public void invalidateBalance(Runnable loaded) { if (loaded != null) loaded.run(); }
    public void updateBalance(TL_stars.StarsAmount balance) {}
    public boolean balanceAvailable() { return false; }

    // Transaction methods - all return empty/false
    public void invalidateTransactions(boolean load) {}
    public void preloadTransactions() {}
    public void loadTransactions(int type) {}
    public boolean isLoadingTransactions(int type) { return false; }
    public boolean didFullyLoadTransactions(int type) { return true; }
    public boolean hasTransactions() { return false; }
    public boolean hasTransactions(int type) { return false; }

    // Subscription methods - all return empty/false
    public boolean hasSubscriptions() { return false; }
    public void invalidateSubscriptions(boolean load) {}
    public void loadSubscriptions() {}
    public boolean isLoadingSubscriptions() { return false; }
    public boolean didFullyLoadSubscriptions() { return true; }
    public void loadInsufficientSubscriptions() {}
    public void invalidateInsufficientSubscriptions(boolean load) {}
    public boolean hasInsufficientSubscriptions() { return false; }

    // Purchase/payment methods - all no-ops
    public void showStarsTopup(Activity activity, long amount, String purpose) {}
    public void buy(Activity activity, TL_stars.TL_starsTopupOption option, Utilities.Callback2<Boolean, String> whenDone) {
        if (whenDone != null) whenDone.run(false, null);
    }
    public void buyGift(Activity activity, TL_stars.TL_starsGiftOption option, long user_id, Utilities.Callback2<Boolean, String> whenDone) {
        if (whenDone != null) whenDone.run(false, null);
    }
    public void buyGiveaway(Activity activity, TL_stars.TL_starsGiveawayOption option, ArrayList<Long> chat_ids, ArrayList<Long> additional_chat_ids, int winners, boolean only_new, boolean show_winners, String prize_description, int until_date, String countries, Utilities.Callback2<Boolean, String> whenDone) {
        if (whenDone != null) whenDone.run(false, null);
    }

    public void buyGiveaway(Activity activity, TLRPC.Chat chat, List selectedChats, TL_stars.TL_starsGiveawayOption option, int users, List countries, int untilDate, boolean showWinners, boolean onlyNew, boolean additionalPrize, String prizeDesc, Utilities.Callback2<Boolean, String> whenDone) {
        if (whenDone != null) whenDone.run(false, null);
    }

    public void buyGiveaway(Object... args) {
        // Varargs no-op stub for flexibility
    }

    public ArrayList<TL_stars.TL_starsGiveawayOption> getGiveawayOptions() {
        return new ArrayList<>();
    }
    public Runnable pay(MessageObject messageObject, Runnable whenShown) { return null; }
    public void openPaymentForm(MessageObject messageObject, TLRPC.InputInvoice inputInvoice, TLRPC.TL_payments_paymentFormStars form, Runnable whenShown, Utilities.Callback<String> whenAllDone) {}
    public void subscribeTo(String hash, TLRPC.ChatInvite chatInvite, Utilities.Callback2<String, Long> whenAllDone) {
        if (whenAllDone != null) whenAllDone.run(null, null);
    }
    public static void showNoSupportDialog(Context context, Theme.ResourcesProvider resourcesProvider) {}
    public void payAfterConfirmed(MessageObject messageObject, TLRPC.InputInvoice inputInvoice, TLRPC.TL_payments_paymentFormStars form, Utilities.Callback<Boolean> whenDone) {
        if (whenDone != null) whenDone.run(false);
    }
    public void updateMediaPrice(MessageObject msg, long price, Runnable done) {
        if (done != null) done.run();
    }

    // MessageId class
    public static class MessageId {
        public final long did;
        public final int mid;
        public MessageId(long did, int mid) {
            this.did = did;
            this.mid = mid;
        }
        public static MessageId from(long did, int mid) {
            return new MessageId(did, mid);
        }
        public static MessageId from(MessageObject msg) {
            return msg == null ? null : new MessageId(msg.getDialogId(), msg.getId());
        }
        @Override
        public int hashCode() {
            return Objects.hash(did, mid);
        }
        @Override
        public boolean equals(@Nullable Object obj) {
            if (!(obj instanceof MessageId)) return false;
            MessageId id = (MessageId) obj;
            return id.did == did && id.mid == mid;
        }
    }

    // Paid reactions methods
    public long getPaidReactionsDialogId(MessageObject messageObject) { return 0; }
    public long getPaidReactionsDialogId(MessageId id, TLRPC.MessageReactions reactions) { return 0; }

    public class PendingPaidReactions {
        public long getPeerId() { return 0; }
        public boolean isAnonymous() { return false; }
        public void setOverlay(Object overlay) {}
        public String getToastTitle() { return ""; }
        public void add(long amount, boolean affect) {}
        public void apply() {}
        public void close() {}
        public void cancel() {}
        public void commit() {}
    }

    public Context getContext(BaseFragment fragment) { return null; }
    public void undoPaidReaction() {}
    public void commitPaidReaction() {}
    public boolean hasPendingPaidReactions(MessageObject messageObject) { return false; }
    public long getPendingPaidReactions(MessageObject messageObject) { return 0; }
    public long getPendingPaidReactions(long dialogId, int messageId) { return 0; }
    public PendingPaidReactions sendPaidReaction(MessageObject messageObject, Object fragment, int count, boolean flag1, boolean flag2, Object param) {
        return new PendingPaidReactions();
    }

    // Star gifts methods
    public void invalidateStarGifts() {}
    public void loadStarGifts() {}
    public void makeStarGiftSoldOut(TL_stars.StarGift starGift) {}
    public Runnable getStarGift(long gift_id, Utilities.Callback<TL_stars.StarGift> whenDone) {
        if (whenDone != null) whenDone.run(null);
        return null;
    }
    public void buyPremiumGift(long dialogId, Object option, TLRPC.TL_textWithEntities text, Utilities.Callback2<Boolean, String> whenDone) {
        if (whenDone != null) whenDone.run(false, null);
    }
    public void buyStarGift(TL_stars.StarGift gift, boolean anonymous, boolean upgraded, long dialogId, TLRPC.TL_textWithEntities text, Utilities.Callback2<Boolean, String> whenDone) {
        if (whenDone != null) whenDone.run(false, null);
    }
    public void getResellingGiftForm(TL_stars.StarGift gift, long dialogId, Utilities.Callback<TLRPC.TL_payments_paymentFormStarGift> whenDone) {
        if (whenDone != null) whenDone.run(null);
    }
    public static long getFormStarsPrice(TLRPC.PaymentForm form) { return 0; }
    public void buyResellingGift(TLRPC.TL_payments_paymentFormStarGift form, TL_stars.StarGift gift, long dialogId, Utilities.Callback2<Boolean, String> whenDone) {
        if (whenDone != null) whenDone.run(false, null);
    }

    // Profile gifts methods
    public GiftsList getProfileGiftsList(long dialogId) { return getProfileGiftsList(dialogId, false); }
    public GiftsList getProfileGiftsList(long dialogId, boolean create) { return null; }
    public GiftsCollections getProfileGiftCollectionsList(long dialogId, boolean create) { return null; }
    public void invalidateProfileGifts(long dialogId) {}
    public void invalidateProfileGifts(TLRPC.UserFull userFull) {}

    // GiftsCollections stub
    public static class GiftsCollections {
        public boolean isMine() { return false; }
        public void updateGiftsCollections(TL_stars.SavedStarGift gift, int collection_id, boolean included) {}
        public void updateGiftsUnsaved(TL_stars.SavedStarGift gift, boolean unsaved) {}
        public GiftsList getListByIndex(int index) { return null; }
        public GiftsList getListById(int collection_id) { return null; }
        public void load() {}
        public void invalidate(boolean load) {}
        public void createCollection(String title, Utilities.Callback<TL_stars.TL_starGiftCollection> created) {
            if (created != null) created.run(null);
        }
        public int indexOf(int id) { return -1; }
        public void removeCollection(int id) {}
        public void updateIcon(int id) {}
        public void rename(int id, String newName) {}
        public void addGift(int id, TL_stars.SavedStarGift gift, boolean insert) {}
        public void addGifts(int id, ArrayList<TL_stars.SavedStarGift> gifts, boolean insert) {}
        public void removeGift(int id, final TL_stars.SavedStarGift gift) {}
        public void removeGifts(int id, final ArrayList<TL_stars.SavedStarGift> gifts) {}
        public void reorder(ArrayList<Integer> collectionIds) {}
        public void sendOrder() {}
    }

    // IGiftsList interface
    public interface IGiftsList {
        void setCollectionId(int collection_id);
        void notifyUpdate();
        void updateGiftsCollections(TL_stars.SavedStarGift gift, int collection_id, boolean included);
        void updateGiftsUnsaved(TL_stars.SavedStarGift gift, boolean unsaved);
    }

    // GiftsList stub
    public static class GiftsList implements IGiftsList {
        public static final int INCLUDE_TYPE_UNIQUE_FLAG = 1;
        public final ArrayList<TL_stars.SavedStarGift> gifts = new ArrayList<>();
        public boolean loading = false;
        public boolean endReached = true;
        public boolean peer_color_available = false;

        public GiftsList() {}
        public GiftsList(int account, long dialogId, boolean flag) {}

        public void setCollectionId(int collection_id) {}
        public void notifyUpdate() {}
        public void updateGiftsCollections(TL_stars.SavedStarGift gift, int collection_id, boolean included) {}
        public void updateGiftsUnsaved(TL_stars.SavedStarGift gift, boolean unsaved) {}
        public int findGiftToUpgrade(int fromIndex) { return -1; }
        public void set(int index, Object obj) {}
        public void forceTypeIncludeFlag(int flag, boolean invalidate) {}
        public void toggleTypeIncludeFlag(int flag) {}
        public void resetFilters() {}
        public void setFilters(int flags) {}
        public boolean hasFilters() { return false; }
        public boolean isInclude_unlimited() { return false; }
        public boolean isInclude_limited() { return false; }
        public boolean isInclude_upgradable() { return false; }
        public boolean isInclude_unique() { return false; }
        public boolean isInclude_displayed() { return false; }
        public boolean isInclude_hidden() { return false; }
        public int getTotalCount() { return 0; }
        public int getLoadedCount() { return 0; }
        public Object get(int index) { return null; }
        public int indexOf(Object object) { return -1; }
        public void invalidate(boolean load) {}
        public void load() {}
        public void cancel() {}
        public int getCount() { return 0; }
        public boolean eq(ArrayList<TL_stars.SavedStarGift> a, ArrayList<TL_stars.SavedStarGift> b) { return false; }
        public void setPinned(ArrayList<TL_stars.SavedStarGift> newPinned) {}
        public boolean togglePinned(TL_stars.SavedStarGift gift, boolean pin, boolean fixLimit) { return false; }
        public void reorderPinned(int fromPosition, int toPosition) {}
        public void reorder(int fromPosition, int toPosition) {}
        public void reorderDone() {}
        public void sendPinnedOrder() {}
        public boolean contains(final TL_stars.SavedStarGift gift) { return false; }
    }

    public static boolean eq(final TL_stars.SavedStarGift a, final TL_stars.SavedStarGift b) { return false; }
    public void getStarGiftPreview(long gift_id, Utilities.Callback<TL_stars.starGiftUpgradePreview> got) {
        if (got != null) got.run(null);
    }
    public void getUserStarGift(TL_stars.InputSavedStarGift inputSavedStarGift, Utilities.Callback<TL_stars.SavedStarGift> got) {
        if (got != null) got.run(null);
    }
    public void getPaidRevenue(long user_id, long parent_id, Utilities.Callback<Long> got) {
        if (got != null) got.run(0L);
    }
    public void stopPaidMessages(long user_id, long parent_id, boolean refund, boolean stop) {}
    public void processUpdateMonoForumNoPaidException(long channelId, long userId, boolean nopaidMessagesException) {}

    // Paid message toast stub
    public class PaidMessageToast {
        public CharSequence getTitle() { return ""; }
        public CharSequence getSubtitle() { return ""; }
        public boolean isUndoRunning() { return false; }
        public boolean isVisible() { return false; }
        public boolean push(MessageObject messageObject, long payStars, Utilities.Callback<HashSet<MessageObject>> undo, Runnable send, boolean needsUndo) { return false; }
        public boolean pop(int messageId) { return false; }
        public void undo() {}
        public void send() {}
    }

    public void showPaidMessageToast(long dialogId, long payStars, Utilities.Callback<HashSet<MessageObject>> undo, Runnable send, boolean needsUndo, ArrayList<MessageObject> msgs) {}
    public void hidePaidMessageToast(MessageObject messageObject) {}
    public void beforeSendingMessage(MessageObject msg) {}
    public boolean beforeSendingFinalRequest(Object req, MessageObject msg, Runnable send) { return false; }
    public boolean beforeSendingFinalRequest(Object req, ArrayList<MessageObject> messages, Runnable send) { return false; }
    public static long getAllowedPaidStars(Object req) { return 0; }
    public static long getPeer(Object req) { return 0; }
    public void showPriceChangedToast(List<MessageObject> msgs) {}
    public static boolean isEnoughAmount(int currentAccount, AmountUtils.Amount amount) { return false; }

    // findAttribute utility method with generic support
    public static <T> T findAttribute(Object attributes, Class<T> clazz) {
        return null;
    }

    public static Object findAttribute(Object... args) {
        return null;
    }

    // Utility methods for formatting (moved from deleted StarsIntroActivity)
    public static String formatStarsAmount(long amount) {
        return String.valueOf(amount);
    }

    public static String formatStarsAmount(TL_stars.StarsAmount amount, float textSize) {
        return amount == null ? "0" : String.valueOf(amount.amount);
    }

    public static String formatStarsAmount(TL_stars.StarsAmount amount, float textSize, char separator) {
        return amount == null ? "0" : String.valueOf(amount.amount);
    }

    public static String formatStarsAmountShort(long amount) {
        return String.valueOf(amount);
    }

    public static String formatStarsAmountShort(TL_stars.StarsAmount amount) {
        return amount == null ? "0" : String.valueOf(amount.amount);
    }

    public static String formatStarsAmountShort(TL_stars.StarsAmount amount, float textSize, boolean flag) {
        return amount == null ? "0" : String.valueOf(amount.amount);
    }

    public static String formatStarsAmountShort(long amount, float textSize, char separator) {
        return String.valueOf(amount);
    }

    public static String formatStarsAmountShort(TL_stars.StarsAmount amount, float textSize, char separator) {
        return amount == null ? "0" : String.valueOf(amount.amount);
    }

    public TL_stars.SavedStarGift findUserStarGift(long id) {
        return null;
    }

    public static CharSequence replaceStarsWithPlain(CharSequence text) {
        return text;
    }

    // Utility method from deleted StarGiftSheet
    public static CharSequence replaceUnderstood(CharSequence text) {
        return text;
    }

    // Inner class for getPlatformDrawable (from deleted StarsTransactionView)
    public static class StarsTransactionView {
        public static android.graphics.drawable.Drawable getPlatformDrawable(Context context, String platform) {
            return null;
        }

        public static org.telegram.ui.Components.CombinedDrawable getPlatformDrawable(String platform) {
            return null;
        }
    }
}
