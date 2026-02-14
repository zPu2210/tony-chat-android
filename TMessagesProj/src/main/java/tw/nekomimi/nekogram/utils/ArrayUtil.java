package tw.nekomimi.nekogram.utils;

public class ArrayUtil {
    public static boolean contains(long[] array, long value) {
        if (array == null) return false;
        for (long item : array) {
            if (item == value) return true;
        }
        return false;
    }
}
