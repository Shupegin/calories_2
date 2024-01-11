package cal.calor.caloriecounter.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cal.calor.caloriecounter.pojo.WaterModel
import cal.calor.caloriecounter.pojo.weight.WeightPogo

@Database(entities = [WaterModel::class], version = 1, exportSchema = false)

abstract class WaterDataBase : RoomDatabase() {

    companion object{
        private var db : WaterDataBase? = null
        private const val DB_NAME = "water.db"
        private val LOCK = Any()
        fun getInstance(context : Context): WaterDataBase{
            synchronized(LOCK){
                db?.let { return  it}
                val instance = Room.databaseBuilder(context,
                    WaterDataBase::class.java,
                    DB_NAME)
                    .build()
                instance.openHelper.writableDatabase
                instance.openHelper.readableDatabase
                db = instance

                return instance
            }
        }
    }
    abstract fun waterInfoDao() : WaterInfoDao
}