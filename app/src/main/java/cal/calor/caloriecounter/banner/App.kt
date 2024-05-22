package cal.calor.caloriecounter.banner

import android.annotation.SuppressLint
import android.app.Application
import com.my.tracker.MyTracker
import com.my.tracker.MyTrackerParams
import com.yandex.mobile.ads.common.MobileAds
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

class App : Application() {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun reverseStringDate(dateStr: String?): String {
            if (dateStr == null)
                return ""

            return try {
                val date = SimpleDateFormat("dd.MM.yyyy").parse(dateStr)
                SimpleDateFormat("yyyy.MM.dd").format(date)
            } catch (_: ParseException) {
                dateStr
            }
        }
        private const val APP_ID = "53458234282013015462"

    }
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this) { }



        MyTracker.setDebugMode(true) // for debugging, turn off in production
        MyTracker.getTrackerConfig().setBufferingPeriod(60)
//        MyTracker.getTrackerParams()
//            .setAge(22)
//            .setGender(MyTrackerParams.Gender.FEMALE)

        MyTracker.initTracker(APP_ID, this)
    }
}