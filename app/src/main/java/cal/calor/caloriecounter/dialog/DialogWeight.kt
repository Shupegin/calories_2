package cal.calor.caloriecounter.dialog

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import cal.calor.caloriecounter.ui.theme.Сoral

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DialogWeight(dialogState: MutableState<Boolean>, weightViewModel: WeightViewModel, owner : LifecycleOwner){

    var weight by remember {
        mutableStateOf("")
    }

    var text by remember { mutableStateOf(TextFieldValue("Text")) }
    val keyboardController = LocalSoftwareKeyboardController.current

    val focusManager = LocalFocusManager.current

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
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Добавьте ваш вес",
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center )

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
                                style = androidx.compose.ui.text.TextStyle(
                                    color = Color.Black
                                )
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
                            weight = weight
                            )

                        weightViewModel.addDao(wieght)
                        dialogState.value = false



                    }) {
                        Text(text = "Ok")
                    }
                    Button(onClick = {
                        dialogState.value = false



                    }) {
                        Text(text = "Закрыть ")
                    }
                }

            }

    }
           
}