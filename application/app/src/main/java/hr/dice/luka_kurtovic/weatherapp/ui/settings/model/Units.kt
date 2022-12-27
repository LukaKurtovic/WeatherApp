package hr.dice.luka_kurtovic.weatherapp.ui.settings.model

enum class Units(
    val valueCurrent: String,
    val valueDaily: String = valueCurrent,
    val temperatureSymbol: String
) {
    STANDARD("standard", "base", "K"),
    IMPERIAL("imperial", "us", "°F"),
    METRIC("metric", temperatureSymbol = "°C")
}

fun Units.getUnitsOfMeasure(): String {
    return when (this) {
        Units.STANDARD -> "km/h"
        Units.IMPERIAL -> "mph"
        else -> "km/h"
    }
}
