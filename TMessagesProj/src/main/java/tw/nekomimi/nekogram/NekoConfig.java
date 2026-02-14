package tw.nekomimi.nekogram;

import android.content.Context;
import android.content.SharedPreferences;
import org.telegram.messenger.ApplicationLoader;
import tw.nekomimi.nekogram.config.ConfigItem;

import java.util.ArrayList;

/**
 * NekoX configuration stub
 * All features removed - returns safe defaults via ConfigItem wrappers
 */
public class NekoConfig {

    public static final SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("nkmrcfg", Context.MODE_PRIVATE);
    public static final Object sync = new Object();

    public static final String channelAliasPrefix = "channelAliasPrefix_";
    public static final ArrayList<DatacenterInfo> datacenterInfos = new ArrayList<>(5);

    // All ConfigItem wrapped fields (accessed via .Bool()/.Int()/.String() methods)
    public static final ConfigItem confirmAVMessage = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem askBeforeCall = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem disableFlagSecure = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem showSeconds = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem autoPauseVideo = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem avatarBackgroundBlur = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem avatarBackgroundDarken = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem cachePath = new ConfigItem("", ConfigItem.configTypeString, "");
    public static final ConfigItem ccInputLang = new ConfigItem("", ConfigItem.configTypeString, "");
    public static final ConfigItem channelAlias = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem chatBlueAlphaValue = new ConfigItem("", ConfigItem.configTypeInt, 127);
    public static final ConfigItem customAudioBitrate = new ConfigItem("", ConfigItem.configTypeInt, 32);
    public static final ConfigItem customSavePath = new ConfigItem("", ConfigItem.configTypeString, "Nagram");
    public static final ConfigItem disableAppBarShadow = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem disableAutoDownloadingArchive = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem disableAutoDownloadingWin32Executable = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem disableChatAction = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem disableChoosingSticker = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem disableInstantCamera = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem disableLinkPreviewByDefault = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem disableNotificationBubbles = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem disableNumberRounding = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem disablePhotoSideAction = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem disablePremiumStickerAnimation = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem disableProximityEvents = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem disableReactionsWhenSelecting = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem disableRemoteEmojiInteractions = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem disableSwipeToNext = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem disableSystemAccount = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem disableUndo = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem disableVibration = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem dontSendGreetingSticker = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem enableStickerPin = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem enhancedFileLoader = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem forceBlurInChat = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem hideAllTab = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem hideGroupSticker = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem hideKeyboardOnChatScroll = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem hidePhone = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem hideProxyByDefault = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem hideProxySponsorChannel = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem hideSendAsChannel = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem hideSponsoredMessage = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem hideTimeForSticker = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem ignoreBlocked = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem ignoreContentRestrictions = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem ignoreMutedCount = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem labelChannelUser = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem largeAvatarInDrawer = new ConfigItem("", ConfigItem.configTypeInt, 0);
    public static final ConfigItem localeToDBC = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem localPremium = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem mapPreviewProvider = new ConfigItem("", ConfigItem.configTypeInt, 0);
    public static final ConfigItem maxRecentStickerCount = new ConfigItem("", ConfigItem.configTypeInt, 20);
    public static final ConfigItem mediaPreview = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem nameOrder = new ConfigItem("", ConfigItem.configTypeInt, 1);
    public static final ConfigItem newYear = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem openArchiveOnPull = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem openPGPApp = new ConfigItem("", ConfigItem.configTypeString, "");
    public static final ConfigItem openPGPKeyId = new ConfigItem("", ConfigItem.configTypeLong, 0L);
    public static final ConfigItem rearVideoMessages = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem rememberAllBackMessages = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem repeatConfirm = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem sendCommentAfterForward = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem showAddToSavedMessages = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem showAdminActions = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem showBottomActionsWhenSelecting = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem showChangePermissions = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem showDeleteDownloadedFile = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem showIdAndDc = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem showMessageDetails = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem showMessageHide = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem showRepeat = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem showReport = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem showShareMessages = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem showSpoilersDirectly = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem showTranslate = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem showViewHistory = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem skipOpenLinkConfirm = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem sortByContacts = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem sortByUnmuted = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem sortByUnread = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem sortByUser = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem stickerSize = new ConfigItem("", ConfigItem.configTypeFloat, 14.0f);
    public static final ConfigItem tabletMode = new ConfigItem("", ConfigItem.configTypeInt, 0);
    public static final ConfigItem tabsTitleType = new ConfigItem("", ConfigItem.configTypeInt, 0);
    public static final ConfigItem takeGIFasVideo = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem translateInputLang = new ConfigItem("", ConfigItem.configTypeString, "en");
    public static final ConfigItem translateToLang = new ConfigItem("", ConfigItem.configTypeString, "");
    public static final ConfigItem typeface = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem unlimitedFavedStickers = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem unlimitedPinnedDialogs = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem unreadBadgeOnBackButton = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem useChatAttachMediaMenu = new ConfigItem("", ConfigItem.configTypeBool, true);
    public static final ConfigItem useIPv6 = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem useMediaStreamInVoip = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem useOSMDroidMap = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem usePersianCalendar = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem useSystemEmoji = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem useTelegramTranslateInChat = new ConfigItem("", ConfigItem.configTypeBool, false);
    public static final ConfigItem actionBarDecoration = new ConfigItem("", ConfigItem.configTypeInt, 0);
    public static final ConfigItem autoUpdateSubInfo = new ConfigItem("", ConfigItem.configTypeBool, true);

    // Ghost mode fields (direct primitive access)
    public static boolean sendReadMessagePackets = true;
    public static boolean sendOnlinePackets = true;
    public static boolean sendOfflineAfterOnline = false;
    public static boolean sendUploadProgress = true;
    public static boolean sendReadStoryPackets = true;
    public static boolean markReadAfterSend = true;
    public static boolean showGhostToggleInDrawer = false;

    // Other primitive fields (not accessed via ConfigItem methods)
    public static boolean migrate = false;
    public static int update_download_soucre = 0;
    public static boolean useCustomEmoji = false;
    public static boolean enablePublicProxy = true;
    public static long lastUpdateCheckTime = 0L;
    public static boolean inappCamera = true;
    public static boolean smoothKeyboard = false;
    public static boolean transparentStatusBar = true;
    public static int eventType = 0;
    public static int translationProvider = 1;
    public static String googleCloudTranslateKey = "";
    public static String ccToLang = "";
    public static boolean useSystemDNS = false;
    public static String customDoH = "";
    public static ConfigItem useProxyItem = new ConfigItem("", ConfigItem.configTypeBool, true);
    // hideProxyByDefault already defined at line 62
    public static boolean proxyAutoSwitch = false;
    public static boolean displayPersianCalendarByLatin = false;
    public static int reactions = 0;
    public static boolean disableGroupVoipAudioProcessing = false;
    public static boolean mapDriftingFixForGoogleMaps = true;

    // Other filter fields (direct boolean access)
    public static boolean filterUsers = true;
    public static boolean filterContacts = true;
    public static boolean filterGroups = true;
    public static boolean filterChannels = true;
    public static boolean filterBots = true;
    public static boolean filterAdmins = true;
    public static boolean filterUnmuted = true;
    public static boolean filterUnread = true;
    public static boolean filterUnmutedAndUnread = true;

    static {
        loadConfig(false);
        checkMigrate(false);
    }

    public static void loadConfig(boolean force) {}

    public static void setGhostMode(boolean enabled) {
        sendReadMessagePackets = !enabled;
        sendOnlinePackets = !enabled;
        sendUploadProgress = !enabled;
        sendReadStoryPackets = !enabled;
        sendOfflineAfterOnline = enabled;
    }

    public static void putBoolean(String key, boolean value) {}

    public static void toggleGhostMode() {
        setGhostMode(!isGhostModeActive());
    }

    public static boolean isGhostModeActive() {
        return !sendReadMessagePackets && !sendOnlinePackets && !sendUploadProgress
                && !sendReadStoryPackets && sendOfflineAfterOnline;
    }

    public static void checkMigrate(boolean force) {}

    public static boolean fixDriftingForGoogleMaps() {
        return !useOSMDroidMap.Bool() && mapDriftingFixForGoogleMaps;
    }

    public static class DatacenterInfo {
        public int id;
        public long pingId;
        public long ping;
        public boolean checking;
        public boolean available;
        public long availableCheckTime;

        public DatacenterInfo(int i) {
            id = i;
        }
    }
}
