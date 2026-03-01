package com.youapps.onlybeans.marketplace.ui.components.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.youapps.designsystem.components.loading.shimmerEffect
import com.youapps.onlybeans.domain.entities.products.OBMarketPlaceProduct
import com.youapps.onlybeans.domain.entities.products.OBPrice
import com.youapps.onlybeans.domain.entities.products.OBProductPricing
import com.youapps.onlybeans.domain.entities.products.OBProductRating
import com.youapps.onlybeans.ui.product.obCoffeeBeansMockProduct
import kotlinx.collections.immutable.ImmutableList



@Composable
fun MarketPlaceProductGridList(
    modifier: Modifier = Modifier,
    data: ImmutableList<OBMarketPlaceProduct>,
    onAddToCardClicked: (product: OBMarketPlaceProduct, isAdded: Boolean) -> Unit,
    onLikeClicked: (product: OBMarketPlaceProduct, isLiked: Boolean) -> Unit
) {
    val gridState = rememberLazyGridState()
    LazyVerticalGrid(
        modifier = modifier,
        state = gridState,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        content = {
            items(data.size, key = {
                data[it].product.id
            }) { index ->
                MarketPlaceProductListItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    obProduct = data[index],
                    onLikeClicked = {
                        onLikeClicked(data[index], it)
                    },
                    onAddToCardClicked = {
                        onAddToCardClicked(data[index], it)
                    }
                )
            }

        }
    )
}

@Composable
fun MarketPlaceProductGridListLoader(
    modifier: Modifier = Modifier
) {
    val gridState = rememberLazyGridState()
    LazyVerticalGrid(
        modifier = modifier,
        state = gridState,
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        content = {
            items(8) { index ->
                Spacer(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .shimmerEffect(true, shape = RoundedCornerShape(16.dp))
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }

        }
    )
}

