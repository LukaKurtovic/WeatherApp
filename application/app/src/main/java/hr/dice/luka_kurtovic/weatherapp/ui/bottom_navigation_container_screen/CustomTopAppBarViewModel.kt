package hr.dice.luka_kurtovic.weatherapp.ui.bottom_navigation_container_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.dice.luka_kurtovic.weatherapp.data.LocationRepository
import hr.dice.luka_kurtovic.weatherapp.ui.bottom_navigation_container_screen.model.TopAppBarData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * Holding UI related states that are shown in [CustomTopAppBar].
 *
 * @property repository Communication with data source for getting information about locations.
 */
class CustomTopAppBarViewModel(private val repository: LocationRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(TopAppBarData("", LocalDate.now()))
    val uiState = _uiState.asStateFlow()

    init {
        getActiveLocation()
    }

    private fun getActiveLocation() {
        viewModelScope.launch {
            repository.getActiveLocation().collect { location ->
                _uiState.value = _uiState.value.copy(city = location.city)
            }
        }
    }

    /**
     * Updates [_uiState] object with provided [date] parameter.
     */
    fun updateDate(date: LocalDate) {
        _uiState.value = _uiState.value.copy(date = date)
    }
}
