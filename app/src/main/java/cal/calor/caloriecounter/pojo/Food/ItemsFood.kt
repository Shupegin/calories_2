package cal.calor.caloriecounter.pojo.Food

import com.example.example.Items
import com.google.gson.annotations.SerializedName

data class ItemsFood (
    @SerializedName("items" ) var items : ArrayList<Items>? = arrayListOf(),
    @SerializedName("message" ) var message : String? = null
)