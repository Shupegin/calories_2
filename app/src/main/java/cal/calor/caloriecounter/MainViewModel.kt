package cal.calor.caloriecounter

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cal.calor.caloriecounter.data.mapper.MapFood
import cal.calor.caloriecounter.database.AppDatabase
import cal.calor.caloriecounter.database.UserDatabase
import cal.calor.caloriecounter.dialog.FoodMapper
import cal.calor.caloriecounter.network.ApiFactory
import cal.calor.caloriecounter.pojo.AuthorizationPassword.AuthorizationPassword
import cal.calor.caloriecounter.pojo.Food.ItemsFood
import cal.calor.caloriecounter.pojo.FoodModel
import cal.calor.caloriecounter.pojo.FoodModelAdd
import cal.calor.caloriecounter.pojo.FoodSearchFirebase
import cal.calor.caloriecounter.pojo.Management
import cal.calor.caloriecounter.pojo.SearchFood.UserCaloriesFirebase
import cal.calor.caloriecounter.pojo.UserIDModel
import cal.calor.caloriecounter.pojo.UserModelFireBase
import co.yml.charts.common.extensions.isNotNull
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.*
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class MainViewModel(application: Application): AndroidViewModel(application) {
    val mapper = FoodMapper()
    val mapperNew = MapFood()
    var food_id : Int? = 321312
    var token : String? = ""

    val list  = ArrayList<UserModelFireBase>()

    var result = " "

    private val context = getApplication<Application>().applicationContext




    private val db = AppDatabase.getInstance(application)
    private val dbUId = UserDatabase.getInstance(application)
    val foodListDAO = db.foodsInfoDao().getFoodsList()
    val foodListView : MutableLiveData<List<FoodModel>> = MutableLiveData()
    val foodListFilter : MutableLiveData<String> = MutableLiveData()
    val userListDAO = dbUId.userInfoDao().getUserIdList()

    private val _historyCalories : MutableLiveData<Int> = MutableLiveData()
//    val addHistoryCalories : MutableLiveData<Int> = _historyCalories

    private val _listHistoryCalories : MutableLiveData<List<UserModelFireBase>> = MutableLiveData()
    val addListHistoryCalories : MutableLiveData<List<UserModelFireBase>> = _listHistoryCalories


    private val _day : MutableLiveData<String> = MutableLiveData()
    val addDay : MutableLiveData<String> = _day

    private var firebaseDatabase : FirebaseDatabase? = null
    private var userReference : DatabaseReference? = null
    private var userIdReference : DatabaseReference? = null
    private var userNameDishReference : DatabaseReference? = null
    private var databaseEntry : DatabaseReference? = null
    private var dataBaseFoods : DatabaseReference? = null

//    private var auth:  FirebaseAuth? = null


    private val _calories : MutableLiveData<String> = MutableLiveData()
    val calories : MutableLiveData<String> =  _calories

    private val _status : MutableLiveData<Boolean> = MutableLiveData()
    val status : LiveData<Boolean> =  _status

    private val _imageQR : MutableLiveData<Bitmap> = MutableLiveData()
    val imageQR : MutableLiveData<Bitmap> = _imageQR

    val listFood : ArrayList<FoodModel> = ArrayList()

    private val _management : MutableLiveData<Management> = MutableLiveData()
    val management : LiveData<Management> =  _management

    private val _loadListForFilter : MutableLiveData<List<FoodModelAdd>> = MutableLiveData()
    val loadListForFilter : LiveData<List<FoodModelAdd>> =  _loadListForFilter

    init {
        authorizationRequest()
        getCurrentDate()
        firebaseDatabase = FirebaseDatabase.getInstance()
        userReference = firebaseDatabase?.getReference("calories")
        userIdReference = firebaseDatabase?.getReference("Users")
        userNameDishReference = firebaseDatabase?.getReference("UsersFoodName")
        databaseEntry = firebaseDatabase?.getReference("foods")

        loadListFoodForFilter()
        foodListDAO.observeForever {
            updateFoodListView()
        }
        foodListFilter.observeForever {
            updateFoodListView()
        }

    }



    @SuppressLint("SuspiciousIndentation")
    fun authorizationRequest() {
        viewModelScope.launch {
            val clientId = "9bf375c35df743e7be742724d0a1fd31";
            val clientSecret= "0e8023668fa943b3ab9555065c53be4e";

            var auth = Credentials.basic(clientId, clientSecret)
            val response = ApiFactory
                .getApiAuthorization()
                .requestAuthorization(auth = auth)

            response?.enqueue(object : Callback<AuthorizationPassword?> {
                override fun onResponse(
                    call: Call<AuthorizationPassword?>,
                    response: Response<AuthorizationPassword?>
                ) {
                    if (response.isSuccessful) {
                        token = response.body()?.accessToken
                    } else {
//                        _status.value = false
//                        Toast.makeText(
//                            context,
//                            "NoSuccess = ${response.code()}",
//                            Toast.LENGTH_LONG
//                        ).show()
                    }
                }

                override fun onFailure(call: Call<AuthorizationPassword?>, t: Throwable) {
                }
            })
        }
    }
    @SuppressLint("SuspiciousIndentation")
    fun loadSearchFood(foodModel : FoodModel) {
        viewModelScope.launch(Dispatchers.IO) {
            listFood.clear()
            listFood.add(foodModel)
            db.foodsInfoDao().insertFoodList(foodModel)
        }
    }

    fun deleteFood(foodModel: FoodModel){
        viewModelScope.launch(Dispatchers.IO) {
            db.foodsInfoDao().remove( id = foodModel.food_id)
        }
    }

    private fun formatData(_data : String) : String {
        val readingFormatter = DateTimeFormatter.ofPattern("ddMMyyyy")
        val date = LocalDate.parse(_data, readingFormatter)

        val writingFormatter = DateTimeFormatter.ofPattern("yyyMMdd")
        val formattedDate = date.format(writingFormatter)
       return formattedDate
    }

    fun removeInFirebaseDatabase(foodModel: FoodModel){
        var data = removePunctuations(foodModel.dataCurrent.toString())

    }


    private fun removePunctuations(source : String) : String{
        return source.replace("\\p{Punct}".toRegex(),"")
    }


    fun getCalories(listFood : List<FoodModel>) : Int{
        return listFood.sumOf { it.calories ?: 0 }
    }

    fun sendSelectedOptionText(selectedOptionText : String,listFood: List<FoodModel>){
        when(selectedOptionText){
            "День" -> getCaloriesOneDay(listFood)
            "Неделя" -> getCaloriesWeek()
            "Две недели" -> Log.d("History","3")
            "Месяц"  -> Log.d("History","4")

        }
        _day.value = selectedOptionText
    }

    fun getCaloriesOneDay(listFood : List<FoodModel>){
        var calories = 0
        for(item in listFood){
            if (item.dataCurrent == getCurrentDate()){
                calories += item.calories ?: 0
            }
        }
        _historyCalories.value = calories
        updateCaloriesHistory()
    }

    fun getCaloriesWeek(){
        _historyCalories.value = 0
    }

    private fun updateCaloriesHistory() {
        if (_listHistoryCalories.value.isNullOrEmpty()) {
            _listHistoryCalories.value = list
        }
        updateUserCaloriesHistory("user1", _historyCalories.value)
    }

    private fun updateUserCaloriesHistory(name: String, value: Int?) {
        try {
            val user = list.first { it.name.equals(name) }
            user.dailyCalories = (value ?: 0).toString()
        } catch (ex: NoSuchElementException) {
            list.add(UserModelFireBase(name = name, dailyCalories = (value ?: 0).toString()))
            list.reverse()
        }
    }


    fun management(){
        var userReference : DatabaseReference?

            userReference = firebaseDatabase?.getReference("Management")
            userReference?.addValueEventListener(object : ValueEventListener {
                @SuppressLint("SuspiciousIndentation")
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(dataSnapshot in snapshot.children){
                        val value = dataSnapshot.getValue(Management::class.java)
                        _management.value = value
                    }
                }
                override fun onCancelled(error: DatabaseError) {}
            })

    }

    fun loadFirebaseData(listUser : List<UserIDModel>){
        var userReference : DatabaseReference?
        val data = removePunctuations(getCurrentDate())
        for(i in listUser){
            userReference = firebaseDatabase?.getReference("calories/${i.userId}/${data}")
            userReference?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var total = 0
                    for(dataSnapshot in snapshot.children){
                        val value = dataSnapshot.getValue(UserCaloriesFirebase::class.java)
                        if (getCurrentDate() == value?.dataCurrent) {
                            total += value.calories ?: 0
                        }
                    }
                    updateUserCaloriesHistory(i.userId, total)
                }
                override fun onCancelled(error: DatabaseError) {}
            })
        }
    }

    fun splitName(name: String): String {
        val names = name.trim().split(Regex("\\s+"))
        return names.firstOrNull().toString()
    }



    fun addInfoFoodBtn(foodModel : FoodModel) {
        loadSearchFood(foodModel)
    }

    @SuppressLint("SimpleDateFormat")
   fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyy")
        return dateFormat.format(Date())
    }


    suspend fun entryDatabase(id: String){
        val userid = UserIDModel(userId = id)
        val listUserId = ArrayList<UserIDModel>()
        listUserId.add(userid)
        dbUId.userInfoDao().insertUserIDList(listUserId)
    }

    @SuppressLint("NewApi", "SuspiciousIndentation")
    fun requestFood(foodModel: FoodModel) {
        foodModel.food?.let { food ->
            var calories = 0
            dataBaseFoods = firebaseDatabase?.getReference("foods/$food")
            dataBaseFoods?.get()?.addOnCompleteListener {  task ->
                val value = task.result.getValue(FoodModelAdd::class.java)
                if (value.isNotNull()){
                    calories = value?.calories ?: 0
                    calories = (foodModel.gramm ?: 0) * calories / 100
                    _calories.value = calories.toString()
                    foodModel.calories = calories
                    foodModel.proteinG = value?.proteinG
                    foodModel.fatTotalG = value?.fatTotalG
                    foodModel.fatSaturatedG = value?.fatSaturatedG
                    foodModel.carbohydratesTotalG = value?.carbohydratesTotalG
                    foodModel.cholesterolMg = value?.cholesterolMg
                    foodModel.fiberG= value?.fiberG
                    foodModel.potassiumMg = value?.potassiumMg
                    foodModel.sodiumMg = value?.sodiumMg
                    foodModel.sugarG = value?.sugarG

                    _status.value = false
                    addInfoFoodBtn(foodModel)
                }else{
                    saving_the_names_of_dishes(food.lowercase().trim())
                    requestFoodApi(food, foodModel)
                }
            }
        }
    }


     fun downloadModel (){
        val translatorOptions = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.RUSSIAN)
            .setTargetLanguage(TranslateLanguage.ENGLISH)
            .build()
        val translatorEnglish = Translation.getClient(translatorOptions)
        translatorEnglish.downloadModelIfNeeded()

    }

    private fun translator(text: String, onSuccess: OnSuccessListener<String>){
           val translatorOptions = TranslatorOptions.Builder()
               .setSourceLanguage(TranslateLanguage.RUSSIAN)
               .setTargetLanguage(TranslateLanguage.ENGLISH)
               .build()
           val translatorEnglish = Translation.getClient(translatorOptions)
            translatorEnglish.downloadModelIfNeeded()

           translatorEnglish.translate(text)

               .addOnSuccessListener(onSuccess)
               .addOnFailureListener {textError->
                   Log.d("textError","$textError")
               }
   }

    fun statusLoad(status : Boolean){
        _status.value = status
    }

    fun recordPreference(toast : Boolean){
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().putBoolean("toast", toast).apply()
    }

    fun dischargePreference() : Boolean{
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val data = prefs.getBoolean("toast",false)

        return data
    }


    fun updateFoodListView() {
        val filter = foodListFilter.value?.trim() ?: ""
        if (filter.isNotEmpty()) {
            foodListView.value = foodListDAO.value?.filter {

                it.dataCurrent?.lowercase()?.contains(filter.lowercase())!!
            }
            return
        }
        foodListView.value = foodListDAO.value
    }


    fun returnSum(list : List<FoodModel>, name : String) : Double {
        var returnSum = 0.0

        list.forEach{
            if (it.dataCurrent == getCurrentDate()){
                when(name){
                    "белки" -> it.proteinG?.let { returnSum += it }
                    "жиры" -> it.fatTotalG?.let { returnSum += it }
                    "углеводы" -> it.carbohydratesTotalG?.let { returnSum += it }
                }
            }
        }
        return returnSum
    }



    fun saving_the_names_of_dishes(name : String){
        userNameDishReference?.child(name)?.setValue(name)
//        delete_item_dateBase(name)
    }

    fun databaseEntry (foodModelDish: FoodModelAdd) {
        databaseEntry?.child(foodModelDish.name.toString())?.setValue(foodModelDish)
        delete_item_dateBase(foodModelDish.name.toString())
    }

    fun  delete_item_dateBase(name : String){
        userNameDishReference?.child(name)?.removeValue()

    }

    private fun requestFoodApi(food :String, foodModel : FoodModel){
        translator(food) { text ->
            text?.let { foodEnglish ->
                viewModelScope.launch {
                    var calories = 0

                    val response = ApiFactory.getApi().getFood(foodEnglish)
                    response?.enqueue(object : Callback<ItemsFood?> {
                        override fun onResponse(
                            call: Call<ItemsFood?>,
                            response: Response<ItemsFood?>
                        ) {
                            if (response.isSuccessful) {
                                if (!response.body()?.items.isNullOrEmpty()){
                                    response.body()?.items?.forEach {

                                        val foodModelList = mapperNew.mapResponseToPosts(it)
                                        for (item in foodModelList) {

                                            calories = item.calories ?: 0
                                            calories = (foodModel.gramm ?: 0) * calories / 100
                                            _calories.value = calories.toString()
                                            foodModel.calories = calories
                                            foodModel.proteinG = item.proteinG
                                            foodModel.fatTotalG = item.fatTotalG
                                            foodModel.fatSaturatedG = item.fatSaturatedG
                                            foodModel.carbohydratesTotalG = item.carbohydratesTotalG
                                            foodModel.cholesterolMg = item.cholesterolMg
                                            foodModel.fiberG= item.fiberG
                                            foodModel.potassiumMg = item.potassiumMg
                                            foodModel.servingSizeG = item.servingSizeG
                                            foodModel.sodiumMg = item.sodiumMg
                                            foodModel.sugarG = item.sugarG
                                        }
                                        _status.value = false
                                    }


                                    addInfoFoodBtn(foodModel)
                                }else{
                                    _status.value = false

                                    Toast.makeText(
                                        context,
                                        "Блюдо не найдено,пример для заполнения <Хачапури с сыром>",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }

                            } else {
                                _status.value = false
                                Toast.makeText(
                                    context,
                                    "NoSuccess = ${response.code()}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<ItemsFood?>, t: Throwable) {
                            _status.value = false

                        }
                    })

                }
            }
        }
    }
    fun  loadListFoodForFilter(){
        var loadFoodDatabase : DatabaseReference?
        loadFoodDatabase = firebaseDatabase?.getReference("foods")
        var list = ArrayList<FoodModelAdd>()

        loadFoodDatabase?.get()?.addOnCompleteListener{ task ->

            for( i in task.result.children){
                val value = i.getValue(FoodModelAdd::class.java)
                list.add(FoodModelAdd(
                    name = value?.name,
                    calories = value?.calories))
            }
            loadCount(list)
        }
    }

    fun loadCount(list : List<FoodModelAdd> ){
        _loadListForFilter.value = list
    }
}


