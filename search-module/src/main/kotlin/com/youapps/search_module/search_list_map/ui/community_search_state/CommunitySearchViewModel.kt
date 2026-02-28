package com.youapps.search_module.search_list_map.ui.community_search_state

import android.content.Context
import android.location.Location
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.youapps.onlybeans.contracts.UseCaseContract
import com.youapps.onlybeans.data.repositories.AppMetaDataAPI
import com.youapps.onlybeans.platform.LocationSettingsType
import com.youapps.onlybeans.platform.OBLocationService
import com.youapps.search_module.search_list_map.domain.entities.MapSearchDataPoint
import com.youapps.search_module.search_list_map.domain.entities.OBMapSearchQuery
import com.youapps.search_module.search_list_map.ui.community_search_screen.map_view.DataClusterItem
import com.youapps.search_module.search_list_map.ui.community_search_screen.map_view.ManualLocationSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.Koin

class CommunitySearchViewModel(
    private val locationService: OBLocationService,
    private val appMetaDataAPI: AppMetaDataAPI,
    private val useCase: UseCaseContract<OBMapSearchQuery, List<MapSearchDataPoint>>,
    private val appContext : Context
) : ViewModel() {


    init {
        fetchSearchFilter()
    }

    private val _currentSearchQuery: MutableStateFlow<String?> = MutableStateFlow(null)
    val currentSearchQuery: StateFlow<String?> = _currentSearchQuery

    private val _searchFilterList: MutableStateFlow<SearchFilterList?> = MutableStateFlow(null)
    val searchFilterList: StateFlow<SearchFilterList?> = _searchFilterList

    private val _selectedFilterIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectedFilterIndex: StateFlow<Int> = _selectedFilterIndex

    private val _currentRadiusValue: MutableStateFlow<Float> = MutableStateFlow(1f)
    val currentRadiusValue: StateFlow<Float> = _currentRadiusValue

    private val _currentSearchByAreaState: MutableStateFlow<SearchByAreaState> =
        MutableStateFlow(SearchByAreaState.Idle)
    val currentSearchByAreaState: StateFlow<SearchByAreaState> = _currentSearchByAreaState

    val currentLocationSource: ManualLocationSource = ManualLocationSource()



    fun listenToLocationUpdates() {
        viewModelScope.launch {
            locationService.subscribe(strategy = LocationSettingsType.HIGH_ACCURACY)
                .collect { location ->
                    location?.run {
                        currentLocationSource.pushLocation(Location("manual").apply {
                            latitude = location.latitude
                            longitude = location.longitude
                        })
                    }
                    Toast.makeText(appContext,location.run {
                        "${this?.latitude},${this?.longitude}"
                    }, Toast.LENGTH_SHORT).show()
                }
        }
    }


    /*
    fun startListeningToCurrentLocationUpdates() {
        viewModelScope.launch {
            val country =   appMetaDataAPI.getDeviceLocalCountry()
             locationService.getSingleFreshLocation()?.let { firstLocationFix->
                 currentLocationSource.pushLocation(Location("manual").apply {
                     latitude = firstLocationFix.latitude
                     longitude = firstLocationFix.longitude
                 })
             }

            locationService.subscribe(strategy = LocationSettingsType.POWER_EFFICIENCY)
                .collect { location ->
                    location?.run {
                        currentLocationSource.pushLocation(Location("manual").apply {
                            latitude = location.latitude
                            longitude = location.longitude
                        })
                    }
                }

        }
    }
     */


    fun updateSearchQuery(searchQuery: String) {
        _currentSearchQuery.value = searchQuery
    }

    fun setSelectedFilterIndex(index: Int) {
        _selectedFilterIndex.update {
            index
        }
    }

    fun setRadiusValue(radiusValue: Float) {
        _currentRadiusValue.update {
            radiusValue
        }
    }

    fun fetchSearchFilter() {
        viewModelScope.launch {
            delay(1000)
            _searchFilterList.update {
                SearchFilterList(
                    data = listOf(
                        "All", "Baristas", "CoffeeShops", "CoffeeRoasters", "CoffeeFarms"
                    )
                )
            }
        }
    }


    fun searchVisibleArea(bounds: SearchByRegionBounds) {
        _currentSearchByAreaState.update {
            SearchByAreaState.Loading
        }
        val query: OBMapSearchQuery = OBMapSearchQuery(
            searchQuery = currentSearchQuery.value ?: "",
            mapBounds = bounds,
            radius = currentRadiusValue.value,
            categoryID = searchFilterList.value?.data[selectedFilterIndex.value]
        )
        viewModelScope.launch {
            delay(1000)
            try {
                val result = useCase.execute(query)
                _currentSearchByAreaState.update {
                    SearchByAreaState.Success(
                        data = SearchByAreaResult(
                            data = result.map {
                                DataClusterItem(
                                    data = it,
                                    itemSnippet = it.title
                                )
                            }
                        )
                    )
                }
            } catch (th: Throwable) {
                th.printStackTrace()
                _currentSearchByAreaState.update {
                    SearchByAreaState.Error()
                }
            }
        }

    }



}