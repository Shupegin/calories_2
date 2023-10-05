package cal.calor.caloriecounter.HistoryScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.LifecycleOwner
import cal.calor.caloriecounter.MainViewModel
import cal.calor.caloriecounter.ui.theme.BackgroundGray

@Composable
fun HistoryScreen(viewModel: MainViewModel,
                  paddingValues: PaddingValues,
                  owner: LifecycleOwner){


    var dbUid = viewModel.userListDAO.observeAsState(listOf())
    viewModel.loadFirebaseData(dbUid.value)

    Box(modifier = Modifier
        .fillMaxSize()
        .background(BackgroundGray)
    ){
        Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center) {
        DropMenu(viewModel = viewModel)}
        VerticalProgressBar(viewModel = viewModel, owner = owner)
    }
}











