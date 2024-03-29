package cal.calor.caloriecounter.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import cal.calor.caloriecounter.pojo.FoodModel

@Database(entities = [FoodModel::class], version = 21, exportSchema = true,
    autoMigrations = [AutoMigration(from = 7 ,21 )])
abstract class AppDatabase : RoomDatabase() {


    companion object{
        private var db : AppDatabase? = null
        private const val DB_NAME = "main.db"
        private val LOCK = Any()

        fun getInstance(context : Context): AppDatabase{
            synchronized(LOCK){
                db?.let { return  it}
                val instance = Room.databaseBuilder(context,
                    AppDatabase::class.java,
                    DB_NAME)
                    .build()
                db = instance
                return instance
            }
        }
    }
    abstract fun foodsInfoDao() : FoodsInfoDao
}