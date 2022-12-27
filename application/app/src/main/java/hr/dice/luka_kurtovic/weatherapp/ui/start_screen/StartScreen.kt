package hr.dice.luka_kurtovic.weatherapp.ui.start_screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hr.dice.luka_kurtovic.weatherapp.ui.destinations.BottomNavigationContainerScreenDestination
import hr.dice.luka_kurtovic.weatherapp.ui.destinations.NoLocationSavedScreenDestination
import hr.dice.luka_kurtovic.weatherapp.ui.destinations.StartScreenDestination
import hr.dice.luka_kurtovic.weatherapp.ui.loading.LoadingScreen
import org.koin.androidx.compose.viewModel

/**
 * Starting destination for the app.
 *
 * @param navigator For navigating between different destinations.
 */
@Composable
@Destination
@RootNavGraph(start = true)
fun StartScreen(
    navigator: DestinationsNavigator
) {
    val viewModel by viewModel<StartScreenViewModel>()
    val isLocationSaved by viewModel.isLocationSaved.collectAsState()

    when (isLocationSaved) {
        null -> LoadingScreen()
        false -> navigator.navigate(NoLocationSavedScreenDestination) {
            popUpTo(StartScreenDestination.route) {
                inclusive = true
            }
        }
        true -> navigator.navigate(BottomNavigationContainerScreenDestination) {
            popUpTo(StartScreenDestination.route) {
                inclusive = true
            }
        }
    }
}
