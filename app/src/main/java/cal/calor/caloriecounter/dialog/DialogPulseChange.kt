package cal.calor.caloriecounter.dialog

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LifecycleOwner
import cal.calor.caloriecounter.PulseScreen.PulseViewModel

import cal.calor.caloriecounter.WaterScreeen.WaterViewModel
import cal.calor.caloriecounter.pojo.WaterModel_2
import cal.calor.caloriecounter.pojo.pulse.PulsePojo
import cal.calor.caloriecounter.ui.theme.ColorRed
import cal.calor.caloriecounter.ui.theme.sf_ui_display_semiboldFontFamily
import cal.calor.caloriecounter.ui.theme.sfproDisplayThinFontFamily

import cal.calor.caloriecounter.ui.theme.СolorWater
import cal.calor.caloriecounter.ui.theme.Сoral
import com.exyte.animatednavbar.utils.noRippleClickable
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun pulseDialogChange(
    context: Context,
    pulseDialogState: MutableState<Boolean>,
    viewModel: PulseViewModel,
    owner: LifecycleOwner
) {

    var datavalue by remember { mutableStateOf("") }
    var pulse by remember { mutableStateOf("") }
    var pressureTop by remember { mutableStateOf("") }
    var pressureLower by remember { mutableStateOf("") }
    var timeValue by remember { mutableStateOf("") }
    var id by remember { mutableStateOf(0) }




    val dateDialogState  = rememberMaterialDialogState()

    viewModel.changeData.observe(owner){
        it.id?.let {
            id = it
        }
        it.data?.let {
            datavalue = it
        }
        it.time?.let {
            timeValue = it
        }
        it.pulse?.let {
            pulse = it.toString()
        }
        it.pressureTop?.let {
            pressureTop = it.toString()
        }

        it.pressureLower?.let {
            pressureLower = it.toString()
        }
    }


    val keyboardController = LocalSoftwareKeyboardController.current

    Dialog(
        onDismissRequest = {
            pulseDialogState.value = false
        },

        ) {


        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, color = ColorRed)
        ) {
            Column(
                modifier = Modifier.padding(top = 20.dp, bottom = 20.dp,start = 20.dp,end =20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(text = "Изменение давления/пульса:",
                    fontFamily = sf_ui_display_semiboldFontFamily,
                    color = Color.Black
                )

                Text(text = "Время: $timeValue ",
                    fontFamily = sfproDisplayThinFontFamily,
                    color = Color.Black
                )
                OutlinedTextField(
                    value = datavalue,
                    modifier = Modifier.clickable(onClick = {dateDialogState.show()}),
                    enabled = false,
                    onValueChange = {},
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),

                    label = {
                        Text(
                            text = "Дата:",
                            fontFamily = sfproDisplayThinFontFamily,
                            color = Color.Black
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor =  ColorRed,
                        unfocusedBorderColor = ColorRed,
                        cursorColor = ColorRed,
                        disabledTextColor = Color.Black,
                        disabledBorderColor = ColorRed,
                        disabledPlaceholderColor = ColorRed,
                        disabledLabelColor = ColorRed,
                    ),
                )

                Text(text = "Показатели давления",fontFamily = sfproDisplayThinFontFamily,
                    color = Color.Black)

                OutlinedTextField(
                    value = pressureTop,
                    onValueChange = {
                        it.let {
                            pressureTop = it
                        }
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    label = {
                        Text(
                            text = "Верхнее?",
                            fontFamily = sfproDisplayThinFontFamily,
                            color = Color.Black
                        )
                    },

                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor =  ColorRed,
                        unfocusedBorderColor = ColorRed,
                        cursorColor = ColorRed
                    ),


                    )


                OutlinedTextField(
                    value = pressureLower,
                    onValueChange = {
                        it.let {
                            pressureLower = it

                        }
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    label = {
                        Text(
                            text = "Нижнее?",
                            fontFamily = sfproDisplayThinFontFamily,
                            color = Color.Black
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor =  ColorRed,
                        unfocusedBorderColor = ColorRed,
                        cursorColor = ColorRed
                    ),

                    )

                OutlinedTextField(
                    value = pulse,
                    onValueChange = {
                        it.let { pulse = it }
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),


                    keyboardActions = KeyboardActions(onDone = {
                            keyboardController?.hide()
                            // do something here
                        }),

                    label = {
                        Text(
                            text = "Показатели пульса?",
                            fontFamily = sfproDisplayThinFontFamily,
                            color = Color.Black
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor =  ColorRed,
                        unfocusedBorderColor = ColorRed,
                        cursorColor = ColorRed
                    ),


                )

                Row() {
                    Button(onClick = { pulseDialogState.value = false }) {
                        Text(text = "Закрыть ",
                            fontFamily = sf_ui_display_semiboldFontFamily)
                    }
                    Spacer(modifier = Modifier.padding(end = 20.dp))

                    Button(onClick = {

                        val addPulseDataBase = PulsePojo(
                            id = id,
                            data = datavalue,
                            pulse = pulse.toIntOrNull() ?: 0,
                            pressureTop = pressureTop.toIntOrNull() ?: 0,
                            pressureLower = pressureLower.toIntOrNull() ?: 0,
                            time = timeValue
                        )
                        viewModel.updateDataBase(addPulseDataBase)

                        pulseDialogState.value = false


                    }) {
                        Text(text = "Ок",
                            fontFamily = sf_ui_display_semiboldFontFamily)
                    }
                }
            }
        }
    }

}



