package cal.calor.caloriecounter.pojo.Food

import cal.calor.caloriecounter.pojo.SearchFood.Serving
import com.example.example.Items
import com.google.gson.annotations.SerializedName

data class ItemsFood (
    @SerializedName("items" ) var items : ArrayList<Items> = arrayListOf()
)