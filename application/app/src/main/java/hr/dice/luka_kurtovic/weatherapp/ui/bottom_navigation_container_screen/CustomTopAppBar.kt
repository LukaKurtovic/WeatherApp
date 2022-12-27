package hr.dice.luka_kurtovic.weatherapp.ui.bottom_navigation_container_screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import hr.dice.luka_kurtovic.weatherapp.ui.bottom_navigation_container_screen.model.TopAppBarData
import hr.dice.luka_kurtovic.weatherapp.ui.destinations.DailyDetailsScreenDestination
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Theme
import hr.dice.luka_kurtovic.weatherapp.ui.theme.WeatherAppTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * Displays top app bar.
 *
 * @param navController [NavHostController] for this NavGraph required to determine current route.
 * @param viewModel [CustomTopAppBarViewModel] instance that holds related UI state.
 */
@Composable
fun CustomTopAppBar(navController: NavHostController, viewModel: CustomTopAppBarViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val callback = { navController.navigateUp() }
    val dateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)

    if (currentRoute == DailyDetailsScreenDestination.route) {
        CustomTopAppBarWithBackButtonContent(uiState, callback, dateTimeFormatter)
    } else {
        CustomTopAppBarContent(uiState)
    }
}

@Composable
fun CustomTopAppBarContent(
    uiState: TopAppBarData,
) {
    TopAppBar(
        title = { Text(uiState.city) },
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.surface
    )
}

@Composable
private fun CustomTopAppBarWithBackButtonContent(
    uiState: TopAppBarData,
    callback: () -> Boolean,
    dateTimeFormatter: DateTimeFormatter
) {
    TopAppBar(
        title = {
            Text("${uiState.city} - ${uiState.date.format(dateTimeFormatter)}")
        },
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.surface,
        navigationIcon = {
            IconButton(onClick = { callback() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back button"
                )
            }
        }
    )
}

@Composable
@Preview
private fun PreviewCustomTopAppBar() {
    WeatherAppTheme(Theme.LIGHT) {
        CustomTopAppBarContent(TopAppBarData("Osijek", LocalDate.now()))
    }
}
