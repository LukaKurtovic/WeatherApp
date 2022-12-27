package hr.dice.luka_kurtovic.weatherapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val TN_LOCATION = "location"
const val COL_LOCATION_CITY_NAME = "city_name"
const val COL_LOCATION_LATITUDE = "latitude"
const val COL_LOCATION_LONGITUDE = "longitude"
const val COL_LOCATION_IS_ACTIVE = "is_active"
const val COL_LOCATION_ID = "id"

@Entity(tableName = TN_LOCATION)
data class Location(
    @ColumnInfo(name = COL_LOCATION_CITY_NAME)
    val city: String = "",
    @ColumnInfo(name = COL_LOCATION_LATITUDE)
    val latitude: Float,
    @ColumnInfo(name = COL_LOCATION_LONGITUDE)
    val longitude: Float,
    @ColumnInfo(name = COL_LOCATION_IS_ACTIVE)
    val isActive: Boolean,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COL_LOCATION_ID)
    val id: Long = 0
)
