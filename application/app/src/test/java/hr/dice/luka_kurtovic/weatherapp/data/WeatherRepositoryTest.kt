package hr.dice.luka_kurtovic.weatherapp.data

import hr.dice.luka_kurtovic.weatherapp.remote.Resource
import hr.dice.luka_kurtovic.weatherapp.remote.api.CurrentWeatherApiService
import hr.dice.luka_kurtovic.weatherapp.remote.api.DailyWeatherApiService
import hr.dice.luka_kurtovic.weatherapp.remote.model.CurrentWeatherInfo
import hr.dice.luka_kurtovic.weatherapp.remote.model.DailyWeatherData
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRepositoryTest {
    @MockK
    private lateinit var currentWeatherApiService: CurrentWeatherApiService

    @MockK
    private lateinit var dailyWeatherApiService: DailyWeatherApiService

    @MockK
    private lateinit var currentWeatherInfo: CurrentWeatherInfo

    @MockK
    private lateinit var dailyWeatherDetails: DailyWeatherData

    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        weatherRepository = WeatherRepository(currentWeatherApiService, dailyWeatherApiService)
    }

    @Test
    fun `test getCurrentWeather returns Error`() {
        coEvery {
            currentWeatherApiService.getCurrentWeather(
                any(),
                any(),
                any(),
                any()
            )
        } throws IOException()

        runTest {
            val response = weatherRepository.getCurrentWeather(10f, 10f, "", "")
            assertTrue(response is Resource.Error)
        }
    }

    @Test
    fun `test getCurrentWeather returns Success`() {
        coEvery {
            currentWeatherApiService.getCurrentWeather(
                any(),
                any(),
                any(),
                any()
            )
        } returns currentWeatherInfo

        runTest {
            val response = weatherRepository.getCurrentWeather(10f, 10f, "", "")
            assertTrue(response is Resource.Success<*>)
        }
    }

    @Test
    fun `test getDailyWeather returns Error`() {
        coEvery {
            dailyWeatherApiService.getDailyWeather(
                any(),
                any(),
                any(),
                any()
            )
        } throws IOException()

        runTest {
            val response = weatherRepository.getDailyWeather("", "", "")
            assertTrue(response is Resource.Error)
        }
    }

    @Test
    fun `test getDailyWeather returns Success`() {
        coEvery {
            dailyWeatherApiService.getDailyWeather(
                any(),
                any(),
                any(),
                any()
            )
        } returns dailyWeatherDetails

        runTest {
            val response = weatherRepository.getDailyWeather("", "", "")
            assertTrue(response is Resource.Success<*>)
        }
    }
}
