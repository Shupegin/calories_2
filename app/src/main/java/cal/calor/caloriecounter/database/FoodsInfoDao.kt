package cal.calor.caloriecounter.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cal.calor.caloriecounter.pojo.FoodModel

@Dao
interface FoodsInfoDao {
    @Query("SELECT * FROM food_name_list")
    fun getFoodsList() : LiveData<List<FoodModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodList (foodModel: FoodModel)

    @Query("DELETE FROM food_name_list WHERE food_id =:id")
    suspend fun remove(id : Int)
}

