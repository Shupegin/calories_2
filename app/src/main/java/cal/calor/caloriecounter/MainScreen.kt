package cal.calor.caloriecounter


import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import cal.calor.caloriecounter.HistoryScreen.HistoryScreen
import cal.calor.caloriecounter.HistoryScreen.HistoryViewModel
import cal.calor.caloriecounter.ProfileScreen.ProfileScreen
import cal.calor.caloriecounter.ProfileScreen.ProfileViewModel
import cal.calor.caloriecounter.dialog.dialog
import cal.calor.caloriecounter.navigation.*
import cal.calor.caloriecounter.ui.theme.BackgroundBottom


@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    viewModelProf: ProfileViewModel,
    historyViewModel: HistoryViewModel,
    owner: LifecycleOwner,
    context: Context,
    navController: NavController
){
    val dialogState = remember {
        mutableStateOf(false)
    }


    if (dialogState.value){
       dialog(dialogState = dialogState, viewModel = mainViewModel, owner )
    }


    val navigationState = rememberNavigationState()
    Scaffold(bottomBar ={
        BottomNavigation(backgroundColor = BackgroundBottom) {
            val navBackStackEntry  by navigationState.navHostController.currentBackStackEntryAsState()
            val currentRout = navBackStackEntry?.destination?.route
            val item = listOf(
                NavigationItem.Home,
                NavigationItem.Favourite,
                NavigationItem.Profile,
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
                    selectedContentColor =  MaterialTheme.colors.onPrimary,
                    unselectedContentColor = MaterialTheme.colors.onSecondary
                )
            }
        }
    }){ paddingValues ->
        AppNavGraph(
            navHostController = navigationState.navHostController,
            homeScreenContent =    { HomeScreen(viewModel = mainViewModel, paddingValues = paddingValues, onItem = {dialogState.value = true})},
            historyScreenContent = { HistoryScreen(viewModel = mainViewModel, historyViewModel = historyViewModel, paddingValues = paddingValues,owner)},
            profileScreenContent = { ProfileScreen(viewModelProf = viewModelProf, paddingValues = paddingValues,owner,context, navController)})
    }
}

