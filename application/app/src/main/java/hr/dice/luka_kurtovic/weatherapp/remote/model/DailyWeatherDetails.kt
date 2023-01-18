package hr.dice.luka_kurtovic.weatherapp.remote.model

import android.icu.text.DateFormat
import com.google.android.gms.common.util.VisibleForTesting
import kotlinx.serialization.SerialName
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

@kotlinx.serialization.Serializable
data class DailyWeatherDetails(
    @SerialName("datetime")
    val date: String,
    @SerialName("windspeed")
    val windSpeed: Double,
    @SerialName("winddir")
    val windDirection: Double,
    @SerialName("tempmax")
    val tempMax: Double,
    @SerialName("tempmin")
    val tempMin: Double,
    @SerialName("datetimeEpoch")
    val dateInMillis: Long,
    val temp: Double,
    val pressure: Double,
    val humidity: Double,
    @SerialName("feelslikemax")
    val realFeelMax: Double,
    @SerialName("feelslikemin")
    val realFeelMin: Double,
    @SerialName("feelslike")
    val realFeel: Double,
    val conditions: String,
    val description: String,
    @SerialName("uvindex")
    val uvIndex: Double,
    val sunrise: String,
    val sunset: String,
    val icon: String
) {
    val dayOfTheWeek: String
        get() =
            toUpperCase(
                LocalDate.parse(date).format(DateTimeFormatter.ofPattern(DateFormat.WEEKDAY))
            )

    val dateFormatted: String
        get() = formatDate(date)

    val iconUrl: String
        get() = "https://raw.githubusercontent.com/visualcrossing/WeatherIcons/main/PNG/2nd%20Set%20-%20Color/$icon.png"
}

@VisibleForTesting
fun toUpperCase(text: String): String {
    return text.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.ROOT
        ) else it.toString()
    }
}

private fun formatDate(date: String): String {
    return LocalDate.parse(date).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))
}
