package tw.nekomimi.nekogram

import android.annotation.SuppressLint
import android.os.Build
import android.service.notification.NotificationListenerService
import androidx.annotation.RequiresApi

/**
 * Stub: Push notification service
 */
@SuppressLint("OverrideAbstract")
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
class NekoXPushService : NotificationListenerService() {
    override fun onCreate() {
        super.onCreate()
    }
}
