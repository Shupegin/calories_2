package cal.calor.caloriecounter

import cal.calor.caloriecounter.pojo.SearchFood.ApiError
import com.example.caloriecounter.Foods
import com.google.gson.annotations.SerializedName


data class SearchPojoFoods (
  @SerializedName("foods_search") var foods : Foods? = null,
  @SerializedName("error") var error : ApiError?  = null
)

