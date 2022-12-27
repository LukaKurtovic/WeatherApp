package hr.dice.luka_kurtovic.weatherapp.di

import hr.dice.luka_kurtovic.weatherapp.data.LocationRepository
import hr.dice.luka_kurtovic.weatherapp.data.SettingsRepository
import hr.dice.luka_kurtovic.weatherapp.data.WeatherRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        WeatherRepository(get(), get())
    }

    single {
        LocationRepository(get())
    }

    single {
        SettingsRepository(get())
    }
}
