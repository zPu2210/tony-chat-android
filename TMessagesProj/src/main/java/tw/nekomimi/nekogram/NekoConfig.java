package tw.nekomimi.nekogram;
import tw.nekomimi.nekogram.config.ConfigItem;
import java.util.ArrayList;
public class NekoConfig {
    public static final Object sync = new Object();
    public static final ConfigItem repeatConfirm = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem showRepeat = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem showBottomActionsWhenSelecting = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem showMessageDetails = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem showMessageHide = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem showShareMessages = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem showAddToSavedMessages = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem showReport = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem showAdminActions = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem showChangePermissions = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem showDeleteDownloadedFile = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem showViewHistory = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem showTranslate = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem sendCommentAfterForward = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem openPGPApp = new ConfigItem("", ConfigItem.configTypeString, "");
    public static final ConfigItem openPGPKeyId = new ConfigItem("", ConfigItem.configTypeLong, 0L);
    public static final ConfigItem useChatAttachMediaMenu = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem disableInstantCamera = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem disableVibration = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem skipOpenLinkConfirm = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem ignoreBlocked = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem hideKeyboardOnChatScroll = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem disablePhotoSideAction = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem hideTimeForSticker = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem showSpoilersDirectly = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem disableLinkPreviewByDefault = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem hideSendAsChannel = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem disableUndo = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem disableChatAction = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem disableChoosingSticker = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem enableStickerPin = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem hideGroupSticker = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem mediaPreview = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem stickerSize = new ConfigItem("", ConfigItem.configTypeFloat, 14.0f);
    public static final ConfigItem ignoreContentRestrictions = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem mapPreviewProvider = new ConfigItem("", ConfigItem.configTypeInt, 0);
    public static final ConfigItem tabsTitleType = new ConfigItem("", ConfigItem.configTypeInt, 0);
    public static final ConfigItem confirmAVMessage = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem rearVideoMessages = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem translateToLang = new ConfigItem("", ConfigItem.configTypeString, "");
    public static final ConfigItem translateInputLang = new ConfigItem("", ConfigItem.configTypeString, "en");
    public static final ConfigItem sortByUnread = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem sortByUnmuted = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem sortByUser = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem sortByContacts = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem cachePath = new ConfigItem("", ConfigItem.configTypeString, "");
    public static boolean sendReadMessagePackets = true;
    public static boolean sendOnlinePackets = true;
    public static boolean sendOfflineAfterOnline = false;
    public static boolean sendUploadProgress = true;
    public static boolean sendReadStoryPackets = true;
    public static boolean markReadAfterSend = true;
    public static boolean showGhostToggleInDrawer = false;
    public static boolean inappCamera = true;
    public static int translationProvider = 1;
    public static boolean isGhostModeActive() { return false; }
    public static void toggleGhostMode() {}
    public static void setGhostMode(boolean e) {}
    public static void putBoolean(String k, boolean v) {}
    public static void loadConfig(boolean f) {}
    public static void checkMigrate(boolean f) {}
}
