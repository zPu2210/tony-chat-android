package tw.nekomimi.nekogram.utils;
import org.telegram.tgnet.TLRPC.InputPeer;
public class AyuGhostUtils {
    public static boolean isGhostRead() { return false; }
    public static boolean getAllowReadPacket() { return true; }
    public static void setAllowReadPacket(boolean allow, int unused) {}
    public static long getDialogId(InputPeer peer) { return 0L; }
}
