package cal.calor.caloriecounter.ProfileScreen

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder

class ProfileViewModel : ViewModel() {
    private var auth:  FirebaseAuth? = null
    private val _clientID : MutableLiveData<String> = MutableLiveData()
    val client : MutableLiveData<String> =  _clientID

    private val _imageQR : MutableLiveData<Bitmap> = MutableLiveData()
    val imageQR : MutableLiveData<Bitmap> = _imageQR

    init {
        auth = FirebaseAuth.getInstance()
        auth?.addAuthStateListener{
            if(it.currentUser != null){
                _clientID.value = it.uid
            }

        }

    }


    fun generateQR(ui : String){
        try {
            val barcodeEncode = BarcodeEncoder()
            val bitmap : Bitmap = barcodeEncode.encodeBitmap(ui, BarcodeFormat.QR_CODE, 750, 750)
            _imageQR.value = bitmap
        } catch (e: WriterException){}

    }
}
