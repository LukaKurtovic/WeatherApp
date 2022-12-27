package hr.dice.luka_kurtovic.weatherapp.ui.no_location

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hr.dice.luka_kurtovic.weatherapp.R
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Theme
import hr.dice.luka_kurtovic.weatherapp.ui.theme.WeatherAppTheme

/**
 * Presented to the user when the current location couldn't be retrieved.
 *
 * @param onTryAgainClick Callback when "Try again" button is clicked.
 */
@SuppressLint("MissingPermission")
@Composable
fun NoLocationScreen(
    onTryAgainClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Spacer(modifier = Modifier.weight(0.5f))
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_no_location),
            contentDescription = stringResource(R.string.no_location),
            tint = MaterialTheme.colors.onBackground,
            modifier = Modifier
                .size(180.dp)
                .padding(bottom = 32.dp)
        )
        Text(
            text = stringResource(R.string.cant_find_location),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.check_location_access),
            style = MaterialTheme.typography.subtitle1,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                onTryAgainClick()
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 16.dp)
        ) {
            Text(
                text = stringResource(R.string.try_again),
                modifier = Modifier.padding(6.dp)
            )
        }
    }
}

@Composable
@Preview
private fun PreviewLoadingScreen() {
    WeatherAppTheme(Theme.LIGHT) {
        NoLocationScreen {
        }
    }
}
