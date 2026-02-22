package com.youapps.search_module.search_list_map.domain.entities

import com.youapps.search_module.search_list_map.ui.community_search_state.SearchByRegionBounds

data class OBMapSearchQuery(
    val searchQuery : String?,
    val mapBounds : SearchByRegionBounds,
    val radius : Float?,
    val categoryID : String?
)