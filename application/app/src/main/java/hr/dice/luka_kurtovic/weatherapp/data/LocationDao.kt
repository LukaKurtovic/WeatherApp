package hr.dice.luka_kurtovic.weatherapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import hr.dice.luka_kurtovic.weatherapp.data.model.COL_LOCATION_IS_ACTIVE
import hr.dice.luka_kurtovic.weatherapp.data.model.Location
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: Location)

    @Query("SELECT * FROM Location WHERE $COL_LOCATION_IS_ACTIVE == 1 LIMIT 1")
    fun getActiveLocation(): Flow<Location>

    @Update
    suspend fun update(location: Location)

    @Query("SELECT EXISTS(SELECT * FROM Location)")
    suspend fun isLocationSaved(): Boolean
}
