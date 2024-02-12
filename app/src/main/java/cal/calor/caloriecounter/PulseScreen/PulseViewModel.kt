package cal.calor.caloriecounter.PulseScreen

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cal.calor.caloriecounter.database.PulseDataBase
import cal.calor.caloriecounter.pojo.pulse.PulsePojo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class PulseViewModel(application: Application,): AndroidViewModel(application){

    private val pulseDB= PulseDataBase.getInstance(application)
    val pulseListDAO = pulseDB.pulseInfoDao().getPulseList()
//
    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyy")
        return dateFormat.format(Date())
    }
//
    fun addPulseDataBase (pulsePojo: PulsePojo) {
        viewModelScope.launch(Dispatchers.IO) {
            pulseDB.pulseInfoDao().insertPulseList(pulsePojo)
        }
    }

    fun deletePulse(pulsePojo: PulsePojo){
        viewModelScope.launch(Dispatchers.IO) {
            pulsePojo.id?.let { pulseDB.pulseInfoDao().remove( id = it) }
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun currentTime(): String {
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("HH:mm") //or use getDateInstance()
        val formatedDate = formatter.format(date)

        return formatedDate


    }

}