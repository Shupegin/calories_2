package cal.calor.caloriecounter.dialog


import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import cal.calor.caloriecounter.MainViewModel
import cal.calor.caloriecounter.pojo.FoodModel



@Composable
fun dialog(dialogState: MutableState<Boolean>,
           viewModel: MainViewModel,
           owner: LifecycleOwner
){
    var userFood by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var numberOfGrams by remember { mutableStateOf("") }
    var numberOfCalories by remember { mutableStateOf("") }
    viewModel.calories.observe(owner, Observer {
        numberOfCalories = it.toString()
    })


        Dialog(
            onDismissRequest = {
                dialogState.value = false
            },

        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Card(modifier = Modifier
                    .fillMaxWidth()){
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        Text(text = "Дата: ${viewModel.getCurrentDate()} ")

                        OutlinedTextField(
                            value = userFood,
                            onValueChange = { it.let {
                                userFood = it
                            }},
                            label = {
                                Text(
                                    text = "что ел?",
                                    style = androidx.compose.ui.text.TextStyle(
                                        color = Color.Black
                                    )
                                )
                            }, colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Red,
                                unfocusedBorderColor = Color.Green,
                                cursorColor = Color.Red
                            )
                        )

                        OutlinedTextField(
                            value = numberOfGrams,
                            onValueChange = { it.let {
                                numberOfGrams  = it
                            } },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            label = {
                                Text(
                                    text = "колличество грамм?",
                                    style = androidx.compose.ui.text.TextStyle(
                                        color = Color.Black
                                    )
                                )
                            }, colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Red,
                                unfocusedBorderColor = Color.Green,
                                cursorColor = Color.Red
                            )
                        )

                        OutlinedTextField(
                            value = numberOfCalories,
                            onValueChange = {it.let {
                                numberOfCalories = it
                            } },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            label = {
                                Text(
                                    text = "каллории?",
                                    style = androidx.compose.ui.text.TextStyle(
                                        color = Color.Black
                                    )
                                )
                            }, colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = Color.Red,
                                unfocusedBorderColor = Color.Green,
                                cursorColor = Color.Red

                            )
                        )

                        Row() {
                            Button(onClick = { dialogState.value = false}) {
                                Text(text = "Закрыть ")
                            }
                            Spacer(modifier = Modifier.padding(end = 20.dp))
                            Button(onClick = {

                                val _category = viewModel.splitName(userFood).lowercase().trim()

                                val foodModel = FoodModel(
                                    category= _category,
                                    food = userFood.lowercase().trim(),
                                    calories = numberOfCalories.toIntOrNull() ?: 0,
                                    gramm = numberOfGrams.toIntOrNull() ?: 0
                                )
                                viewModel.addInfoFoodBtn(foodModel)
                                viewModel.loadFirebaseFood(foodModel)

                                dialogState.value = false

                            }) {
                                Text(text = "Ок")
                            }
                        }

                        Button(onClick = {
                            val _category = viewModel.splitName(userFood).lowercase().trim()
                            val _userfood =  userFood.lowercase().trim()

                            val foodModel = FoodModel(
                                category= _category,
                                food = _userfood,
                                calories = numberOfCalories.toIntOrNull() ?: 0,
                                gramm = numberOfGrams.toIntOrNull() ?: 0
                            )
                            viewModel.loadFirebaseFood(foodModel)


                        }) {
                            Text(text = "Обновить")
                        }
                    }
                }

            }

        }
}
