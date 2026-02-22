package com.youapps.search_module.search_list_map.ui.community_search_screen.map_view

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.clustering.Clustering
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import com.youapps.onlybeans.domain.entities.users.OBLocation
import com.youapps.onlybeans.platform.LocalLocationStateEnabled
import com.youapps.onlybeans.search_module.R
import com.youapps.search_module.search_list_map.ui.community_search_state.CommunitySearchStateHolder
import com.youapps.search_module.search_list_map.ui.community_search_state.SearchByAreaState
import com.youapps.search_module.search_list_map.ui.community_search_state.SearchByRegionBounds
import com.youapps.search_module.search_list_map.ui.components.MapSearchAreaChip
import com.youapps.search_module.search_list_map.ui.components.OBMapCluster
import com.youapps.search_module.search_list_map.ui.components.OBMapRecenterButton
import kotlinx.coroutines.launch




@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun CommunityMapView(
    modifier: Modifier = Modifier,
    screenState : CommunitySearchStateHolder,
    onSearchVisibleArea : (SearchByRegionBounds)-> Unit
) {
    val context = LocalContext.current
    val tunisia = screenState.locationSource.getCurrentLocation()?.run {
        LatLng(
            latitude,
            longitude
        )
    }
    val cameraPositionState = rememberCameraPositionState {
        tunisia?.run {
            position = CameraPosition.fromLatLngZoom(tunisia, 7f)
        }
    }

    val isLocationEnabled = LocalLocationStateEnabled.current

    Box(
        modifier = modifier
    ){
        val mapCoroutineScope = rememberCoroutineScope()
        val mapUiState = remember(isLocationEnabled) {
            MapUiSettings(
                compassEnabled = true,
                zoomControlsEnabled = false,
                myLocationButtonEnabled = isLocationEnabled,
                mapToolbarEnabled = false
            )
        }
        val mapProperties = remember(isLocationEnabled) {
            MapProperties(
                mapType = MapType.NORMAL,
                isMyLocationEnabled = isLocationEnabled
            )
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            contentDescription = stringResource(R.string.content_description_map_view),
            uiSettings = mapUiState,
            locationSource = screenState.locationSource,
            properties =mapProperties,
            onMapLoaded = {

            },
            content = {
                val stateSnapShot = screenState.searchOperationState.value
                if(stateSnapShot is SearchByAreaState.Success){
                    Clustering(
                        items = stateSnapShot.data.data,
                        onClusterClick = { cluster ->
                            false
                        },
                        clusterContent = { data->
                            OBMapCluster(
                                size = data.size
                            )
                        },
                        onClusterItemClick = { item ->
                            // Handle clicking an individual marker
                            false
                        }
                    )
                }

            }
        )
        if(screenState.searchOperationState.value is SearchByAreaState.Loading){
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center),
                color = MaterialTheme.colorScheme.secondary
            )
        }
        if(screenState.searchOperationState.value is SearchByAreaState.Error){
            Toast.makeText(context, stringResource(com.youapps.onlybeans.designsystem.R.string.error_toast_unknown), Toast.LENGTH_SHORT).show()
        }
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            val (refreshBtn,recenterBtn)= createRefs()
            OBMapRecenterButton(
                enabled = isLocationEnabled,
                modifier = Modifier.constrainAs(recenterBtn) {
                    bottom.linkTo(parent.bottom,16.dp)
                    end.linkTo(parent.end,12.dp)
                },
                onClick = {
                    tunisia?.run {
                        mapCoroutineScope.launch {

                            cameraPositionState.animate(
                                update = CameraUpdateFactory.newLatLngZoom(tunisia, 15f),
                                durationMs = 1000
                            )
                        }
                    }

                }
            )

            AnimatedVisibility(
                modifier = Modifier
                    .constrainAs(refreshBtn) {
                        bottom.linkTo(parent.bottom, 12.dp)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    }
                    .wrapContentSize(),
                visible = (screenState.searchOperationState.value !is SearchByAreaState.Loading) &&  cameraPositionState.isMoving.not()
            ) {
                MapSearchAreaChip(
                    modifier = Modifier
                        .wrapContentSize(),
                    onClick = {
                        cameraPositionState.projection?.visibleRegion?.latLngBounds?.run {
                            onSearchVisibleArea(
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
           /*
            val isLocationEnabled = remember {
                mutableStateOf(screenState.isLocationSettingEnabled.value)
            }
            AnimatedVisibility(
                visible = isLocationEnabled.value.not()
            ) {
                EnableLocationChip(
                    modifier = Modifier.constrainAs(locationBtn) {
                        bottom.linkTo(parent.bottom,12.dp)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                    },
                    onLocationEnabled = {
                        isLocationEnabled.value = true
                    }
                )
            }
            */
        }
    }
}


