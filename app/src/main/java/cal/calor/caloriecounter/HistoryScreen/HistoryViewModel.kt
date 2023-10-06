package cal.calor.caloriecounter.HistoryScreen

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cal.calor.caloriecounter.database.AppDatabase
import co.yml.charts.common.model.Point
import kotlin.random.Random

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    val foodListDAO = db.foodsInfoDao().getFoodsList()
    private var _listPoint : MutableLiveData<List<Point>> = MutableLiveData()
    var listPoint : LiveData<List<Point>> = _listPoint

    init {
        getPointsList()
    }

    private fun getPointsList() {
        val  list = ArrayList<Point>()
        for(i in 0..31){
            list.add(Point(i.toFloat(),
                Random.nextInt(1000,10000).toFloat())
            )
        }
        _listPoint.value = list
    }
}