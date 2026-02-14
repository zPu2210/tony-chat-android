package tw.nekomimi.nekogram.utils;

import org.telegram.tgnet.TLRPC;

public class NeteaseEmbed {

    public static boolean isNeteaseWebPage(String url) {
        return false;
    }

    public static String getNeteaseAlbumId(String url) {
        return null;
    }

    public static String getNeteaseAlbumEmbed(String id) {
        return "";
    }

    public static String getNeteaseSongId(String url) {
        return null;
    }

    public static String getNeteaseSongEmbed(String id) {
        return "";
    }

    public static void fixWebPage(TLRPC.WebPage webpage) {}
}
