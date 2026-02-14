package tw.nekomimi.nekogram.config;

/**
 * ConfigItem stub - mimics original NekoX ConfigItem behavior
 * All features removed - returns safe defaults
 */
public class ConfigItem {
    // Config type constants
    public static final int configTypeBool = 1;
    public static final int configTypeInt = 2;
    public static final int configTypeLong = 3;
    public static final int configTypeFloat = 4;
    public static final int configTypeString = 5;
    public static final int configTypeSetInt = 6;
    public static final int configTypeMapIntInt = 7;
    public static final int configTypeBoolLinkInt = 8;

    private final Object defaultValue;

    public ConfigItem(String key, int type, Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean Bool() {
        return defaultValue instanceof Boolean ? (Boolean) defaultValue : false;
    }

    public String String() {
        return defaultValue instanceof String ? (String) defaultValue : "";
    }

    public int Int() {
        return defaultValue instanceof Integer ? (Integer) defaultValue : 0;
    }

    public long Long() {
        return defaultValue instanceof Long ? (Long) defaultValue : 0L;
    }

    public float Float() {
        return defaultValue instanceof Float ? (Float) defaultValue : 0f;
    }

    public void setConfigString(String value) {
        // No-op: config persistence removed
    }

    public void setConfigBool(boolean value) {
        // No-op: config persistence removed
    }

    public void setConfigInt(int value) {
        // No-op: config persistence removed
    }

    public void setConfigLong(long value) {
        // No-op: config persistence removed
    }

    public void setConfigFloat(float value) {
        // No-op: config persistence removed
    }

    public void toggleConfigBool() {
        // No-op: config persistence removed
    }

    // Inner field for compatibility with NaConfig
    public Object value = null;
    public String key = "";
    public int type = 0;
}
