package cal.calor.caloriecounter.WaterScreeen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cal.calor.caloriecounter.ui.theme.BackgroundGray
import cal.calor.caloriecounter.ui.theme.Сoral
import com.example.caloriecounter.cardFood

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WaterScreen(onItem: () -> Unit, viewModel: WaterViewModel) {

    var waters by remember { mutableStateOf(0) }
    var drainedWater by remember { mutableStateOf(0) }
    val waterList = viewModel.waterListDAO.observeAsState(listOf())
    val list = waterList.value.asReversed().groupBy { it.dataCurrent }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxSize()
            .background(BackgroundGray)
    ) {

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
                            .background(color = Color.Cyan ),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h6,
                    )
                }
                items(listWater, key = { it.waterId }) { waterModel ->
                    cardWater(waterModel = waterModel,  viewModel = viewModel)
                }

                item {
                    Row (){
                        val totalWater = viewModel.getWater(listWater)
                        waters = totalWater
                        Text(
                            modifier = Modifier
                                .shadow(
                                    elevation = 4.dp,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .background(color = Color.Cyan),
                            text = "Выпито воды =  ${waters}",
                            textAlign = TextAlign.Right
                        )

                        Spacer(Modifier.weight(1f))


                        val totalWater_2 = viewModel.getDrainedWater(listWater)
                        drainedWater = totalWater_2
                        Text(
                            modifier = Modifier
                                .shadow(
                                    elevation = 4.dp,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .background(color = Color.Cyan),
                            text = "Слито воды =  ${drainedWater}",
                            textAlign = TextAlign.Right
                        )
                    }
//                    Box(
//                        modifier = Modifier.fillMaxWidth(),
//                        contentAlignment = Alignment.BottomEnd
//                    ) {


//                    }

//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(top = 5.dp),
//                        contentAlignment = Alignment.BottomEnd
//                    ) {


//                    }
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