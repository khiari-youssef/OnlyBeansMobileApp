package com.youapps.search_module.search_list_map.ui.community_search_state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CommunitySearchViewModel : ViewModel() {


    init {
        fetchSearchFilter()
    }

 private val _currentSearchQuery: MutableStateFlow<String?> = MutableStateFlow(null)
   val currentSearchQuery: StateFlow<String?> = _currentSearchQuery

 private val _searchFilterList: MutableStateFlow<SearchFilterList?> = MutableStateFlow(null)
 val searchFilterList: StateFlow<SearchFilterList?> = _searchFilterList

 private val _selectedFilterIndex: MutableStateFlow<Int> = MutableStateFlow(-1)
 val selectedFilterIndex: StateFlow<Int> = _selectedFilterIndex

 private val _currentRadiusValue: MutableStateFlow<Float> = MutableStateFlow(1f)
 val currentRadiusValue: StateFlow<Float> = _currentRadiusValue

 private val _currentSearchByAreaState: MutableStateFlow<SearchByAreaState> = MutableStateFlow(SearchByAreaState.Idle)
 val currentSearchByAreaState: StateFlow<SearchByAreaState> = _currentSearchByAreaState




  fun updateSearchQuery(searchQuery: String) {
      _currentSearchQuery.value = searchQuery
  }

    fun setSelectedFilterIndex(index : Int){
        _selectedFilterIndex.update {
            index
        }
    }

    fun setRadiusValue(radiusValue : Float){
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
                        "All","Baristas","CoffeeShops","CoffeeRoasters","CoffeeFarms"
                    )
                )
            }
        }
    }


    fun searchVisibleArea(bounds : SearchByRegionBounds){
        _currentSearchByAreaState.update {
            SearchByAreaState.Loading
        }
        viewModelScope.launch {
            delay(1000)
            _currentSearchByAreaState.update {
                SearchByAreaState.Success
            }
        }

    }


}