package cal.calor.caloriecounter.HistoryScreen

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import cal.calor.caloriecounter.MainViewModel
import cal.calor.caloriecounter.ui.theme.Gray500


@Composable
fun VerticalProgressBar(viewModel: MainViewModel,
                        owner: LifecycleOwner) {

    var progress by remember { mutableStateOf(0f) }
    var listUsers = viewModel.addListHistoryCalories.observeAsState(listOf())

    var day = viewModel.addDay.observeAsState()

    LazyRow(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 55.dp),){

        items(listUsers.value){it
            if (it.dailyCalories != null) {

                val caloriesPerDay = 2000
                val calories  = it.dailyCalories!!.toFloat() / (caloriesPerDay)

                progress = calories
            }
            val  size by   animateFloatAsState(targetValue = progress,
                tween(durationMillis = 1000,
                    delayMillis = 100,
                    easing = LinearOutSlowInEasing), label = ""
            )

            Column(
                modifier = Modifier
                    .padding(top = 70.dp, start = 30.dp, end = 30.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally


            ) {

                Box(
                    modifier = Modifier,
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "${day.value}",
                        color = Color.White)
                }
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
                                            Color(0xffE000FF),
                                            Color(0xffE000FF),
                                            Color(0xFF7700FF),
                                            Color(0xFF7700FF),
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
                            text = "${it.dailyCalories}",
                            color = Color.White

                        )
                    }
                }
            }
        }

    }

}