package tw.nekomimi.nekogram.config;

/**
 * ConfigItemKeyLinked stub - mimics original NekoX ConfigItemKeyLinked behavior
 * Feature removed - returns safe defaults
 */
public class ConfigItemKeyLinked extends ConfigItem {
    public ConfigItem keyLinked;
    private int bitValue;

    public ConfigItemKeyLinked(String key, ConfigItem keyLinked, int bitValue, Object defaultValue) {
        super(key, configTypeBoolLinkInt, defaultValue);
        this.keyLinked = keyLinked;
        this.bitValue = bitValue;
    }

    public void changedFromKeyLinked(int value) {
        // No-op: feature removed
    }
}
