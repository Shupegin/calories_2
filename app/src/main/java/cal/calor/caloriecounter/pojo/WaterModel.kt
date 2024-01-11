package cal.calor.caloriecounter.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "water_list")

data class WaterModel(
    @PrimaryKey(autoGenerate = true)
    val waterId : Int = 0,
    val dataCurrent : String? = null,
    val water_is_drunk : String? = null,
    val drained_of_water : String? = null
)
