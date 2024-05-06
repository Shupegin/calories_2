package cal.calor.caloriecounter.HistoryScreen

//import com.google.firebase.auth.FirebaseAuth

import android.R.attr.label
import android.R.attr.text
import android.annotation.SuppressLint
import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cal.calor.caloriecounter.database.AppDatabase
import cal.calor.caloriecounter.database.WaterDataBase_2
import cal.calor.caloriecounter.database.WeightDataBase
import cal.calor.caloriecounter.pojo.FoodModel
import cal.calor.caloriecounter.pojo.FoodModelAdd
import cal.calor.caloriecounter.pojo.WaterModel_2
import cal.calor.caloriecounter.pojo.weight.WeightPogo
import co.yml.charts.common.model.Point
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.text.SimpleDateFormat
import java.util.Date


class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    val foodListDAO = db.foodsInfoDao().getFoodsList()

    private val dbWater = WaterDataBase_2.getInstance(application)
    val waterListDAO = dbWater.waterInfoDao_2().getWaterList()

    private val dbWeight = WeightDataBase.getInstance(application)
    val weightListDAO = dbWeight.weightInfoDao().getUserIdList()

    private var _listPoint : MutableLiveData<List<Point>> = MutableLiveData()
    var listPoint : LiveData<List<Point>> = _listPoint

    private var _listPointWater : MutableLiveData<List<Point>> = MutableLiveData()
    var listPointWater : LiveData<List<Point>> = _listPointWater

    private var _listPointWeight : MutableLiveData<List<Point>> = MutableLiveData()
    var listPointWeight : LiveData<List<Point>> = _listPointWeight

    var _listKey : MutableLiveData<ArrayList<String>> = MutableLiveData()
    var result2 : HashMap<String, Long> = HashMap()






    private val _imageQR : MutableLiveData<Bitmap> = MutableLiveData()
    val imageQR : MutableLiveData<Bitmap> = _imageQR

    private val _clientID : MutableLiveData<String> = MutableLiveData()
    val client : MutableLiveData<String> =  _clientID


//    private var auth:  FirebaseAuth? = null
    private var firebaseDatabase : FirebaseDatabase? = null

    var userId : String = ""

    init {
    }



    fun getFirebaseData(){
//        val userReference : DatabaseReference?
//        userReference = firebaseDatabase?.getReference("calories/${userId}")
//
//        userReference?.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                var key  = ""
//                var result : ArrayList<UserCaloriesFirebase> = ArrayList()
//                var keyList : ArrayList<String> = ArrayList()
//
//
//                result.clear()
//                for(dataSnapshot in snapshot.children){
//                    key = dataSnapshot.key.toString()
//                    keyList.add(key)
//
//                    val foods = dataSnapshot.value as HashMap<String, *>
//
//                    var caloriesTotal: Long = 0
//                    val foodNames = foods.keys
//                    for (foodName in foodNames) {
//                        val foodFields = foods[foodName] as HashMap<String, *>
//                        val calories = foodFields["calories"] as Long
//                        caloriesTotal += calories
//                    }
//                    result2[key] = caloriesTotal
//                }
//                getPointsList(result2)
//            }
//            override fun onCancelled(error: DatabaseError) {}
//        })
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
         val listSort = _list.entries.sortedBy { it.key }.associate { it.toPair() }
            var ir = 1
         for (i in listSort ){
                ir++

             listPoint.add(Point(ir.toFloat(),i.value.toFloat()))
         }
        _listPoint.value = listPoint
    }


     fun getPointsList_2( _list : List<FoodModel>? = null) {
         val  listPoint = ArrayList<Point>()
         val listSortPoint = sortElementEat(_list)
         var ir = 1

         listSortPoint.values.forEach{
             ir++
             listPoint.add(Point(ir.toFloat(),it.let { it!!.toFloat() }))

         }

        _listPoint.value = listPoint
    }


    fun getPointsListWater( _list : List<WaterModel_2>? = null) {
        val  listPoint = ArrayList<Point>()

        val listSortPoint = sortElementWater(_list)
        // отсортировать список по возрастанию
        var ir = 1

        listSortPoint.values.forEach{
            ir++
            listPoint.add(Point(ir.toFloat(),it.let { it!!.toFloat() }))
        }

        _listPointWater.value = listPoint


//        if (listSort != null) {
//            for (i in listSort ){
//                if (i.dataCurrent.equals(i.dataCurrent)){
//                    ir++
//                    if (i.water_is_drunk != null){
//                        water += i.water_is_drunk
//                    }
//                }
//
//            }
//            listPoint.add(Point(ir.toFloat(),water.let { it!!.toFloat() }))
//        }
    }





    fun getPointsListWeight( _list : List<WeightPogo>? = null) {
        val  listPoint = ArrayList<Point>()
        // отсортировать список по возрастанию
        val listSort = _list
        var ir = 1
        if (listSort != null) {
            for (i in listSort ){
                ir++

                listPoint.add(Point(ir.toFloat(),i.weight.let { it!!.toFloat() }))
            }
        }
        _listPointWeight.value = listPoint
    }


    fun sortElementEat(listSort : List<FoodModel>? = null) : MutableMap<String,Int> {
        val mapPoint = mutableMapOf<String, Int>()


        listSort?.forEach() {
            val key = it.dataCurrent.toString()
            mapPoint[key] = (mapPoint[key] ?: 0) + (it.calories ?: 0)
        }

        return mapPoint
    }

    fun sortElementWater(listSort : List<WaterModel_2>? = null) : MutableMap<String,Int> {
        val mapPoint = mutableMapOf<String, Int>()


        listSort?.forEach() {
            val key = it.dataCurrent.toString()
            mapPoint[key] = (mapPoint[key] ?: 0) + (it.water_is_drunk ?: 0)
        }

        return mapPoint
    }


    fun generateQR(ui : String){
        try {
            val barcodeEncode = BarcodeEncoder()
            val bitmap : Bitmap = barcodeEncode.encodeBitmap(ui, BarcodeFormat.QR_CODE, 750, 750)
            _imageQR.value = bitmap
        } catch (e: WriterException){}

    }


    fun saveCardInClipboard(context: Context){
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
        val clip = ClipData.newPlainText("card", "2200700162545817")
        clipboard?.setPrimaryClip(clip)
    }
}