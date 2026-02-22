package com.youapps.search_module.search_list_map.di

import com.youapps.onlybeans.contracts.UseCaseContract
import com.youapps.onlybeans.di.AppMetaDataAPITag
import com.youapps.onlybeans.di.OBLocationServicePlayServicesImplTag
import com.youapps.onlybeans.di.platformServicesModule
import com.youapps.onlybeans.di.sharedRepositories
import com.youapps.search_module.search_list_map.data.SearchCommunityLocalDataSource
import com.youapps.search_module.search_list_map.data.SearchCommunityLocalDataSourceImpl
import com.youapps.search_module.search_list_map.data.SearchCommunityRemoteDataSource
import com.youapps.search_module.search_list_map.data.SearchCommunityRemoteDataSourceImpl
import com.youapps.search_module.search_list_map.data.SearchCommunityRepository
import com.youapps.search_module.search_list_map.data.SearchCommunityRepositoryContract
import com.youapps.search_module.search_list_map.domain.entities.MapSearchDataPoint
import com.youapps.search_module.search_list_map.domain.entities.OBMapSearchQuery
import com.youapps.search_module.search_list_map.domain.usecases.SearchCommunityUseCase
import com.youapps.search_module.search_list_map.ui.community_search_state.CommunitySearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


internal val SearchCommunityRemoteDataSourceImplTag =
    named("SearchCommunityRemoteDataSourceImplTag")
internal val SearchCommunityLocalDataSourceImplTag = named("SearchCommunityLocalDataSourceImplTag")
internal val SearchCommunityRepositoryImplTag = named("SearchCommunityRepositoryImplTag")
internal val SearchCommunityUseCaseImplTag = named("SearchCommunityUseCaseImplTag")


val searchModule = module {
    includes(sharedRepositories)
    includes(platformServicesModule)
    factory<SearchCommunityRemoteDataSource>(SearchCommunityRemoteDataSourceImplTag) {
        SearchCommunityRemoteDataSourceImpl()
    }
    factory<SearchCommunityLocalDataSource>(SearchCommunityLocalDataSourceImplTag) {
        SearchCommunityLocalDataSourceImpl()
    }
    factory<SearchCommunityRepositoryContract>(SearchCommunityRepositoryImplTag) {
        SearchCommunityRepository(
            get(SearchCommunityRemoteDataSourceImplTag),
            get(SearchCommunityLocalDataSourceImplTag)
        )
    }
    factory<UseCaseContract<OBMapSearchQuery, List<MapSearchDataPoint>>>(
        SearchCommunityUseCaseImplTag
    ) {
        SearchCommunityUseCase(
            get(SearchCommunityRepositoryImplTag)
        )
    }
    viewModel<CommunitySearchViewModel> {
        CommunitySearchViewModel(
            get(OBLocationServicePlayServicesImplTag),
            get(AppMetaDataAPITag),
            get(SearchCommunityUseCaseImplTag)
        )
    }
}