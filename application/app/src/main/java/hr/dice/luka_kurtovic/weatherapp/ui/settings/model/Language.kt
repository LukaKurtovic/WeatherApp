package hr.dice.luka_kurtovic.weatherapp.ui.settings.model

enum class Language(
    val value: String,
    val valueDailyWeather: String = value
) {
    CROATIAN("hr", "sr"), ENGLISH("en"), GERMAN("de")
}
