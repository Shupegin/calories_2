package cal.calor.caloriecounter.Service

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.yandex.mobile.ads.common.MobileAds

class RunningApp : Application() {
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this){}

        val channel = NotificationChannel(
            "running_channel", "Running Notificattion",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}