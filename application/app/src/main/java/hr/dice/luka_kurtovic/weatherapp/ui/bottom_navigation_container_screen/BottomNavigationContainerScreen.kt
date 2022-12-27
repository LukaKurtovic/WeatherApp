package hr.dice.luka_kurtovic.weatherapp.ui.bottom_navigation_container_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import hr.dice.luka_kurtovic.weatherapp.ui.NavGraphs
import hr.dice.luka_kurtovic.weatherapp.ui.bottom_navigation_container_screen.model.TopAppBarData
import hr.dice.luka_kurtovic.weatherapp.ui.daily_weather.DailyWeatherScreen
import hr.dice.luka_kurtovic.weatherapp.ui.destinations.CurrentWeatherScreenDestination
import hr.dice.luka_kurtovic.weatherapp.ui.destinations.DailyWeatherScreenDestination
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Theme
import hr.dice.luka_kurtovic.weatherapp.ui.theme.WeatherAppTheme
import org.koin.androidx.compose.inject
import java.time.LocalDate

/**
 * Provides content for TopAppBar, BottomBar and NavHost for navigation between BottomNavBar screens.
 */
@Destination
@Composable
fun BottomNavigationContainerScreen() {
    val navController = rememberNavController()
    val topAppBarViewModel: CustomTopAppBarViewModel by inject()
    val bottomBar: @Composable () -> Unit = {
        CustomBottomNavigation(navController = navController)
    }
    val topBar: @Composable () -> Unit = {
        CustomTopAppBar(
            navController = navController,
            viewModel = topAppBarViewModel
        )
    }
    val content: @Composable (PaddingValues) -> Unit = {
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            startRoute = CurrentWeatherScreenDestination,
            navController = navController
        ) {
            composable(DailyWeatherScreenDestination) {
                DailyWeatherScreen(
                    navigator = destinationsNavigator,
                    topAppBarViewModel = topAppBarViewModel
                )
            }
        }
    }
    BottomNavigationContainerScreenContent(
        bottomBar, topBar, content
    )
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun BottomNavigationContainerScreenContent(
    bottomBar: @Composable () -> Unit,
    topBar: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit

) {
    Scaffold(
        bottomBar = bottomBar,
        topBar = topBar,
        content = content
    )
}

@Composable
@Preview
private fun PreviewBottomNavigationContainerScreen() {
    WeatherAppTheme(Theme.LIGHT) {
        BottomNavigationContainerScreenContent(
            bottomBar = { CustomBottomNavigation(navController = rememberNavController()) },
            topBar = {
                CustomTopAppBarContent(
                    uiState = TopAppBarData(
                        "Osijek",
                        LocalDate.parse("10.10.")
                    )
                )
            }
        ) {
        }
    }
}
