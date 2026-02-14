package xyz.nextalone.nagram;
public enum TabStyle {
    DEFAULT(0), TEXT(1), ICON(2), MIX(3), PILLS(4), PURE(5);
    private final int value;
    TabStyle(int value) { this.value = value; }
    public int getValue() { return value; }
}
