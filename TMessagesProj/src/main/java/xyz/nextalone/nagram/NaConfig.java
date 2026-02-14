package xyz.nextalone.nagram;
import tw.nekomimi.nekogram.config.ConfigItem;
public class NaConfig {
    public static final NaConfig INSTANCE = new NaConfig();
    private static final ConfigItem TRUE_ITEM = new ConfigItem("", 0, true);
    private static final ConfigItem FALSE_ITEM = new ConfigItem("", 0, false);
    private static final ConfigItem ZERO_ITEM = new ConfigItem("", 1, 0);
    // Text formatting - all true
    public ConfigItem getShowTextBold() { return TRUE_ITEM; }
    public ConfigItem getShowTextItalic() { return TRUE_ITEM; }
    public ConfigItem getShowTextMono() { return TRUE_ITEM; }
    public ConfigItem getShowTextStrikethrough() { return TRUE_ITEM; }
    public ConfigItem getShowTextUnderline() { return TRUE_ITEM; }
    public ConfigItem getShowTextQuote() { return TRUE_ITEM; }
    public ConfigItem getShowTextSpoiler() { return TRUE_ITEM; }
    public ConfigItem getShowTextCreateLink() { return TRUE_ITEM; }
    public ConfigItem getShowTextCreateMention() { return TRUE_ITEM; }
    public ConfigItem getShowTextRegular() { return TRUE_ITEM; }
    public ConfigItem getShowReactions() { return TRUE_ITEM; }
    public ConfigItem getAlwaysSaveChatOffset() { return TRUE_ITEM; }
    public ConfigItem getAutoReplaceRepeat() { return TRUE_ITEM; }
    public ConfigItem getShowRepeatAsCopy() { return TRUE_ITEM; }
    public ConfigItem getShowCopyPhoto() { return TRUE_ITEM; }
    public ConfigItem getShowInvertReply() { return TRUE_ITEM; }
    public ConfigItem getShowNoQuoteForward() { return TRUE_ITEM; }
    public ConfigItem getShowServicesTime() { return TRUE_ITEM; }
    public ConfigItem getCodeSyntaxHighlight() { return TRUE_ITEM; }
    public ConfigItem getShowPremiumStarInChat() { return TRUE_ITEM; }
    public ConfigItem getShowPremiumAvatarAnimation() { return TRUE_ITEM; }
    public ConfigItem getAutoInsertGIFCaption() { return TRUE_ITEM; }
    public ConfigItem getUseSystemAiService() { return TRUE_ITEM; }
    public ConfigItem getNavigationAnimationSpring() { return TRUE_ITEM; }
    public ConfigItem getFixUrlPagePreview() { return TRUE_ITEM; }
    public ConfigItem getFixUrlAutoInlineBot() { return TRUE_ITEM; }
    public ConfigItem getShowTimeHint() { return TRUE_ITEM; }
    public ConfigItem getUseSystemUnlock() { return TRUE_ITEM; }
    public ConfigItem getDisableSecondAddress() { return TRUE_ITEM; }
    public ConfigItem getPushServiceTypeInAppDialog() { return TRUE_ITEM; }
    public ConfigItem getSentryAnalytics() { return TRUE_ITEM; }
    public ConfigItem getRegexFiltersEnableInChats() { return TRUE_ITEM; }
    public ConfigItem getShowRecentChatsInSidebar() { return TRUE_ITEM; }
    public ConfigItem getRemoveFavouriteStickersInRecentStickers() { return TRUE_ITEM; }
    // False defaults
    public ConfigItem getDisableActionBarButtonCopy() { return FALSE_ITEM; }
    public ConfigItem getDisableActionBarButtonEdit() { return FALSE_ITEM; }
    public ConfigItem getDisableActionBarButtonForward() { return FALSE_ITEM; }
    public ConfigItem getDisableActionBarButtonReply() { return FALSE_ITEM; }
    public ConfigItem getDisableActionBarButtonSelectBetween() { return FALSE_ITEM; }
    public ConfigItem getDisableChannelMuteButton() { return FALSE_ITEM; }
    public ConfigItem getDisableFavoriteSearchEmojiTags() { return FALSE_ITEM; }
    public ConfigItem getDisableNonPremiumChannelChatShow() { return FALSE_ITEM; }
    public ConfigItem getDisablePhoneSharePrompt() { return FALSE_ITEM; }
    public ConfigItem getDisablePremiumFavoriteEmojiTags() { return FALSE_ITEM; }
    public ConfigItem getDisablePremiumSendTodo() { return FALSE_ITEM; }
    public ConfigItem getDisablePreviewVideoSoundShortcut() { return FALSE_ITEM; }
    public ConfigItem getEnhancedVideoBitrate() { return FALSE_ITEM; }
    public ConfigItem getRealHideTimeForSticker() { return FALSE_ITEM; }
    public ConfigItem getChatActivityNavbarTransparent() { return FALSE_ITEM; }
    public ConfigItem getDoNotUnarchiveBySwipe() { return FALSE_ITEM; }
    public ConfigItem getHideMessageSeenTooltip() { return FALSE_ITEM; }
    public ConfigItem getShowQuickReplyInBotCommands() { return FALSE_ITEM; }
    public ConfigItem getDoubleTapAction() { return ZERO_ITEM; }
    public ConfigItem getNotificationIcon() { return new ConfigItem("", 1, 1); }
    public ConfigItem getPushServiceType() { return new ConfigItem("", 1, 1); }
    public ConfigItem getEmojiPack() { return new ConfigItem("", 4, ""); }
    // Direct fields
    public boolean rememberAllBackMessages = false;
    public boolean autoPauseVideo = false;
    public boolean takeGIFasVideo = false;
    public boolean disableSwipeToNext = false;
    public boolean disableReactionsWhenSelecting = false;
    public void addToRecentDialogs(int account, long dialogId) {}
}
