package cal.calor.caloriecounter.dialog

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LifecycleOwner
import cal.calor.caloriecounter.WeightScreen.WeightViewModel
import cal.calor.caloriecounter.pojo.weight.WeightPogo
import cal.calor.caloriecounter.ui.theme.Green
import cal.calor.caloriecounter.ui.theme.sf_ui_display_semiboldFontFamily
import cal.calor.caloriecounter.ui.theme.sfproDisplayThinFontFamily
import cal.calor.caloriecounter.ui.theme.Сoral

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DialogWeight(dialogState: MutableState<Boolean>, weightViewModel: WeightViewModel, owner : LifecycleOwner){

    var weight by remember {
        mutableStateOf("")
    }
    var hip by remember {
        mutableStateOf("")
    }
    var breast by remember {
        mutableStateOf("")
    }

    var waist by remember {
        mutableStateOf("")
    }

    var biceps by remember {
        mutableStateOf("")
    }

    var belly by remember {
        mutableStateOf("")
    }

    var buttocks by remember {
        mutableStateOf("")
    }


    Dialog(
        onDismissRequest = {
            dialogState.value = false
        },

        ) {

            Card(modifier = Modifier
                .fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(1.dp, color = Green)
            ) {
                Column(modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Добавьте вашы данные",
                        textAlign = TextAlign.Center,
                        fontFamily = sfproDisplayThinFontFamily,
                        color = Color.Black
                    )

                    OutlinedTextField(
                        value = weight,
                        onValueChange = { it.let {
                            weight = it
                        }},
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = {
                            Text(
                                text = "Ваш вес?",
                                fontFamily = sfproDisplayThinFontFamily,
                                color = Color.Black
                            )

                        }, colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor =  Green,
                            unfocusedBorderColor = Green,
                            cursorColor = Green
                        )

                    )


                    OutlinedTextField(
                        value = breast,
                        onValueChange = { it.let {
                            breast = it
                        }},
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = {
                            Text(
                                text = "Грудь?(cм)",
                                fontFamily = sfproDisplayThinFontFamily,
                                color = Color.Black
                            )

                        }, colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor =  Green,
                            unfocusedBorderColor = Green,
                            cursorColor = Green
                        )

                    )

                    OutlinedTextField(
                        value = waist,
                        onValueChange = { it.let {
                            waist = it
                        }},
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = {
                            Text(
                                text = "Талия?(cм)",
                                fontFamily = sfproDisplayThinFontFamily,
                                color = Color.Black
                            )

                        }, colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor =  Green,
                            unfocusedBorderColor = Green,
                            cursorColor = Green
                        )

                    )

                    OutlinedTextField(
                        value = biceps,
                        onValueChange = { it.let {
                            biceps = it
                        }},
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = {
                            Text(
                                text = "Бицепс?(cм)",
                                fontFamily = sfproDisplayThinFontFamily,
                                color = Color.Black
                            )

                        }, colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor =  Green,
                            unfocusedBorderColor = Green,
                            cursorColor = Green
                        )

                    )

                    OutlinedTextField(
                        value = belly,
                        onValueChange = { it.let {
                            belly = it
                        }},
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = {
                            Text(
                                text = "Живот?(cм)",
                                fontFamily = sfproDisplayThinFontFamily,
                                color = Color.Black
                            )

                        }, colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor =  Green,
                            unfocusedBorderColor = Green,
                            cursorColor = Green
                        )

                    )

                    OutlinedTextField(
                        value = hip,
                        onValueChange = { it.let {
                            hip = it
                        }},
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = {
                            Text(
                                text = "Бедро?(cм)",
                                fontFamily = sfproDisplayThinFontFamily,
                                color = Color.Black
                            )

                        }, colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor =  Green,
                            unfocusedBorderColor = Green,
                            cursorColor = Green
                        )

                    )

                    OutlinedTextField(
                        value = buttocks,
                        onValueChange = { it.let {
                            buttocks = it
                        }},
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = {
                            Text(
                                text = "Ягодицы?(cм)",
                                fontFamily = sfproDisplayThinFontFamily,
                                color = Color.Black
                            )

                        }, colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor =  Green,
                            unfocusedBorderColor = Green,
                            cursorColor = Green
                        )

                    )



                    Button(onClick = {

                        val wieght = WeightPogo(
                            data = weightViewModel.getCurrentDate(),
                            weight = weight,
                            breast = breast,
                            waist = waist,
                            biceps = biceps,
                            belly = belly,
                            hip = hip,
                            buttocks = buttocks
                        )

                        weightViewModel.addDao(wieght)
                        dialogState.value = false



                    }) {
                        Text(text = "Ok",
                            fontFamily = sf_ui_display_semiboldFontFamily
                        )
                    }
                    Button(onClick = {
                        dialogState.value = false
                    }) {
                        Text(text = "Закрыть ",
                            fontFamily = sf_ui_display_semiboldFontFamily)
                    }
                }

            }

    }
           
}