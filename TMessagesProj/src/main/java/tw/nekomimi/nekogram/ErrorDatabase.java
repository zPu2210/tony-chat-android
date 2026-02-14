package tw.nekomimi.nekogram;

import org.telegram.tgnet.TLObject;

/**
 * Stub: Error database for API error handling
 */
public class ErrorDatabase {
    public static void recordError(String method, int errorCode, String errorText) {}

    public static void showErrorToast(TLObject request, String errorText) {}

    public static boolean isUserOnlyMethod(String method) {
        return false;
    }

    public static void handleError(TLObject request, int errorCode, String errorText) {}
}
