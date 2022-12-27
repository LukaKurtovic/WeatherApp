package hr.dice.luka_kurtovic.weatherapp.ui.start_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.dice.luka_kurtovic.weatherapp.data.LocationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StartScreenViewModel(private val locationRepository: LocationRepository) : ViewModel() {
    private val _isLocationSaved: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isLocationSaved = _isLocationSaved.asStateFlow()

    init {
        viewModelScope.launch {
            _isLocationSaved.value = locationRepository.isLocationSaved()
        }
    }
}
