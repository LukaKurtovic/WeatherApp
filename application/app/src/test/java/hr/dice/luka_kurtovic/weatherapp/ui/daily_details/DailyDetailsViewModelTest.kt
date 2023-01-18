package hr.dice.luka_kurtovic.weatherapp.ui.daily_details

import app.cash.turbine.test
import hr.dice.luka_kurtovic.weatherapp.MainDispatcherRule
import hr.dice.luka_kurtovic.weatherapp.data.SettingsRepository
import hr.dice.luka_kurtovic.weatherapp.data.model.UserSettings
import hr.dice.luka_kurtovic.weatherapp.remote.model.DailyWeatherDetails
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Language
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.NumberOfDays
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Theme
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Units
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DailyDetailsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK
    private lateinit var settingsRepository: SettingsRepository

    @MockK(relaxed = true)
    private lateinit var dailyWeatherDetails: DailyWeatherDetails

    private val userSettings = UserSettings(
        Units.METRIC, Language.ENGLISH, NumberOfDays.THREE, Theme.LIGHT
    )

    private lateinit var viewModel: DailyDetailsViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = DailyDetailsViewModel(settingsRepository)

        every { settingsRepository.userSettings } returns flow {
            emit(
                userSettings
            )
        }
    }

    @Test
    fun `test userSettings returns correct value`() = runTest {
        val job = launch {
            viewModel.userSettings.test {
                val actual = awaitItem()
                val expected = userSettings
                assertEquals(expected, actual)
            }
        }
        job.join()
        job.cancel()
    }

    @Test
    fun `test prepareDataForUI`() = runTest {
        val job = launch {
            viewModel.uiState.test {
                val data = awaitItem()
                assertTrue(data.isNotEmpty())
            }
        }
        viewModel.prepareDataForUI(dailyWeatherDetails)
        job.join()
        job.cancel()
    }
}
