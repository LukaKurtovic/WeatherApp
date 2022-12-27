package hr.dice.luka_kurtovic.weatherapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import hr.dice.luka_kurtovic.weatherapp.data.model.Location

@Database(entities = [Location::class], version = 2)
abstract class LocationDatabase : RoomDatabase() {
    abstract fun getLocationDao(): LocationDao
}
