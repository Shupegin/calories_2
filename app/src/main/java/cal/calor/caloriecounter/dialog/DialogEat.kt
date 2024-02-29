package cal.calor.caloriecounter.dialog


import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import cal.calor.caloriecounter.MainViewModel
import cal.calor.caloriecounter.R
import cal.calor.caloriecounter.pojo.FoodModel
import cal.calor.caloriecounter.ui.theme.fontFamily
import cal.calor.caloriecounter.ui.theme.Сoral
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@SuppressLint("SuspiciousIndentation")
@Composable
fun dialog(dialogState: MutableState<Boolean>,
           viewModel: MainViewModel,
           owner: LifecycleOwner
){
    var userFood by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var datavalue by remember { mutableStateOf("") }
    var isLoading  by remember { mutableStateOf( false) }
    var editeTextVisibility  by remember { mutableStateOf( false) }
    var numberOfGrams by remember { mutableStateOf("") }
    var numberOfCalories by remember { mutableStateOf("") }

    var pickDate by remember {
        mutableStateOf(LocalDate.now())
    }
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("dd.MM.yyyy").format(pickDate)
        }
    }

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
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {

                        Text(text = "Добавление съеденной еды:",
                            style = MaterialTheme.typography.body1
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
                                    style = androidx.compose.ui.text.TextStyle(
                                        color = Color.Black
                                    )
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
                            onValueChange = { it.let {
                                userFood = it
                            }},
                            maxLines = 1,
                            label = {
                                Text(
                                    text = "что ел?",
                                    style = androidx.compose.ui.text.TextStyle(
                                        color = Color.Black
                                    )
                                )
                            }, colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor =  Сoral,
                                unfocusedBorderColor = Сoral,
                                cursorColor = Сoral
                            )
                        )

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
                                    style = androidx.compose.ui.text.TextStyle(
                                        color = Color.Black
                                    )
                                )
                            }, colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor =  Сoral,
                                unfocusedBorderColor = Сoral,
                                cursorColor = Сoral
                            )
                        )



                        if(editeTextVisibility){
                            OutlinedTextField(
                                value = numberOfCalories,

                                onValueChange = {it.let {
                                    numberOfCalories = it
                                } },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                maxLines = 1,
                                trailingIcon = {
                                    if (isLoading) {
                                        CircularProgressIndicator(
                                            color = Color.Green
                                        )
                                    }
                                },
                                label = {
                                    Text(
                                        text = "калории?",
                                        style = androidx.compose.ui.text.TextStyle(
                                            color = Color.Black
                                        )
                                    )
                                }, colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor =  Сoral,
                                    unfocusedBorderColor = Сoral,
                                    cursorColor = Сoral

                                )
                            )
                        }
                        
                        Row() {
                            Button(onClick = { dialogState.value = false}) {
                                Text(text = "Закрыть ")
                            }
                            Spacer(modifier = Modifier.padding(end = 20.dp))
                            Button(onClick = {

                                val _category = viewModel.splitName(userFood).lowercase().trim()

                                Log.d("TESTORTIME","$formattedDate")

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

//                                viewModel.addInfoFoodBtn(foodModel)
//                                viewModel.loadFirebaseFood(foodModel)
                                isLoading = true


                                dialogState.value = false

                            }) {
                                Text(text = "Ок")
                            }
                        }

//                        Button(onClick = {
//                            val _category = viewModel.splitName(userFood).lowercase().trim()
//                            val _userfood =  userFood.lowercase().trim()
//
//                            val foodModel = FoodModel(
//                                category= _category,
//                                food = _userfood,
//                                calories = numberOfCalories.toIntOrNull() ?: 0,
//                                gramm = numberOfGrams.toIntOrNull() ?: 0
//                            )
////                            viewModel.loadFirebaseFood(foodModel)
//                            viewModel.requestFood(foodModel)
//                            isLoading = true
//
//                        }) {
//                            Text(text = "Обновить")
//                        }
                    }
                }


        }
}
