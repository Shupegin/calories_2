package cal.calor.caloriecounter.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "water_list_2")

data class WaterModel_2(
    @PrimaryKey(autoGenerate = true)
    val waterId : Int = 0,
    val dataCurrent : String? = null,
    val water_is_drunk : Int? = null,
    val drained_of_water : Int? = null,
    val sum : Int? = null
)
