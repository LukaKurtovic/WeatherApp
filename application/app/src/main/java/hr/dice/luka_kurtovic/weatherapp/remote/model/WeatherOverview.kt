package hr.dice.luka_kurtovic.weatherapp.remote.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class WeatherOverview(
    val description: String,
    val icon: String,
    val id: Int,
    @SerialName("main")
    val status: String
)
