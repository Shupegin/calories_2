package cal.calor.caloriecounter.pojo.SearchFood

import com.google.gson.annotations.SerializedName


data class Servings (

  @SerializedName("serving" ) var serving : ArrayList<Serving> = arrayListOf()

)