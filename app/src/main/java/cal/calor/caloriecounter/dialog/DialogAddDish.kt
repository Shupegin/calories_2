package cal.calor.caloriecounter.dialog


import android.annotation.SuppressLint
import android.content.Context
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
import cal.calor.caloriecounter.pojo.FoodModel
import cal.calor.caloriecounter.pojo.FoodModelAdd
import cal.calor.caloriecounter.ui.theme.sf_ui_display_semiboldFontFamily
import cal.calor.caloriecounter.ui.theme.sfproDisplayThinFontFamily
import cal.calor.caloriecounter.ui.theme.Сoral
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@SuppressLint("SuspiciousIndentation")
@Composable
fun dialogAddDish(dialogState: MutableState<Boolean>,
           viewModel: MainViewModel,
           owner: LifecycleOwner,
           сontext : Context
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

    Dialog(
        onDismissRequest = {
            dialogState.value = false
        },) {
        Card(modifier = Modifier
            .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, color = Сoral)
        ){
            Column(
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                androidx.compose.material3.OutlinedTextField(
                    value = name,

                    onValueChange = {
                        it.let {
                            name = it
                        }
                    },
                    maxLines = 1,

                    label = {
                        androidx.compose.material3.Text(text = "Название")
                    }
                )

                androidx.compose.material3.OutlinedTextField(
                    value = calories,

                    onValueChange = {
                        it.let {
                            calories = it
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1,

                    label = {
                        androidx.compose.material3.Text(text = "Калории(Только целые числа)")
                    }
                )


                androidx.compose.material3.OutlinedTextField(
                    value = protein_g,

                    onValueChange = {
                        it.let {
                            protein_g = it
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1,

                    label = {
                        androidx.compose.material3.Text(text = "Белок")
                    }
                )


                androidx.compose.material3.OutlinedTextField(
                    value = carbohydrates_total_g,

                    onValueChange = {
                        it.let {
                            carbohydrates_total_g = it
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1,

                    label = {
                        androidx.compose.material3.Text(text = "Углеводы")
                    }
                )

                androidx.compose.material3.OutlinedTextField(
                    value = fat_total_g,

                    onValueChange = {
                        it.let {
                            fat_total_g = it
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1,

                    label = {
                        androidx.compose.material3.Text(text = "Жиры")
                    }
                )

                androidx.compose.material3.OutlinedTextField(
                    value = cholesterolMg,

                    onValueChange = {
                        it.let {
                            cholesterolMg = it
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1,

                    label = {
                        androidx.compose.material3.Text(text = "Холестерин")
                    }
                )

                androidx.compose.material3.OutlinedTextField(
                    value = fiber_g,

                    onValueChange = {
                        it.let {
                            fiber_g = it
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1,

                    label = {
                        androidx.compose.material3.Text(text = "Волокно")
                    }
                )


                androidx.compose.material3.OutlinedTextField(
                    value = fat_saturated_g,

                    onValueChange = {
                        it.let {
                            fat_saturated_g = it
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1,

                    label = {
                        androidx.compose.material3.Text(text = "Насыщенны жиры")
                    }
                )


                androidx.compose.material3.OutlinedTextField(
                    value = sodium_mg,

                    onValueChange = {
                        it.let {
                            sodium_mg = it
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1,

                    label = {
                        androidx.compose.material3.Text(text = "Натрий")
                    }
                )



                androidx.compose.material3.OutlinedTextField(
                    value = potassium_mg,

                    onValueChange = {
                        it.let {
                            potassium_mg = it
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1,

                    label = {
                        androidx.compose.material3.Text(text = "Калий")
                    }
                )


                androidx.compose.material3.OutlinedTextField(
                    value = sugar_g,

                    onValueChange = {
                        it.let {
                            sugar_g = it
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1,

                    label = {
                        androidx.compose.material3.Text(text = "Сахар")
                    }
                )





                androidx.compose.material3.Button(onClick = {


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

                    if (foodModelAdd.name?.isEmpty()!!){
                        Toast.makeText(сontext,"Введите название", Toast.LENGTH_LONG).show()
                    }else{
                        viewModel.databaseEntry(foodModelAdd)
                    }
                }) {
                    androidx.compose.material3.Text(text = "Записать в базу")
                }


            }
        }


    }


}
