package com.youapps.onlybeans.marketplace.ui.components.lists

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.youapps.designsystem.OBFontFamilies
import com.youapps.designsystem.OBTheme
import com.youapps.designsystem.components.buttons.OBLikeButton
import com.youapps.designsystem.components.buttons.OBLikeCardButton
import com.youapps.designsystem.components.images.OBCoverPhoto
import com.youapps.designsystem.components.loading.shimmerEffect
import com.youapps.onlybeans.designsystem.R
import com.youapps.onlybeans.domain.entities.products.OBMarketPlaceProduct
import com.youapps.onlybeans.domain.entities.products.OBPrice
import com.youapps.onlybeans.domain.entities.products.OBProductPricing
import com.youapps.onlybeans.domain.entities.products.OBProductRating
import com.youapps.onlybeans.marketplace.ui.components.OBProductRatingTag
import com.youapps.onlybeans.marketplace.ui.components.ShoppingCardIcon
import com.youapps.onlybeans.ui.product.obCoffeeBeansMockProduct


@Preview(widthDp = 300)
@Composable
fun MarketPlaceProductListItemPreview() {
    OBTheme{
        val isLiked = remember {
            mutableStateOf(false)
        }
        val isAddedToCard = remember {
            mutableStateOf(false)
        }

        MarketPlaceProductListItem(
            modifier = Modifier.width(300.dp)
                .wrapContentHeight(),
            obProduct = OBMarketPlaceProduct(
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
                isAddedToFavoriteList = isLiked.value,
                isAddedToCard = isAddedToCard.value,
                inStockItems = 5,
                rating = OBProductRating(
                    reviewsNumber = 124,
                    averageRating = 4.8f
                )
            ),
            onLikeClicked = {
                isLiked.value  = it
            },
            onAddToCardClicked = {
                isAddedToCard.value = it
            }
        )
    }
}


@Composable
fun MarketPlaceProductListItem(
    modifier: Modifier = Modifier,
    obProduct: OBMarketPlaceProduct,
    onAddToCardClicked : (isAdded : Boolean)-> Unit,
    onLikeClicked : (isLiked : Boolean)-> Unit
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp)),
      shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
      elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    )  {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        )  {
            val isLoading = remember {
                mutableStateOf(true)
            }
            Box{
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .shimmerEffect(isLoading.value),
                    contentScale = ContentScale.Crop,
                    model = obProduct.product.productCovers.firstOrNull(),
                    onState = {state->
                        isLoading.value = state is AsyncImagePainter.State.Loading
                    },
                    contentDescription = "contentDescription"
                )
                OBLikeCardButton(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(
                            top = 8.dp,
                            end = 8.dp
                        ),
                    isLiked = obProduct.isAddedToFavoriteList,
                    onClick = onLikeClicked
                )
            }
            Column(
                modifier = Modifier
                    .padding(
                      vertical = 8.dp,
                        horizontal = 8.dp
                    )
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top)
            ){
                obProduct.rating?.run {
                    OBProductRatingTag(
                        rating = averageRating,
                        reviewCount = reviewsNumber,
                        textColor = Color(0xFF5F6368)
                    )
                }
                Text(
                    text = obProduct.product.name,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Start,
                    maxLines = 1
                )
                Text(
                    text = obProduct.product.displayMetadata,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                        alpha = 0.7f
                    ),
                    textAlign = TextAlign.Start,
                    maxLines = 2
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                   Text(
                       text =  when (val pricing = obProduct.pricing) {
                           is OBProductPricing.OBProductMultipleWeightBasedPricing -> pricing.pricePerWeight.values.first()
                           is OBProductPricing.OBProductMultipleBundleBasedPricing -> pricing.pricePerBundle.values.first()
                           is OBProductPricing.OBProductSinglePricing -> pricing.price
                       }.let { (price, _) -> "$price ${obProduct.pricing.currency}"  },
                       textAlign = TextAlign.Start,
                       maxLines = 1,
                       style = TextStyle(
                           color = MaterialTheme.colorScheme.onSurface,
                           fontSize = 18.sp,
                           fontFamily = OBFontFamilies.MainBoldFontFamily
                       )
                   )
                    FloatingActionButton(
                        onClick = {
                            onAddToCardClicked(obProduct.isAddedToCard.not())
                        },
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(40.dp),
                        shape = CircleShape,
                        containerColor =  if(obProduct.isAddedToCard) Color(0xFF92400e) else Color.Black,
                        elevation = FloatingActionButtonDefaults.elevation(4.dp)
                    ) {
                        Icon(
                            ImageVector.vectorResource(R.drawable.ic_shopping_bag),
                            contentDescription = stringResource(R.string.content_description_product_item_add_to_card_button),
                            tint = Color.White
                        )
                    }

                }
            }

        }
    }
}