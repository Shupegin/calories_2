package cal.calor.caloriecounter.pojo.pulse

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "pulse_id_list")

data class PulsePojo(
    @PrimaryKey
    val id : Int? = null,
    val data : String? = null,
    val pulse : Int? = null,
    val pressureLower : Int? = null,
    val pressureTop : Int? = null,
    val time : String? = null
)
