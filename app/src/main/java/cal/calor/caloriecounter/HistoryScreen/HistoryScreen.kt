package cal.calor.caloriecounter.HistoryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import cal.calor.caloriecounter.MainViewModel
import cal.calor.caloriecounter.ui.theme.BackgroundGray
import co.yml.charts.common.model.Point


@Composable
fun HistoryScreen(viewModel: MainViewModel,
                  historyViewModel: HistoryViewModel,
                  paddingValues: PaddingValues,
                  owner: LifecycleOwner){


    var dbUid = viewModel.userListDAO.observeAsState(listOf())
    viewModel.loadFirebaseData(dbUid.value)

    var pointList : List<Point> = ArrayList()
    historyViewModel.listPoint.observe(owner, Observer {
        pointList = it
    })





    Box(modifier = Modifier
        .fillMaxSize()
        .background(BackgroundGray),

    ){

        Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                DropMenu(viewModel = viewModel)
                VerticalProgressBar(viewModel = viewModel, owner = owner)
                Text(text = "График калорий", fontSize = 25.sp)
                Chart(pointList)
        }


    }


}













