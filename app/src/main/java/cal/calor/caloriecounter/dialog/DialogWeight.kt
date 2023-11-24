package cal.calor.caloriecounter.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LifecycleOwner
import cal.calor.caloriecounter.HistoryScreen.HistoryViewModel
import cal.calor.caloriecounter.ProfileScreen.ProfileViewModel
import cal.calor.caloriecounter.pojo.FoodModel
import cal.calor.caloriecounter.pojo.weight.WeightPogo

@Composable
fun DialogWeight(dialogState: MutableState<Boolean>, profileViewModel: ProfileViewModel, owner : LifecycleOwner){

    var weight by remember {
        mutableStateOf("")
    }

    Dialog(
        onDismissRequest = {
            dialogState.value = false
        },

        ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Card(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Добавьте ваш вес", fontSize = 30.sp, textAlign = TextAlign.Center )

                    OutlinedTextField(
                        value = weight,
                        onValueChange = { it.let {
                            weight = it
                        }},
                        maxLines = 1,
                        label = {
                            Text(
                                text = "Ваш вес?",
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

                    Button(onClick = {

                        val wieght = WeightPogo(
                            data = profileViewModel.getCurrentDate(),
                            weight = weight
                            )
                        profileViewModel.addDao(wieght)
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
           
}