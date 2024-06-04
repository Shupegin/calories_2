package cal.calor.caloriecounter.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import cal.calor.caloriecounter.pojo.weight.WeightPogo

@Dao
interface WeightDao {
    @Query("SELECT * FROM weight_id_list")
    fun getUserIdList() : LiveData<List<WeightPogo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserIDList (weightPogo: WeightPogo)

    @Query("DELETE FROM weight_id_list WHERE id =:id")
    suspend fun remove(id : Int)

    @Update
    suspend fun update(weightPogo: WeightPogo)
}