package com.youapps.onlybeans.platform

import android.Manifest.permission
import android.content.Context
import android.location.Location
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.suspendCancellableCoroutine


data class LocationData(
    val latitude: Double,
    val longitude: Double,
    val accuracy: Float,
    val altitude: Double? = null,
    val timestamp: Long
)

enum class LocationSettingsType {
    POWER_EFFICIENCY,
    HIGH_ACCURACY
}


interface OBLocationService {

    suspend fun isLocationEnabled(settingsType: LocationSettingsType = LocationSettingsType.POWER_EFFICIENCY): Boolean

    fun subscribe(strategy: LocationSettingsType): StateFlow<LocationData?>

    suspend fun getSingleFreshLocation(): LocationData?

    fun unSubscribe()

}


class OBLocationServicePlayServicesImpl(val applicationContext: Context) : LocationListener,
    OBLocationService {


    private val client: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(applicationContext)


    private val _defaultPowerEfficientLocationRequest = LocationRequest
        .Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY, 3000)
        .setMinUpdateDistanceMeters(5f)
        .build()


    private val _defaultHighAccuracyLocationRequest = LocationRequest
        .Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
        .setMinUpdateDistanceMeters(2f)
        .build()

    val settingsBuilder = LocationSettingsRequest.Builder()


    private val _locationDataFlow: MutableStateFlow<LocationData?> =
        MutableStateFlow(value = null)


    override suspend fun isLocationEnabled(settingsType: LocationSettingsType): Boolean {
        val settings = settingsBuilder.addLocationRequest(
            when (settingsType) {
                LocationSettingsType.POWER_EFFICIENCY -> _defaultPowerEfficientLocationRequest
                LocationSettingsType.HIGH_ACCURACY -> _defaultHighAccuracyLocationRequest
            }
        ).build()
        val client: SettingsClient = LocationServices.getSettingsClient(applicationContext)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(settings)
        val result = suspendCancellableCoroutine<Boolean> { continuation ->
            task.addOnSuccessListener { response ->
                val states = response.locationSettingsStates
                continuation.resumeWith(Result.success(states?.isLocationUsable ?: false))
            }
            task.addOnFailureListener { exception ->
                continuation.resumeWith(Result.success(false))
            }
            continuation.invokeOnCancellation {
                continuation.resumeWith(Result.success(false))
            }
        }
        return result
    }


    @RequiresPermission(anyOf = [permission.ACCESS_COARSE_LOCATION, permission.ACCESS_FINE_LOCATION])
    override fun subscribe(strategy: LocationSettingsType): StateFlow<LocationData?> {
        val selectedLocationRequest = when (strategy) {
            LocationSettingsType.POWER_EFFICIENCY -> _defaultPowerEfficientLocationRequest
            LocationSettingsType.HIGH_ACCURACY -> _defaultHighAccuracyLocationRequest
        }
        client.requestLocationUpdates(
            selectedLocationRequest,
            this,
            applicationContext.mainLooper,
        )
        return _locationDataFlow;
    }


    @RequiresPermission(allOf = [permission.ACCESS_FINE_LOCATION, permission.ACCESS_COARSE_LOCATION])
    override suspend fun getSingleFreshLocation(): LocationData? {
        val result = suspendCancellableCoroutine<LocationData?> { continuation ->
            client.lastLocation
                .addOnSuccessListener { location: Location? ->
                    continuation.resumeWith(
                        Result.success(location?.run {
                            LocationData(
                                latitude = location.latitude,
                                longitude = location.longitude,
                                accuracy = location.accuracy,
                                altitude = location.altitude,
                                timestamp = location.time
                            )
                        })
                    )
                }
        }
        return result
    }


    override fun unSubscribe() {
        _locationDataFlow.update {
            null
        }
        client.removeLocationUpdates(this)
    }

    override fun onLocationChanged(location: Location) {
        _locationDataFlow.update {
            LocationData(
                latitude = location.latitude,
                longitude = location.longitude,
                accuracy = location.accuracy,
                altitude = location.altitude,
                timestamp = location.time
            )
        }
    }


}