package cal.calor.caloriecounter.HistoryScreen

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import cal.calor.caloriecounter.MainViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropMenu(viewModel: MainViewModel){
    val options = listOf("День", "Неделя", "Две недели","Месяц")
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedOptionText by rememberSaveable { mutableStateOf(options[0]) }
    val foodList = viewModel.foodListDAO.observeAsState(listOf())
    viewModel.sendSelectedOptionText(selectedOptionText, listFood = foodList.value)

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            label = { Text("История") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOptionText = selectionOption
                        viewModel.sendSelectedOptionText(selectedOptionText, listFood = foodList.value)
                        expanded = false
                    }
                ){
                    Text(text = selectionOption)
                }
            }
        }
    }
}
