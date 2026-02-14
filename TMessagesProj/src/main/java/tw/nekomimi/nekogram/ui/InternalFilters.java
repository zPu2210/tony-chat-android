package tw.nekomimi.nekogram.ui;

import org.telegram.tgnet.TLRPC;
import java.util.LinkedList;

/**
 * Stub: Internal chat filters
 */
public class InternalFilters {
    public static LinkedList<TLRPC.TL_dialogFilterSuggested> internalFilters = new LinkedList<>();

    public static final TLRPC.DialogFilter usersFilter = null;
    public static final TLRPC.DialogFilter contactsFilter = null;
    public static final TLRPC.DialogFilter groupsFilter = null;
    public static final TLRPC.DialogFilter channelsFilter = null;
    public static final TLRPC.DialogFilter botsFilter = null;
    public static final TLRPC.DialogFilter unmutedFilter = null;
    public static final TLRPC.DialogFilter unreadFilter = null;
    public static final TLRPC.DialogFilter unmutedAndUnreadFilter = null;
}
