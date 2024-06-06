package cal.calor.caloriecounter.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import cal.calor.caloriecounter.pojo.WaterModel_2

@Dao
interface WaterInfoDao_2 {

    @Query("SELECT * FROM water_list_2")
    fun getWaterList() : LiveData<List<WaterModel_2>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWaterList (waterModel_2: WaterModel_2)

    @Query("DELETE FROM water_list_2 WHERE waterId =:id")
    suspend fun remove(id : Int)

    @Update
    suspend fun update(waterModel_2: WaterModel_2)
}