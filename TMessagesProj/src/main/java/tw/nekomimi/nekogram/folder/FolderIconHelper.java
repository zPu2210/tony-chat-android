package tw.nekomimi.nekogram.folder;

import androidx.core.util.Pair;

import org.telegram.messenger.R;

import java.util.LinkedHashMap;

public class FolderIconHelper {
    public static LinkedHashMap<String, Integer> folderIcons = new LinkedHashMap<>();

    public static Pair<String, String> getEmoticonFromFlags(int newFilterFlags) {
        return Pair.create("", "");
    }

    public static int getIconWidth() {
        return 0;
    }

    public static int getPadding() {
        return 0;
    }

    public static int getTotalIconWidth() {
        return 0;
    }

    public static int getPaddingTab() {
        return 0;
    }

    public static int getTabIcon(String emoji) {
        return R.drawable.filter_custom;
    }
}
