package cal.calor.caloriecounter.database

import android.content.Context
import androidx.compose.ui.Modifier
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cal.calor.caloriecounter.pojo.UserIDModel
import cal.calor.caloriecounter.pojo.weight.WeightPogo

@Database(entities = [WeightPogo::class], version = 1, exportSchema = false)

abstract class WeightDataBase : RoomDatabase() {
    companion object{

        private var db : WeightDataBase? = null
        private const val DB_NAME = "weight.db"
        private val LOCK = Any()
        fun getInstance(context : Context): WeightDataBase{
            synchronized(LOCK){
                db?.let { return  it}
                val instance = Room.databaseBuilder(context,
                    WeightDataBase::class.java,
                    DB_NAME)
                    .build()
                db = instance
                instance.openHelper.writableDatabase
                instance.openHelper.readableDatabase
                return instance
            }
        }
    }
    abstract fun weightInfoDao() : WeightDao
}