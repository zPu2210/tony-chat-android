package tw.nekomimi.nekogram.helpers.remote;

public class PagePreviewRulesHelper {
    public static PagePreviewRulesHelper getInstance() {
        return new PagePreviewRulesHelper();
    }

    public void loadPagePreviewRules() {
    }

    public void savePagePreviewRules() {
    }

    public boolean needUpdate() {
        return false;
    }

    public void checkPagePreviewRules() {
    }

    public String doRegex(CharSequence textToCheck) {
        return textToCheck != null ? textToCheck.toString() : "";
    }
}
