package org.telegram.ui.Stories.recorder;

import org.telegram.tgnet.AbstractSerializedData;

/**
 * Weather stub - Stories removed in Tony Chat
 */
public class Weather {
    public static class State {
        public String emoji;
        public float temperature;

        // Tony Chat: WeatherView compatibility
        public String getEmoji() { return emoji != null ? emoji : ""; }
        public String getTemperature() { return String.valueOf((int) temperature) + "°"; }

        // Tony Chat: Stories removed - serialization stubs
        public static State TLdeserialize(AbstractSerializedData stream) {
            return new State();
        }

        public void serializeToStream(AbstractSerializedData stream) {
            // No-op
        }
    }
}
