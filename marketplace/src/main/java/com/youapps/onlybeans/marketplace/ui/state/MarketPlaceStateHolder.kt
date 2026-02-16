package com.youapps.onlybeans.marketplace.ui.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import com.youapps.onlybeans.marketplace.domain.entities.MarketPlaceNewsCard
import com.youapps.onlybeans.marketplace.ui.components.lists.MarketPlaceProductGridListData


@Immutable
data class MarketPlaceNewsCardList(
    val data : List<MarketPlaceNewsCard>
)

@Immutable
data class MarketPlaceFilterCategoryList(
    val data : List<String>
)

sealed interface MarketPlaceProductGridListState {
    data object Loading : MarketPlaceProductGridListState
    data class Success(val data : MarketPlaceProductGridListData) : MarketPlaceProductGridListState
    data class Error(val message : String) : MarketPlaceProductGridListState
}


data class MarketPlaceStateHolder(
  val searchQuery : State<String?>,
  val newsCardsList :  MarketPlaceNewsCardList,
  val filterCategoryList : MarketPlaceFilterCategoryList,
  val selectedFilterIndex : State<Int>,
  val productsListState : State<MarketPlaceProductGridListState>
) {

    companion object {
        @Composable
        fun rememberMarketPlaceState(
            searchQuery : State<String?>,
             newsCardsList :  MarketPlaceNewsCardList,
             filterCategoryList : MarketPlaceFilterCategoryList,
            selectedFilterIndex : State<Int>,
            productsList : State<MarketPlaceProductGridListState>
        ) : MarketPlaceStateHolder = remember(
            searchQuery,
            newsCardsList,
            filterCategoryList,
            selectedFilterIndex,
            productsList
        ) {
            MarketPlaceStateHolder(
                searchQuery,
                newsCardsList,
                filterCategoryList,
                selectedFilterIndex,
                productsList
            )
        }
    }
}
