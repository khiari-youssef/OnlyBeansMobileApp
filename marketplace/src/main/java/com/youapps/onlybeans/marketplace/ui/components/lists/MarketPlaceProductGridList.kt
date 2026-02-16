package com.youapps.onlybeans.marketplace.ui.components.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.youapps.onlybeans.domain.entities.products.OBMarketPlaceProduct
import com.youapps.onlybeans.domain.entities.products.OBPrice
import com.youapps.onlybeans.domain.entities.products.OBProductPricing
import com.youapps.onlybeans.domain.entities.products.OBProductRating
import com.youapps.onlybeans.ui.product.obCoffeeBeansMockProduct

@Preview
@Composable
fun MarketPlaceProductGridListPreview() {
    MarketPlaceProductGridList(
        modifier = Modifier
            .fillMaxWidth(),
        data = MarketPlaceProductGridListData(
            items = List(10){
                OBMarketPlaceProduct(
                    marketPlaceID = "marketplace-id-0aegd2sh15srh1",
                    product = obCoffeeBeansMockProduct,
                    pricing = OBProductPricing.OBProductMultipleWeightBasedPricing(
                        pricePerWeight = mapOf(
                            250 to OBPrice(price = 18.5f,0.2f),
                            500 to OBPrice(price = 35f),
                            1000 to OBPrice(price = 65f)
                        ),
                        currency = "USD",
                        weightUnit = "g"
                    ) ,
                    isAddedToFavoriteList = it % 2 == 0,
                    isAddedToCard =  it % 2 != 0,
                    inStockItems = 5,
                    rating = OBProductRating(
                        reviewsNumber = 124,
                        averageRating = 4.8f
                    )
                )
            }
        )
    )
}

@Immutable
data class MarketPlaceProductGridListData(
    val items : List<OBMarketPlaceProduct>
)


@Composable
fun MarketPlaceProductGridList(
    modifier: Modifier = Modifier,
    data: MarketPlaceProductGridListData
) {
    val gridState = rememberLazyGridState()
    LazyVerticalGrid(
        modifier = modifier,
        state = gridState,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        content ={
            items(data.items.size, key = {
                data.items[it].product.id
            }){ index->
                MarketPlaceProductListItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    obProduct = data.items[index] ,
                    onLikeClicked = {

                    },
                    onAddToCardClicked = {

                    }
                )
            }

        }
    )
}

