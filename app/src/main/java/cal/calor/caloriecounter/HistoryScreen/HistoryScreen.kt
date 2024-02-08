package cal.calor.caloriecounter.HistoryScreen

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import cal.calor.caloriecounter.MainViewModel
import cal.calor.caloriecounter.dialog.DialogQrCode
import cal.calor.caloriecounter.ui.theme.BackgroundGray
import cal.calor.caloriecounter.ui.theme.Green
import cal.calor.caloriecounter.ui.theme.СolorWater
import cal.calor.caloriecounter.ui.theme.Сoral

import co.yml.charts.common.model.Point
import com.google.zxing.integration.android.IntentIntegrator


@Composable
fun HistoryScreen(viewModel: MainViewModel,
                  historyViewModel: HistoryViewModel,
                  paddingValues: PaddingValues,
                  owner: LifecycleOwner,
                  context: Context){


    val dialogStateQr = remember {
        mutableStateOf(false)
    }


    if (dialogStateQr.value){
        DialogQrCode(
            dialogState = dialogStateQr,
            historyViewModel = historyViewModel,
            owner = owner)
    }

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


    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(BackgroundGray),

        ){

        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {

           item { DropMenu(viewModel = viewModel)

               VerticalProgressBar(viewModel = viewModel, owner = owner)

               if(pointList.isNotEmpty()){
                   Text(text = "График калорий", fontSize = 25.sp, color = Сoral)
                   Chart(pointList = pointList,viewModel = viewModel, color = Сoral)
               }

               if(pointList.isNotEmpty()){
                   Text(text = "График воды", fontSize = 25.sp, color = СolorWater)
                   Chart(pointList = pointListWater,viewModel = viewModel, color = СolorWater)
               }

               if(pointList.isNotEmpty()){
                   Text(text = "График веса", fontSize = 25.sp, color = Green)
                   Chart(pointList = pointListWeight,viewModel = viewModel, color = Green)
               }

           }
        }
    }
}















