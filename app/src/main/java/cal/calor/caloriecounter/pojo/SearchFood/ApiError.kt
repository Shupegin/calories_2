package cal.calor.caloriecounter.pojo.SearchFood

import com.google.gson.annotations.SerializedName

data class ApiError(
    @SerializedName("code"         ) var code : Int,
    @SerializedName("message"         ) var message : String
)
