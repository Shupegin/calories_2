package cal.calor.caloriecounter.dialog

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import cal.calor.caloriecounter.MainViewModel
import cal.calor.caloriecounter.pojo.FoodModelAdd
import cal.calor.caloriecounter.ui.theme.brilliant_green
import cal.calor.caloriecounter.ui.theme.sf_ui_display_semiboldFontFamily
import cal.calor.caloriecounter.ui.theme.spring_bud_green
import cal.calor.caloriecounter.ui.theme.white
import cal.calor.caloriecounter.ui.theme.Сoral

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dialogAdd(dialogState: MutableState<Boolean>,
           viewModel: MainViewModel,
           owner: LifecycleOwner,
           context: Context
){
    var name by remember { mutableStateOf("") }
    var calories by remember { mutableStateOf("") }
    var protein_g by remember { mutableStateOf("") }
    var carbohydrates_total_g by remember { mutableStateOf("") }
    var fat_total_g by remember { mutableStateOf("") }
    var fat_saturated_g by remember { mutableStateOf("") }
    var sodium_mg by remember { mutableStateOf("") }
    var fiber_g by remember { mutableStateOf("") }
    var potassium_mg by remember { mutableStateOf("") }
    var sugar_g by remember { mutableStateOf("") }
    var cholesterolMg by remember { mutableStateOf("") }

    var isErrorName by rememberSaveable { mutableStateOf(false) }
    var isErrorCalories by rememberSaveable { mutableStateOf(false) }
    var isErrorProtein by rememberSaveable { mutableStateOf(false) }
    var isErrorFatTotal by rememberSaveable { mutableStateOf(false) }
    var isErrorСarbohydrates by rememberSaveable { mutableStateOf(false) }

    var text_error = "Поле не должно быть пустым"
    var text_error_calories = "Поле не должно быть пустым (только целые числа)"
//    val charLimit = 10
//    fun validate(text: String) {
//        isError = text.length > charLimit
//    }


    viewModel.nameDish.observe(owner, Observer{
        name = it
    })

    viewModel.management()
    val management =  viewModel.management.observeAsState()

//    viewModel.item.observe(owner, Observer {
//        name = it
//    })

    Dialog(
        onDismissRequest = {
            dialogState.value = false
        },) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(Color.White),
            shape = RoundedCornerShape (20.dp),
            border = BorderStroke(1.dp, color = brilliant_green),
        ){
            Column(
                Modifier.fillMaxWidth().padding(top = 20.dp, bottom = 20.dp,start = 20.dp,end =20.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {

                Text(text = "Добавление блюд в базу:", textAlign = TextAlign.Center,
                    fontFamily = sf_ui_display_semiboldFontFamily
                )
                OutlinedTextField(
                    value = name,

                    onValueChange = {
                        it.let {
                            name = it
                            if (!it.isEmpty()){
                                isErrorName = false
                            }
                        }
                    },
                    maxLines = 1,

                    label = {
                        Text(text = "Название",
                            color = Color.Black
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor =  brilliant_green,
                        unfocusedBorderColor = brilliant_green,
                        cursorColor = brilliant_green
                    ),
                    isError = isErrorName,
                    supportingText = {
                        if (isErrorName){
                            Text(
                                text = text_error,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }

                    }

                )

                OutlinedTextField(
                    value = calories,

                    onValueChange = {
                        it.let {
                            calories = it
                            if (!it.isEmpty()){
                                isErrorCalories = false
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1,

                    label = {
                        Text(text = "Калории(Только целые числа)",
                            color = Color.Black

                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor =  brilliant_green,
                        unfocusedBorderColor = brilliant_green,
                        cursorColor = brilliant_green
                    ),
                    isError = isErrorCalories,
                    supportingText = {
                        if (isErrorCalories){
                            Text(
                                text = text_error_calories,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }

                    }

                )


                OutlinedTextField(
                    value = protein_g,

                    onValueChange = {
                        it.let {
                            protein_g = it

                            if (!it.isEmpty()){
                                isErrorProtein = false
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1,

                    label = {
                        Text(text = "Белок",
                            color = Color.Black
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor =  brilliant_green,
                        unfocusedBorderColor = brilliant_green,
                        cursorColor = brilliant_green
                    ),
                    isError = isErrorProtein,
                    supportingText = {
                        if (isErrorProtein) {
                            Text(
                                text = text_error,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                )

                OutlinedTextField(
                    value = fat_total_g,

                    onValueChange = {
                        it.let {
                            fat_total_g = it

                            if (!it.isEmpty()){
                                isErrorFatTotal = false
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1,

                    label = {
                        Text(text = "Жиры",
                            color = Color.Black
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor =  brilliant_green,
                        unfocusedBorderColor = brilliant_green,
                        cursorColor = brilliant_green
                    ),
                    isError = isErrorFatTotal,
                    supportingText = {
                        if (isErrorFatTotal) {
                            Text(
                                text = text_error,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                )


                OutlinedTextField(
                    value = carbohydrates_total_g,

                    onValueChange = {
                        it.let {
                            carbohydrates_total_g = it

                            if (!it.isEmpty()){
                                isErrorСarbohydrates = false
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1,

                    label = {
                        Text(text = "Углеводы",
                            color = Color.Black)
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor =  brilliant_green,
                        unfocusedBorderColor = brilliant_green,
                        cursorColor = brilliant_green
                    ),
                    isError = isErrorСarbohydrates,
                    supportingText = {
                        if (isErrorСarbohydrates) {
                            Text(
                                text = text_error,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }

                )



//                OutlinedTextField(
//                    value = fiber_g,
//
//                    onValueChange = {
//                        it.let {
//                            fiber_g = it
//                        }
//                    },
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                    maxLines = 1,
//
//                    label = {
//                        Text(text = "Волокно")
//                    }
//                )

//                OutlinedTextField(
//                    value = cholesterolMg,
//
//                    onValueChange = {
//                        it.let {
//                            cholesterolMg = it
//                        }
//                    },
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                    maxLines = 1,
//
//                    label = {
//                        Text(text = "Холестерин")
//                    }
//                )




//                OutlinedTextField(
//                    value = fat_saturated_g,
//
//                    onValueChange = {
//                        it.let {
//                            fat_saturated_g = it
//                        }
//                    },
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                    maxLines = 1,
//
//                    label = {
//                        Text(text = "Насыщенны жиры")
//                    }
//                )


//                OutlinedTextField(
//                    value = sodium_mg,
//
//                    onValueChange = {
//                        it.let {
//                            sodium_mg = it
//                        }
//                    },
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                    maxLines = 1,
//
//                    label = {
//                        Text(text = "Натрий")
//                    }
//                )



//                OutlinedTextField(
//                    value = potassium_mg,
//
//                    onValueChange = {
//                        it.let {
//                            potassium_mg = it
//                        }
//                    },
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                    maxLines = 1,
//
//                    label = {
//                        Text(text = "Калий")
//                    }
//                )


//                OutlinedTextField(
//                    value = sugar_g,
//
//                    onValueChange = {
//                        it.let {
//                            sugar_g = it
//                        }
//                    },
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                    maxLines = 1,
//
//                    label = {
//                        Text(text = "Сахар")
//                    }
//                )





                Button(onClick = {


                    val foodModelAdd = FoodModelAdd(
                        name = name.lowercase().trim(),
                        calories = calories.toIntOrNull(),
                        fatTotalG = fat_total_g.toDoubleOrNull(),
                        fatSaturatedG = fat_saturated_g.toDoubleOrNull(),
                        proteinG = protein_g.toDoubleOrNull(),
                        sodiumMg = sodium_mg.toDoubleOrNull(),
                        potassiumMg = potassium_mg.toDoubleOrNull(),
                        cholesterolMg = cholesterolMg.toDoubleOrNull(),
                        carbohydratesTotalG = carbohydrates_total_g.toDoubleOrNull(),
                        fiberG = fiber_g.toDoubleOrNull(),
                        sugarG = sugar_g.toDoubleOrNull()

                    )

                    if (foodModelAdd.name?.isEmpty()!! ){
                        isErrorName = true
                    }else if(foodModelAdd.calories == null){
                        isErrorCalories = true
                    }else if (foodModelAdd.proteinG == null){
                        isErrorProtein = true
                    }else if(foodModelAdd.fatTotalG == null){
                        isErrorFatTotal = true
                    }else if(foodModelAdd.carbohydratesTotalG == null){
                        isErrorСarbohydrates = true
                    }else{
                        viewModel.databaseEntryFoodsUserNew(foodModelAdd)

                        if (management.value?.addingOriginalList == true){
                            viewModel.databaseEntryFoods(foodModelAdd)
                        }
                        Toast.makeText(context,"Блюдо добавлено в базу",Toast.LENGTH_LONG).show()
                        viewModel.loadListFoodForFilter()
                        dialogState.value = false

                    }
                }, colors = ButtonDefaults.buttonColors(brilliant_green)
                    ) {
                    Text(text = "Записать в базу")
                }


                Button(onClick = {

                        dialogState.value = false


                }, colors = ButtonDefaults.buttonColors(brilliant_green)
                ) {
                    Text(text = "закрыть")
                }


            }
        }
    }
}