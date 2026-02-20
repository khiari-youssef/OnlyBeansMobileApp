package com.youapps.search_module.search_list_map.ui.community_search_state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.youapps.search_module.search_list_map.ui.community_search_screen.SearchViewType
import kotlin.Float

@Immutable
data class SearchFilterList(
    val data : List<String>
)

data class CommunitySearchStateHolder(
    val searchQuery : State<String?>,
    val searchFilters : State<SearchFilterList?>,
    val selectedFilterIndex : State<Int>,
    val currentRadiusValue : State<Float>
) {

    companion object{

        @Composable
        fun rememberCommunitySearchState(
            searchQuery : State<String?>,
            searchFilters : State<SearchFilterList?>,
            selectedFilterIndex : State<Int>,
            currentRadiusValue : State<Float>
        ) : CommunitySearchStateHolder = remember(searchQuery,currentRadiusValue,selectedFilterIndex) {
            CommunitySearchStateHolder(
                searchQuery = searchQuery,
                searchFilters = searchFilters,
                selectedFilterIndex = selectedFilterIndex,
                currentRadiusValue = currentRadiusValue
            )
        }

        @Composable
        fun bindViewModelState(
            viewModel : CommunitySearchViewModel
        ) : CommunitySearchStateHolder = rememberCommunitySearchState(
            searchQuery = viewModel.currentSearchQuery.collectAsStateWithLifecycle(initialValue = null),
            searchFilters = viewModel.searchFilterList.collectAsStateWithLifecycle(initialValue = null),
            selectedFilterIndex = viewModel.selectedFilterIndex.collectAsStateWithLifecycle(initialValue = -1),
            currentRadiusValue =  viewModel.currentRadiusValue.collectAsStateWithLifecycle(initialValue = 1f)
        )



    }

}