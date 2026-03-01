package com.youapps.onlybeans.platform

import android.Manifest.permission
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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

    fun isLocationSettingsEnabled(settingsType: LocationSettingsType = LocationSettingsType.POWER_EFFICIENCY): Flow<Boolean?>

    fun isLocationServiceEnabled(): Boolean

    fun subscribe(strategy: LocationSettingsType): Flow<LocationData?>

    suspend fun getSingleFreshLocation(): LocationData?


}


class OBLocationServicePlayServicesImpl(val applicationContext: Context) :
    OBLocationService {


    private val client: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(applicationContext)

    var lm: LocationManager? =
        applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager?


    private val _defaultPowerEfficientLocationRequest = LocationRequest
        .Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY, 3000)
        .setMinUpdateDistanceMeters(5f)
        .build()


    private val _defaultHighAccuracyLocationRequest = LocationRequest
        .Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
        .setMinUpdateDistanceMeters(2f)
        .build()

    val settingsBuilder = LocationSettingsRequest.Builder()


    override fun isLocationServiceEnabled(): Boolean {
        val isEnabled = lm?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false
        return  isEnabled
    }

    override fun isLocationSettingsEnabled(settingsType: LocationSettingsType): Flow<Boolean?> =
        callbackFlow {
            val settings = settingsBuilder.addLocationRequest(
                when (settingsType) {
                    LocationSettingsType.POWER_EFFICIENCY -> _defaultPowerEfficientLocationRequest
                    LocationSettingsType.HIGH_ACCURACY -> _defaultHighAccuracyLocationRequest
                }
            )
                .build()
            val client: SettingsClient = LocationServices.getSettingsClient(applicationContext)
            val task: Task<LocationSettingsResponse> = client.checkLocationSettings(settings)
            task.addOnSuccessListener { response ->
                val states = response.locationSettingsStates
                trySend(states?.isLocationUsable ?: false)
            }
            task.addOnFailureListener { _ ->
                trySend(false)
            }
            awaitClose()
        }


    @RequiresPermission(anyOf = [permission.ACCESS_COARSE_LOCATION, permission.ACCESS_FINE_LOCATION])
    override fun subscribe(strategy: LocationSettingsType): Flow<LocationData?> = callbackFlow {
        val selectedLocationRequest = when (strategy) {
            LocationSettingsType.POWER_EFFICIENCY -> _defaultPowerEfficientLocationRequest
            LocationSettingsType.HIGH_ACCURACY -> _defaultHighAccuracyLocationRequest
        }
        val callback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
                val location = p0.lastLocation
                location?.run {
                    trySend(
                        LocationData(
                            latitude = location.latitude,
                            longitude = location.longitude,
                            accuracy = location.accuracy,
                            altitude = location.altitude,
                            timestamp = location.time
                        )
                    )
                }
            }
        }
        client.requestLocationUpdates(
            selectedLocationRequest,
            callback,
            Looper.getMainLooper(),
        )
        awaitClose { client.removeLocationUpdates(callback) }
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


}