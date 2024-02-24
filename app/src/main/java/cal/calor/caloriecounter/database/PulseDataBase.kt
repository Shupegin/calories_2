package cal.calor.caloriecounter.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cal.calor.caloriecounter.pojo.WaterModel_2
import cal.calor.caloriecounter.pojo.pulse.PulsePojo

@Database(entities = [PulsePojo::class], version = 5, exportSchema = true,
    autoMigrations = [AutoMigration(from = 4 ,5 )])

abstract class PulseDataBase : RoomDatabase() {

    companion object{
        private var db : PulseDataBase? = null
        private const val DB_NAME = "pulse.db"
        private val LOCK = Any()
        fun getInstance(context : Context): PulseDataBase{
            synchronized(LOCK){
                db?.let { return  it}
                val instance = Room.databaseBuilder(context,
                    PulseDataBase::class.java,
                    DB_NAME)
                    .build()
                instance.openHelper.writableDatabase
                instance.openHelper.readableDatabase
                db = instance

                return instance
            }
        }
    }
    abstract fun pulseInfoDao() : PulseInfoDao
}