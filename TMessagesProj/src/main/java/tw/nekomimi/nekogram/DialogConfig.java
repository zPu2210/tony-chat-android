package tw.nekomimi.nekogram;

/**
 * Stub: Dialog configuration storage
 */
public class DialogConfig {
    public static String getAutoTranslateKey(long dialogId, long topicId) {
        return "autoTranslate_" + dialogId + (topicId != 0 ? "_" + topicId : "");
    }

    public static boolean isAutoTranslateEnable(long dialogId, long topicId) {
        return false;
    }

    public static boolean hasAutoTranslateConfig(long dialogId, long topicId) {
        return false;
    }

    public static void setAutoTranslateEnable(long dialogId, long topicId, boolean enable) {}

    public static void removeAutoTranslateConfig(long dialogId, long topicId) {}

    public static String getCustomForumTabsKey(long dialogId) {
        return "customForumTabs_" + dialogId;
    }

    public static boolean isCustomForumTabsEnable(long dialogId) {
        return false;
    }

    public static boolean hasCustomForumTabsConfig(long dialogId) {
        return false;
    }

    public static void setCustomForumTabsEnable(long dialogId, boolean enable) {}

    public static void removeCustomForumTabsConfig(long dialogId) {}

    public static void modifyShareTarget(java.util.ArrayList<org.telegram.tgnet.TLRPC.TL_topPeer> list) {
        // No-op stub
    }
}
