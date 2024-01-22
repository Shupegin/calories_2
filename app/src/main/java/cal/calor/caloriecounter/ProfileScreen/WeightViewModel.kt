package cal.calor.caloriecounter.ProfileScreen

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cal.calor.caloriecounter.database.WeightDataBase
import cal.calor.caloriecounter.pojo.weight.WeightPogo
//import com.google.firebase.auth.FirebaseAuth
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class WeightViewModel (application: Application): AndroidViewModel(application) {

    private val db = WeightDataBase.getInstance(application)
    val wieghtListDAO = db.weightInfoDao().getUserIdList()


//    private var auth:  FirebaseAuth? = null
    private val _clientID : MutableLiveData<String> = MutableLiveData()
    val client : MutableLiveData<String> =  _clientID

    private val _imageQR : MutableLiveData<Bitmap> = MutableLiveData()
    val imageQR : MutableLiveData<Bitmap> = _imageQR

//    init {
//        auth = FirebaseAuth.getInstance()
//        auth?.addAuthStateListener{
//            if(it.currentUser != null){
//                _clientID.value = it.uid
//            }
//
//        }
//
//    }


    fun generateQR(ui : String){
        try {
            val barcodeEncode = BarcodeEncoder()
            val bitmap : Bitmap = barcodeEncode.encodeBitmap(ui, BarcodeFormat.QR_CODE, 750, 750)
            _imageQR.value = bitmap
        } catch (e: WriterException){}

    }

    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyy")
        return dateFormat.format(Date())
    }

    fun addDao(weightPogo: WeightPogo){
        viewModelScope.launch {
            db.weightInfoDao().insertUserIDList(weightPogo)

        }
    }

    fun deleteWeight(weightPogo: WeightPogo){
        viewModelScope.launch(Dispatchers.IO) {

            weightPogo.id?.let { db.weightInfoDao().remove(it)}
        }
    }
}
