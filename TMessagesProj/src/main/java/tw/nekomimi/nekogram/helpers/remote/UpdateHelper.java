package tw.nekomimi.nekogram.helpers.remote;

import java.util.Map;

public class UpdateHelper {
    public static UpdateHelper getInstance() {
        return new UpdateHelper();
    }

    public void checkNewVersionAvailable(Delegate delegate) {
    }

    public void checkNewVersionAvailable(Delegate delegate, boolean updateAlways) {
    }

    public interface Delegate {
        void onTLResponse(org.telegram.tgnet.TLRPC.TL_help_appUpdate response, String error);
    }

    public static class Update {
        public boolean can_not_skip;
        public Boolean canNotSkip;  // alias
        public String version;
        public Integer versionCode;
        public Long timeStamp;
        public Integer sticker;
        public Integer message;
        public Map<String, Integer> gcm;
        public String url;

        public Update(Boolean canNotSkip, String version, int versionCode, long timeStamp, int sticker, int message, Map<String, Integer> gcm, String url) {
            this.can_not_skip = canNotSkip != null && canNotSkip;
            this.canNotSkip = canNotSkip;
            this.version = version;
            this.versionCode = versionCode;
            this.timeStamp = timeStamp;
            this.sticker = sticker;
            this.message = message;
            this.gcm = gcm;
            this.url = url;
        }
    }
}
