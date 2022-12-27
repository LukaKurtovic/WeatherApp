package hr.dice.luka_kurtovic.weatherapp.remote.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class CurrentWeatherInfo(
    @SerialName("main")
    val weatherData: WeatherData,
    val name: String,
    val visibility: Int,
    val weather: List<WeatherOverview>,
    val wind: WindData
)
