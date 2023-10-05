package cal.calor.caloriecounter.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import cal.calor.caloriecounter.R

sealed class NavigationItem (
    val screen : Screen,
    val titleResId: Int,
    val icon: ImageVector
){
    object Home : NavigationItem(
        screen = Screen.CaloriesFeed,
        titleResId = R.string.navigation_item_main,
        icon = Icons.Outlined.Home
    )

    object Favourite : NavigationItem(
        screen = Screen.History,
        titleResId = R.string.navigation_item_favourite,
        icon = Icons.Outlined.Favorite
    )

    object Profile : NavigationItem(
        screen = Screen.Profile,
        titleResId = R.string.navigation_item_profile,
        icon = Icons.Outlined.Person
    )
}