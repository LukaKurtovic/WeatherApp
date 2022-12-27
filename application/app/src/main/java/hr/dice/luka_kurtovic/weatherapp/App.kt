package hr.dice.luka_kurtovic.weatherapp

import android.app.Application
import hr.dice.luka_kurtovic.weatherapp.di.appModule
import hr.dice.luka_kurtovic.weatherapp.di.repositoryModule
import hr.dice.luka_kurtovic.weatherapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, repositoryModule, viewModelModule))
        }
    }
}
