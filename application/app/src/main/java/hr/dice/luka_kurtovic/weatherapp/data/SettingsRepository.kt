package hr.dice.luka_kurtovic.weatherapp.data

import androidx.datastore.core.DataStore
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Language
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.NumberOfDays
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Theme
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Units
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Communicating with local persistent storage for getting settings data.
 *
 * @param settingsPreferences Persists user settings using [DataStore] and exposes it to the rest of the app through Flow variable.
 */
class SettingsRepository(private val settingsPreferences: SettingsPreferencesManager) {

    val userSettings = settingsPreferences.userSettingsFlow

    /**
     * Updates the unit value.
     *
     * @param units [Units] instance that represents desired units of measurement to be shown.
     */
    suspend fun updateUnits(units: Units) = withContext(Dispatchers.IO) {
        settingsPreferences.updateUnits(units)
    }

    /**
     * Updates the language value.
     *
     * @param language User's language preference for this application.
     */
    suspend fun updateLanguage(language: Language) = withContext(Dispatchers.IO) {
        settingsPreferences.updateLanguage(language)
    }

    /**
     * Updates the number of days value.
     *
     * @param numberOfDays Desired number of days to be shown on the Daily tab.
     */
    suspend fun updateNumberOfDays(numberOfDays: NumberOfDays) =
        withContext(Dispatchers.IO) {
            settingsPreferences.updateNumberOfDays(numberOfDays)
        }

    /**
     * Updates the theme value.
     *
     * @param theme User's theme preference in the application.
     */
    suspend fun updateTheme(theme: Theme) = withContext(Dispatchers.IO) {
        settingsPreferences.updateTheme(theme)
    }
}
