package cal.calor.caloriecounter.navigation

sealed class Screen(
    val route: String
){
    object CaloriesFeed: Screen(ROUTE_CALORIES_FEED)
    object History: Screen(ROUTE_HISTORY)
    object Profile: Screen(ROUTE_PROFILE)

    private companion object{
        const val ROUTE_CALORIES_FEED = "calories_feed"
        const val ROUTE_HISTORY = "history"
        const val ROUTE_PROFILE = "profile"
    }
}
