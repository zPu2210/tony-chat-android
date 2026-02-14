package tw.nekomimi.nekogram.helpers;

import org.telegram.messenger.MessageObject;

import java.util.ArrayList;

public class AyuFilter {
    public static ArrayList<FilterModel> getRegexFilters() {
        return new ArrayList<>();
    }

    public static void addFilter(String text, boolean caseInsensitive) {
    }

    public static void editFilter(int filterIdx, String text, boolean caseInsensitive) {
    }

    public static void saveFilter(ArrayList<FilterModel> filterModels1) {
    }

    public static void removeFilter(int filterIdx) {
    }

    public static CharSequence getMessageText(MessageObject selectedObject, MessageObject.GroupedMessages selectedObjectGroup) {
        return null;
    }

    public static void rebuildCache() {
    }

    public static boolean isFiltered(MessageObject msg, MessageObject.GroupedMessages group) {
        return false;
    }

    public static class FilterModel {
        public String regex;
        public boolean caseInsensitive;
        public ArrayList<Long> enabledGroups = new ArrayList<>();
        public ArrayList<Long> disabledGroups = new ArrayList<>();

        public void buildPattern() {
        }

        public boolean defaultStatus() {
            return false;
        }

        public boolean isEnabled(Long id) {
            return false;
        }

        public void setEnabled(boolean enabled, Long id) {
        }
    }
}
