package com.youapps.search_module.search_list_map.data

import com.youapps.search_module.search_list_map.domain.entities.MapSearchDataPoint


interface SearchCommunityLocalDataSource {

    suspend fun getCommunityDataByLatestSearchResult(): List<MapSearchDataPoint>

}

class SearchCommunityLocalDataSourceImpl : SearchCommunityLocalDataSource {

    override suspend fun getCommunityDataByLatestSearchResult(): List<MapSearchDataPoint> {
        TODO("Not yet implemented")
    }

}