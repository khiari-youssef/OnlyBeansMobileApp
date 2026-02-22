package com.youapps.search_module.search_list_map.data

import com.youapps.search_module.search_list_map.domain.entities.MapSearchDataPoint
import com.youapps.search_module.search_list_map.domain.entities.OBMapSearchQuery


interface SearchCommunityRepositoryContract {

    suspend fun fetchCommunityDataByCustomQuery(query: OBMapSearchQuery): List<MapSearchDataPoint>
}

class SearchCommunityRepository(
    private val remoteDataSource: SearchCommunityRemoteDataSource,
    private val localDataSource: SearchCommunityLocalDataSource
) : SearchCommunityRepositoryContract {

    override suspend fun fetchCommunityDataByCustomQuery(query: OBMapSearchQuery): List<MapSearchDataPoint> =
        remoteDataSource
            .fetchCommunityDataByCustomQuery(query)

}