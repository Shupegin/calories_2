package cal.calor.caloriecounter.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cal.calor.caloriecounter.pojo.UserIDModel

@Database(entities = [UserIDModel::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    companion object{

        private var db : UserDatabase? = null
        private const val DB_NAME = "user.db"
        private val LOCK = Any()
        fun getInstance(context : Context): UserDatabase{
            synchronized(LOCK){
                db?.let { return  it}
                val instance = Room.databaseBuilder(context,
                    UserDatabase::class.java,
                    DB_NAME)
                    .build()
                db = instance
                instance.openHelper.writableDatabase
                instance.openHelper.readableDatabase
                return instance
            }
        }
    }
    abstract fun userInfoDao() : UserInfoDao
}