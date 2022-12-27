package hr.dice.luka_kurtovic.weatherapp.remote.api

import hr.dice.luka_kurtovic.weatherapp.remote.model.CurrentWeatherInfo
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Defines API endpoints for getting current weather data.
 */
interface CurrentWeatherApiService {

    /**
     * Gets current weather data for given location.
     *
     * @param latitude Latitude of desired location.
     * @param longitude Longitude of desired location.
     * @param units Units of measurement (standard, metric or imperial).
     * @param language Gets output in specified language.
     * @param apiKey API key mandatory for sending requests on server.
     *
     * @return The [CurrentWeatherInfo] that contains current weather information.
     */
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float,
        @Query("units") units: String,
        @Query("lang") language: String,
        @Query("appid") apiKey: String = "7d132b01f60de45143847223474c3901"
    ): CurrentWeatherInfo
}
