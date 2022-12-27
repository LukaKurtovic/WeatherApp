package hr.dice.luka_kurtovic.weatherapp.ui.daily_details

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import hr.dice.luka_kurtovic.weatherapp.data.model.UserSettings
import hr.dice.luka_kurtovic.weatherapp.remote.model.DailyWeatherDetails
import hr.dice.luka_kurtovic.weatherapp.ui.daily_details.model.DailyDetailsScreenComponent
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Language
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.NumberOfDays
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Theme
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Units
import hr.dice.luka_kurtovic.weatherapp.ui.theme.WeatherAppTheme
import org.koin.androidx.compose.inject

/**
 * Displays the detailed weather information for a particular day.
 *
 * @param state [DailyWeatherDetails] that holds weather information for the given day.
 */
@Composable
@Destination
fun DailyDetailsScreen(state: DailyWeatherDetails) {
    val viewModel by inject<DailyDetailsViewModel>()
    viewModel.prepareDataForUI(state)
    val data by viewModel.uiState.collectAsState()
    val userSettings by viewModel.userSettings.collectAsState()
    DailyDetailsScreenContent(detailsComponents = data, userSettings = userSettings)
}

@Composable
private fun DailyDetailsScreenContent(
    detailsComponents: List<DailyDetailsScreenComponent>,
    userSettings: UserSettings
) {
    LazyColumn(
        modifier = Modifier.background(MaterialTheme.colors.background)
    ) {
        items(items = detailsComponents) { component ->
            when (component) {
                is DailyDetailsScreenComponent.Item -> DetailItem(component)
                is DailyDetailsScreenComponent.Card -> WeatherCard(component, userSettings)
                is DailyDetailsScreenComponent.Header -> Header(component)
            }
        }
    }
}

@Composable
private fun WeatherCard(
    data: DailyDetailsScreenComponent.Card,
    userSettings: UserSettings
) {
    Card(
        border = BorderStroke(1.dp, MaterialTheme.colors.onBackground),
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text(
                    text = "${data.temperature.toInt()}${userSettings.units.temperatureSymbol}",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = data.description,
                    fontSize = 18.sp,
                )
            }
            Column(
                horizontalAlignment = CenterHorizontally,
                modifier = Modifier
                    .align(CenterVertically)
                    .fillMaxWidth(0.4f)
            ) {
                AsyncImage(
                    model = data.icon,
                    contentDescription = "Weather icon",
                    modifier = Modifier.size(60.dp)
                )
            }
        }
    }
}

@Composable
private fun DetailItem(
    data: DailyDetailsScreenComponent.Item
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(start = 26.dp, top = 4.dp, bottom = 10.dp)
    ) {
        Text(
            text = stringResource(id = data.title),
            color = MaterialTheme.colors.onBackground,
            fontSize = 12.sp
        )
        Text(
            text = data.value,
            color = MaterialTheme.colors.onBackground
        )
        Divider(thickness = 1.dp, color = MaterialTheme.colors.onBackground)
    }
}

@Composable
private fun Header(
    data: DailyDetailsScreenComponent.Header
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(start = 14.dp, top = 20.dp, bottom = 2.dp)
    ) {
        Text(
            text = stringResource(id = data.title),
            fontSize = 20.sp,
            color = MaterialTheme.colors.onBackground,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
@Preview
private fun PreviewDailyDetailsScreen() {
    WeatherAppTheme(Theme.LIGHT) {
    }
}

@SuppressLint("ResourceType")
@Composable
@Preview
private fun PreviewHeaderWithItems() {
    WeatherAppTheme(Theme.LIGHT) {
        Column {
            Header(data = DailyDetailsScreenComponent.Header(1))
            DetailItem(DailyDetailsScreenComponent.Item(1, "10"))
            DetailItem(DailyDetailsScreenComponent.Item(1, "10"))
            DetailItem(DailyDetailsScreenComponent.Item(1, "10"))
            DetailItem(DailyDetailsScreenComponent.Item(1, "10"))
        }
    }
}

@Composable
@Preview
private fun PreviewWeatherCard() {
    WeatherAppTheme(Theme.LIGHT) {
        WeatherCard(
            DailyDetailsScreenComponent.Card(
                temperature = 10.0,
                description = "Kiša",
                icon = ""
            ),
            userSettings = UserSettings(
                units = Units.METRIC,
                language = Language.ENGLISH,
                numberOfDays = NumberOfDays.THREE,
                theme = Theme.DEFAULT
            )
        )
    }
}

@SuppressLint("ResourceType")
@Composable
@Preview
private fun PreviewDetailItem() {
    WeatherAppTheme(Theme.LIGHT) {
        DetailItem(DailyDetailsScreenComponent.Item(5, "Vrijednost"))
    }
}
