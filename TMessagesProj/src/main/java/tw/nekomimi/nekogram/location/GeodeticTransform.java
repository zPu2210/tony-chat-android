package tw.nekomimi.nekogram.location;

import android.util.Pair;

/**
 * Stub: Geodetic coordinate transformation
 */
public class GeodeticTransform {
    public static Pair<Double, Double> transform(double latitude, double longitude) {
        // No-op: Return coordinates unchanged (no transformation)
        return new Pair<>(latitude, longitude);
    }
}
