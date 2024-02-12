package cal.calor.caloriecounter.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Contrast
import androidx.compose.material.icons.outlined.Fastfood
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LineWeight
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.MonitorWeight
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Scale
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import cal.calor.caloriecounter.R
import cal.calor.caloriecounter.ui.theme.Brown
import cal.calor.caloriecounter.ui.theme.ColorRed
import cal.calor.caloriecounter.ui.theme.Green
import cal.calor.caloriecounter.ui.theme.СolorWater
import cal.calor.caloriecounter.ui.theme.Сoral

sealed class NavigationItem (
    val screen : Screen,
    val titleResId: Int,
    val icon: ImageVector,
    val color : Color
){
    object Home : NavigationItem(
        screen = Screen.CaloriesFeed,
        titleResId = R.string.navigation_item_main,
        icon = Icons.Outlined.Fastfood,
        color = Сoral
    )
    object Water : NavigationItem(
        screen = Screen.Water,
        titleResId = R.string.navigation_item_water,
        icon = Icons.Outlined.WaterDrop,
        color = СolorWater
    )

    object Favourite : NavigationItem(
        screen = Screen.History,
        titleResId = R.string.navigation_item_favourite,
        icon = Icons.Outlined.MenuBook,
        color = Brown
    )

    object Profile : NavigationItem(
        screen = Screen.Weight,
        titleResId = R.string.navigation_item_profile,
        icon = Icons.Outlined.Scale,
        color = Green
    )

    object Pulse : NavigationItem(
        screen = Screen.Pulse,
        titleResId = R.string.navigation_item_pulse,
        icon = Icons.Outlined.Contrast,
        color = ColorRed
    )
}