package xyz.nextalone.nagram.helper;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
public class SystemAiServiceHelper {
    public static final SystemAiServiceHelper INSTANCE = new SystemAiServiceHelper();
    public static boolean isSystemAiAvailable(Context c) { return false; }
    public boolean startSystemAiService(Context c, String text) { return false; }
    public boolean startSystemAiService(Context c, Uri uri) { return false; }
    public boolean startSystemAiService(View v, String text) { return false; }
    public boolean startSystemAiService(EditText v) { return false; }
    public boolean startSystemAiService(Object c, CharSequence text) { return false; }
}
