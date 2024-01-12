package cal.calor.caloriecounter.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cal.calor.caloriecounter.pojo.WaterModel_2

@Database(entities = [WaterModel_2::class], version = 1, exportSchema = false)

abstract class WaterDataBase_2 : RoomDatabase() {

    companion object{
        private var db : WaterDataBase_2? = null
        private const val DB_NAME = "water_2.db"
        private val LOCK = Any()
        fun getInstance(context : Context): WaterDataBase_2{
            synchronized(LOCK){
                db?.let { return  it}
                val instance = Room.databaseBuilder(context,
                    WaterDataBase_2::class.java,
                    DB_NAME)
                    .build()
                instance.openHelper.writableDatabase
                instance.openHelper.readableDatabase
                db = instance

                return instance
            }
        }
    }
    abstract fun waterInfoDao_2() : WaterInfoDao_2
}