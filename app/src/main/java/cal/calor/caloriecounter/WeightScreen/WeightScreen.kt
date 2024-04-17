package cal.calor.caloriecounter.WeightScreen

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import cal.calor.caloriecounter.dialog.DialogWeight
import cal.calor.caloriecounter.ui.theme.BackgroundGray
import cal.calor.caloriecounter.ui.theme.Green
import cal.calor.caloriecounter.ui.theme.sf_ui_display_semiboldFontFamily
import cal.calor.caloriecounter.ui.theme.Сoral


@Composable
fun WeightScreen(viewModelWeight: WeightViewModel,
                 paddingValues: PaddingValues,
                 owner: LifecycleOwner,
                 context: Context,
                 navController: NavController
){
    var datavalue by remember { mutableStateOf("") }

    val dialogStateWeight = remember {
        mutableStateOf(false)
    }
    val weightList = viewModelWeight.wieghtListDAO.observeAsState(listOf())

    val list = weightList.value.asReversed()

    if (dialogStateWeight.value){
        DialogWeight(
            dialogState = dialogStateWeight,
            weightViewModel= viewModelWeight,
            owner = owner,
        )


    }

    datavalue = viewModelWeight.getCurrentDate()

    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Текущая дата = $datavalue",
                modifier = Modifier.padding(top = 20.dp),
                color = Green,
                fontSize = 20.sp,
                fontFamily = sf_ui_display_semiboldFontFamily
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))


            LazyColumn(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 55.dp),
            ){
                items(list, key= { it.id!! },){ weightList ->
                    cardViewWeight(weightPogo = weightList, weightViewModel =viewModelWeight )
                }
            }

        }

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 70.dp),
            contentAlignment = Alignment.BottomCenter

        ){
            Button(onClick = {dialogStateWeight.value = true

            }) {
                Text(text = "Взвеситься")
            }
        }
    }
}
