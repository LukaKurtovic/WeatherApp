package hr.dice.luka_kurtovic.weatherapp.di

import hr.dice.luka_kurtovic.weatherapp.ui.bottom_navigation_container_screen.CustomTopAppBarViewModel
import hr.dice.luka_kurtovic.weatherapp.ui.current_weather.CurrentWeatherViewModel
import hr.dice.luka_kurtovic.weatherapp.ui.daily_details.DailyDetailsViewModel
import hr.dice.luka_kurtovic.weatherapp.ui.daily_weather.DailyWeatherViewModel
import hr.dice.luka_kurtovic.weatherapp.ui.no_location_saved.NoLocationSavedViewModel
import hr.dice.luka_kurtovic.weatherapp.ui.settings.SettingsViewModel
import hr.dice.luka_kurtovic.weatherapp.ui.start_screen.StartScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        CurrentWeatherViewModel(get(), get(), get())
    }

    viewModel {
        CustomTopAppBarViewModel(get())
    }

    viewModel {
        NoLocationSavedViewModel(get(), get())
    }

    viewModel {
        DailyWeatherViewModel(get(), get(), get())
    }

    viewModel {
        SettingsViewModel(get())
    }

    viewModel {
        DailyDetailsViewModel(get())
    }

    viewModel {
        StartScreenViewModel(get())
    }
}
