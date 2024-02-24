package cal.calor.caloriecounter.WaterScreeen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

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
import cal.calor.caloriecounter.ui.theme.СolorWater
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
            .background(BackgroundGray)
    ) {


        if (list.size == 0){

            Box(modifier = Modifier
                .fillMaxSize(),
                contentAlignment = Alignment.Center

            ) {
                Column(modifier = Modifier.background(BackgroundGray)) {
                    Text(text = "Здесь пока ничего нет...", color = Color.White)
                    Text(text = "Добавьте выпитую воду ", color = Color.White)
                }

            }

        }else{
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
                                .background(color = СolorWater),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.h6,

                            )
                    }
                    items(listWater, key = { it.waterId }) { waterModel ->
                        cardWater(waterModel = waterModel,  viewModel = viewModel)
                    }

                    item {
                        Text(text = "Итого:",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Color.White)
                        Row (){
                            val totalWater = viewModel.getWater(listWater)
                            waters = totalWater
                            Spacer(modifier = Modifier.padding(start = 5.dp))
                            Text(
                                modifier = Modifier
                                    .shadow(
                                        elevation = 4.dp,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .background(color = СolorWater)
                                    .padding(5.dp),
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
                                    .background(color = СolorWater)
                                    .padding(5.dp),

                                text = "Слито воды =  ${drainedWater}",
                                textAlign = TextAlign.Right

                            )
                            Spacer(modifier = Modifier.padding(end = 5.dp))

                        }
                        Spacer(modifier = Modifier.padding(top = 5.dp))
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