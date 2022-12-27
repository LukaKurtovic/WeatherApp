package hr.dice.luka_kurtovic.weatherapp.ui.no_location_saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import hr.dice.luka_kurtovic.weatherapp.data.LocationRepository
import hr.dice.luka_kurtovic.weatherapp.data.model.Location
import hr.dice.luka_kurtovic.weatherapp.location_provider.getCurrentLocation
import hr.dice.luka_kurtovic.weatherapp.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for [NoLocationSavedScreen].
 *
 * @property locationRepository Communication with data source for getting information about locations.
 * @property locationClient A [FusedLocationProviderClient] for requesting location updates.
 */
class NoLocationSavedViewModel(
    private val locationRepository: LocationRepository,
    private val locationClient: FusedLocationProviderClient
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState?>(null)
    val uiState = _uiState.asStateFlow()

    fun getLocation() {
        _uiState.value = UiState.Loading
        locationClient.getCurrentLocation(
            onLocationSuccess = { location ->
                _uiState.value = UiState.Success(
                    data = location
                )
                insertLocation(location.latitude, location.longitude)
            },
            onLocationFailed = {
                _uiState.value = UiState.Error(null)
            }
        )
    }

    /**
     * Inserts active location in database.
     */
    private fun insertLocation(latitude: Double, longitude: Double) {
        val location = Location("", latitude.toFloat(), longitude.toFloat(), true)
        viewModelScope.launch {
            locationRepository.insertLocation(location)
        }
    }
}
