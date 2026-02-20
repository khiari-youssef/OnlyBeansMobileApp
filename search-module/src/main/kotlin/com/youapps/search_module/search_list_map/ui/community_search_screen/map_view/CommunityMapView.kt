package com.youapps.search_module.search_list_map.ui.community_search_screen.map_view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import com.youapps.search_module.search_list_map.ui.community_search_state.CommunitySearchStateHolder



@Composable
fun CommunityMapView(
    modifier: Modifier = Modifier,
    screenState : CommunitySearchStateHolder
) {
    val tunisia = LatLng(37.57,11.6)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(tunisia, 7f)
    }
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        contentDescription = "Community Map",
        onMapLoaded = {

        },
        content = {
            val markerState = rememberUpdatedMarkerState()
            Marker(
                state = markerState,
                title = "Singapore",
                snippet = "Marker in Singapore",
                onInfoWindowClick = {
                    it.hideInfoWindow()
                }
            )
        }
    )
}