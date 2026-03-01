package com.youapps.onlybeans.marketplace.ui.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.youapps.onlybeans.marketplace.domain.entities.MarketPlaceNewsCard
import com.youapps.onlybeans.marketplace.ui.models.MarketPlacePageData


@Immutable
data class MarketPlaceNewsCardList(
    val data: List<MarketPlaceNewsCard>
)


sealed interface MarketPlaceDataState {
    data class Loading(val withPullToRefresh : Boolean = false) : MarketPlaceDataState
    data class Success(val data: MarketPlacePageData,val showOnlySearchResult : Boolean = false) : MarketPlaceDataState
    data class Error(val message: String) : MarketPlaceDataState
}


data class MarketPlaceStateHolder(
    val searchQuery: State<String?>,
    val selectedFilterIndex: State<Int>,
    val marketPlaceDataState: State<MarketPlaceDataState>
) {

    companion object {
        @Composable
        fun rememberMarketPlaceState(
            searchQuery: State<String?>,
            selectedFilterIndex: State<Int>,
            productsList: State<MarketPlaceDataState>
        ): MarketPlaceStateHolder = remember(
            searchQuery,
            selectedFilterIndex,
            productsList
        ) {
            MarketPlaceStateHolder(
                searchQuery,
                selectedFilterIndex,
                productsList
            )
        }

        @Composable
        fun bindViewModelState(
            viewModel: MarketPlaceViewModel
        ): MarketPlaceStateHolder = rememberMarketPlaceState(
            searchQuery = viewModel.currentSearchQuery.collectAsStateWithLifecycle(
                initialValue = null
            ),
            selectedFilterIndex = viewModel.selectedFilterIndex.collectAsStateWithLifecycle(
                0
            ),
            productsList = viewModel.marketPlaceDataState.collectAsStateWithLifecycle(
                initialValue = MarketPlaceDataState.Loading()
            )
        )
    }
}
