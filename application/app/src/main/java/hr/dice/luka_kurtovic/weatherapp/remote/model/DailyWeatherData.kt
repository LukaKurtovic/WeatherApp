package hr.dice.luka_kurtovic.weatherapp.remote.model

@kotlinx.serialization.Serializable
data class DailyWeatherData(
    val days: List<DailyWeatherDetails>
)
