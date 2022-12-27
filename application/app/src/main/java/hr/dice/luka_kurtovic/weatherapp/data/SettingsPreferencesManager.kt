package hr.dice.luka_kurtovic.weatherapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import hr.dice.luka_kurtovic.weatherapp.data.model.UserSettings
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Language
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.NumberOfDays
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Theme
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Units
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

/**
 * Persists user settings using [DataStore] and exposes it to the rest of the app through [userSettingsFlow] variable.
 */
class SettingsPreferencesManager(context: Context) {
    private val Context._dataStore: DataStore<Preferences> by preferencesDataStore("Settings")
    private val dataStore = context._dataStore

    val userSettingsFlow = dataStore.data.map { preferences ->
        val units = Units.valueOf(
            preferences[PreferencesKeys.UNITS] ?: Units.METRIC.name
        )
        val language = Language.valueOf(
            preferences[PreferencesKeys.LANGUAGE] ?: Language.ENGLISH.name
        )
        val numberOfDays = NumberOfDays.valueOf(
            preferences[PreferencesKeys.NUMBER_OF_DAYS] ?: NumberOfDays.THREE.name
        )
        val theme = Theme.valueOf(
            preferences[PreferencesKeys.THEME] ?: Theme.DEFAULT.name
        )
        UserSettings(units, language, numberOfDays, theme)
    }.distinctUntilChanged()

    /**
     * Updates the unit value.
     *
     * @param units [Units] instance that represents desired units of measurement to be shown.
     */
    suspend fun updateUnits(units: Units) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.UNITS] = units.name
        }
    }

    /**
     * Updates the language value.
     *
     * @param language User's language preference for this application.
     */
    suspend fun updateLanguage(language: Language) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.LANGUAGE] = language.name
        }
    }

    /**
     * Updates the number of days value.
     *
     * @param numberOfDays Desired number of days to be shown on the Daily tab.
     */
    suspend fun updateNumberOfDays(numberOfDays: NumberOfDays) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.NUMBER_OF_DAYS] = numberOfDays.name
        }
    }

    /**
     * Updates the theme value.
     *
     * @param theme User's theme preference in the application.
     */
    suspend fun updateTheme(theme: Theme) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME] = theme.name
        }
    }

    /**
     * Stores [stringPreferencesKey] required to manipulate with [DataStore].
     */
    private object PreferencesKeys {
        val UNITS = stringPreferencesKey("Units")
        val LANGUAGE = stringPreferencesKey("Language")
        val NUMBER_OF_DAYS = stringPreferencesKey("NumberOfDays")
        val THEME = stringPreferencesKey("Theme")
    }
}
