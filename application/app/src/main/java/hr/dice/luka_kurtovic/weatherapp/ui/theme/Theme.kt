package hr.dice.luka_kurtovic.weatherapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color.Companion.White
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import hr.dice.luka_kurtovic.weatherapp.data.SettingsRepository
import hr.dice.luka_kurtovic.weatherapp.data.model.UserSettings
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Language
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.NumberOfDays
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Theme
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Units
import org.koin.androidx.compose.inject

private val DarkColorPalette = darkColors(
    surface = Blue,
    onSurface = White,
    background = Navy,
    onBackground = LightBlue
)

private val LightColorPalette = lightColors(
    background = Cream,
    onBackground = Navy,
    surface = Blue,
    onSurface = White
)

@Composable
fun WeatherAppTheme(
    theme: Theme = currentAppTheme(),
    content: @Composable () -> Unit
) {
    val colors = when (theme) {
        Theme.DARK -> DarkColorPalette
        Theme.LIGHT -> LightColorPalette
        Theme.DEFAULT -> {
            if (isSystemInDarkTheme()) DarkColorPalette
            else LightColorPalette
        }
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = colors.surface
    )

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

@Composable
private fun currentAppTheme(): Theme {
    val prefs: SettingsRepository by inject()
    val settings by prefs.userSettings.collectAsState(
        initial = UserSettings(
            Units.METRIC,
            Language.ENGLISH,
            NumberOfDays.THREE,
            Theme.DEFAULT
        )
    )
    return settings.theme
}
