package org.telegram.messenger;

import com.tonychat.core.TonyColors;
import com.tonychat.core.TonyConfig;
import org.telegram.ui.ActionBar.Theme;

/**
 * Applies Tony Chat v2.0 Warm Indigo brand colors onto Telegram's theme key system.
 * Called after Theme.refreshThemeColors() to overlay brand identity.
 */
public class TonyChatThemeOverlay {

    public static void apply() {
        if (!TonyConfig.INSTANCE.getUseTonyTheme()) {
            return;
        }
        boolean dark = TonyConfig.INSTANCE.getDarkMode();
        int primary = dark ? TonyColors.DARK_PRIMARY : TonyColors.PRIMARY;

        // Action bar
        Theme.setColor(Theme.key_actionBarDefault, primary, false);
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
        Theme.setColor(Theme.key_chats_unreadCounter, primary, false);
        Theme.setColor(Theme.key_chats_unreadCounterText, 0xFFFFFFFF, false);

        // Links and accent elements
        Theme.setColor(Theme.key_windowBackgroundWhiteBlueText, primary, false);
        Theme.setColor(Theme.key_windowBackgroundWhiteBlueText2, primary, false);
        Theme.setColor(Theme.key_windowBackgroundWhiteValueText, primary, false);
        Theme.setColor(Theme.key_chat_messageLinkIn, primary, false);
        Theme.setColor(Theme.key_chat_messageLinkOut, primary, false);

        // FAB / floating button
        Theme.setColor(Theme.key_chats_actionBackground, primary, false);
        Theme.setColor(Theme.key_chats_actionIcon, 0xFFFFFFFF, false);

        // Dividers
        int border = dark ? TonyColors.DARK_BORDER : TonyColors.LIGHT_BORDER;
        Theme.setColor(Theme.key_divider, border, false);

        // Tony Chat design tokens (for custom screens)
        Theme.setColor(Theme.key_tony_primary, primary, false);
        Theme.setColor(Theme.key_tony_primaryDark, TonyColors.PRIMARY_DARK, false);
        Theme.setColor(Theme.key_tony_primaryLight, TonyColors.PRIMARY_LIGHT, false);
        Theme.setColor(Theme.key_tony_aiAccent, dark ? TonyColors.DARK_AI_ACCENT : TonyColors.AI_ACCENT, false);
        Theme.setColor(Theme.key_tony_aiAccentText, dark ? TonyColors.DARK_AI_ACCENT_TEXT : TonyColors.AI_ACCENT_TEXT, false);
        Theme.setColor(Theme.key_tony_aiLight, TonyColors.AI_LIGHT, false);
        Theme.setColor(Theme.key_tony_success, dark ? TonyColors.DARK_SUCCESS : TonyColors.SUCCESS, false);
        Theme.setColor(Theme.key_tony_error, dark ? TonyColors.DARK_ERROR : TonyColors.ERROR, false);
        Theme.setColor(Theme.key_tony_warning, TonyColors.WARNING, false);
        Theme.setColor(Theme.key_tony_background, bg, false);
        Theme.setColor(Theme.key_tony_surface, surface, false);
        Theme.setColor(Theme.key_tony_surface2, dark ? TonyColors.DARK_SURFACE2 : TonyColors.LIGHT_SURFACE2, false);
        Theme.setColor(Theme.key_tony_border, border, false);
        Theme.setColor(Theme.key_tony_textPrimary, textPrimary, false);
        Theme.setColor(Theme.key_tony_textSecondary, textSecondary, false);
        Theme.setColor(Theme.key_tony_textTertiary, dark ? TonyColors.DARK_TEXT_TERTIARY : TonyColors.LIGHT_TEXT_TERTIARY, false);
        Theme.setColor(Theme.key_tony_navGlass, dark ? TonyColors.DARK_NAV_GLASS : TonyColors.LIGHT_NAV_GLASS, false);
    }
}
