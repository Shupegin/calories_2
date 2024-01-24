package cal.calor.caloriecounter


import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import cal.calor.caloriecounter.HistoryScreen.HistoryScreen
import cal.calor.caloriecounter.HistoryScreen.HistoryViewModel
import cal.calor.caloriecounter.WeightScreen.WeightScreen
import cal.calor.caloriecounter.WeightScreen.WeightViewModel
import cal.calor.caloriecounter.WaterScreeen.WaterScreen
import cal.calor.caloriecounter.WaterScreeen.WaterViewModel
import cal.calor.caloriecounter.banner.Banner
import cal.calor.caloriecounter.dialog.dialog
import cal.calor.caloriecounter.dialog.waterDialog
import cal.calor.caloriecounter.navigation.*
import cal.calor.caloriecounter.ui.theme.BackgroundBottom


@SuppressLint("SuspiciousIndentation")
@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    viewModelWeight: WeightViewModel,
    historyViewModel: HistoryViewModel,
    waterViewModel: WaterViewModel,
    owner: LifecycleOwner,
    context: Context,
    navController: NavController
){
    val dialogState = remember {
        mutableStateOf(false)
    }
    val waterDialogState = remember {
        mutableStateOf(false)
    }
    if (waterDialogState.value){
        waterDialog(waterDialogState = waterDialogState, viewModel = waterViewModel, owner )
    }
    if (dialogState.value){
       dialog(dialogState = dialogState, viewModel = mainViewModel, owner )
    }

    val advertisement =  mainViewModel.management.observeAsState()


    val navigationState = rememberNavigationState()
        Column (modifier = Modifier.fillMaxSize()){
            Scaffold(modifier = Modifier.weight(1f, true), bottomBar ={
                BottomNavigation(backgroundColor = BackgroundBottom) {
                    val navBackStackEntry  by navigationState.navHostController.currentBackStackEntryAsState()
                    val currentRout = navBackStackEntry?.destination?.route
                    val item = listOf(
                        NavigationItem.Home,
                        NavigationItem.Water,
                        NavigationItem.Profile,
                        NavigationItem.Favourite,
                    )

                    item.forEach{ item ->
                        BottomNavigationItem(
                            selected = currentRout == item.screen.route,
                            onClick = { navigationState.navigateTo(item.screen.route)},
                            icon = {
                                Icon(item.icon, contentDescription = null, )
                            },
                            label = {
                                Text(text = stringResource(id = item.titleResId))
                            },
                            selectedContentColor =  item.color,
                            unselectedContentColor = MaterialTheme.colors.onSecondary
                        )
                    }
                }
            }){ paddingValues ->
                AppNavGraph(
                    navHostController = navigationState.navHostController,
                    homeScreenContent =    { HomeScreen(viewModel = mainViewModel, paddingValues = paddingValues, onItem = {dialogState.value = true}, owner = owner)},
                    waterScreenContent = { WaterScreen(onItem = {waterDialogState.value = true}, viewModel = waterViewModel)},
                    weightScreenContent = { WeightScreen(viewModelWeight = viewModelWeight, paddingValues = paddingValues,owner,context, navController)},
                    historyScreenContent = { HistoryScreen(viewModel = mainViewModel, historyViewModel = historyViewModel, paddingValues = paddingValues,owner, context)}
                )
            }

            if (advertisement.value?.advertisement != null){
                advertisement.value?.advertisement.let {
                    if(it == true){
                        Banner(id = R.string.banner_1)
                    }
                }
            }
        }
}

