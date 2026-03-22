package com.youapps.onlybeans.platform.network

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.annotation.RequiresPermission
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.InetSocketAddress
import java.net.Socket


interface  OBNetworkMonitor {

    sealed interface  OBNetworkState{

        fun hasInternetAccess() : Boolean = this is Available
        enum class NetworkType{
            Cellular,
            Wifi
        }
        data object Unavailable : OBNetworkState
        data class Available(val networkType: NetworkType) : OBNetworkState {
            override fun equals(other: Any?): Boolean = other is Available && other.networkType == networkType
            override fun hashCode(): Int {
                return networkType.hashCode()
            }
        }
    }


    fun startMonitoring()
    fun stopMonitoring()

    fun watchNetWorkState() : Flow<OBNetworkState>
}


class OBNetworkMonitorImpl(
    private val context:  Context
) : OBNetworkMonitor {


    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    private val _networkState = MutableStateFlow<OBNetworkMonitor.OBNetworkState>(OBNetworkMonitor.OBNetworkState.Unavailable)

    private var _currentNetworkData : Network?=null

    private val localCoroutineScope = CoroutineScope(Dispatchers.IO)



    override fun watchNetWorkState(): Flow<OBNetworkMonitor.OBNetworkState> = _networkState.distinctUntilChanged { old, new -> old == new}

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            // Network is available
            Log.d("NetworkMonitor", "Network available")
            _currentNetworkData = network
        }

        override fun onLost(network: Network) {
            _networkState.update {
                OBNetworkMonitor.OBNetworkState.Unavailable
            }
            _currentNetworkData = null
            Log.d("NetworkMonitor", "Network lost")
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            _currentNetworkData = network
            val hasInternet = networkCapabilities
                .hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            val isWifi = networkCapabilities
                .hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            val isCellular = networkCapabilities
                .hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)

            if (hasInternet && isWifi) {
                 localCoroutineScope.launch {
                     val isReachable =  isInternetReachable()
                     if (isReachable) {
                         _networkState.update {
                             OBNetworkMonitor.OBNetworkState.Available(
                                 networkType = OBNetworkMonitor.OBNetworkState.NetworkType.Wifi
                             )
                         }
                         Log.d("NetworkMonitor", "Internet Available: Cellular")
                     } else {
                         _networkState.update {
                             OBNetworkMonitor.OBNetworkState.Unavailable
                         }
                         Log.d("NetworkMonitor", "Internet Unavailable")
                     }
                }
                return
            }
            if (hasInternet && isCellular){
                localCoroutineScope.launch {
                    val isReachable =  isInternetReachable()
                    if (isReachable) {
                        _networkState.update {
                            OBNetworkMonitor.OBNetworkState.Available(
                                networkType = OBNetworkMonitor.OBNetworkState.NetworkType.Cellular
                            )
                        }
                        Log.d("NetworkMonitor", "Internet Available: Wifi")
                    } else {
                        _networkState.update {
                            OBNetworkMonitor.OBNetworkState.Unavailable
                        }
                        Log.d("NetworkMonitor", "Internet Unavailable")
                    }
                }
                return
            }
        }

        override fun onUnavailable() {
            _networkState.update {
                OBNetworkMonitor.OBNetworkState.Unavailable
            }
            _currentNetworkData = null
            Log.d("NetworkMonitor", "Network unavailable")
        }

        override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
            super.onBlockedStatusChanged(network, blocked)
            _networkState.update {
                OBNetworkMonitor.OBNetworkState.Unavailable
            }
            _currentNetworkData = null
            Log.d("NetworkMonitor", "Network blocked")}
    }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
   override fun startMonitoring() {
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun stopMonitoring() {
        _currentNetworkData = null
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    suspend fun isInternetReachable(
        host: String = "8.8.8.8",   // Google DNS
        port: Int = 53,              // DNS port
        timeoutMs: Int = 1500
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            Socket().use { socket ->
                socket.connect(InetSocketAddress(host, port), timeoutMs)
                true
            }
        } catch (e: Exception) {
            false
        }
    }

}