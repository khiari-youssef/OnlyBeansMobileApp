package com.youapps.search_module.search_list_map.ui.community_search_screen.map_view

import android.location.Location
import androidx.compose.runtime.Immutable
import com.google.android.gms.maps.LocationSource

@Immutable
class ManualLocationSource : LocationSource {
    private var mapListener: LocationSource.OnLocationChangedListener? = null
    private var currentLocation : Location?=null


    override fun activate(listener: LocationSource.OnLocationChangedListener) {
        this.mapListener = listener
    }

    override fun deactivate() {
        // The Map calls this when it's destroyed.
        this.mapListener = null
    }

    fun getCurrentLocation() : Location?= currentLocation

    // You call this manually to push a new location to the blue dot
    fun pushLocation(location: Location) {
        currentLocation= location
        mapListener?.onLocationChanged(location)
    }
}