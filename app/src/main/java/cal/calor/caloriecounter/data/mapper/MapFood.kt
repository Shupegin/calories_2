package cal.calor.caloriecounter.data.mapper

import cal.calor.caloriecounter.pojo.FoodModel
import com.example.example.Items

class MapFood {
    fun mapResponseToPosts(responseDto: Items): List<FoodModel> {
        val result = mutableListOf<FoodModel>()
        val posts = responseDto

        if (posts != null) {

                val foodModel = FoodModel(
                    calories = posts.calories?.toInt(),
                    proteinG = posts.proteinG,
                    servingSizeG = posts.servingSizeG,
                    fatTotalG = posts.fatTotalG,
                    sodiumMg = posts.sodiumMg,
                    potassiumMg = posts.potassiumMg,
                    cholesterolMg = posts.cholesterolMg,
                    carbohydratesTotalG = posts.carbohydratesTotalG,
                    fiberG = posts.fiberG,
                    sugarG = posts.sugarG,

                )
                result.add(foodModel)

        }
        return result
    }
}



