package cal.calor.caloriecounter.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_name_list")

data class FoodModel(
    @PrimaryKey(autoGenerate = true)
    var food_id: Int = 0,
    var dataCurrent: String? = null,
    var category: String? = null,
    var food: String? = null,
    var desctription: String? = null,
    var calories: Int? = null,
    var servingSizeG: Double? = null,
    var fatTotalG: Double? = null,
    var fatSaturatedG: Double? = null,
    var proteinG: Double? = null,
    var sodiumMg: Double? = null,
    var potassiumMg: Double? = null,
    var cholesterolMg: Double? = null,
    var carbohydratesTotalG: Double? = null,
    var fiberG: Double? = null,
    var sugarG: Double? = null,
    var error: Int? = null,
    var eating : String? = null,
    var gramm : Int? = null
)
