package tw.nekomimi.nekogram.utils;

import org.telegram.tgnet.TLRPC;

public class AyuGhostUtils {

    public static void setAllowReadPacket(boolean val, int resetAfter) {}

    public static boolean getAllowReadPacket() {
        return false;
    }

    public static Long getDialogId(TLRPC.InputPeer peer) {
        return 0L;
    }

    public static Long getDialogId(TLRPC.InputChannel peer) {
        return 0L;
    }
}
