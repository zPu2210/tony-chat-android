package nekox.messenger;
import android.content.Context;
import com.google.android.gms.maps.LocationSource;
public class NekoLocationSource implements LocationSource {
    public NekoLocationSource(Context context) {}
    @Override public void activate(OnLocationChangedListener l) {}
    @Override public void deactivate() {}
}
