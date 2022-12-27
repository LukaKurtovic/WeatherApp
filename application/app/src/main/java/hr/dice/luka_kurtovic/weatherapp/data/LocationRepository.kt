package hr.dice.luka_kurtovic.weatherapp.data

import hr.dice.luka_kurtovic.weatherapp.data.model.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext

/**
 * Communication with data source for getting information about locations.
 *
 * @property dao [LocationDao] that holds methods for database operations.
 */
class LocationRepository(private val dao: LocationDao) {

    /**
     * Inserts location in database.
     *
     * @param location Location that is being inserted.
     */
    suspend fun insertLocation(location: Location) {
        withContext(Dispatchers.IO) {
            dao.insert(location)
        }
    }

    /**
     * Fetches current active location.
     */
    fun getActiveLocation(): Flow<Location> = dao.getActiveLocation().distinctUntilChanged()

    /**
     * Updates provided location that already exits in database.
     *
     * @param location [Location] to be updated.
     */
    suspend fun updateLocation(location: Location) {
        withContext(Dispatchers.IO) {
            dao.update(location)
        }
    }

    /**
     * Checks if there is any entries in the table and returns corresponding [Boolean] value.
     */
    suspend fun isLocationSaved(): Boolean {
        return withContext(Dispatchers.IO) {
            dao.isLocationSaved()
        }
    }
}
