package hr.dice.luka_kurtovic.weatherapp.ui.bottom_navigation_container_screen.model

import java.time.LocalDate

/**
 * Data to be displayed in the TopAppBar.
 *
 * @param city [String] that represents the location for which the weather info is shown.
 * @param date An exact date for which the weather details are being displayed.
 */
data class TopAppBarData(
    val city: String,
    val date: LocalDate
)
