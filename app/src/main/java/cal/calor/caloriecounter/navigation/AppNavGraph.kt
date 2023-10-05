package cal.calor.caloriecounter.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable



@Composable
fun AppNavGraph(
   navHostController: NavHostController,
   homeScreenContent: @Composable () -> Unit,
   historyScreenContent: @Composable () -> Unit,
   profileScreenContent: @Composable () -> Unit,


){
    NavHost(
        navController = navHostController,
        startDestination = Screen.CaloriesFeed.route
    ){
        composable(Screen.CaloriesFeed.route){
            homeScreenContent()
        }
        composable(Screen.History.route){
            historyScreenContent()
        }
        composable(Screen.Profile.route){
            profileScreenContent()
        }
    }
}