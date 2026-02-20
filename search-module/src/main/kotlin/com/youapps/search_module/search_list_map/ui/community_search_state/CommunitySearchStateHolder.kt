package com.youapps.search_module.search_list_map.ui.community_search_state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.youapps.onlybeans.domain.entities.users.OBLocation
import com.youapps.onlybeans.domain.exception.DomainErrorType
import com.youapps.search_module.search_list_map.ui.community_search_screen.SearchViewType
import kotlin.Float

@Immutable
data class SearchFilterList(
    val data : List<String>
)


data class SearchByRegionBounds(
    val northEast : OBLocation,
    val southWest : OBLocation
)

sealed interface SearchByAreaState{
    data object Idle : SearchByAreaState
    data object Loading : SearchByAreaState
    data class Error(val domainErrorType: DomainErrorType) : SearchByAreaState
    data object Success : SearchByAreaState
}

data class CommunitySearchStateHolder(
    val searchQuery : State<String?>,
    val searchFilters : State<SearchFilterList?>,
    val selectedFilterIndex : State<Int>,
    val currentRadiusValue : State<Float>,
    val searchOperationState : State<SearchByAreaState>
) {

    companion object{

        @Composable
        fun rememberCommunitySearchState(
            searchQuery : State<String?>,
            searchFilters : State<SearchFilterList?>,
            selectedFilterIndex : State<Int>,
            currentRadiusValue : State<Float>,
            searchOperationState : State<SearchByAreaState>
        ) : CommunitySearchStateHolder = remember(searchQuery,currentRadiusValue,selectedFilterIndex,searchOperationState) {
            CommunitySearchStateHolder(
                searchQuery = searchQuery,
                searchFilters = searchFilters,
                selectedFilterIndex = selectedFilterIndex,
                currentRadiusValue = currentRadiusValue,
                searchOperationState = searchOperationState
            )
        }

        @Composable
        fun bindViewModelState(
            viewModel : CommunitySearchViewModel
        ) : CommunitySearchStateHolder = rememberCommunitySearchState(
            searchQuery = viewModel.currentSearchQuery.collectAsStateWithLifecycle(initialValue = null),
            searchFilters = viewModel.searchFilterList.collectAsStateWithLifecycle(initialValue = null),
            selectedFilterIndex = viewModel.selectedFilterIndex.collectAsStateWithLifecycle(initialValue = -1),
            currentRadiusValue =  viewModel.currentRadiusValue.collectAsStateWithLifecycle(initialValue = 1f),
            searchOperationState = viewModel.currentSearchByAreaState.collectAsStateWithLifecycle(initialValue = SearchByAreaState.Idle)
        )



    }

}