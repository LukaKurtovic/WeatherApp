package hr.dice.luka_kurtovic.weatherapp.ui.settings

import app.cash.turbine.test
import hr.dice.luka_kurtovic.weatherapp.MainDispatcherRule
import hr.dice.luka_kurtovic.weatherapp.data.SettingsRepository
import hr.dice.luka_kurtovic.weatherapp.data.model.UserSettings
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Language
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.NumberOfDays
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Theme
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Units
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK(relaxed = true)
    private lateinit var settingsRepository: SettingsRepository

    private lateinit var viewModel: SettingsViewModel

    private val userSettings = UserSettings(
        Units.METRIC, Language.ENGLISH, NumberOfDays.THREE, Theme.LIGHT
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = SettingsViewModel(settingsRepository)

        every { settingsRepository.userSettings } returns flow {
            emit(
                userSettings
            )
        }
    }

    @Test
    fun `test userSettings returns correct value`() = runTest {
        val job = launch {
            viewModel.settings.test {
                val actual = awaitItem()
                val expected = userSettings
                assertEquals(expected, actual)
            }
        }
        job.join()
        job.cancel()
    }

    @Test
    fun `test updateUnits`() = runTest {
        viewModel.updateUnits(Units.METRIC)

        val job = launch {
            coVerify {
                settingsRepository.updateUnits(any())
            }
        }
        job.join()
        job.cancel()
    }

    @Test
    fun `test updateLanguage`() = runTest {
        viewModel.updateLanguage(Language.ENGLISH)

        val job = launch {
            coVerify {
                settingsRepository.updateLanguage(any())
            }
        }
        job.join()
        job.cancel()
    }

    @Test
    fun `test updateNumberOfDays`() = runTest {
        viewModel.updateNumberOfDays(NumberOfDays.THREE)

        val job = launch {
            coVerify {
                settingsRepository.updateNumberOfDays(any())
            }
        }
        job.join()
        job.cancel()
    }

    @Test
    fun `test updateTheme`() = runTest {
        viewModel.updateTheme(Theme.LIGHT)

        val job = launch {
            coVerify {
                settingsRepository.updateTheme(any())
            }
        }
        job.join()
        job.cancel()
    }
}
