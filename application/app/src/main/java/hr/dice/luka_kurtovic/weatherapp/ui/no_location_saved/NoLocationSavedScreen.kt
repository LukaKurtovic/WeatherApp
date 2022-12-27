package hr.dice.luka_kurtovic.weatherapp.ui.no_location_saved

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
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
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import hr.dice.luka_kurtovic.weatherapp.R
import hr.dice.luka_kurtovic.weatherapp.ui.UiState
import hr.dice.luka_kurtovic.weatherapp.ui.destinations.BottomNavigationContainerScreenDestination
import hr.dice.luka_kurtovic.weatherapp.ui.destinations.NoLocationSavedScreenDestination
import hr.dice.luka_kurtovic.weatherapp.ui.loading.LoadingScreen
import hr.dice.luka_kurtovic.weatherapp.ui.no_location.NoLocationScreen
import hr.dice.luka_kurtovic.weatherapp.ui.settings.model.Theme
import hr.dice.luka_kurtovic.weatherapp.ui.theme.WeatherAppTheme
import org.koin.androidx.compose.viewModel

/**
 * Handles navigation and location permissions.
 *
 * @param navigator NavController wrapper for navigation between screens inside RootNavGraph.
 */
@OptIn(ExperimentalPermissionsApi::class)
@Destination
@Composable
fun NoLocationSavedScreen(
    navigator: DestinationsNavigator
) {
    val viewModel by viewModel<NoLocationSavedViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    val permissionsState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    )
    Crossfade(
        targetState = uiState,
        animationSpec = tween(500)
    ) { state ->
        when (state) {
            is UiState.Loading -> LoadingScreen()
            is UiState.Error -> NoLocationScreen(onTryAgainClick = viewModel::getLocation)
            is UiState.Success<*> -> LaunchedEffect(Unit) {
                navigateToBottomNavScreen(navigator)
            }
            else -> {
                NoLocationSavedScreenContent(
                    onShareCurrentLocationClick = {
                        if (permissionsState.allPermissionsGranted) {
                            viewModel.getLocation()
                        } else {
                            permissionsState.launchMultiplePermissionRequest()
                        }
                    }
                )
            }
        }

        if (permissionsState.allPermissionsGranted) {
            viewModel.getLocation()
        } else if (!permissionsState.allPermissionsGranted && permissionsState.shouldShowRationale) {
            NoLocationScreen(onTryAgainClick = viewModel::getLocation)
        }
    }
}

/**
 * Holds content that is displayed on app start if there is no previously saved locations.
 *
 * @param onShareCurrentLocationClick Callback when user clicks on "Share current location" button.
 */
@Composable
private fun NoLocationSavedScreenContent(
    onShareCurrentLocationClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(8.dp)
        ) {
            Spacer(modifier = Modifier.weight(0.5f))
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_my_location),
                tint = MaterialTheme.colors.onBackground,
                contentDescription = stringResource(R.string.location_icon_description),
                modifier = Modifier
                    .size(180.dp)
                    .padding(bottom = 32.dp)
            )
            Text(
                text = stringResource(R.string.no_location_saved),
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.padding(bottom = 16.dp),
                textAlign = TextAlign.Center

            )
            Text(
                text = stringResource(R.string.location_needed),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    onShareCurrentLocationClick()
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
            ) {
                Text(
                    text = stringResource(R.string.share_current_location),
                    modifier = Modifier.padding(6.dp)
                )
            }
        }
    }
}

fun navigateToBottomNavScreen(
    navigator: DestinationsNavigator,
) {
    navigator.navigate(BottomNavigationContainerScreenDestination) {
        popUpTo(NoLocationSavedScreenDestination.route) {
            inclusive = true
        }
    }
}

@Preview
@Composable
private fun PreviewNoLocationSavedScreen() {
    WeatherAppTheme(theme = Theme.LIGHT) {
        NoLocationSavedScreenContent {
        }
    }
}
