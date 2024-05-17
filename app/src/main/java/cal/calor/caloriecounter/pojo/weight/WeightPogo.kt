package cal.calor.caloriecounter.pojo.weight

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weight_id_list")

data class WeightPogo(
    @PrimaryKey
    val id : Int? = null,
    val data : String? = null,
    val weight: String? = null,
    val breast: String? = null,
    val waist: String? = null,
    val biceps: String? = null,
    val belly: String? = null,
    val hip: String? = null,
    val buttocks: String? = null
)
