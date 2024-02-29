package cal.calor.caloriecounter.banner

import android.annotation.SuppressLint
import android.app.Application
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
    }
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this) { }
    }
}