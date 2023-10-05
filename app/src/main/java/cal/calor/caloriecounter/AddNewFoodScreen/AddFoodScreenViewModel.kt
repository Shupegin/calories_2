package cal.calor.caloriecounter.AddNewFoodScreen

import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase

class AddFoodScreenViewModel : ViewModel() {
    private var firebaseDatabase = FirebaseDatabase.getInstance()

    fun addDatabase(dish: Dish){
        if(!dish.name.isNullOrEmpty() && !dish.category.isNullOrEmpty()){
            val reference = firebaseDatabase.getReference("food/" + dish.category + "/" + dish.name)
            reference.setValue(dish)
        }
    }

    fun splitName(name: String): String {
        val names = name.trim().split(Regex("\\s+"))
        return names.firstOrNull().toString()
    }
}