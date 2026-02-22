package com.youapps.onlybeans.platform

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext


val LocalLocationStateEnabled = compositionLocalOf {
    false
}

class LocationProviderChangedReceiver(
    private val onLocationStatusChanged: (isEnabled: Boolean) -> Unit
) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == LocationManager.PROVIDERS_CHANGED_ACTION) {
            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled =
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            onLocationStatusChanged(isGpsEnabled || isNetworkEnabled)
        }
    }
}


@Composable
fun OBLocationServiceStateLocale(
    obLocationService: OBLocationService,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val isLocationDefaultEnabled = produceState(initialValue = false) {
        value = obLocationService.isLocationEnabled()
    }
    var isLocationEnabled: Boolean by remember {
        mutableStateOf(isLocationDefaultEnabled.value)
    }


    DisposableEffect(context) {
        val receiver = LocationProviderChangedReceiver { isEnabled ->
            isLocationEnabled = isEnabled
        }

        val filter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        context.registerReceiver(receiver, filter)

        onDispose {
            context.unregisterReceiver(receiver)
        }
    }
    CompositionLocalProvider(LocalLocationStateEnabled provides isLocationEnabled) {
        content()
    }
}