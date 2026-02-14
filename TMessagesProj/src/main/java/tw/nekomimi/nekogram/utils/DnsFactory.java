package tw.nekomimi.nekogram.utils;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
public class DnsFactory {
    public static InetAddress[] lookup(String domain) throws Exception { return new InetAddress[0]; }
    public static List<String> getTxts(String domain) { return new ArrayList<>(); }
    public static boolean isIpv6Address(String addr) { return addr != null && addr.contains(":"); }
    public static boolean isVPNEnabled() { return false; }
}
