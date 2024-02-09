package cal.calor.caloriecounter.HistoryScreen

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import cal.calor.caloriecounter.MainViewModel
import cal.calor.caloriecounter.WaterScreeen.WaterViewModel
import cal.calor.caloriecounter.ui.theme.Gray500
import cal.calor.caloriecounter.ui.theme.СolorWater


@Composable
fun VerticalProgressBarWater(viewModel: WaterViewModel,
                        owner: LifecycleOwner) {

    var progress by remember { mutableStateOf(0f) }

    var listWaterDB =  viewModel.waterListDAO.observeAsState()

    listWaterDB.value?.let { viewModel.sumWater(it) }

    var listUsers = viewModel.sumWater.observeAsState(0)

                val caloriesPerDay = 2500
                val calories  = listUsers.value.toFloat() / (caloriesPerDay)

                progress = calories

            val  size by   animateFloatAsState(targetValue = progress,
                tween(durationMillis = 1000,
                    delayMillis = 100,
                    easing = LinearOutSlowInEasing), label = ""
            )

            Column(
                modifier = Modifier
                    .padding(top = 10.dp, start = 30.dp, end = 30.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally


            ) {

                Row(
                    modifier = Modifier
                        .height(150.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(40.dp)
                            .clip(RoundedCornerShape(9.dp))
                            .background(Gray500),
                        contentAlignment = Alignment.BottomEnd
                    ) {


                        Box(
                            modifier = Modifier
                                .width(40.dp)
                                .fillMaxHeight(size)
                                .clip(RoundedCornerShape(9.dp))
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            Color(0xff6dd5ed),
                                            Color(0xff2193b0)

                                        )
                                    )
                                )
                                .animateContentSize(),
                            contentAlignment = Alignment.TopCenter


                        ) {}
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(start = 3.dp, bottom = 16.dp),
                        contentAlignment = Alignment.BottomEnd

                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxHeight(size)

                            // .padding(horizontal = 20.dp)
                            //.fillMaxWidth()
                            ,
                            text = "${listUsers.value}",
                            color = Color.White

                        )
                    }
                }
            }

}