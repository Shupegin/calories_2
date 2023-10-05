package cal.calor.caloriecounter.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cal.calor.caloriecounter.pojo.UserIDModel


@Dao
interface UserInfoDao {
    @Query("SELECT * FROM user_id_list")
    fun getUserIdList() : LiveData<List<UserIDModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserIDList (listFood : List<UserIDModel>)

    @Query("DELETE FROM user_id_list")
    suspend fun delete()
}