package cal.calor.caloriecounter.pojo.SearchFood

import cal.calor.caloriecounter.Food
import com.google.gson.annotations.SerializedName

data class ResultFood (
    @SerializedName("food") var food: ArrayList<Food> = arrayListOf()
)