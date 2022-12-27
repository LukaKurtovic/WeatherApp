package hr.dice.luka_kurtovic.weatherapp.location_provider

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

/**
 * Gets a single location in order to determine where the device is now.
 *
 * @param onLocationSuccess Callback when location is successfully retrieved.
 * @param onLocationFailed Callback when retrieving location is unsuccessful.
 */
@SuppressLint("MissingPermission")
fun FusedLocationProviderClient.getCurrentLocation(
    onLocationSuccess: (Location) -> Unit,
    onLocationFailed: () -> Unit
) {
    val cancellationTokenSource = CancellationTokenSource()
    this.getCurrentLocation(
        Priority.PRIORITY_HIGH_ACCURACY,
        cancellationTokenSource.token
    ).addOnSuccessListener { location ->
        if (location != null) {
            onLocationSuccess(location)
        } else {
            onLocationFailed()
        }
        cancellationTokenSource.cancel()
    }
}
