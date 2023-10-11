package cal.calor.caloriecounter.navigation



import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
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
        startDestination = Screen.CaloriesFeed.route,
        enterTransition = { EnterTransition.None},
        exitTransition = { ExitTransition.None}
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