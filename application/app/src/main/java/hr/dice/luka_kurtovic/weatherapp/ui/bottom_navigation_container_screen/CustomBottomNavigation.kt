package hr.dice.luka_kurtovic.weatherapp.ui.bottom_navigation_container_screen

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.navigation.navigate
import hr.dice.luka_kurtovic.weatherapp.R
import hr.dice.luka_kurtovic.weatherapp.ui.bottom_navigation_container_screen.model.BottomNavigationItem
import hr.dice.luka_kurtovic.weatherapp.ui.destinations.DailyDetailsScreenDestination
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Theme
import hr.dice.luka_kurtovic.weatherapp.ui.theme.WeatherAppTheme

/**
 * Displays a bottom navigation bar.
 *
 * @param navController The navController for the bottom navigation.
 */
@Composable
fun CustomBottomNavigation(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (currentRoute != DailyDetailsScreenDestination.route) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.onBackground
        ) {
            BottomNavigationItem.values().forEach { item ->
                BottomNavigationItem(
                    selected = currentRoute == item.destination.route,
                    onClick = {
                        navController.navigate(item.destination) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = stringResource(R.string.bottom_bar_icon)
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = item.title),
                            fontSize = 10.sp
                        )
                    },
                    selectedContentColor = MaterialTheme.colors.onBackground,
                    alwaysShowLabel = true,
                )
            }
        }
    }
}

@Composable
@Preview
private fun PreviewCustomBottomNavigation() {
    WeatherAppTheme(Theme.LIGHT) {
        CustomBottomNavigation(navController = rememberNavController())
    }
}
