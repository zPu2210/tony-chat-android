package org.telegram.messenger;

import com.tonychat.core.TonyColors;
import com.tonychat.core.TonyConfig;
import org.telegram.ui.ActionBar.Theme;

/**
 * Applies Tony Chat brand colors onto Telegram's theme key system.
 * Called after Theme.refreshThemeColors() to overlay brand identity.
 */
public class TonyChatThemeOverlay {

    public static void apply() {
        if (!TonyConfig.INSTANCE.getUseTonyTheme()) {
            return;
        }
        boolean dark = TonyConfig.INSTANCE.getDarkMode();

        // Action bar
        Theme.setColor(Theme.key_actionBarDefault, TonyColors.PRIMARY, false);
        Theme.setColor(Theme.key_actionBarDefaultIcon, 0xFFFFFFFF, false);
        Theme.setColor(Theme.key_actionBarDefaultTitle, 0xFFFFFFFF, false);
        Theme.setColor(Theme.key_actionBarDefaultSubtitle, 0xCCFFFFFF, false);
        Theme.setColor(Theme.key_actionBarDefaultSearch, 0xFFFFFFFF, false);
        Theme.setColor(Theme.key_actionBarDefaultSearchPlaceholder, 0x88FFFFFF, false);
        Theme.setColor(Theme.key_actionBarTabActiveText, 0xFFFFFFFF, false);
        Theme.setColor(Theme.key_actionBarTabUnactiveText, 0xB2FFFFFF, false);
        Theme.setColor(Theme.key_actionBarTabLine, 0xFFFFFFFF, false);

        // Window backgrounds
        int bg = dark ? TonyColors.DARK_BACKGROUND : TonyColors.LIGHT_BACKGROUND;
        int surface = dark ? TonyColors.DARK_SURFACE : TonyColors.LIGHT_SURFACE;
        Theme.setColor(Theme.key_windowBackgroundWhite, bg, false);
        Theme.setColor(Theme.key_windowBackgroundGray, surface, false);

        // Text colors
        int textPrimary = dark ? TonyColors.DARK_TEXT_PRIMARY : TonyColors.LIGHT_TEXT_PRIMARY;
        int textSecondary = dark ? TonyColors.DARK_TEXT_SECONDARY : TonyColors.LIGHT_TEXT_SECONDARY;
        Theme.setColor(Theme.key_windowBackgroundWhiteBlackText, textPrimary, false);
        Theme.setColor(Theme.key_windowBackgroundWhiteGrayText, textSecondary, false);
        Theme.setColor(Theme.key_windowBackgroundWhiteGrayText2, textSecondary, false);

        // Chat list (dialogs)
        Theme.setColor(Theme.key_chats_name, textPrimary, false);
        Theme.setColor(Theme.key_chats_message, textSecondary, false);
        Theme.setColor(Theme.key_chats_date, textSecondary, false);
        Theme.setColor(Theme.key_chats_unreadCounter, TonyColors.PRIMARY, false);
        Theme.setColor(Theme.key_chats_unreadCounterText, 0xFFFFFFFF, false);

        // Chat bubbles
        int inBubble = dark ? TonyColors.DARK_CHAT_IN_BUBBLE : TonyColors.LIGHT_CHAT_IN_BUBBLE;
        int outBubble = dark ? TonyColors.DARK_CHAT_OUT_BUBBLE : TonyColors.LIGHT_CHAT_OUT_BUBBLE;
        Theme.setColor(Theme.key_chat_inBubble, inBubble, false);
        Theme.setColor(Theme.key_chat_outBubble, outBubble, false);

        // Links and accent elements
        Theme.setColor(Theme.key_windowBackgroundWhiteBlueText, TonyColors.PRIMARY, false);
        Theme.setColor(Theme.key_windowBackgroundWhiteBlueText2, TonyColors.PRIMARY, false);
        Theme.setColor(Theme.key_windowBackgroundWhiteValueText, TonyColors.PRIMARY, false);
        Theme.setColor(Theme.key_chat_messageLinkIn, TonyColors.PRIMARY, false);
        Theme.setColor(Theme.key_chat_messageLinkOut, TonyColors.PRIMARY, false);

        // FAB / floating button
        Theme.setColor(Theme.key_chats_actionBackground, TonyColors.PRIMARY, false);
        Theme.setColor(Theme.key_chats_actionIcon, 0xFFFFFFFF, false);

        // Dividers
        int divider = dark ? TonyColors.DARK_DIVIDER : TonyColors.LIGHT_DIVIDER;
        Theme.setColor(Theme.key_divider, divider, false);
    }
}
