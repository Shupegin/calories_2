package cal.calor.caloriecounter.InfoScreen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.DropdownMenu
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.MenuDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.ui.window.Dialog
import cal.calor.caloriecounter.HistoryScreen.HistoryViewModel
import cal.calor.caloriecounter.RegistrationScreen.RegistrationViewModel
import cal.calor.caloriecounter.ui.theme.Black500
import cal.calor.caloriecounter.ui.theme.ColorRed
import cal.calor.caloriecounter.ui.theme.brilliant_green
import cal.calor.caloriecounter.ui.theme.rust_brown
import cal.calor.caloriecounter.ui.theme.sfproDisplayThinFontFamily
import cal.calor.caloriecounter.ui.theme.tickle_me
import retrofit2.http.Body
import java.math.RoundingMode
import java.text.DecimalFormat

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InfoScreen(infoScreenState: MutableState<Boolean>, viewModel: HistoryViewModel, сontext : Context){
    val SharedPreferences = сontext.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val _agePref = SharedPreferences.getString("age", null)
    val _heightPref = SharedPreferences.getString("height", null)
    val _weightPref = SharedPreferences.getString("weight", null)
    val _acivity = SharedPreferences.getString("activity", null)
    val _calories = SharedPreferences.getString("calories", null)
    val _bodyMassIndex = SharedPreferences.getString("bodyMassIndex", null)
    val _man = SharedPreferences.getBoolean("man", true)
    val _woman = SharedPreferences.getBoolean("woman", false)

    var isCheckedMan by rememberSaveable { mutableStateOf(_man) }
    var isCheckedWoman by rememberSaveable { mutableStateOf(_woman) }
    var age  by rememberSaveable { mutableStateOf("") }
    var daily_norm by rememberSaveable { mutableStateOf("") }
    var weight by rememberSaveable { mutableStateOf("") }
    var height by rememberSaveable { mutableStateOf("") }
    var body_mass_index by rememberSaveable { mutableStateOf("") }
    var _сolor by remember { mutableStateOf(rust_brown) }


    if (isCheckedWoman){
        _сolor = tickle_me
    }
    if (isCheckedMan){
        _сolor = rust_brown
    }

    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    var gender by rememberSaveable {
        mutableStateOf("")
    }

    var visual_activity by rememberSaveable { mutableStateOf(1.2) }


    fun gender_activity(_gender : String){
        when(_gender){
            "Сидячий образ жизни" -> visual_activity = 1.2
            "минимальная активность (простые тренировки один-три раза в неделю)" -> visual_activity = 1.375
            "средняя активность (активные занятия три-пять раз в неделю)" -> visual_activity = 1.55
            "повышенная активность (спортивные занятия каждый день)" -> visual_activity = 1.725
            "экстра-активность (тяжелый физический труд или изматывающие занятия спортом)" -> visual_activity = 1.9
        }
    }
    fun body_mass_index(){

        try {
            val w = if (weight.isEmpty()) 0.0 else weight.toDouble()
            val h = if (height.isEmpty()) 0.0 else height.toDouble()
            val _h = h/100

            val index_body = (w / (_h * _h))
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.DOWN
            val roundoff = df.format(index_body)
            body_mass_index = roundoff.toString()
        }catch (ex: NumberFormatException){ }



    }

   // для мужчин: 10 х вес (кг) + 6,25 x рост (см) – 5 х возраст (г) + 5 x A;
   // для женщин: 10 x вес (кг) + 6,25 x рост (см) – 5 x возраст (г) – 161 x A.
   //  Формула Миффлина-Сан Жеора 2005г

    fun calories( x : Int): Double{
        val w = if (weight.isEmpty()) 0.0 else weight.toDouble()
        val h = if (height.isEmpty()) 0.0 else height.toDouble()
        val a = if (age.isEmpty()) 0.0 else age.toDouble()

        if (x == 5){
            return (10 * w + 6.25 * h - 5 * a + x)
        }else{
            return (10 * w + 6.25 * h - 5 * a - x)
        }
    }

    fun caloriesMan() {
        try {
            val dailyNorm = calories(5)
            daily_norm = (dailyNorm * visual_activity).toInt().toString()

        } catch (ex: NumberFormatException) {
            daily_norm = "Fuck Off"
        }
    }
    fun caloriesWoman() {
        try {
            val dailyNorm = calories(161)
            daily_norm = (dailyNorm * visual_activity).toInt().toString()
        }catch (ex: NumberFormatException){
            daily_norm = "Fuck Off"
        }
    }

    if (_agePref != null) {
        age = _agePref
    }

    if (_heightPref != null) {
        height = _heightPref
    }
    if (_weightPref != null) {
        weight = _weightPref
    }

    if(_acivity != null ){
        gender = _acivity
    }

    if (_calories != null) {
        daily_norm = _calories
    }

    if (_bodyMassIndex != null) {
        body_mass_index = _bodyMassIndex
    }


    Dialog(onDismissRequest = { infoScreenState.value = false }) {
        Card( // or Surface
            elevation = 8.dp ,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .requiredWidth(LocalConfiguration.current.screenWidthDp.dp * 0.96f)
                .padding(4.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.padding(top = 30.dp))
                    Text(text = "Мужчина")
                    Checkbox(
                        checked = isCheckedMan,
                        onCheckedChange = {
                            isCheckedMan = it
                            caloriesMan()
                            if (it) {
                                isCheckedWoman = false
                            }
                        },
                        colors = CheckboxDefaults.colors(rust_brown)
                    )

                    Text(text = "Девушка")
                    Checkbox(
                        checked = isCheckedWoman,
                        onCheckedChange = {
                            isCheckedWoman = it
                            caloriesWoman()
                            if (it) {
                                isCheckedMan = false
                            }
                        },
                        colors = CheckboxDefaults.colors(tickle_me)
                    )
                }

                OutlinedTextField(
                    value = age,
                    placeholder = { Text("0") },
                    onValueChange = {
                        var sas = it
                        age = sas
                        if(isCheckedMan) {
                            caloriesMan()
                        }
                        if(isCheckedWoman) {
                            caloriesWoman()
                        }
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    label = {
                        androidx.compose.material.Text(
                            text = "Возраст",
                            fontFamily = sfproDisplayThinFontFamily,
                            color = Color.Black
                        )
                    },

                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = _сolor,
                        unfocusedBorderColor = _сolor,
                        cursorColor = _сolor
                    ),
                    )

                OutlinedTextField(
                    value = height,
                    placeholder = { Text("0") },
                    onValueChange = {
                        it.let {
                            height = it
                            body_mass_index()
                            if(isCheckedMan) {
                                caloriesMan()
                            }
                            if(isCheckedWoman) {
                                caloriesWoman()
                            }
                        }
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    label = {
                        androidx.compose.material.Text(
                            text = "Рост(см)",
                            fontFamily = sfproDisplayThinFontFamily,
                            color = Color.Black
                        )
                    },

                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = _сolor,
                        unfocusedBorderColor = _сolor,
                        cursorColor = _сolor
                    ),
                )

                OutlinedTextField(
                    value = weight,
                    placeholder = { Text("0") },
                    onValueChange = {
                        it.let {
                            weight = it
                            body_mass_index()
                            if(isCheckedMan) {
                                caloriesMan()
                            }
                            if(isCheckedWoman) {
                                caloriesWoman()
                            }
                        }
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    label = {
                        androidx.compose.material.Text(
                            text = "вес(кг)",
                            fontFamily = sfproDisplayThinFontFamily,
                            color = Color.Black
                        )
                    },

                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = _сolor,
                        unfocusedBorderColor = _сolor,
                        cursorColor = _сolor
                    ),
                )
                Column {
                    Text("Средняя активность")
                    ExposedDropdownMenuBox(
                        expanded = isExpanded,
                        onExpandedChange = {isExpanded = it}
                    ) {
                        TextField(
                            value = gender,
                            placeholder = { Text(text = "Сидячий образ жизни")},
                            onValueChange ={},
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(backgroundColor = _сolor),

                        )

                        ExposedDropdownMenu(
                            expanded = isExpanded ,
                            onDismissRequest = { isExpanded = false}
                        ) {
                            
                            DropdownMenuItem(text = { Text(text = "Сидячий образ жизни")}, onClick = {
                                gender = "Сидячий образ жизни"

                                gender_activity("Сидячий образ жизни")
                                if (isCheckedMan){
                                    caloriesMan()
                                }
                                if (isCheckedWoman){
                                    caloriesWoman()
                                }
                                isExpanded = false

                            }
                            )

                            DropdownMenuItem(text = { Text(text = "минимальная активность" +
                                    " (простые тренировки один-три раза в неделю)")}, onClick = {
                                gender = "минимальная активность " +
                                        "(простые тренировки один-три раза в неделю)"

                                gender_activity("минимальная активность " +
                                        "(простые тренировки один-три раза в неделю)")
                                if (isCheckedMan){
                                    caloriesMan()
                                }
                                if (isCheckedWoman){
                                    caloriesWoman()
                                }
                                isExpanded = false
                            })


                            DropdownMenuItem(text = { Text(text = "средняя активность (активные занятия три-пять раз в неделю)")}, onClick = {
                                gender = "средняя активность (активные занятия три-пять раз в неделю)"

                                gender_activity("средняя активность (активные занятия три-пять раз в неделю)")


                                if (isCheckedMan){
                                    caloriesMan()
                                }
                                if (isCheckedWoman){
                                    caloriesWoman()
                                }
                                isExpanded = false


                            })

                            DropdownMenuItem(text = { Text(text = "повышенная активность (спортивные занятия каждый день)")}, onClick = {
                                gender = "повышенная активность (спортивные занятия каждый день)"

                                gender_activity("повышенная активность (спортивные занятия каждый день)")


                                if (isCheckedMan){
                                    caloriesMan()
                                }
                                if (isCheckedWoman){
                                    caloriesWoman()
                                }
                                isExpanded = false


                            })


                            DropdownMenuItem(text = { Text(text = "экстра-активность (тяжелый физический труд или изматывающие занятия спортом)")}, onClick = {
                                gender = "экстра-активность (тяжелый физический труд или изматывающие занятия спортом)"
                                gender_activity("экстра-активность (тяжелый физический труд или изматывающие занятия спортом)")

                                if (isCheckedMan){
                                    caloriesMan()
                                }
                                if (isCheckedWoman){
                                    caloriesWoman()
                                }
                                isExpanded = false
                            })

                        }
                    }

                }

                OutlinedTextField(
                    value = daily_norm.toString(),
                    onValueChange = {
                        it.let {
                            daily_norm = it
                        }
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    label = {
                        androidx.compose.material.Text(
                            text = "Рек-дуемая дневная норма(кКал)",
                            fontFamily = sfproDisplayThinFontFamily,
                            color = Color.Black
                        )
                    },

                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = _сolor,
                        unfocusedBorderColor = _сolor,
                        cursorColor = _сolor
                    ),
                )
                Box(modifier = Modifier.fillMaxWidth().padding(top = 10.dp,start = 25.dp, end = 25.dp)){
                    Column (horizontalAlignment = Alignment.CenterHorizontally){
                        Text(text = "ИМТ = $body_mass_index")
                        Text(text = "Расшифровка ИМТ: ")
                        Text(text = "16 и менее - выраженный дефицит массы тела",
                            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
                        Text(text = "16-18,5 - недостаточная (дефицит) масса тела ",
                            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
                        Text(text = "18,5-25 - норма",
                            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
                        Text(text = "25-30 - избыточная масса тела (предожирение)",
                            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
                        Text(text = "30-35 - ожирение первой степени",
                            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
                        Text(text = "35-40 - ожирение второй степени",
                            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
                        Text(text = "40 и более - ожирение третьей степени (морбидное)",
                            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
                        Spacer(modifier = Modifier.padding(top = 10.dp))
                        Text(text = " * Для подсчета калорий использовалась формула Миффлина-Сан Жеора 2005г",
                            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
                    }

                }

                
                Button(onClick = {
                    viewModel.saveSharedPreference(man = isCheckedMan, woman = isCheckedWoman,
                        age = age, height = height, weight = weight,
                        activity = gender, calories =  daily_norm, bodyMassIndex = body_mass_index)

                    viewModel.caloriesDay(daily_norm)


                    infoScreenState.value = false
                }, colors = androidx.compose.material3.ButtonDefaults.buttonColors(_сolor)
                ) {
                    Text(text = "Сохранить")
                }

                Button(onClick = {
                    infoScreenState.value = false
                }, colors = androidx.compose.material3.ButtonDefaults.buttonColors(_сolor)
                ) {
                    Text(text = "Закрыть")
                }

            }
        }
    }
}