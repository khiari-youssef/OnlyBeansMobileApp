package com.youapps.onlybeans.marketplace.ui.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import com.youapps.onlybeans.marketplace.domain.entities.MarketPlaceNewsCard


@Immutable
data class MarketPlaceNewsCardList(
    val data : List<MarketPlaceNewsCard>
)

@Immutable
data class MarketPlaceFilterCategoryList(
    val data : List<String>
)


data class MarketPlaceStateHolder(
  val searchQuery : State<String?>,
  val newsCardsList :  MarketPlaceNewsCardList,
  val filterCategoryList : MarketPlaceFilterCategoryList,
  val selectedFilterIndex : State<Int>
) {

    companion object {
        @Composable
        fun rememberMarketPlaceState(
            searchQuery : State<String?>,
             newsCardsList :  MarketPlaceNewsCardList,
             filterCategoryList : MarketPlaceFilterCategoryList,
            selectedFilterIndex : State<Int>
        ) : MarketPlaceStateHolder = remember(
            searchQuery,
            newsCardsList,
            filterCategoryList,
            selectedFilterIndex
        ) {
            MarketPlaceStateHolder(
                searchQuery,
                newsCardsList,
                filterCategoryList,
                selectedFilterIndex
            )
        }
    }
}
