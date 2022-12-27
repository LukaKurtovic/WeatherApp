package hr.dice.luka_kurtovic.weatherapp.data

import hr.dice.luka_kurtovic.weatherapp.remote.Resource
import hr.dice.luka_kurtovic.weatherapp.remote.api.CurrentWeatherApiService
import hr.dice.luka_kurtovic.weatherapp.remote.api.DailyWeatherApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Communication with data source for getting weather data.
 *
 * @property currentWeatherApiService Defines API endpoints for getting current weather data.
 * @property dailyWeatherApiService Defines API endpoints for getting daily weather data.
 */
class WeatherRepository(
    private val currentWeatherApiService: CurrentWeatherApiService,
    private val dailyWeatherApiService: DailyWeatherApiService
) {

    /**
     * Retrieving current weather data for the given location.
     *
     * @param latitude The latitude of the location.
     * @param longitude The longitude of the location.
     * @param units Units of measurement (standard, metric or imperial).
     * @param language Gets output in specified language.
     *
     * @return [Resource] that contains data if successful, otherwise it returns [Exception] that caused error.
     */
    suspend fun getCurrentWeather(
        latitude: Float,
        longitude: Float,
        units: String,
        language: String
    ): Resource {
        return try {
            withContext(Dispatchers.IO) {
                val data =
                    currentWeatherApiService.getCurrentWeather(latitude, longitude, units, language)
                Resource.Success(data)
            }
        } catch (exception: Exception) {
            Resource.Error(exception)
        }
    }

    /**
     * Retrieving daily weather data from the given location.
     *
     * @param location The location for which the data is being retrieved.
     * @param units Units of measurement (standard, metric or imperial).
     * @param language Gets output in specified language.
     *
     * @return [Resource] that contains data if successful, otherwise it returns [Exception] that caused error.
     */
    suspend fun getDailyWeather(
        location: String,
        units: String,
        language: String
    ): Resource {
        return try {
            withContext(Dispatchers.IO) {
                val data = dailyWeatherApiService.getDailyWeather(
                    location = location,
                    units = units,
                    language = language
                )
                Resource.Success(data)
            }
        } catch (exception: Exception) {
            Resource.Error(exception)
        }
    }
}
