package hr.dice.luka_kurtovic.weatherapp.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.dice.luka_kurtovic.weatherapp.data.SettingsRepository
import hr.dice.luka_kurtovic.weatherapp.data.model.UserSettings
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Language
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.NumberOfDays
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Theme
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Units
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Holds UI related states that are shown on [SettingsScreen].
 *
 * @param settingsRepository Communicating with local persistent storage for getting settings data.
 */
class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _userSettings = MutableStateFlow(
        UserSettings(
            units = Units.METRIC,
            language = Language.ENGLISH,
            numberOfDays = NumberOfDays.THREE,
            theme = Theme.DEFAULT
        )
    )
    val settings = _userSettings.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepository.userSettings.collect { settings ->
                _userSettings.value = settings
            }
        }
    }

    /**
     * Updates the unit value.
     *
     * @param unit [Units] instance that represents desired units of measurement to be shown.
     */
    fun updateUnits(unit: Units) {
        viewModelScope.launch {
            settingsRepository.updateUnits(unit)
        }
    }

    /**
     * Updates the language value.
     *
     * @param language User's language preference for this application.
     */
    fun updateLanguage(language: Language) {
        viewModelScope.launch {
            settingsRepository.updateLanguage(language)
        }
    }

    /**
     * Updates the number of days value.
     *
     * @param numberOfDays Desired number of days to be shown on the Daily tab.
     */
    fun updateNumberOfDays(numberOfDays: NumberOfDays) {
        viewModelScope.launch {
            settingsRepository.updateNumberOfDays(numberOfDays)
        }
    }

    /**
     * Updates the theme value.
     *
     * @param theme User's theme preference in the application.
     */
    fun updateTheme(theme: Theme) {
        viewModelScope.launch {
            settingsRepository.updateTheme(theme)
        }
    }
}
