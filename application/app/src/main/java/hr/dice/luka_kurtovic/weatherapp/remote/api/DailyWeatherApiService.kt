package hr.dice.luka_kurtovic.weatherapp.remote.api

import hr.dice.luka_kurtovic.weatherapp.remote.model.DailyWeatherData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Defines API endpoints for getting daily weather data.
 */
interface DailyWeatherApiService {

    /**
     * Gets daily weather data for given location.
     *
     * @param location Coordinates of the desired location.
     * @param units Units of measurement (standard, metric or imperial).
     * @param language Gets output in specified language.
     * @param apiKey API key mandatory for sending requests on server.
     *
     * @return The [DailyWeatherData] that contains daily weather information.
     */
    @GET("VisualCrossingWebServices/rest/services/timeline/{location}")
    suspend fun getDailyWeather(
        @Path("location") location: String,
        @Query("key") apiKey: String = "UBZ9ZL6LPZXKSXJDB5QS4DEX7",
        @Query("unitGroup") units: String,
        @Query("lang") language: String,
    ): DailyWeatherData
}
