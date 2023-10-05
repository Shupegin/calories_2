package cal.calor.caloriecounter

import cal.calor.caloriecounter.pojo.SearchFood.Servings
import com.google.gson.annotations.SerializedName


data class Food (

  @SerializedName("food_id"   ) var foodId   : String?   = null,
  @SerializedName("food_name" ) var foodName : String?   = null,
  @SerializedName("food_type" ) var foodType : String?   = null,
  @SerializedName("food_url"  ) var foodUrl  : String?   = null,
  @SerializedName("servings"  ) var servings : Servings? = Servings()

)