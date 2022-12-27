package hr.dice.luka_kurtovic.weatherapp.di

import androidx.room.Room
import com.google.android.gms.location.LocationServices
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import hr.dice.luka_kurtovic.weatherapp.data.LocationDatabase
import hr.dice.luka_kurtovic.weatherapp.data.SettingsPreferencesManager
import hr.dice.luka_kurtovic.weatherapp.remote.api.CurrentWeatherApiService
import hr.dice.luka_kurtovic.weatherapp.remote.api.DailyWeatherApiService
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

private const val OPEN_WEATHER_MAP = "openWeatherMapApi"
private const val VISUAL_CROSSING = "visualCrossingApi"
private const val DATABASE_NAME = "database"
private const val OPEN_WEATHER_MAP_BASE_URL = "https://api.openweathermap.org/"
private const val VISUAL_CROSSING_BASE_URL = "https://weather.visualcrossing.com/"

@OptIn(ExperimentalSerializationApi::class)
val appModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
        }
    }

    single {
        get<Json>().asConverterFactory("application/json".toMediaType())
    }

    single(named(OPEN_WEATHER_MAP)) {
        Retrofit.Builder()
            .baseUrl(OPEN_WEATHER_MAP_BASE_URL)
            .addConverterFactory(get())
            .build()
    }

    single(named(VISUAL_CROSSING)) {
        Retrofit.Builder()
            .baseUrl(VISUAL_CROSSING_BASE_URL)
            .addConverterFactory(get())
            .build()
    }

    single {
        get<Retrofit>(named(OPEN_WEATHER_MAP)).create(CurrentWeatherApiService::class.java)
    }

    single {
        get<Retrofit>(named(VISUAL_CROSSING)).create(DailyWeatherApiService::class.java)
    }

    single {
        Room.databaseBuilder(
            androidApplication(),
            LocationDatabase::class.java,
            DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    single {
        get<LocationDatabase>().getLocationDao()
    }

    single {
        LocationServices.getFusedLocationProviderClient(androidApplication())
    }

    single {
        SettingsPreferencesManager(get())
    }
}
