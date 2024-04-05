package cal.calor.caloriecounter.banner

import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

import com.yandex.mobile.ads.banner.BannerAdEventListener
import com.yandex.mobile.ads.banner.BannerAdSize.a.stickySize
import com.yandex.mobile.ads.banner.BannerAdView
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import kotlin.math.roundToInt

@Composable
fun Banner(id : String){
    AndroidView(factory = { context->
        BannerAdView(context).apply {
            setAdUnitId(id)
                // Calculate the width of the ad, taking into account the padding in
                var adWidthPixels = width
                if (adWidthPixels == 0) {
                    // If the ad hasn't been laid out, default to the full screen wir
                    adWidthPixels = resources.displayMetrics.widthPixels
                }
                val adWidth = (adWidthPixels / resources.displayMetrics.density).roundToInt()

            setAdSize(stickySize(context,adWidth))
            val adRequest = AdRequest.Builder().build()
            setBannerAdEventListener(object : BannerAdEventListener{
                override fun onAdLoaded() {

                }

                override fun onAdFailedToLoad(p0: AdRequestError) {
                }

                override fun onAdClicked() {
                }

                override fun onLeftApplication() {
                }

                override fun onReturnedToApplication() {
                }

                override fun onImpression(p0: ImpressionData?) {
                }

            })
            loadAd(adRequest)
        }
    })
}

