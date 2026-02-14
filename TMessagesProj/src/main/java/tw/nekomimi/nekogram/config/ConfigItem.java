package tw.nekomimi.nekogram.config;
public class ConfigItem {
    public static final int configTypeBool = 0;
    public static final int configTypeInt = 1;
    public static final int configTypeFloat = 2;
    public static final int configTypeLong = 3;
    public static final int configTypeString = 4;
    private Object value;
    public ConfigItem(String k, int t, Object d) { this.value = d; }
    public boolean Bool() { return value instanceof Boolean ? (Boolean) value : false; }
    public int Int() { return value instanceof Number ? ((Number) value).intValue() : 0; }
    public String String() { return value instanceof String ? (String) value : ""; }
    public float Float() { return value instanceof Number ? ((Number) value).floatValue() : 0f; }
    public long Long() { return value instanceof Number ? ((Number) value).longValue() : 0L; }
    public void setConfigBool(boolean v) { this.value = v; }
    public void setConfigInt(int v) { this.value = v; }
    public void setConfigString(String v) { this.value = v; }
    public void setConfigLong(long v) { this.value = v; }
    public void setConfigFloat(float v) { this.value = v; }
}
