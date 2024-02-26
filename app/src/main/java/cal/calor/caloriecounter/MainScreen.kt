package cal.calor.caloriecounter


import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import cal.calor.caloriecounter.HistoryScreen.HistoryScreen
import cal.calor.caloriecounter.HistoryScreen.HistoryViewModel
import cal.calor.caloriecounter.PulseScreen.PulseScreen
import cal.calor.caloriecounter.PulseScreen.PulseViewModel
import cal.calor.caloriecounter.WeightScreen.WeightScreen
import cal.calor.caloriecounter.WeightScreen.WeightViewModel
import cal.calor.caloriecounter.WaterScreeen.WaterScreen
import cal.calor.caloriecounter.WaterScreeen.WaterViewModel
import cal.calor.caloriecounter.banner.Banner
import cal.calor.caloriecounter.dialog.SettingDialog
import cal.calor.caloriecounter.dialog.dialog
import cal.calor.caloriecounter.dialog.dialogInfo
import cal.calor.caloriecounter.dialog.pulseDialog
import cal.calor.caloriecounter.dialog.waterDialog
import cal.calor.caloriecounter.navigation.*
import cal.calor.caloriecounter.ui.theme.BackgroundBottom
import cal.calor.caloriecounter.ui.theme.BackgroundGray


@SuppressLint("SuspiciousIndentation")
@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    viewModelWeight: WeightViewModel,
    historyViewModel: HistoryViewModel,
    waterViewModel: WaterViewModel,
    pulseViewModel: PulseViewModel,
    owner: LifecycleOwner,
    context: Context,
    aplicationContext: Context,
    navController: NavController,
){


    val dialogState = remember {
        mutableStateOf(false)
    }
    val waterDialogState = remember {
        mutableStateOf(false)
    }

    val pulseDialogState = remember {
        mutableStateOf(false)
    }

    val dialogInfoState = remember {
        mutableStateOf(false)
    }

    val dialogSetting = remember {
        mutableStateOf(false)
    }

    if (pulseDialogState.value){
        pulseDialog(pulseDialogState = pulseDialogState,
            viewModel = pulseViewModel, owner =  owner,
            context = context)
    }

    if (dialogSetting.value){
        SettingDialog(dialogState = dialogSetting, applicationContext = aplicationContext)
    }

    if (dialogInfoState.value){
        dialogInfo(dialogState = dialogInfoState, context = context, historyViewModel)
    }

    if (waterDialogState.value){
        waterDialog(waterDialogState = waterDialogState, viewModel = waterViewModel, owner )
    }
    if (dialogState.value){
       dialog(dialogState = dialogState, viewModel = mainViewModel, owner )
    }

    val advertisement =  mainViewModel.management.observeAsState()


    val navigationState = rememberNavigationState()
    val advertisementInt = remember {
        mutableStateOf(1)
    }

        Column (modifier = Modifier
            .fillMaxSize()){
            Scaffold(modifier = Modifier.weight(1f, true), bottomBar ={
                BottomNavigation(backgroundColor = BackgroundBottom) {
                    val navBackStackEntry  by navigationState.navHostController.currentBackStackEntryAsState()
                    val currentRout = navBackStackEntry?.destination?.route
                    val item = listOf(
                        NavigationItem.Home,
                        NavigationItem.Water,
                        NavigationItem.Pulse,
                        NavigationItem.Profile,
                        NavigationItem.Favourite,
                    )


                    item.forEach{ item ->
                        BottomNavigationItem(
                            selected = currentRout == item.screen.route,
                            onClick = { navigationState.navigateTo(item.screen.route)
                                advertisementInt.value = item.int

                            },
                            icon = { Icon(item.icon , contentDescription = null) },
                            label = {
                                Text(text = stringResource(id = item.titleResId), fontSize = 10.sp)
                            },
                            selectedContentColor =  item.color,
                            unselectedContentColor = MaterialTheme.colors.onSecondary
                        )

                    }

                }
            }){ paddingValues ->
                AppNavGraph(
                    navHostController = navigationState.navHostController,
                    homeScreenContent = { HomeScreen(viewModel = mainViewModel, paddingValues = paddingValues, onItem = {dialogState.value = true}, owner = owner)},
                    waterScreenContent = { WaterScreen(onItem = {waterDialogState.value = true}, viewModel = waterViewModel, onItemSetting = {dialogSetting.value = true})},
                    weightScreenContent = { WeightScreen(viewModelWeight = viewModelWeight, paddingValues = paddingValues,owner,context, navController)},
                    pulseScreenContent = { PulseScreen(pulseViewModel = pulseViewModel, owner = owner, onItem = {pulseDialogState.value = true})},
                    historyScreenContent = { HistoryScreen(onItem = { dialogInfoState.value = true}, viewModel = mainViewModel, historyViewModel = historyViewModel, waterViewModel = waterViewModel, paddingValues = paddingValues, owner = owner, context =  context)}
                )
            }


            if (advertisement.value?.advertisement != null){
                advertisement.value?.advertisement.let {
                    if(it == true){
                        advertisement.value?.advertisementKey?.let { it1 -> Banner(id = it1) }
                    }
                }
            }

//            if (advertisement.value?.advertisement != null){
//
//                advertisement.value!!.advertisementSwitch.let {
//                    Log.d("TestReclama","$it")
//                    if (it == true){
//                        Box(
//                            modifier = Modifier
//                                .fillMaxWidth().height(58.dp).background(BackgroundGray),
//                            contentAlignment = Alignment.BottomCenter
//                        ){
//
//                            when(advertisementInt.value){
//                                1-> if (advertisement.value?.advertisement != null){
//                                    advertisement.value?.advertisement.let {
//                                        if(it == true){
//                                            advertisement.value?.advertisementKey?.let { it1 -> Banner(id = it1) }
//                                        }
//                                    }
//                                }
//
//                                2-> if (advertisement.value?.advertisement != null){
//                                    advertisement.value?.advertisement.let {
//                                        if(it == true){
//                                            advertisement.value?.advertisementKey2?.let { it1 -> Banner(id = it1) }
//                                        }
//                                    }
//                                }
//
//                                3-> if (advertisement.value?.advertisement != null){
//                                    advertisement.value?.advertisement.let {
//                                        if(it == true){
//
//                                            advertisement.value?.advertisementKey3?.let { it1 -> Banner(id = it1) }
//                                        }
//                                    }
//                                }
//
//                                4-> if (advertisement.value?.advertisement != null){
//                                    advertisement.value?.advertisement.let {
//                                        if(it == true){
//
//                                            advertisement.value?.advertisementKey4?.let { it1 -> Banner(id = it1) }
//                                        }
//                                    }
//                                }
//
//                                5-> if (advertisement.value?.advertisement != null){
//                                    advertisement.value?.advertisement.let {
//                                        if(it == true){
//
//                                            advertisement.value?.advertisementKey5?.let { it1 -> Banner(id = it1) }
//                                        }
//                                    }
//                                }
//
//                                else {
//                                    if (advertisement.value?.advertisement != null){
//                                        advertisement.value?.advertisement.let {
//                                            if(it == true){
//                                                advertisement.value?.advertisementKey?.let { it1 -> Banner(id = it1) }
//                                            }
//                                        }
//                                    }
//                                }

//                            }
//                        }
//                    }else{

//                    }
//                }
//            }
        }
}

