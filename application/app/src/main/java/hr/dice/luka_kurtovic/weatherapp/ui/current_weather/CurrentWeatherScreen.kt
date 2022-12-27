package hr.dice.luka_kurtovic.weatherapp.ui.current_weather

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
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
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import hr.dice.luka_kurtovic.weatherapp.R
import hr.dice.luka_kurtovic.weatherapp.data.model.UserSettings
import hr.dice.luka_kurtovic.weatherapp.remote.model.CurrentWeatherInfo
import hr.dice.luka_kurtovic.weatherapp.remote.model.WeatherData
import hr.dice.luka_kurtovic.weatherapp.remote.model.WeatherOverview
import hr.dice.luka_kurtovic.weatherapp.remote.model.WindData
import hr.dice.luka_kurtovic.weatherapp.ui.UiState
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

/**
 * Loading and error handling for CurrentWeatherScreen.
 */
@Destination
@Composable
fun CurrentWeatherScreen() {
    val viewModel by viewModel<CurrentWeatherViewModel>()
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
                        onTryAgainClick = viewModel::loadCurrentWeather
                    )
                } else {
                    ErrorScreen(
                        message = state.exception?.message,
                        onTryAgainClick = viewModel::loadCurrentWeather
                    )
                }
            }
            is UiState.Success<*> -> CurrentWeatherScreenContent(
                currentWeatherInfo = state.data as CurrentWeatherInfo,
                userSettings = userSettings
            )
        }
    }
}

/**
 * Contains information about current weather details for desired location.
 *
 * @param currentWeatherInfo Holds all current weather details.
 * @param userSettings Currently selected settings for the app as a [UserSettings].
 */
@Composable
private fun CurrentWeatherScreenContent(
    currentWeatherInfo: CurrentWeatherInfo,
    userSettings: UserSettings
) {
    Column(
        horizontalAlignment = CenterHorizontally,
        modifier = Modifier
            .background(color = MaterialTheme.colors.background)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.weight(0.4f))
        AsyncImage(
            model = "https://openweathermap.org/img/wn/${currentWeatherInfo.weather[0].icon}@2x.png",
            contentDescription = "Weather icon",
            modifier = Modifier.size(130.dp)
        )
        Spacer(modifier = Modifier.weight(0.4f))
        Text(
            text = currentWeatherInfo.weather[0].description,
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colors.onBackground,
            fontSize = 24.sp
        )
        Text(
            text = "${currentWeatherInfo.weatherData.temp.toInt()}${userSettings.units.temperatureSymbol}",
            color = MaterialTheme.colors.onBackground,
            fontSize = 68.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))
        Card(
            border = BorderStroke(1.dp, MaterialTheme.colors.onBackground),
            backgroundColor = MaterialTheme.colors.background,
            modifier = Modifier
                .fillMaxWidth()
                .align(CenterHorizontally),
            shape = RoundedCornerShape(6.dp)
        ) {
            Row(
                modifier = Modifier.height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                WeatherInfo(
                    value = currentWeatherInfo.weatherData.humidity,
                    unit = "%",
                    description = stringResource(R.string.humidity)
                )
                CustomDivider()
                WeatherInfo(
                    value = currentWeatherInfo.weatherData.pressure,
                    unit = "hPa",
                    description = stringResource(R.string.pressure)
                )
                CustomDivider()
                WeatherInfo(
                    value = currentWeatherInfo.wind.speed.toInt(),
                    unit = userSettings.units.getUnitsOfMeasure(),
                    description = stringResource(R.string.wind)
                )
            }
        }
        Spacer(modifier = Modifier.weight(0.7f))
    }
}

@Composable
fun WeatherInfo(
    value: Int,
    unit: String,
    description: String
) {
    Row {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(horizontal = 18.dp, vertical = 6.dp),
            horizontalAlignment = CenterHorizontally
        ) {
            Text(
                text = "$value$unit",
                color = MaterialTheme.colors.onBackground,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Text(
                text = description,
                color = MaterialTheme.colors.onBackground,
                maxLines = 1,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun CustomDivider() {
    Divider(
        color = MaterialTheme.colors.onBackground,
        modifier = Modifier
            .fillMaxHeight()
            .width(1.dp)
    )
}

@Preview
@Composable
private fun PreviewCurrentWeatherScreen() {
    WeatherAppTheme(Theme.LIGHT) {
        CurrentWeatherScreenContent(
            currentWeatherInfo = CurrentWeatherInfo(
                weatherData = WeatherData(
                    10.0, 10, 1009, 10.0, 10.0, 10.0
                ),
                name = "Osijek",
                visibility = 10,
                weather = listOf(
                    WeatherOverview(
                        "Clear sky",
                        "10d",
                        1,
                        "Clear"
                    )
                ),
                wind = WindData(
                    10.0
                ),
            ),
            userSettings = UserSettings(
                Units.METRIC,
                Language.ENGLISH,
                NumberOfDays.THREE,
                Theme.DEFAULT
            )
        )
    }
}

@Preview
@Composable
private fun PreviewWeatherInfo() {
    WeatherAppTheme(Theme.LIGHT) {
        WeatherInfo(
            5, "%", "vlaznost"
        )
    }
}
