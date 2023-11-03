package cal.calor.caloriecounter.data.mapper

import cal.calor.caloriecounter.SearchPojoFoods
import cal.calor.caloriecounter.pojo.Food.ItemsFood
import cal.calor.caloriecounter.pojo.FoodModel

class MapFood {
    fun mapResponseToPosts(responseDto: ItemsFood): List<FoodModel> {
        val result = mutableListOf<FoodModel>()
        val posts = responseDto.items

        if (posts != null) {
            for (post in posts) {

                val foodModel = FoodModel(
                    calories = post.calories?.toInt()
                )
                result.add(foodModel)
            }
        }
        return result
    }
}


