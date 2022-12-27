package hr.dice.luka_kurtovic.weatherapp.ui.daily_weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.dice.luka_kurtovic.weatherapp.data.LocationRepository
import hr.dice.luka_kurtovic.weatherapp.data.SettingsRepository
import hr.dice.luka_kurtovic.weatherapp.data.WeatherRepository
import hr.dice.luka_kurtovic.weatherapp.data.model.Location
import hr.dice.luka_kurtovic.weatherapp.data.model.UserSettings
import hr.dice.luka_kurtovic.weatherapp.remote.Resource
import hr.dice.luka_kurtovic.weatherapp.remote.model.DailyWeatherData
import hr.dice.luka_kurtovic.weatherapp.ui.UiState
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Language
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.NumberOfDays
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Theme
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Units
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

/**
 * Holding UI related states that are shown in [DailyWeatherScreen].
 *
 * @property weatherRepository The [WeatherRepository] for communication between the data source and the rest of the app.
 * @property locationRepository Communication with data source for getting information about locations.
 */
class DailyWeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository,
    settingsRepository: SettingsRepository
) : ViewModel() {
    private lateinit var activeLocation: Location
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    private val _userSettings = MutableStateFlow(
        UserSettings(
            units = Units.METRIC,
            language = Language.ENGLISH,
            numberOfDays = NumberOfDays.THREE,
            theme = Theme.DEFAULT
        )
    )
    val uiState = _uiState.asStateFlow()
    val userSettings = _userSettings.asStateFlow()
    private val userSettingsFlow = settingsRepository.userSettings

    init {
        viewModelScope.launch {
            userSettingsFlow.combine(locationRepository.getActiveLocation()) { userSettings, location ->
                _userSettings.value = userSettings
                activeLocation = location
                weatherRepository.getDailyWeather(
                    "${location.latitude},${location.longitude}",
                    userSettings.units.valueDaily,
                    userSettings.language.valueDailyWeather
                )
            }.collect { response ->
                updateUiState(response)
            }
        }
    }

    /**
     * Retrieves daily weather data from the data source and assigns that value to a [UiState] object.
     */
    fun loadDailyWeather() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            val response = weatherRepository.getDailyWeather(
                "${activeLocation.latitude},${activeLocation.longitude}",
                userSettings.value.units.valueCurrent,
                userSettings.value.language.value
            )
            updateUiState(response)
        }
    }

    private fun updateUiState(response: Resource) {
        when (response) {
            is Resource.Success<*> -> {
                val data = response.data as DailyWeatherData
                _uiState.value = UiState.Success(
                    data = data
                )
            }
            is Resource.Error -> {
                _uiState.value = UiState.Error(
                    exception = response.exception
                )
            }
        }
    }
}
