package com.youapps.onlybeans.marketplace.ui.models

import androidx.compose.runtime.Immutable
import com.youapps.onlybeans.domain.entities.products.OBMarketPlaceProduct
import com.youapps.onlybeans.marketplace.domain.entities.MarketPlaceNewsCard
import kotlinx.collections.immutable.ImmutableList


@Immutable
data class MarketPlacePageData(
    val newsCards: List<MarketPlaceNewsCard>,
    val filterCategories: List<String>,
    val products: ImmutableList<OBMarketPlaceProduct>
)