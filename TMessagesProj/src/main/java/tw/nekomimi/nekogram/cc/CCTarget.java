package tw.nekomimi.nekogram.cc;

/**
 * Stub: Chinese conversion target
 */
public enum CCTarget {
    CN("Simplified Chinese"),
    TW("Traditional Chinese - Taiwan"),
    HK("Traditional Chinese - Hong Kong");

    private final String displayName;

    CCTarget(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
