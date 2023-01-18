package hr.dice.luka_kurtovic.weatherapp.data

import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Language
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.NumberOfDays
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Theme
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Units
import io.mockk.MockKAnnotations
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsRepositoryTest {
    @MockK(relaxed = true)
    private lateinit var settingsPreferencesManager: SettingsPreferencesManager

    private lateinit var settingsRepository: SettingsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        settingsRepository = SettingsRepository(settingsPreferencesManager)
    }

    @Test
    fun `test updateUnits`() = runTest {
        settingsRepository.updateUnits(Units.METRIC)

        coVerify {
            settingsPreferencesManager.updateUnits(any())
        }
    }

    @Test
    fun `test updateLanguage`() = runTest {
        settingsRepository.updateLanguage(Language.ENGLISH)

        coVerify {
            settingsPreferencesManager.updateLanguage(any())
        }
    }

    @Test
    fun `test updateNumberOfDays`() = runTest {
        settingsRepository.updateNumberOfDays(NumberOfDays.THREE)

        coVerify {
            settingsPreferencesManager.updateNumberOfDays(any())
        }
    }

    @Test
    fun `test updateTheme`() = runTest {
        settingsRepository.updateTheme(Theme.LIGHT)

        coVerify {
            settingsPreferencesManager.updateTheme(any())
        }
    }
}
