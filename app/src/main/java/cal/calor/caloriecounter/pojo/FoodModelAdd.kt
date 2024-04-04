package cal.calor.caloriecounter.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey


data class FoodModelAdd(
    var name: String? = null,
    var calories: Int? = null,
    var fatTotalG: Double? = null,
    var fatSaturatedG: Double? = null,
    var proteinG: Double? = null,
    var sodiumMg: Double? = null,
    var potassiumMg: Double? = null,
    var cholesterolMg: Double? = null,
    var carbohydratesTotalG: Double? = null,
    var fiberG: Double? = null,
    var sugarG: Double? = null,
)
