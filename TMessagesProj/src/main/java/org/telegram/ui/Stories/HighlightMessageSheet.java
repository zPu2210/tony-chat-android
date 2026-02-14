package org.telegram.ui.Stories;

import org.telegram.ui.ActionBar.BottomSheet;

/**
 * HighlightMessageSheet stub - Stories removed in Tony Chat
 */
public class HighlightMessageSheet extends BottomSheet {
    public static final int TIER_LENGTH = 0;

    public HighlightMessageSheet(android.content.Context context) {
        super(context, false);
    }

    public static String tiersToString(int[] tiers) {
        if (tiers == null || tiers.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tiers.length; i++) {
            if (i > 0) sb.append(",");
            sb.append(tiers[i]);
        }
        return sb.toString();
    }

    public static int[] parseTiers(Object jsonArray) {
        return new int[0];
    }

    public static int[] parseTiersString(String str) {
        return new int[0];
    }

    public static boolean tiersEqual(int[] tiers1, int[] tiers2) {
        if (tiers1 == null && tiers2 == null) return true;
        if (tiers1 == null || tiers2 == null) return false;
        if (tiers1.length != tiers2.length) return false;
        for (int i = 0; i < tiers1.length; i++) {
            if (tiers1[i] != tiers2[i]) return false;
        }
        return true;
    }

    public static int getMaxLength(int currentAccount) {
        return 1000; // Default max length
    }

    public static int getTierOption(int currentAccount, int price, int tierType) {
        return 1000; // Default tier length
    }
}
