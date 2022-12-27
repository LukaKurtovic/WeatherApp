package hr.dice.luka_kurtovic.weatherapp.data.model

import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Language
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.NumberOfDays
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Theme
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Units

/**
 * Currently selected settings by the user.
 *
 * @property units Currently selected units of measurement in the form of [Units].
 * @property language Currently selected language of the whole app in the form of [Language].
 * @property numberOfDays Number of days to be shown on the Daily tab in the form of [NumberOfDays].
 * @property theme Currently selected theme (dark, light or system default).
 */
data class UserSettings(
    val units: Units,
    val language: Language,
    val numberOfDays: NumberOfDays,
    val theme: Theme
)
