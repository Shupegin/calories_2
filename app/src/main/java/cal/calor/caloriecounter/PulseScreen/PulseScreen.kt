package cal.calor.caloriecounter.PulseScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import cal.calor.caloriecounter.WaterScreeen.cardWater
import cal.calor.caloriecounter.WeightScreen.cardViewWeight
import cal.calor.caloriecounter.ui.theme.BackgroundGray
import cal.calor.caloriecounter.ui.theme.ColorRed
import cal.calor.caloriecounter.ui.theme.Green
import cal.calor.caloriecounter.ui.theme.СolorWater

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PulseScreen(pulseViewModel: PulseViewModel,owner: LifecycleOwner, onItem: () -> Unit){
    var datavalue by remember { mutableStateOf("") }

    datavalue = pulseViewModel.getCurrentDate()

    val weightList = pulseViewModel.pulseListDAO.observeAsState(listOf())
    val list = weightList.value.asReversed().groupBy { it.data }





    Box(modifier = Modifier
        .fillMaxSize()
        .background(BackgroundGray),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 55.dp),
            ) {
                list?.forEach { (dataCurrent, listWater) ->
                    stickyHeader {
                        Text(
                            text = dataCurrent.toString(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = ColorRed),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h6,

                            )
                    }

                    items(listWater, key = { it.id!! },) { pulseList ->
                        Spacer(modifier = Modifier.padding(top = 5.dp))
                        cardPulse(pulseModel = pulseList, pulseViewModel = pulseViewModel)
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp),
            contentAlignment = Alignment.BottomCenter

        ) {
            FloatingActionButton(onClick = {
                onItem.invoke()
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    }
}