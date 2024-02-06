package cal.calor.caloriecounter.HistoryScreen

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.ui.unit.sp

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import cal.calor.caloriecounter.MainViewModel
import cal.calor.caloriecounter.dialog.DialogQrCode
import cal.calor.caloriecounter.ui.theme.BackgroundGray

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

    var pointList : List<Point> = ArrayList()
    historyViewModel.listPoint.observe(owner, Observer {
        pointList = it
    })

    historyViewModel.getFirebaseData()


    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(BackgroundGray),

        ){

        Column(modifier = Modifier.fillMaxSize()
            .padding(bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {

            DropMenu(viewModel = viewModel)
//            Row {
//                Button(
//                    onClick = { dialogStateQr.value = true
//                    },Modifier.width(120.dp)
//                ) {
//                    Text(text = "qr-code")
//                }
//
//                Button(onClick = {
//                    var intentIntegrator = IntentIntegrator(context as Activity?)
//                    intentIntegrator.setOrientationLocked(true)
//                    intentIntegrator.setPrompt("Scan a QR code")
//                    intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
//                    intentIntegrator.initiateScan()
//                },
//                    Modifier.width(120.dp)
//                ) {
//                    Text(text = "Qr-scanner")
//                }
//
//            }
            VerticalProgressBar(viewModel = viewModel, owner = owner)
            if(pointList.isNotEmpty()){
                Text(text = "График калорий", fontSize = 25.sp)
                Chart(pointList = pointList,viewModel = viewModel)
            }
        }
    }
}















