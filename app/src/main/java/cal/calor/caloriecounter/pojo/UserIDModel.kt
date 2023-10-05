package cal.calor.caloriecounter.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_id_list")
data class UserIDModel(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var userId : String
)
