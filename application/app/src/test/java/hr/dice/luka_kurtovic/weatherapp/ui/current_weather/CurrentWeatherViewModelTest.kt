package hr.dice.luka_kurtovic.weatherapp.ui.current_weather

import hr.dice.luka_kurtovic.weatherapp.MainDispatcherRule
import hr.dice.luka_kurtovic.weatherapp.data.LocationRepository
import hr.dice.luka_kurtovic.weatherapp.data.SettingsRepository
import hr.dice.luka_kurtovic.weatherapp.data.WeatherRepository
import hr.dice.luka_kurtovic.weatherapp.data.model.Location
import hr.dice.luka_kurtovic.weatherapp.remote.Resource
import hr.dice.luka_kurtovic.weatherapp.remote.model.CurrentWeatherInfo
import hr.dice.luka_kurtovic.weatherapp.remote.model.WeatherData
import hr.dice.luka_kurtovic.weatherapp.remote.model.WeatherOverview
import hr.dice.luka_kurtovic.weatherapp.remote.model.WindData
import hr.dice.luka_kurtovic.weatherapp.ui.UiState
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CurrentWeatherViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var weatherRepository: WeatherRepository

    @MockK
    private lateinit var locationRepository: LocationRepository

    @MockK(relaxed = true)
    private lateinit var settingsRepository: SettingsRepository

    private lateinit var currentWeatherInfo: CurrentWeatherInfo

    private lateinit var viewModel: CurrentWeatherViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel =
            CurrentWeatherViewModel(weatherRepository, locationRepository, settingsRepository)

        currentWeatherInfo = CurrentWeatherInfo(
            weatherData = WeatherData(0.0, 0, 0, 0.0, 0.0, 0.0),
            name = "",
            visibility = 0,
            weather = listOf(
                WeatherOverview("", "", 0, "")
            ),
            wind = WindData(0.0)
        )
    }

    @Test
    fun `test initial uiState loading`() {
        assertTrue(viewModel.uiState.value is UiState.Loading)
    }

    @Test
    fun `test updateUiState returns uiState success`() {
        viewModel.updateUiState(
            response = Resource.Success(data = currentWeatherInfo),
            location = Location("", 0f, 0f, true)
        )

        assertTrue(viewModel.uiState.value is UiState.Success<*>)
    }

    @Test
    fun `test updateUiState returns uiState error`() {
        viewModel.updateUiState(
            response = Resource.Error(exception = Exception()),
            location = Location("", 0f, 0f, true)
        )

        assertTrue(viewModel.uiState.value is UiState.Error)
    }
}
