package hr.dice.luka_kurtovic.weatherapp.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.annotation.Destination
import com.robertlevonyan.compose.buttontogglegroup.RowToggleButtonGroup
import hr.dice.luka_kurtovic.weatherapp.R
import hr.dice.luka_kurtovic.weatherapp.data.model.UserSettings
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Language
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.NumberOfDays
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Theme
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Units
import hr.dice.luka_kurtovic.weatherapp.ui.theme.WeatherAppTheme
import org.koin.androidx.compose.viewModel

/**
 * Manages state of the UI for [SettingsScreenContent].
 */
@Destination
@Composable
fun SettingsScreen() {
    val viewModel by viewModel<SettingsViewModel>()
    val uiState by viewModel.settings.collectAsState()
    SettingsScreenContent(
        updateUnits = {
            viewModel.updateUnits(it)
        },
        updateLanguage = {
            viewModel.updateLanguage(it)
        },
        updateNumberOfDays = {
            viewModel.updateNumberOfDays(it)
        },
        updateTheme = {
            viewModel.updateTheme(it)
        },
        userSettings = uiState,
    )
}

/**
 * Displays application settings that can be changed by user.
 */
@Composable
private fun SettingsScreenContent(
    userSettings: UserSettings,
    updateUnits: (Units) -> Unit,
    updateLanguage: (Language) -> Unit,
    updateNumberOfDays: (NumberOfDays) -> Unit,
    updateTheme: (Theme) -> Unit
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colors.background)
            .fillMaxHeight()
    ) {
        ToggleButtonGroupWithLabel(
            numberOfButtons = 3,
            labelText = stringResource(R.string.theme),
            buttonTexts = arrayOf(
                stringResource(R.string.light),
                stringResource(R.string.dark),
                stringResource(R.string.system)
            ),
            callback = {
                updateTheme(Theme.values()[it])
            },
            selectedIndex = userSettings.theme.ordinal
        )

        ToggleButtonGroupWithLabel(
            numberOfButtons = 3,
            labelText = stringResource(R.string.language),
            buttonTexts = arrayOf(
                stringResource(R.string.croatian),
                stringResource(R.string.english),
                stringResource(R.string.german)
            ),
            callback = {
                updateLanguage(Language.values()[it])
            },
            selectedIndex = userSettings.language.ordinal
        )

        ToggleButtonGroupWithLabel(
            numberOfButtons = 3,
            labelText = stringResource(R.string.units),
            buttonTexts = arrayOf(
                stringResource(R.string.standard),
                stringResource(R.string.imperial),
                stringResource(R.string.metric)
            ),
            callback = {
                updateUnits(Units.values()[it])
            },
            selectedIndex = userSettings.units.ordinal
        )

        ToggleButtonGroupWithLabel(
            numberOfButtons = 5,
            labelText = stringResource(R.string.number_of_days),
            buttonTexts = arrayOf("3", "4", "5", "6", "7"),
            callback = {
                updateNumberOfDays(NumberOfDays.values()[it])
            },
            selectedIndex = userSettings.numberOfDays.ordinal
        )
    }
}

@Composable
private fun ToggleButtonGroupWithLabel(
    numberOfButtons: Int,
    labelText: String,
    buttonTexts: Array<String>,
    callback: (Int) -> Unit,
    selectedIndex: Int
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = labelText,
            modifier = Modifier.padding(8.dp),
            color = MaterialTheme.colors.onBackground,
            fontSize = 16.sp
        )
        RowToggleButtonGroup(
            buttonCount = numberOfButtons,
            selectedColor = MaterialTheme.colors.onBackground,
            unselectedColor = MaterialTheme.colors.background,
            selectedContentColor = MaterialTheme.colors.background,
            unselectedContentColor = MaterialTheme.colors.onBackground,
            buttonTexts = buttonTexts,
            borderColor = MaterialTheme.colors.onBackground,
            primarySelection = selectedIndex,
            shape = RoundedCornerShape(30.dp),
            buttonHeight = 50.dp,
            onButtonClick = callback
        )
    }
}

@Composable
@Preview
private fun PreviewSettingsScreen() {
    WeatherAppTheme(Theme.LIGHT) {
        SettingsScreenContent(
            updateLanguage = {},
            updateUnits = {},
            userSettings = UserSettings(
                Units.METRIC,
                Language.ENGLISH,
                NumberOfDays.THREE,
                Theme.DEFAULT
            ),
            updateNumberOfDays = {},
            updateTheme = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun PreviewToggleButtonGroupWithLabel() {
    WeatherAppTheme(Theme.LIGHT) {
        ToggleButtonGroupWithLabel(
            numberOfButtons = 3,
            labelText = "NAČIN PRIKAZA",
            buttonTexts = arrayOf(
                "Svijetlo",
                "Tamno",
                "Zadano"
            ),
            callback = {},
            selectedIndex = 0
        )
    }
}
