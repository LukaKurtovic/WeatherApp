package hr.dice.luka_kurtovic.weatherapp

import android.app.LocaleManager
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.lifecycleScope
import com.ramcosta.composedestinations.DestinationsNavHost
import hr.dice.luka_kurtovic.weatherapp.data.SettingsRepository
import hr.dice.luka_kurtovic.weatherapp.ui.NavGraphs
import hr.dice.luka_kurtovic.weatherapp.ui.theme.WeatherAppTheme
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val prefs: SettingsRepository by inject()

    init {
        lifecycleScope.launchWhenStarted {
            prefs.userSettings.collect {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    applicationContext.getSystemService(LocaleManager::class.java).applicationLocales =
                        LocaleList.forLanguageTags(it.language.value)
                } else {
                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags(
                            it.language.value
                        )
                    )
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}
