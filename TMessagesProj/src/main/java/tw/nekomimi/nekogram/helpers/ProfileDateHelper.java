package tw.nekomimi.nekogram.helpers;

public class ProfileDateHelper {
    public static String getUserTime(String prefix, long date) {
        return "";
    }

    public static String getUserTime(Long userId) {
        return "";
    }

    public static class ProfileDateData {
        private final long id;
        private final long date;

        public ProfileDateData(long id, long date) {
            this.id = id;
            this.date = date;
        }

        public long getId() {
            return id;
        }

        public long getDate() {
            return date;
        }
    }
}
