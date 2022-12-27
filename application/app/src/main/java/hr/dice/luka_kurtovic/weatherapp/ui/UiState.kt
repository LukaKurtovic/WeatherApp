package hr.dice.luka_kurtovic.weatherapp.ui

sealed interface UiState {
    object Loading : UiState
    data class Success<T>(val data: T) : UiState
    data class Error(val exception: Exception?) : UiState
}
