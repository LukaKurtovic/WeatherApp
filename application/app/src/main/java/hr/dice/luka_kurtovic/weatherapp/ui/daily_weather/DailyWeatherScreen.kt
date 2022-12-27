package hr.dice.luka_kurtovic.weatherapp.ui.daily_weather

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hr.dice.luka_kurtovic.weatherapp.R
import hr.dice.luka_kurtovic.weatherapp.data.model.UserSettings
import hr.dice.luka_kurtovic.weatherapp.remote.model.DailyWeatherData
import hr.dice.luka_kurtovic.weatherapp.remote.model.DailyWeatherDetails
import hr.dice.luka_kurtovic.weatherapp.ui.UiState
import hr.dice.luka_kurtovic.weatherapp.ui.bottom_navigation_container_screen.CustomTopAppBarViewModel
import hr.dice.luka_kurtovic.weatherapp.ui.current_weather.WeatherInfo
import hr.dice.luka_kurtovic.weatherapp.ui.destinations.DailyDetailsScreenDestination
import hr.dice.luka_kurtovic.weatherapp.ui.error.ErrorScreen
import hr.dice.luka_kurtovic.weatherapp.ui.loading.LoadingScreen
import hr.dice.luka_kurtovic.weatherapp.ui.no_internet.NoInternetScreen
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Language
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.NumberOfDays
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Theme
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Units
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.getUnitsOfMeasure
import hr.dice.luka_kurtovic.weatherapp.ui.theme.WeatherAppTheme
import okio.IOException
import org.koin.androidx.compose.viewModel
import java.time.LocalDate
import kotlin.math.roundToInt

/**
 * Loading and error handling for DailyWeatherScreen.
 *
 * @param navigator [DestinationsNavigator] for navigating to other destinations.
 * @param topAppBarViewModel [CustomTopAppBarViewModel] instance that holds UI state related to the TopAppBar.
 */
@Composable
@Destination
fun DailyWeatherScreen(
    navigator: DestinationsNavigator,
    topAppBarViewModel: CustomTopAppBarViewModel
) {
    val viewModel by viewModel<DailyWeatherViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    val userSettings by viewModel.userSettings.collectAsState()
    Crossfade(
        targetState = uiState,
        animationSpec = tween(500)
    ) { state ->
        when (state) {
            is UiState.Loading -> LoadingScreen()
            is UiState.Error -> {
                if (state.exception is IOException) {
                    NoInternetScreen(
                        onTryAgainClick = viewModel::loadDailyWeather
                    )
                } else {
                    ErrorScreen(
                        message = state.exception?.message,
                        onTryAgainClick = viewModel::loadDailyWeather
                    )
                }
            }
            is UiState.Success<*> -> DailyWeatherScreenContent(
                state = state.data as DailyWeatherData,
                callback = { uiState ->
                    topAppBarViewModel.updateDate(LocalDate.parse(uiState.date))
                    navigator.navigate(DailyDetailsScreenDestination(uiState))
                },
                userSettings = userSettings
            )
        }
    }
}

/**
 * Displays brief daily weather information for the given day. Number of pages (days) depends on the
 * user's selection from Settings tab.
 *
 * @param state [DailyWeatherData] Holds the list of the weather data for all forecasted days.
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
private fun DailyWeatherScreenContent(
    state: DailyWeatherData,
    callback: (DailyWeatherDetails) -> Unit,
    userSettings: UserSettings

) {
    val pagerState = rememberPagerState()
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colors.background)
            .fillMaxHeight()
    ) {
        HorizontalPager(
            count = userSettings.numberOfDays.value,
            state = pagerState
        ) { page ->
            CustomWeatherCard(
                dailyWeatherDetails = state.days[page],
                userSettings = userSettings,
                page = page,
                callback = callback
            )
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(32.dp),
            activeColor = MaterialTheme.colors.surface,
            inactiveColor = MaterialTheme.colors.onBackground,
            indicatorWidth = 16.dp
        )
    }
}

@Composable
private fun CustomWeatherCard(
    dailyWeatherDetails: DailyWeatherDetails,
    userSettings: UserSettings,
    page: Int,
    callback: (DailyWeatherDetails) -> Unit
) {
    Card(
        border = BorderStroke(1.dp, MaterialTheme.colors.onBackground),
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 26.dp, vertical = 56.dp)
            .clickable(onClick = { callback(dailyWeatherDetails) }),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = when (page) {
                    0 -> stringResource(R.string.today)
                    1 -> stringResource(R.string.tomorrow)
                    else -> {
                        dailyWeatherDetails.dayOfTheWeek
                    }
                },
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(6.dp),
                color = MaterialTheme.colors.onBackground
            )
            Text(
                text = dailyWeatherDetails.dateFormatted,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 36.dp),
                color = MaterialTheme.colors.onBackground
            )
            Text(
                text = "${dailyWeatherDetails.temp.roundToInt()}${userSettings.units.temperatureSymbol}",
                fontSize = 56.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 26.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "↓${dailyWeatherDetails.tempMin.roundToInt()}${userSettings.units.temperatureSymbol}",
                    fontSize = 28.sp
                )
                Text(
                    text = "↑${dailyWeatherDetails.tempMax.roundToInt()}${userSettings.units.temperatureSymbol}",
                    fontSize = 28.sp
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            ) {
                WeatherInfo(
                    value = dailyWeatherDetails.humidity.toInt(),
                    unit = "%",
                    description = stringResource(R.string.humidity)
                )
                WeatherInfo(
                    value = dailyWeatherDetails.pressure.toInt(),
                    unit = "hPa",
                    description = stringResource(R.string.pressure)
                )
                WeatherInfo(
                    value = dailyWeatherDetails.windSpeed.toInt(),
                    unit = userSettings.units.getUnitsOfMeasure(),
                    description = stringResource(R.string.wind)
                )
            }
        }
    }
}

@Composable
@Preview
private fun PreviewDailyWeatherScreen() {
    WeatherAppTheme(Theme.LIGHT) {
        DailyWeatherScreenContent(
            state = DailyWeatherData(
                listOf(
                    DailyWeatherDetails(
                        "2022-10-20",
                        10.0,
                        10.0,
                        10.0,
                        10.0,
                        100,
                        1000.0,
                        90.0,
                        10.0,
                        10.0,
                        10.0,
                        10.0,
                        "",
                        "",
                        2.0,
                        "",
                        "", ""
                    ),
                    DailyWeatherDetails(
                        "2022-10-20",
                        10.0,
                        10.0,
                        10.0,
                        10.0,
                        100,
                        1000.0,
                        90.0,
                        10.0,
                        10.0,
                        10.0,
                        10.0,
                        "",
                        "",
                        2.0,
                        "",
                        "", ""
                    ),
                    DailyWeatherDetails(
                        "2022-10-20",
                        10.0,
                        10.0,
                        10.0,
                        10.0,
                        100,
                        1000.0,
                        90.0,
                        10.0,
                        10.0,
                        10.0,
                        10.0,
                        "",
                        "",
                        2.0,
                        "",
                        "", ""
                    ),
                )
            ),
            userSettings = UserSettings(
                Units.METRIC,
                Language.ENGLISH,
                NumberOfDays.THREE,
                Theme.DEFAULT
            ),
            callback = {}
        )
    }
}
