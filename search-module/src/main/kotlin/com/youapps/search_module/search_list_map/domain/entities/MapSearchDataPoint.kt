package com.youapps.search_module.search_list_map.domain.entities

import com.youapps.onlybeans.domain.entities.users.OBLocation


data class MapSearchDataPoint(
    val location: OBLocation,
    val title: String
)