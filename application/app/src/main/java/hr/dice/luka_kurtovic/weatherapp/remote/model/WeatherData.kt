package hr.dice.luka_kurtovic.weatherapp.remote.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class WeatherData(
    @SerialName("feels_like")
    val feelsLike: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    @SerialName("temp_max")
    val tempMax: Double,
    @SerialName("temp_min")
    val tempMin: Double
)
