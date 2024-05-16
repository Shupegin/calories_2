package cal.calor.caloriecounter.HistoryScreen

import android.content.Context
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
import androidx.lifecycle.Observer
import cal.calor.caloriecounter.MainViewModel
import cal.calor.caloriecounter.ui.theme.Gray500
import cal.calor.caloriecounter.ui.theme.СolorWater


@Composable
fun VerticalProgressBar(viewModel: MainViewModel,
                        viewModelHistory : HistoryViewModel,
                        owner: LifecycleOwner,
                        context: Context
                        ) {

    val SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val _calories = SharedPreferences.getString("calories", null)


    var progress by remember { mutableStateOf(0f) }
    var listUsers = viewModel.addListHistoryCalories.observeAsState(listOf())
    var caloriesDay by remember {
        mutableStateOf("2000")
    }

    viewModelHistory.caloriesDay.observe(owner, Observer {
        caloriesDay = it
    })

    if (_calories != null) {
        caloriesDay = _calories
    }else{
        caloriesDay = "2000"
    }

    LazyRow(modifier = Modifier
        .padding(bottom = 55.dp),){

        items(listUsers.value){it
            if (it.dailyCalories != null) {

                try {

                    val caloriesPerDay = caloriesDay.toInt()
                    val calories  = it.dailyCalories!!.toFloat() / (caloriesPerDay)
                    progress = calories
                }catch (ex: NumberFormatException){

                }

            }
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
                                            Color(0xffF7B42C),
                                            Color(0xffFC575E),
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

//            Column(
//                modifier = Modifier
//                    .padding(top = 10.dp, start = 30.dp, end = 30.dp),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//
//
//            ) {
//
//                Row(
//                    modifier = Modifier
//                        .height(150.dp)
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxHeight()
//                            .width(40.dp)
//                            .clip(RoundedCornerShape(9.dp))
//                            .background(Gray500),
//                        contentAlignment = Alignment.BottomEnd
//                    ) {
//
//
//                        Box(
//                            modifier = Modifier
//                                .width(40.dp)
//                                .fillMaxHeight(size)
//                                .clip(RoundedCornerShape(9.dp))
//                                .background(
//                                    Brush.verticalGradient(
//                                        listOf(
//                                            Color(0xff6dd5ed),
//                                            Color(0xff2193b0)
//
//                                        )
//                                    )
//                                )
//                                .animateContentSize(),
//                            contentAlignment = Alignment.TopCenter
//
//
//                        ) {}
//                    }
//                    Box(
//                        modifier = Modifier
//                            .fillMaxHeight()
//                            .padding(start = 3.dp, bottom = 16.dp),
//                        contentAlignment = Alignment.BottomEnd
//
//                    ) {
//                        Text(
//                            modifier = Modifier
//                                .fillMaxHeight(size)
//
//                            // .padding(horizontal = 20.dp)
//                            //.fillMaxWidth()
//                            ,
//                            text = "${it.dailyCalories}",
//                            color = Color.White
//
//                        )
//                    }
//                }
//            }
        }

    }

}