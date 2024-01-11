package cal.calor.caloriecounter.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cal.calor.caloriecounter.pojo.WaterModel

@Dao
interface WaterInfoDao {

    @Query("SELECT * FROM water_list")
    fun getWaterList() : LiveData<List<WaterModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWaterList (waterModel: WaterModel)

    @Query("DELETE FROM water_list WHERE waterId =:id")
    suspend fun remove(id : Int)
}