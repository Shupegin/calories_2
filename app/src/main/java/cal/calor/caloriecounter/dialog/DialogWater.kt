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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.LifecycleOwner

import cal.calor.caloriecounter.WaterScreeen.WaterViewModel
import cal.calor.caloriecounter.pojo.WaterModel


@Composable
fun waterDialog(
    waterDialogState: MutableState<Boolean>,
    viewModel: WaterViewModel,
    owner: LifecycleOwner
) {

    var datavalue by remember { mutableStateOf("") }
    var amount_water by remember { mutableStateOf("") }
    var quantity_drained_water by remember { mutableStateOf("") }

    datavalue = viewModel.getCurrentDate()

    Dialog(
        onDismissRequest = {
            waterDialogState.value = false
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
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {


                    OutlinedTextField(
                        value = datavalue,
                        onValueChange = {
                            it.let {
                                datavalue = it
                            }
                        },
                        maxLines = 1,
                        label = {
                            Text(
                                text = "Дата:",
                                style = TextStyle(
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
                        value = amount_water,
                        onValueChange = {
                            it.let {
                                amount_water = it
                            }
                        },
                        maxLines = 1,
                        label = {
                            Text(
                                text = "Количество выпитой воды?",
                                style = TextStyle(
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
                        value = quantity_drained_water,
                        onValueChange = {
                            it.let {
                                quantity_drained_water = it
                            }
                        },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = {
                            Text(
                                text = "Количество слитой воды?",
                                style = TextStyle(
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
                        Button(onClick = { waterDialogState.value = false }) {
                            Text(text = "Закрыть ")
                        }
                        Spacer(modifier = Modifier.padding(end = 20.dp))

                        Button(onClick = {


                            val waterModel = WaterModel(
                                dataCurrent = viewModel.getCurrentDate(),
                                water_is_drunk = amount_water,
                                drained_of_water = quantity_drained_water
                            )

                            viewModel.addWaterDataBase(waterModel)
                            waterDialogState.value = false
                        }) {
                            Text(text = "Ок")
                        }
                    }
                }
            }
        }
    }
}