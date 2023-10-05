package cal.calor.caloriecounter.HistoryScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import cal.calor.caloriecounter.database.AppDatabase

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    val foodListDAO = db.foodsInfoDao().getFoodsList()
}