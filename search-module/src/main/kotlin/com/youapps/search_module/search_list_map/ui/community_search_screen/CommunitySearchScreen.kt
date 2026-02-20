package com.youapps.search_module.search_list_map.ui.community_search_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.youapps.designsystem.components.buttons.OBFilterButton
import com.youapps.designsystem.components.inputs.OBSearchField
import com.youapps.designsystem.components.menus.OBFilterMenu
import com.youapps.onlybeans.search_module.R
import com.youapps.search_module.search_list_map.ui.community_search_screen.list_view.CommunityListView
import com.youapps.search_module.search_list_map.ui.community_search_screen.map_view.CommunityMapView
import com.youapps.search_module.search_list_map.ui.community_search_state.CommunitySearchStateHolder
import com.youapps.search_module.search_list_map.ui.components.OBSearchFilterDialog


@Composable
fun CommunitySearchScreen(
    modifier: Modifier = Modifier,
    screenState : CommunitySearchStateHolder,
    onSearchQueryChanged : (String)-> Unit,
    onSearchFilterChanged : (selectedFilterIndex : Int,radiusValue : Float)-> Unit
) {
    var searchViewType by remember {
       mutableStateOf(SearchViewType.Map)
    }

    var isFilterDialogVisible by remember {
        mutableStateOf(false)
    }

    OBSearchFilterDialog(
        modifier = Modifier,
        isVisible = isFilterDialogVisible,
        categoryFilters = screenState.searchFilters.value,
        selectedFilterIndex = screenState.selectedFilterIndex.value,
        currentRadiusValue = screenState.currentRadiusValue.value,
        onDismiss = {
            isFilterDialogVisible = false
        },
        onApply = onSearchFilterChanged
    )

    Box(
        modifier = modifier
    ){
        when(searchViewType){
            SearchViewType.Map -> CommunityMapView(
                modifier = Modifier.fillMaxSize(),
                screenState = screenState
            )
            SearchViewType.List -> CommunityListView(
                modifier = Modifier.fillMaxSize(),
                screenState = screenState
            )
        }
        Column(
            modifier = Modifier.padding(
                top = 12.dp,
                start = 12.dp,
                end = 12.dp
            ).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)
        ){
            OBSearchField(
                modifier = Modifier,
                placeholderRes = R.string.search_bar_placeholder,
                query = screenState.searchQuery.value ?: "",
                onSearchQueryChanged = onSearchQueryChanged
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                OBFilterButton(
                modifier = Modifier,
                onClick = {
                    isFilterDialogVisible = true
                }
            )}
           /*
            screenState.searchFilters.value?.data?.run {
                OBFilterMenu(
                    modifier = Modifier,
                    filters = this,
                    selectedFilterIndex = screenState.selectedFilterIndex.value ,
                    onFilterSelected = onSelectedFilterIndex
                )
            }
            */
        }

    }
}