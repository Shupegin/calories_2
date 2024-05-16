package cal.calor.caloriecounter.HistoryScreen

import android.content.Context
import android.util.Log
import android.widget.ProgressBar
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Verified

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import cal.calor.caloriecounter.MainViewModel
import cal.calor.caloriecounter.R
import cal.calor.caloriecounter.WaterScreeen.WaterViewModel
import cal.calor.caloriecounter.ui.theme.BackgroundGray
import cal.calor.caloriecounter.ui.theme.Green
import cal.calor.caloriecounter.ui.theme.brightBlue
import cal.calor.caloriecounter.ui.theme.orange
import cal.calor.caloriecounter.ui.theme.sf_ui_display_semiboldFontFamily
import cal.calor.caloriecounter.ui.theme.sfproDisplayThinFontFamily
import cal.calor.caloriecounter.ui.theme.white_green
import cal.calor.caloriecounter.ui.theme.СolorWater
import cal.calor.caloriecounter.ui.theme.Сoral
import co.yml.charts.common.extensions.isNotNull

import co.yml.charts.common.model.Point


@Composable
fun HistoryScreen(viewModel: MainViewModel,
                  onItem: () -> Unit,
                  onItemClick: () -> Unit,
                  waterViewModel: WaterViewModel,
                  historyViewModel: HistoryViewModel,
                  paddingValues: PaddingValues,
                  owner: LifecycleOwner,
                  context: Context){


    var dbUid = viewModel.userListDAO.observeAsState(listOf())
    viewModel.loadFirebaseData(dbUid.value)


    //Еда
    var pointList : List<Point> = ArrayList()

    val listPoint = historyViewModel.foodListDAO.observeAsState()

    listPoint.value?.let { historyViewModel.getPointsList_2(it) }

    historyViewModel.listPoint.observe(owner, Observer {
        pointList = it
    })


    //Вода

   var pointListWater : List<Point> = ArrayList()

    val listPointWater = historyViewModel.waterListDAO.observeAsState()

    listPointWater.value?.let { historyViewModel.getPointsListWater(it) }

    historyViewModel.listPointWater.observe(owner, Observer {
        pointListWater = it
    })


    //Вес

    var pointListWeight : List<Point> = ArrayList()

    val listPointWeight = historyViewModel.weightListDAO.observeAsState()

    listPointWeight.value?.let { historyViewModel.getPointsListWeight(it) }

    historyViewModel.listPointWeight.observe(owner, Observer {
        pointListWeight = it
    })


    historyViewModel.getFirebaseData()

    val foodList = viewModel.foodListDAO.observeAsState(listOf())

    var proteinG by remember {
        mutableStateOf(viewModel.returnSum(foodList.value,"белки"))
    }

    var fatTotalG by remember {
        mutableStateOf(viewModel.returnSum(foodList.value, "жиры"))
    }

    var carbohydratesTotalG by remember {
        mutableStateOf(viewModel.returnSum(foodList.value,"углеводы"))
    }
    var isLoading  by remember { mutableStateOf( true) }



    viewModel.sendSelectedOptionText("День", listFood = foodList.value)


   val count = viewModel.count.observeAsState(null)
    if (count.value != null){
        isLoading = false
    }

    Box(modifier = Modifier
        .fillMaxSize(),

        ){

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 58.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {

           item {
               Spacer(modifier = Modifier.padding(top = 10.dp))


               Row (modifier = Modifier.fillMaxWidth(),
                   horizontalArrangement = Arrangement.Center){
                   Spacer(modifier = Modifier.padding(start = 10.dp))
                   Image(
                       painterResource(R.drawable.icon_menu),
                       contentDescription = "",modifier= Modifier
                           .size(25.dp)
                           .clickable { onItemClick.invoke() },)

                   Row (modifier = Modifier.weight(1.1f),
                       horizontalArrangement = Arrangement.Center
                   ) {
                       Text(
                           text = "Позиций в базе = ",
                           modifier = Modifier,
                           color = Color.White,
                           fontSize = 25.sp,
                           fontFamily = sf_ui_display_semiboldFontFamily
                       )

                       if (isLoading){
                           CircularProgressIndicator(
                               color = Color.White,
                               modifier = Modifier.size(25.dp)
                           )
                       }

                       if (count.value != null){
                           Text(
                               text = "${count.value}",
                               modifier = Modifier,
                               color = Color.White,
                               fontSize = 25.sp,
                               fontFamily = sf_ui_display_semiboldFontFamily
                           )
                       }

                   }
               }



               Spacer(modifier = Modifier.padding(top = 10.dp))

               Row (modifier = Modifier.fillMaxWidth()){

                   Text(
                       text = "День",
                       modifier = Modifier
                           .weight(1.1f)
                           .padding(start = 140.dp),
                       color = Color.White,
                       fontSize = 25.sp,
                       fontFamily = sf_ui_display_semiboldFontFamily
                   )

                   Image(
                       painterResource(R.drawable.icon_exclamation_point_svg),
                       contentDescription = "",modifier= Modifier
                           .weight(0.1f)
                           .size(25.dp)
                           .clickable { onItem.invoke() },)

                   Spacer(modifier = Modifier.padding(end = 10.dp))

               }


               var showDescription by remember {
                   mutableStateOf(true)
               }


               Row {
                   VerticalProgressBar(viewModel = viewModel,viewModelHistory = historyViewModel,owner = owner, context= context)
                   VerticalProgressBarWater(viewModel = waterViewModel, owner = owner)
               }

               Text(text = "БЖУ",
                   fontSize = 25.sp,
                   color = Color.White,
                   fontFamily = sf_ui_display_semiboldFontFamily
               )

               PieChart(
                   data = mapOf(
                       Pair("Белки", proteinG.toFloat()),
                       Pair("Жиры", fatTotalG.toFloat()),
                       Pair("Углеводы", carbohydratesTotalG.toFloat()),
                   )
               )
               
               Spacer(modifier = Modifier.padding(top = 30.dp))

               if(pointList.isNotEmpty()){
                   Text(text = "График калорий",
                       fontSize = 25.sp,
                       color = Сoral,
                       fontFamily = sf_ui_display_semiboldFontFamily
                   )
                   Chart(pointList = pointList,viewModel = viewModel, color = Сoral)
               }

               if(pointListWater.isNotEmpty()){
                   Text(text = "График воды",
                       fontSize = 25.sp,
                       color = СolorWater,
                       fontFamily = sf_ui_display_semiboldFontFamily
                   )
                   Chart(pointList = pointListWater,viewModel = viewModel, color = СolorWater)
               }

               if(pointListWeight.isNotEmpty()){
                   Text(text = "График веса",
                       fontSize = 25.sp,
                       color = Green,
                       fontFamily = sf_ui_display_semiboldFontFamily
                   )
                   Chart(pointList = pointListWeight,viewModel = viewModel, color = Green)
               }

           }
        }
    }
}















