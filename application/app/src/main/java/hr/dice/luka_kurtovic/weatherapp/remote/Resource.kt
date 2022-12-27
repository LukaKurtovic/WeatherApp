package hr.dice.luka_kurtovic.weatherapp.remote

/**
 * Wrapper class where each subclass represents a state returned by a data source request.
 */
sealed class Resource {
    /**
     * Encapsulates the given data as successful value. Represents a successful outcome of data source request.
     *
     * @property data Value returned from data source.
     */
    data class Success<T>(val data: T) : Resource()

    /**
     * Encapsulates the given throwable as failure value. Represents a failed outcome of data source request.
     *
     * @property exception Thrown from the data source and caused an error state.
     */
    data class Error(val exception: Exception) : Resource()
}
