package tw.nekomimi.nekogram.shamsicalendar;

import java.util.Date;

/**
 * Stub: Persian calendar - returns defaults
 */
public class PersianDate {
    public static final int FARVARDIN = 1;
    public static final int ORDIBEHEST = 2;
    public static final int KHORDAD = 3;
    public static final int TIR = 4;
    public static final int MORDAD = 5;
    public static final int SHAHRIVAR = 6;
    public static final int MEHR = 7;
    public static final int ABAN = 8;
    public static final int AZAR = 9;
    public static final int DAY = 10;
    public static final int BAHMAN = 11;
    public static final int ESFAND = 12;

    public PersianDate() {}

    public PersianDate(long timestamp) {}

    public PersianDate(Date date) {}

    public int getShYear() {
        return 1400;
    }

    public int getShMonth() {
        return 1;
    }

    public int getShDay() {
        return 1;
    }

    public String format(String pattern) {
        return "";
    }

    public long getTime() {
        return System.currentTimeMillis();
    }

    public String getPersianMonthDay() {
        return "";
    }

    public String getPersianNormalDate() {
        return "";
    }
}
