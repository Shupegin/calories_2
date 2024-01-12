package cal.calor.caloriecounter.WaterScreeen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cal.calor.caloriecounter.database.WaterDataBase_2
import cal.calor.caloriecounter.pojo.WaterModel_2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class WaterViewModel (application: Application) : AndroidViewModel(application)  {

    private val db = WaterDataBase_2.getInstance(application)
    val waterListDAO = db.waterInfoDao_2().getWaterList()


    fun addWaterDataBase (waterModel_2: WaterModel_2) {
        viewModelScope.launch(Dispatchers.IO) {
            db.waterInfoDao_2().insertWaterList(waterModel_2)
        }
    }

    fun deleteWater(waterModel: WaterModel_2){
        viewModelScope.launch(Dispatchers.IO) {
            db.waterInfoDao_2().remove( id = waterModel.waterId)
        }
    }

    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyy")
        return dateFormat.format(Date())
    }

    fun getWater(listWater : List<WaterModel_2>) : Int{
        return listWater.sumOf { it.water_is_drunk ?: 0 }
    }

    fun getDrainedWater(listWater : List<WaterModel_2>) : Int{
        return listWater.sumOf { it.drained_of_water ?: 0 }
    }
}