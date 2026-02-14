package tw.nekomimi.nekogram.helpers.remote;
public class PagePreviewRulesHelper {
    private static final PagePreviewRulesHelper instance = new PagePreviewRulesHelper();
    public static PagePreviewRulesHelper getInstance() { return instance; }
    public void checkPagePreviewRules() {}
    public CharSequence doRegex(CharSequence text) { return text; }
}
