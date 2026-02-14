package tw.nekomimi.nekogram.utils;
import android.content.Intent;
import java.io.InputStream;
import java.io.OutputStream;
public class PGPUtil {
    public static PGPApi api = new PGPApi();
    public static void post(Runnable r) { if (r != null) r.run(); }
    public static class PGPApi {
        public void executeApiAsync(Intent intent, InputStream input, OutputStream output, Object callback) {}
    }
}
