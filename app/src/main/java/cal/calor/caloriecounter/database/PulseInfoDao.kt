package cal.calor.caloriecounter.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cal.calor.caloriecounter.pojo.FoodModel
import cal.calor.caloriecounter.pojo.pulse.PulsePojo

@Dao
interface PulseInfoDao {
    @Query("SELECT * FROM pulse_id_list")
    fun getPulseList() : LiveData<List<PulsePojo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPulseList (pulseModel: PulsePojo)

    @Query("DELETE FROM pulse_id_list WHERE id =:id")
    suspend fun remove(id : Int)
}

