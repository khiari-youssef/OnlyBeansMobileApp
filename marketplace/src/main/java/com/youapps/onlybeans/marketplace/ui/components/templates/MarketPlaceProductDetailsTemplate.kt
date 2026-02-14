package com.youapps.onlybeans.marketplace.ui.components.templates

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.youapps.designsystem.OBTheme
import com.youapps.designsystem.components.dialogs.ImageViewerDialog
import com.youapps.onlybeans.ui.product.OBProductTopBar
import com.youapps.onlybeans.domain.entities.products.OBCoffeeBeansProductDetails
import com.youapps.onlybeans.domain.entities.products.OBCoffeeRegion
import com.youapps.onlybeans.domain.entities.products.OBCoffeeRoaster
import com.youapps.onlybeans.domain.entities.products.OBFlavorNotes
import com.youapps.onlybeans.domain.entities.products.OBFlavorProfileData
import com.youapps.onlybeans.domain.entities.products.OBMarketPlaceProduct
import com.youapps.onlybeans.domain.entities.products.OBPrice
import com.youapps.onlybeans.domain.entities.products.OBProductPricing
import com.youapps.onlybeans.domain.entities.products.OBProductRating
import com.youapps.onlybeans.domain.entities.products.OBRoastLevel
import com.youapps.onlybeans.domain.entities.users.OBLocation
import com.youapps.onlybeans.ui.product.ProductBottomAppBar
import com.youapps.onlybeans.ui.product.components.OBProductCoversCarousel
import com.youapps.onlybeans.ui.product.components.OBProductHeader
import com.youapps.onlybeans.marketplace.ui.components.OBProductPriceSection
import com.youapps.onlybeans.marketplace.ui.components.OBProductRatingTag
import com.youapps.onlybeans.ui.product.obCoffeeBeansMockProduct

@Preview()
@Composable
fun MarketPlaceProductDetailsTemplatePreview(){
    val    obCoffeeBeansProduct: OBCoffeeBeansProductDetails = OBCoffeeBeansProductDetails(
        id = "product-coffee-0x5gae1dhfsd1hs1hf1",
        name = "Ethiopian Yirgacheffe",
        productCovers = listOf(

        ),
        productDescription = "Known for its bright acidity and complex flavor profile, this Ethiopian Yirgacheffe offers notes of jasmine, lemon, and blueberry. A perfect morning brew for pour-over enthusiasts looking for a clean, floral cup",
        species = "Arabica",
        variety = "Geisha",
        origins = listOf(
            OBCoffeeRegion(
                country = "Ethiopia",
                flag = "ET",
                region = "Yirgacheffe",
                farm = "Gedeo Zone Coop",
                description = "Grown by Alemu at 2,000m elevation in rich volcanic soil."
            )
        ),
        altitude = "2000m",
        processingMethod = "WASHED",
        roaster = OBCoffeeRoaster(
            id = "coffee-roaster-0xatdhshzrhfsdj",
            name = "Bloom Coffee Co.",
            description = "Roasting small batches in Seattle, WA since 2015.",
            locations = listOf(OBLocation(23.154757,65.2254789))
        ) ,
        roastDate = "04-02-2026",
        endConsumptionDate = "04-05-2026",
        flavorProfileData = OBFlavorProfileData(
            description = "Complex & Floral",
            radarChartData =  mapOf(
                "Acidity" to 80f,
                "Aroma" to 90f,
                "Sweetness" to 80f,
                "Body" to 60f,
                "Bitterness" to 10f
            )
        ),
        flavorNotes = listOf(
            OBFlavorNotes(
                name = "Blueberry",
                thumbnailUrl = null
            ),
            OBFlavorNotes(
                name = "Jasmine",
                thumbnailUrl = null
            ),
            OBFlavorNotes(
                name = "Lemon",
                thumbnailUrl = null
            )
        ),
        roastLevel = OBRoastLevel.MEDIUM,
        rating = OBProductRating(
            reviewsNumber = 124,
            averageRating = 4.8f
        ),
        displayMetadata = "100% Arabica • Single Origin • Medium Roast"
    )
    OBTheme{
        MarketPlaceProductDetailsTemplate(
            oBMarketPlaceProduct = OBMarketPlaceProduct(
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
                isAddedToFavoriteList = false,
                inStockItems = 5,
            ),
            onBackClick = {

            },
            onShareClick = {

            },
            onLikeClicked = {

            },
            onAddToCartClicked = {

            },
            content = {

            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketPlaceProductDetailsTemplate(
    modifier: Modifier = Modifier,
    oBMarketPlaceProduct: OBMarketPlaceProduct,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onAddToCartClicked : (Int)-> Unit,
    onLikeClicked : (isLiked : Boolean)-> Unit,
    content : @Composable ColumnScope.()-> Unit
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
     topBar = {
         OBProductTopBar(
             modifier = Modifier
                 .fillMaxWidth(),
             scrollBehavior = topAppBarScrollBehavior,
             topAppBarColors = TopAppBarDefaults.topAppBarColors(
                 containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                     0.9f
                 ),
                 scrolledContainerColor = Color.Transparent
             ),
             onBackClick = onBackClick,
             onShareClick = onShareClick,
             onShoppingBagClicked = {

             }
         )
     },
        bottomBar = {
            var isLiked by remember {
                mutableStateOf(false)
            }
            var quantity by remember {
                mutableIntStateOf(0)
            }

            ProductBottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                isLiked = isLiked,
                onProductQuantityChanged = {
                    quantity = it
                },
                onLikeClicked = {
                    isLiked = it
                },
                onAddToCartClicked = {
                    onAddToCartClicked(quantity)
                }
            )
        }
    ) { _ ->
        val scrollState = rememberScrollState()
        val imageViewerContent : MutableState<String?> = remember {
            mutableStateOf(null)
        }
        ImageViewerDialog(
            imageUrl = imageViewerContent.value ?: "" ,
            isVisible = imageViewerContent.value != null,
            onDismissRequest = {
                imageViewerContent.value = null
            }
        )
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            val carouselCorners = RoundedCornerShape(
                bottomStart = 16.dp,
                bottomEnd = 16.dp
            )
            OBProductCoversCarousel(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(carouselCorners),
                covers = oBMarketPlaceProduct.product.productCovers,
                onPhotoClicked = {
                    imageViewerContent.value = it
                },
                preferredItemWidth = 300.dp
            )
            Card(
                modifier = Modifier
                    .offset(
                        y = (-10).dp
                    )
                    .wrapContentHeight()
                    .fillMaxWidth(),
                shape = RoundedCornerShape(
                    topEnd = 32.dp,
                    topStart = 32.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(
                            top = 24.dp
                        )
                        .padding(
                            horizontal = 12.dp
                        )
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        val (left,right) = createRefs()

                        OBProductHeader(
                            modifier = Modifier
                                .fillMaxWidth()
                                .constrainAs(left) {
                                    start.linkTo(parent.start)
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    end.linkTo(right.start, 8.dp)
                                    width = Dimension.fillToConstraints
                                },
                            title = oBMarketPlaceProduct.product.name,
                            subTitle = oBMarketPlaceProduct.product.run {
                                this.displayMetadata
                            }
                        )
                       Column(
                           modifier = Modifier
                               .wrapContentHeight()
                               .constrainAs(right){
                                   top.linkTo(parent.top)
                                   bottom.linkTo(parent.bottom)
                                   end.linkTo(parent.end)
                               },
                           horizontalAlignment = Alignment.CenterHorizontally,
                           verticalArrangement = Arrangement.spacedBy(4.dp)
                       ) {
                           oBMarketPlaceProduct.pricing.run {
                               OBProductPriceSection(
                                   modifier = Modifier,
                                   onOBPrice = when (this) {
                                       is OBProductPricing.OBProductMultipleWeightBasedPricing -> this.pricePerWeight.values.first()
                                       is OBProductPricing.OBProductMultipleBundleBasedPricing -> this.pricePerBundle.values.first()
                                       is OBProductPricing.OBProductSinglePricing -> this.price
                                   },
                                   currency = this.currency
                               )
                           }
                           oBMarketPlaceProduct.product.rating?.run {
                               OBProductRatingTag(
                                   modifier = Modifier,
                                   rating = averageRating,
                                   reviewCount = reviewsNumber
                               )
                           }
                       }

                    }
                    content()
                }
            }

        }
    }




}