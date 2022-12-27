package hr.dice.luka_kurtovic.weatherapp.ui.daily_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.dice.luka_kurtovic.weatherapp.R
import hr.dice.luka_kurtovic.weatherapp.data.SettingsRepository
import hr.dice.luka_kurtovic.weatherapp.data.model.UserSettings
import hr.dice.luka_kurtovic.weatherapp.remote.model.DailyWeatherDetails
import hr.dice.luka_kurtovic.weatherapp.ui.daily_details.model.DailyDetailsScreenComponent
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Language
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.NumberOfDays
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Theme
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Units
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.getUnitsOfMeasure
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * State holder for [DailyDetailsScreen]. Exposes the state through [uiState] variable.
 */
class DailyDetailsViewModel(
    settingsRepository: SettingsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        emptyList<DailyDetailsScreenComponent>()
    )
    private val _userSettings = MutableStateFlow(
        UserSettings(
            units = Units.METRIC,
            language = Language.ENGLISH,
            numberOfDays = NumberOfDays.THREE,
            theme = Theme.DEFAULT
        )
    )
    val userSettings = _userSettings.asStateFlow()
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepository.userSettings.collect { userSettings ->
                _userSettings.value = userSettings
            }
        }
    }

    /**
     * Maps provided [state] to the corresponding list of [DailyDetailsScreenComponent] elements and assigns that list to the [uiState].
     *
     * @param state [DailyWeatherDetails] that contains weather information for the particular day.
     */
    fun prepareDataForUI(
        state: DailyWeatherDetails
    ) {
        val temperatureSymbol = _userSettings.value.units.temperatureSymbol
        _uiState.value = listOf(
            DailyDetailsScreenComponent.Card(
                temperature = state.temp,
                description = state.description,
                icon = state.iconUrl
            ),
            DailyDetailsScreenComponent.Header(title = R.string.temperature),
            DailyDetailsScreenComponent.Item(
                title = R.string.minimum_daily,
                value = "${state.tempMin.roundToInt()}$temperatureSymbol"
            ),
            DailyDetailsScreenComponent.Item(
                title = R.string.maximum_daily,
                value = "${state.tempMax.roundToInt()}$temperatureSymbol"
            ),
            DailyDetailsScreenComponent.Item(
                title = R.string.daily,
                value = "${state.temp.roundToInt()}$temperatureSymbol"
            ),

            DailyDetailsScreenComponent.Header(title = R.string.real_feel),
            DailyDetailsScreenComponent.Item(
                title = R.string.real_feel_minimum,
                value = "${state.realFeelMin.roundToInt()}$temperatureSymbol"
            ),
            DailyDetailsScreenComponent.Item(
                title = R.string.real_feel_maximum,
                value = "${state.realFeelMax.roundToInt()}$temperatureSymbol"
            ),
            DailyDetailsScreenComponent.Item(
                title = R.string.real_feel_daily,
                value = "${state.realFeel.roundToInt()}$temperatureSymbol"
            ),

            DailyDetailsScreenComponent.Header(title = R.string.sun),
            DailyDetailsScreenComponent.Item(
                title = R.string.sunrise,
                value = state.sunrise
            ),
            DailyDetailsScreenComponent.Item(
                title = R.string.sunset,
                value = state.sunset
            ),
            DailyDetailsScreenComponent.Item(
                title = R.string.uv_index,
                value = "${state.uvIndex}"
            ),

            DailyDetailsScreenComponent.Header(title = R.string.wind),
            DailyDetailsScreenComponent.Item(
                title = R.string.speed,
                value = "${state.windSpeed.toInt()}${_userSettings.value.units.getUnitsOfMeasure()}"
            ),
            DailyDetailsScreenComponent.Item(
                title = R.string.direction,
                value = "${state.windDirection.toInt()}°"
            ),

            DailyDetailsScreenComponent.Header(title = R.string.weather),
            DailyDetailsScreenComponent.Item(
                title = R.string.conditions,
                value = state.conditions
            ),
            DailyDetailsScreenComponent.Item(
                title = R.string.description,
                value = state.description
            ),

            DailyDetailsScreenComponent.Header(title = R.string.other),
            DailyDetailsScreenComponent.Item(
                title = R.string.humidity,
                value = "${state.humidity.toInt()}%"
            ),
            DailyDetailsScreenComponent.Item(
                title = R.string.pressure,
                value = "${state.pressure.toInt()}hPa"
            )
        )
    }
}
