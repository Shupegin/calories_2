package cal.calor.caloriecounter.dialog


import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import cal.calor.caloriecounter.MainViewModel
import cal.calor.caloriecounter.R
import cal.calor.caloriecounter.pojo.FoodModel
import cal.calor.caloriecounter.pojo.FoodModelAdd
import cal.calor.caloriecounter.ui.theme.brilliant_green
import cal.calor.caloriecounter.ui.theme.sf_ui_display_semiboldFontFamily
import cal.calor.caloriecounter.ui.theme.sfproDisplayThinFontFamily
import cal.calor.caloriecounter.ui.theme.Сoral
import co.yml.charts.common.extensions.isNotNull
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter



@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun dialog(dialogState: MutableState<Boolean>,
           viewModel: MainViewModel,
           owner: LifecycleOwner,
           context: Context
){
    var userFood by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var datavalue by remember { mutableStateOf("") }
    var isLoading  by remember { mutableStateOf( false) }
    var editeTextVisibility  by remember { mutableStateOf( false) }
    var numberOfGrams by remember { mutableStateOf("") }
    var numberOfCalories by remember { mutableStateOf("") }
    var openList by remember {
        mutableStateOf(false)
    }

    val SharedPreferences = context.getSharedPreferences("error", Context.MODE_PRIVATE)
    val errorMessage = SharedPreferences.getBoolean("massage", true)




    var openDialog   =  remember { mutableStateOf( false) }

    if (openDialog.value){
        dialogAdd(dialogState = openDialog, viewModel = viewModel, owner = owner, context = context)
    }

    val listForFilter  = viewModel.loadListForFilter.observeAsState(null)


    var openButton by remember {
        mutableStateOf(false)
    }
    var openMessage by remember {
        mutableStateOf(false)
    }

    openMessage = openButton

    var mainList by remember {
        mutableStateOf(listForFilter.value)
    }


    var pickDate by remember {
        mutableStateOf(LocalDate.now())
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("dd.MM.yyyy").format(pickDate)
        }
    }


    viewModel.management()
    val management =  viewModel.management.observeAsState()

    val dateDialogState  = rememberMaterialDialogState()


    viewModel.calories.observe(owner, Observer {
        numberOfCalories = it.toString()
    })

    editeTextVisibility = true

    datavalue = viewModel.getCurrentDate()



        Dialog(

            onDismissRequest = {
                dialogState.value = false
            },

        ) {
                Card(modifier = Modifier
                    .fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(1.dp, color = Сoral)
                ){
                    Column(
                        modifier = Modifier.padding(top = 20.dp, bottom = 20.dp,start = 20.dp,end =20.dp),
                        verticalArrangement = Arrangement.spacedBy(1.dp),
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {

                        Text(text = "Добавление съеденной еды:",
                            fontFamily = sf_ui_display_semiboldFontFamily
                        )

                        OutlinedTextField(
                            value = formattedDate,
                            modifier = Modifier.clickable(onClick = {dateDialogState.show()}),
                            enabled = false,
                            onValueChange = {},
                            maxLines = 1,

                            label = {
                                Text(
                                    text = "Дата:",
                                    fontFamily = sfproDisplayThinFontFamily,
                                    color = Color.Black
                                )
                            }, colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor =  Сoral,
                                unfocusedBorderColor = Сoral,
                                cursorColor = Сoral,
                                disabledTextColor = Color.Black,
                                disabledBorderColor = Сoral,
                                disabledPlaceholderColor = Сoral,
                                disabledLabelColor = Сoral,
                            ),
                        )

                        MaterialDialog(
                            dialogState = dateDialogState,
                            buttons = {
                                positiveButton(text = "ок"){
                                }
                                negativeButton(text = "Закрыть")
                            },
                            backgroundColor = Сoral
                        ) {
                            datepicker(
                                initialDate = LocalDate.now(),
                                title = "Выберите дату",

                                ){
                                pickDate = it
                            }
                        }




                        OutlinedTextField(
                            value = userFood,
                            onValueChange = {
                                it.let {
                                    userFood = it
                                    openList = if(it.isEmpty()){
                                        false
                                    }else{
                                        true
                                    }
                                    mainList = listForFilter.value?.filter { it1 ->
                                        it1.name?.lowercase()?.startsWith(it.lowercase()) ?: false
                                    }
                            } },
                            maxLines = 2,
                            label = {
                                Text(
                                    text = "Введите название",
                                    fontFamily = sfproDisplayThinFontFamily,
                                    color = Color.Black
                                )
                            },
                            trailingIcon = {
                                if(openButton){
                                    Icon(painter = painterResource(id = R.drawable.send),
                                        contentDescription = "", modifier = Modifier.clickable {
                                            viewModel.saveSharedPreference(false)
                                            openMessage = false
                                            val containsSymbol = userFood?.let { it.findAnyOf(strings = listOf(".", "$", "#", "[", "]"), startIndex = 0, ignoreCase = false) != null}

                                            containsSymbol?.let {
                                                if (!it){
                                                    viewModel.saving_the_names_of_dishes(userFood.lowercase().trim())
                                                    Toast.makeText(context, "Ваша позиция добавлена на добавление",Toast.LENGTH_SHORT).show()
                                                }else{
                                                    Toast.makeText(context,"Нельзя использовать символы \".\", \"\$\", \"#\", \"[\", \"]\"",Toast.LENGTH_LONG).show()
                                                }
                                            }

                                        } )
                                }
                            },

                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor =  Сoral,
                                unfocusedBorderColor = Сoral,
                                cursorColor = Сoral
                            )
                        )

                        if (openMessage){
                            if(errorMessage){
                                androidx.compose.material3.Text(
                                    text = "Это сообщение появилось, потому что введенной вами позиции нет в базе. \n" +
                                            "Вы можете её добавить самостоятельно, нажав на кнопку «Добавить позицию в базу». " +
                                            "Либо ввести в поле название блюда и нажать кнопку отправить, и мы добавим его в течение дня.",
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(start = 16.dp)
                                )
                            }

                        }

                        if (management.value?.openButtonAddFood == true){
                            if (openButton) {
                                Button(onClick = {
                                    viewModel.saveSharedPreference(false)
                                    openMessage = false
                                    viewModel.nameDish(userFood)
                                    openDialog.value = true },
                                ) {
                                    Text(text = "Добавьте позицию в базу")
                                }
                            }
                        }


                        if (openList){

                            LazyColumn(modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)

                            ){


                                if(mainList.orEmpty().isEmpty()){
                                    openButton = true
                                }else{
                                    openButton = false
                                }
                                mainList?.let {
                                    items(it) { it ->

                                        Row(modifier = Modifier
                                            .clickable {
                                                it.name?.let { text ->
                                                    userFood = text
                                                    openList = false
                                                }
                                            }
                                            .padding(2.dp)) {
                                            Spacer(modifier = Modifier.padding(start = 5.dp))

                                            it.name?.let { text ->
                                                Text(text = text, modifier = Modifier
                                                    .width(240.dp))

                                            }

                                            Spacer(modifier = Modifier.weight(1f))
                                            it.calories?.let { text ->
                                                Text(text = text.toString())
                                            }
                                            Spacer(modifier = Modifier.padding(end = 5.dp))

                                        }
                                    }
                                }
                            }
                        }


                            OutlinedTextField(
                                value = numberOfGrams,
                                onValueChange = { it.let {
                                    numberOfGrams  = it
                                } },
                                maxLines = 1,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                label = {
                                    Text(
                                        text = "количество грамм?",
                                        fontFamily = sfproDisplayThinFontFamily,
                                        color = Color.Black
                                    )
                                }, colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor =  Сoral,
                                    unfocusedBorderColor = Сoral,
                                    cursorColor = Сoral
                                )
                            )



                        Row() {
                            Button(onClick = { dialogState.value = false}) {
                                Text(text = "Закрыть ",
                                    fontFamily = sf_ui_display_semiboldFontFamily)
                            }
                            Spacer(modifier = Modifier.padding(end = 20.dp))
                            Button(onClick = {
                                val _category = viewModel.splitName(userFood).lowercase().trim()
                                val time = formattedDate

                                val foodModel = FoodModel(
                                    category= _category,
                                    food = userFood.lowercase().trim(),
                                    calories = numberOfCalories.toIntOrNull() ?: 0,
                                    gramm = numberOfGrams.toIntOrNull() ?: 0,
                                    dataCurrent = time

                                )
                                viewModel.statusLoad(true)
                                viewModel.requestFood(foodModel)

                                isLoading = true


                                dialogState.value = false

                            }) {
                                Text(text = "Ок",
                                    fontFamily = sf_ui_display_semiboldFontFamily
                                )
                            }
                        }

                    }
                }
        }

}
