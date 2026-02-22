package com.youapps.search_module.search_list_map.domain.usecases

import com.youapps.onlybeans.contracts.UseCaseContract
import com.youapps.search_module.search_list_map.data.SearchCommunityRepositoryContract
import com.youapps.search_module.search_list_map.domain.entities.MapSearchDataPoint
import com.youapps.search_module.search_list_map.domain.entities.OBMapSearchQuery


class SearchCommunityUseCase(
    val repository: SearchCommunityRepositoryContract
) : UseCaseContract<OBMapSearchQuery, List<MapSearchDataPoint>> {


    // to write business rules here later
    override suspend fun execute(input: OBMapSearchQuery): List<MapSearchDataPoint> {
        return repository.fetchCommunityDataByCustomQuery(input)
    }

}