package com.youapps.onlybeans.ui.product.coffeeBeans

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.packInts
import com.youapps.designsystem.OBTheme
import com.youapps.designsystem.components.bars.OBTopBar
import com.youapps.designsystem.components.images.OBCoverPhoto
import com.youapps.onlybeans.domain.entities.products.OBCoffeeBeansPricing
import com.youapps.onlybeans.domain.entities.products.OBCoffeeBeansProductDetails
import com.youapps.onlybeans.domain.entities.products.OBCoffeeRegion
import com.youapps.onlybeans.domain.entities.products.OBCoffeeRoaster
import com.youapps.onlybeans.domain.entities.products.OBFlavorNotes
import com.youapps.onlybeans.domain.entities.products.OBFlavorProfileData
import com.youapps.onlybeans.domain.entities.products.OBProductRating
import com.youapps.onlybeans.domain.entities.products.OBRoastLevel
import com.youapps.onlybeans.domain.entities.users.OBLocation
import com.youapps.onlybeans.ui.product.ProductBottomAppBar

@Preview()
@Composable
fun CoffeeBeansProductPreview(){
    val    obCoffeeBeansProduct: OBCoffeeBeansProductDetails = OBCoffeeBeansProductDetails(
        id = "product-coffee-0x5gae1dhfsd1hs1hf1",
        label = "Ethiopian Yirgacheffe",
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
        pricing = OBCoffeeBeansPricing(
            pricePerWeight = mapOf(
                18.5f to 250,
                35f to 500,
                65f to 1000
            ),
            currency = "USD",
            weightUnit = "g"
        ) ,
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
        )
    )
    OBTheme{
        CoffeeBeansProduct(
            obCoffeeBeansProduct = obCoffeeBeansProduct,
            onBackClick = {

            },
            onShareClick = {

            },
            onLikeClicked = {

            },
            onAddToCartClicked = {

            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeBeansProduct(
    modifier: Modifier = Modifier,
    obCoffeeBeansProduct: OBCoffeeBeansProductDetails,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onAddToCartClicked : (Int)-> Unit,
    onLikeClicked : (isLiked : Boolean)-> Unit
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
     topBar = {
         OBTopBar(
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
            OBCoverPhoto(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(carouselCorners),
                url = "https://plus.unsplash.com/premium_photo-1724820188081-17b09ee3f2b7?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
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
                Spacer(
                    Modifier.height(1000.dp)
                )
            }

        }
    }

}