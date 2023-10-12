package cal.calor.caloriecounter.HistoryScreen

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import cal.calor.caloriecounter.database.AppDatabase
import cal.calor.caloriecounter.pojo.SearchFood.UserCaloriesFirebase
import cal.calor.caloriecounter.pojo.UserIDModel
import co.yml.charts.common.model.Point
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Objects
import kotlin.math.log
import kotlin.random.Random

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    val foodListDAO = db.foodsInfoDao().getFoodsList()
    private var _listPoint : MutableLiveData<List<Point>> = MutableLiveData()
    var listPoint : LiveData<List<Point>> = _listPoint
    var _listKey : MutableLiveData<ArrayList<String>> = MutableLiveData()
    var result2 : HashMap<String, Long> = HashMap()


    private var auth:  FirebaseAuth? = null
    private var firebaseDatabase : FirebaseDatabase? = null

    var userId : String = ""

    init {
        auth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        auth?.addAuthStateListener{

            it.uid?.let {
                Log.d("BAlTICO","TEST ")
                userId = it
                getFirebaseData()
            }
        }
    }

    fun getFirebaseData(){
        Log.d("BAlTICO","TEST 2")

        var userReference : DatabaseReference?
        userReference = firebaseDatabase?.getReference("calories/${userId}")

        userReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var key  = ""
                var result : ArrayList<UserCaloriesFirebase> = ArrayList()
                var keyList : ArrayList<String> = ArrayList()


                result.clear()
                for(dataSnapshot in snapshot.children){
                    key = dataSnapshot.key.toString()
                    keyList.add(key)

                    val foods = dataSnapshot.value as HashMap<String, *>

                    var caloriesTotal: Long = 0
                    val foodNames = foods.keys
                    for (foodName in foodNames) {
                        val foodFields = foods[foodName] as HashMap<String, *>
                        val calories = foodFields["calories"] as Long
                        caloriesTotal += calories
                    }
                    result2[key] = caloriesTotal
                }
                getPointsList(result2)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }



    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyy")
        return dateFormat.format(Date())
    }

    private fun removePunctuations(source : String) : String{
        return source.replace("\\p{Punct}".toRegex(),"")
    }


    private fun getPointsList( _list : HashMap<String, Long>) {
        val  listPoint = ArrayList<Point>()
    // отсортировать список по возрастанию
         var listSort = _list.entries.sortedBy { it.key }.associate { it.toPair() }
            var ir = 1
         for (i in listSort ){
                ir++

             listPoint.add(Point(ir.toFloat(),i.value.toFloat()))
         }



        _listPoint.value = listPoint
    }

}