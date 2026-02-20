package com.youapps.search_module.search_list_map.ui.community_search_screen.map_view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import com.youapps.onlybeans.domain.entities.users.OBLocation
import com.youapps.onlybeans.search_module.R
import com.youapps.search_module.search_list_map.ui.community_search_state.CommunitySearchStateHolder
import com.youapps.search_module.search_list_map.ui.community_search_state.SearchByAreaState
import com.youapps.search_module.search_list_map.ui.community_search_state.SearchByRegionBounds
import com.youapps.search_module.search_list_map.ui.components.MapSearchAreaChip


@Composable
fun CommunityMapView(
    modifier: Modifier = Modifier,
    screenState : CommunitySearchStateHolder,
    searchVisibleArea : (SearchByRegionBounds)-> Unit
) {
    val tunisia = LatLng(37.57,11.6)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(tunisia, 7f)
    }

    Box(
        modifier = modifier
    ){
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            contentDescription = stringResource(R.string.content_description_map_view),
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
        if ( screenState.searchOperationState.value is SearchByAreaState.Loading){
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center),
                color = MaterialTheme.colorScheme.secondary
            )
        }

        AnimatedVisibility(
            modifier = Modifier
                .padding(
                    bottom = 12.dp
                )
                .align(Alignment.BottomCenter)
                .wrapContentSize(),
            visible = (screenState.searchOperationState.value !is SearchByAreaState.Loading) &&  cameraPositionState.isMoving.not()
        ) {
            MapSearchAreaChip(
                modifier = Modifier
                    .wrapContentSize(),
                onClick = {
                    cameraPositionState.projection?.visibleRegion?.latLngBounds?.run {
                        searchVisibleArea(
                            SearchByRegionBounds(
                                northEast = OBLocation(
                                    latitude = this.northeast.latitude,
                                    longitude = this.northeast.longitude
                                ),
                                southWest = OBLocation(
                                    latitude = this.southwest.latitude,
                                    longitude = this.southwest.longitude
                                )
                            )
                        )
                    }
                }
            )
        }
    }
}