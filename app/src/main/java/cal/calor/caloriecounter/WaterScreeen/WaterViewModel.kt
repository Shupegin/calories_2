package cal.calor.caloriecounter.WaterScreeen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cal.calor.caloriecounter.database.WaterDataBase_2
import cal.calor.caloriecounter.pojo.FoodModel
import cal.calor.caloriecounter.pojo.WaterModel_2
import cal.calor.caloriecounter.pojo.weight.WeightPogo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class WaterViewModel (application: Application) : AndroidViewModel(application)  {

    private val db = WaterDataBase_2.getInstance(application)
    val waterListDAO = db.waterInfoDao_2().getWaterList()

    val waterListView : MutableLiveData<List<WaterModel_2>> = MutableLiveData()
    val waterListFilter : MutableLiveData<String> = MutableLiveData()

    private val _sumWater : MutableLiveData<Int> = MutableLiveData()
    val sumWater : LiveData<Int> =  _sumWater


    private val _changeDate : MutableLiveData<WaterModel_2> = MutableLiveData()
    val changeDate : MutableLiveData<WaterModel_2> = _changeDate

    init {
        waterListDAO.observeForever {
            updateWaterListView()
        }

        waterListFilter.observeForever {
            updateWaterListView()
        }
    }


    fun addWaterDataBase (waterModel_2: WaterModel_2) {
        viewModelScope.launch(Dispatchers.IO) {
            db.waterInfoDao_2().insertWaterList(waterModel_2)
        }
    }

    fun updateDataBase (waterModel_2: WaterModel_2) {
        viewModelScope.launch(Dispatchers.IO) {
            db.waterInfoDao_2().update(waterModel_2)
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

    fun sumWater(listWater : List<WaterModel_2>){
        var water = 0

        for(item in listWater){
            if (item.dataCurrent == getCurrentDate()){
                water += item.water_is_drunk ?: 0
            }
        }
        _sumWater.value = water
    }


    fun updateWaterListView() {
        val filter = waterListFilter.value?.trim() ?: ""
        if (filter.isNotEmpty()) {
            waterListView.value = waterListDAO.value?.filter {

                it.dataCurrent?.lowercase()?.contains(filter.lowercase())!!
            }
            return
        }
        waterListView.value = waterListDAO.value
    }

    fun changeData(waterModel: WaterModel_2){
        _changeDate.value  = waterModel
    }


}