package cal.calor.caloriecounter.navigation



import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable



@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    homeScreenContent: @Composable () -> Unit,
    waterScreenContent: @Composable () -> Unit,
    historyScreenContent: @Composable () -> Unit,
    pulseScreenContent: @Composable () -> Unit,
    weightScreenContent: @Composable () -> Unit

){
    NavHost(
        navController = navHostController,
        startDestination = Screen.CaloriesFeed.route,
        enterTransition = { EnterTransition.None},
        exitTransition = { ExitTransition.None}
    ){
        composable(Screen.CaloriesFeed.route){
            homeScreenContent()
        }
        composable(Screen.Water.route){
            waterScreenContent()
        }

        composable(Screen.Pulse.route){
            pulseScreenContent()
        }
        composable(Screen.Weight.route){
            weightScreenContent()
        }

        composable(Screen.History.route){
            historyScreenContent()
        }

    }
}