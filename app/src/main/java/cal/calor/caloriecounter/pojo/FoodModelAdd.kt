package cal.calor.caloriecounter.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey


data class FoodModelAdd(
    var name: String? = null,
    var calories: Int? = null,
    var fatTotalG: Double? = 0.0,
    var fatSaturatedG: Double? = 0.0,
    var proteinG: Double? = 0.0,
    var sodiumMg: Double? = 0.0,
    var potassiumMg: Double? = 0.0,
    var cholesterolMg: Double? = 0.0,
    var carbohydratesTotalG: Double? = 0.0,
    var fiberG: Double? = 0.0,
    var sugarG: Double? = 0.0,
)
