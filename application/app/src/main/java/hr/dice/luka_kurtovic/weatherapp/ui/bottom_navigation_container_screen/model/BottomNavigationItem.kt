package hr.dice.luka_kurtovic.weatherapp.ui.bottom_navigation_container_screen.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import hr.dice.luka_kurtovic.weatherapp.R
import hr.dice.luka_kurtovic.weatherapp.ui.destinations.CurrentWeatherScreenDestination
import hr.dice.luka_kurtovic.weatherapp.ui.destinations.DailyWeatherScreenDestination
import hr.dice.luka_kurtovic.weatherapp.ui.destinations.DirectionDestination
import hr.dice.luka_kurtovic.weatherapp.ui.destinations.SettingsScreenDestination

/**
 * Holds instances of all bottom navigation icons.
 *
 * @property title Label that corresponds to the item in form of a [StringRes].
 * @property icon [DrawableRes] for the corresponding item in bottom nav.
 * @property destination Holds route that is used for safe navigation to that destination.
 */
enum class BottomNavigationItem(
    @StringRes
    val title: Int,
    @DrawableRes
    val icon: Int,
    val destination: DirectionDestination
) {
    CURRENT_WEATHER(
        title = R.string.current_weather_screen_label,
        icon = R.drawable.ic_today,
        destination = CurrentWeatherScreenDestination
    ),
    DAILY_WEATHER(
        title = R.string.daily_weather_screen_label,
        icon = R.drawable.ic_daily,
        destination = DailyWeatherScreenDestination
    ),
    SETTINGS(
        title = R.string.settings_screen_label,
        icon = R.drawable.ic_settings,
        destination = SettingsScreenDestination
    )
}
