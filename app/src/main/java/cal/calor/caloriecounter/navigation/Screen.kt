package cal.calor.caloriecounter.navigation

sealed class Screen(
    val route: String
){
    object CaloriesFeed: Screen(ROUTE_CALORIES_FEED)
    object Water: Screen(WATER_PROFILE)
    object History: Screen(ROUTE_HISTORY)
    object Pulse: Screen(ROUTE_PULSE)
    object Weight: Screen(ROUTE_WEIGHT)

    private companion object{
        const val ROUTE_CALORIES_FEED = "calories_feed"
        const val ROUTE_HISTORY = "history"
        const val ROUTE_PULSE = "pulse"
        const val ROUTE_WEIGHT = "weight"
        const val WATER_PROFILE = "water"
    }
}
