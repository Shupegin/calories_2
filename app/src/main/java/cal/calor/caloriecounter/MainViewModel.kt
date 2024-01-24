package cal.calor.caloriecounter


import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Bitmap
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
import cal.calor.caloriecounter.pojo.Food.ItemsFood
import cal.calor.caloriecounter.pojo.FoodModel
import cal.calor.caloriecounter.pojo.FoodSearchFirebase
import cal.calor.caloriecounter.pojo.Management
import cal.calor.caloriecounter.pojo.SearchFood.UserCaloriesFirebase
import cal.calor.caloriecounter.pojo.UserIDModel
import cal.calor.caloriecounter.pojo.UserModelFireBase
import com.google.android.gms.tasks.OnSuccessListener
//import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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




    init {
        authorizationRequest()
        getCurrentDate()
//        auth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        userReference = firebaseDatabase?.getReference("calories")
        userIdReference = firebaseDatabase?.getReference("Users")
    }



    @SuppressLint("SuspiciousIndentation")
    fun authorizationRequest() {
        viewModelScope.launch {
            val clientId = "9bf375c35df743e7be742724d0a1fd31";
            val clientSecret= "0e8023668fa943b3ab9555065c53be4e";

            var auth = Credentials.basic(clientId, clientSecret)
            val response = ApiFactory.getApiAuthorization().requestAuthorization(auth = auth)
            Log.d("TESTER","request token = ${response.accessToken}")

            token = response.accessToken
        }
    }
    @SuppressLint("SuspiciousIndentation")
    fun loadSearchFood(foodModel : FoodModel) {
        val nameFood : String = foodModel.food.toString()
        viewModelScope.launch(Dispatchers.IO) {
//            val response = ApiFactory.getApi(token?: "").loadSearchFoods(search_expression = nameFood)
//            val foodModelList = mapper.mapResponseToPosts(response)
//            var calories = 0
//            for (item in foodModelList){
//                food_id = item.food_id
//                try {
//                    val desctription = item.calories
//                    calories += desctription ?: 0
//                } catch (_ : java.lang.Exception) { }
//            }
//            calories /= foodModelList.size
//            foodModel.calories = calories
            foodModel.dataCurrent = getCurrentDate()
            listFood.clear()
            listFood.add(foodModel)
            db.foodsInfoDao().insertFoodList(foodModel)

            var currentData  = removePunctuations(foodModel.dataCurrent.toString())
            val data = formatData(currentData)
//            auth?.addAuthStateListener{
//                it.uid?.let { it1 -> userReference?.child(it1)?.child(data)?.
//                child(foodModel.food.toString())?.setValue(foodModel) }
//            }
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
//        auth?.addAuthStateListener{
//
//            it.uid?.let { it1 -> userReference?.child(it1)?.child(data)?.child(foodModel.food.toString())?.removeValue()}
//        }
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




    fun loadFirebaseFood(foodModel : FoodModel) {
        viewModelScope.launch(Dispatchers.IO) {
            var calories = 0
            var category = foodModel.category
            val userReference : DatabaseReference?
            userReference = firebaseDatabase?.getReference("food")
            userReference?.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(dataSnapshot in snapshot.children){
                        val _value = dataSnapshot.key
                        if(_value?.contains(category.toString())!!){

                            val userReference = firebaseDatabase?.getReference("food/${_value}")
                            userReference?.addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    var result = 0
                                    for (dataSnapshot in snapshot.children) {
                                        val value = dataSnapshot.getValue(FoodSearchFirebase::class.java)
                                        if (value?.name?.equals(foodModel.food.toString())!!) {
                                            calories = value?.calories ?: 0
                                        }
                                    }
                                    if (calories != 0) {
                                        result = (foodModel.gramm ?: 0) * calories / 100
                                    }

                                    _calories.value = result.toString()
                                }

                                override fun onCancelled(error: DatabaseError) {
                                }
                            })
                        }

                    }

                }

                override fun onCancelled(error: DatabaseError) {
                }
            })

        }
    }

    private fun categoryFirebase(foodModel: FoodModel, onSuccess: ValueEventListener){

        var category = foodModel.category
        val userReference : DatabaseReference?
        userReference = firebaseDatabase?.getReference("food")
        userReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(dataSnapshot in snapshot.children){
                    val _value = dataSnapshot.key
                    if(_value?.contains(category.toString())!!){
                        result = _value

                    }

                }

            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun splitName(name: String): String {
        val names = name.trim().split(Regex("\\s+"))
        return names.firstOrNull().toString()
    }

    fun textFilter(text: String) : Int{
        val index = text.lastIndexOf("-")
        val filteredText = text.substring(index + 2).substringBefore("|")
        val filterText = filteredText.filter { it.isDigit() }
        return filterText.toInt()
    }

    fun addInfoFoodBtn(foodModel : FoodModel) {
        loadSearchFood(foodModel)
    }

    @SuppressLint("SimpleDateFormat")
   fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyy")
        return dateFormat.format(Date())
    }

    fun databaseEntryUser(id : String){
        userIdReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(dataSnapshot in snapshot.children){
                    val value = dataSnapshot.getValue(UserModelFireBase::class.java)
                    if (value?.id.equals(id)){
                        viewModelScope.launch {
                            entryDatabase(id)
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
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
                                    response.body()?.items?.forEach {
                                        val foodModelList = mapperNew.mapResponseToPosts(it)
                                        for (item in foodModelList) {
                                            calories = item.calories ?: 0
                                            calories = (foodModel.gramm ?: 0) * calories / 100
                                             _calories.value = calories.toString()
                                            foodModel.calories = calories
                                        }
                                    }
                                    addInfoFoodBtn(foodModel)
                                } else {
                                    Toast.makeText(
                                        context,
                                        "NoSuccess = ${response.code()}",
                                        Toast.LENGTH_LONG
                                    )
                                }
                            }

                            override fun onFailure(call: Call<ItemsFood?>, t: Throwable) {
                            }
                        })

                    }
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
}


