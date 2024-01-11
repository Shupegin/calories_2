package cal.calor.caloriecounter.WaterScreeen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import cal.calor.caloriecounter.database.WaterDataBase
import cal.calor.caloriecounter.pojo.FoodModel
import cal.calor.caloriecounter.pojo.WaterModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class WaterViewModel (application: Application) : AndroidViewModel(application)  {

    private val db = WaterDataBase.getInstance(application)
    val waterListDAO = db.waterInfoDao().getWaterList()


    fun addWaterDataBase (waterModel: WaterModel) {
        viewModelScope.launch(Dispatchers.IO) {
            db.waterInfoDao().insertWaterList(waterModel)
        }
    }

    fun deleteWater(waterModel: WaterModel){
        viewModelScope.launch(Dispatchers.IO) {
            db.waterInfoDao().remove( id = waterModel.waterId)
        }
    }

    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyy")
        return dateFormat.format(Date())
    }

    fun getWater(listWater : List<WaterModel>) : Int{
        return listWater.sumOf { it.water_is_drunk?.toInt() ?: 0 }
    }
}