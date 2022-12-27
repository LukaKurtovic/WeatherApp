package hr.dice.luka_kurtovic.weatherapp.ui.current_weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.dice.luka_kurtovic.weatherapp.data.LocationRepository
import hr.dice.luka_kurtovic.weatherapp.data.SettingsRepository
import hr.dice.luka_kurtovic.weatherapp.data.WeatherRepository
import hr.dice.luka_kurtovic.weatherapp.data.model.Location
import hr.dice.luka_kurtovic.weatherapp.data.model.UserSettings
import hr.dice.luka_kurtovic.weatherapp.remote.Resource
import hr.dice.luka_kurtovic.weatherapp.remote.model.CurrentWeatherInfo
import hr.dice.luka_kurtovic.weatherapp.ui.UiState
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Language
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.NumberOfDays
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Theme
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Units
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.Locale

/**
 * Holding UI related states that are shown in [CurrentWeatherScreen].
 *
 * @property weatherRepository The [WeatherRepository] for communication between the data source and the rest of the app.
 * @property locationRepository Communication with data source for getting information about locations.
 */
class CurrentWeatherViewModel(
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
    val userSettings = _userSettings.asStateFlow()
    val uiState = _uiState.asStateFlow()
    private val userSettingsFlow = settingsRepository.userSettings

    init {
        viewModelScope.launch {
            userSettingsFlow.combine(locationRepository.getActiveLocation()) { userSettings, location ->
                _userSettings.value = userSettings
                activeLocation = location
                weatherRepository.getCurrentWeather(
                    location.latitude,
                    location.longitude,
                    userSettings.units.valueCurrent,
                    userSettings.language.value
                )
            }.collect { response ->
                updateUiState(response, activeLocation)
            }
        }
    }

    /**
     * Retrieves current weather data from the data source and assigns that value to a [UiState] object.
     */
    fun loadCurrentWeather() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            val response = weatherRepository.getCurrentWeather(
                activeLocation.latitude,
                activeLocation.longitude,
                userSettings.value.units.valueCurrent,
                userSettings.value.language.value
            )
            updateUiState(response, activeLocation)
        }
    }

    private fun updateUiState(response: Resource, location: Location) {
        when (response) {
            is Resource.Success<*> -> {
                val data = response.data as CurrentWeatherInfo
                _uiState.value = UiState.Success(
                    data = data.copy(
                        weather = listOf(
                            data.weather[0].copy(
                                description = toUpperCase(data.weather[0].description)
                            )
                        )
                    )
                )
                updateLocation(
                    location.copy(
                        city = response.data.name
                    )
                )
            }
            is Resource.Error -> {
                _uiState.value = UiState.Error(
                    exception = response.exception
                )
            }
        }
    }

    private fun updateLocation(location: Location) {
        viewModelScope.launch {
            locationRepository.updateLocation(location)
        }
    }

    private fun toUpperCase(text: String): String {
        return text.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }
    }
}
