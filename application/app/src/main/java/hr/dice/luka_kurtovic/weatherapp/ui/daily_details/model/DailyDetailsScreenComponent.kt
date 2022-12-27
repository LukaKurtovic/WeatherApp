package hr.dice.luka_kurtovic.weatherapp.ui.daily_details.model

import androidx.annotation.StringRes

/**
 * Wrapper class for all different components that are part of the DailyDetailsScreen.
 */
sealed class DailyDetailsScreenComponent {
    /**
     * Component that represents a single section.
     *
     * @param title The section title as a [StringRes].
     */
    data class Header(@StringRes val title: Int) : DailyDetailsScreenComponent()

    /**
     * Component that holds [temperature], [description] and [icon] information.
     */
    data class Card(val temperature: Double, val description: String, val icon: String) :
        DailyDetailsScreenComponent()

    /**
     * A single item that holds one piece of weather information inside a whole section.
     *
     * @param title Weather information that is represented by this item.
     * @param value Value for this piece of weather information in some units of measurement.
     */
    data class Item(@StringRes val title: Int, val value: String) : DailyDetailsScreenComponent()
}
