package com.youapps.onlybeans.marketplace.ui.screens.home_marketplace

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.youapps.designsystem.OBTheme
import com.youapps.designsystem.components.PageSection
import com.youapps.designsystem.components.inputs.OBSearchField
import com.youapps.onlybeans.domain.entities.products.OBMarketPlaceProduct
import com.youapps.onlybeans.domain.entities.products.OBPrice
import com.youapps.onlybeans.domain.entities.products.OBProductPricing
import com.youapps.onlybeans.domain.entities.products.OBProductRating
import com.youapps.onlybeans.marketplace.R
import com.youapps.onlybeans.marketplace.domain.entities.MarketPlaceNewsCard
import com.youapps.onlybeans.marketplace.ui.components.MarketPlaceCarousel
import com.youapps.onlybeans.marketplace.ui.components.MarketplaceProductsCategoryFilter
import com.youapps.onlybeans.marketplace.ui.components.MarketplaceTopBar
import com.youapps.onlybeans.marketplace.ui.components.lists.MarketPlaceProductGridList
import com.youapps.onlybeans.marketplace.ui.components.lists.MarketPlaceProductGridListData
import com.youapps.onlybeans.marketplace.ui.state.MarketPlaceFilterCategoryList
import com.youapps.onlybeans.marketplace.ui.state.MarketPlaceNewsCardList
import com.youapps.onlybeans.marketplace.ui.state.MarketPlaceProductGridListState
import com.youapps.onlybeans.marketplace.ui.state.MarketPlaceStateHolder
import com.youapps.onlybeans.ui.product.obCoffeeBeansMockProduct

@Preview
@Composable
fun HomeMarketPlacePreview() {
    OBTheme {
        HomeMarketPlace(
            modifier = Modifier.fillMaxSize(),
            state = MarketPlaceStateHolder(
                searchQuery = remember {
                    mutableStateOf("")
                },
                newsCardsList = MarketPlaceNewsCardList(
                    data = listOf()
                ),
                filterCategoryList  = MarketPlaceFilterCategoryList(
                    data = listOf()
                ),
                selectedFilterIndex = remember {
                    mutableIntStateOf(-1)
                },
                productsListState = remember {
                    mutableStateOf(
                        MarketPlaceProductGridListState.Success(
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
                    )
                }
            ),

            onSearchQueryChanged = {

            },
            onCategorySelectedIndexChanged = {

            },
            onNewsCardClicked = {

            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeMarketPlace(
    modifier: Modifier = Modifier,
    state : MarketPlaceStateHolder,
    onSearchQueryChanged : (String)-> Unit,
    onCategorySelectedIndexChanged: (Int)-> Unit,
    onNewsCardClicked: (MarketPlaceNewsCard)-> Unit
) {
    val bodyScrollState = rememberScrollState()
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            MarketplaceTopBar(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                    .shadow(
                        elevation = 1.dp
                    )
                    .padding(
                        vertical = 12.dp,
                        horizontal = 16.dp
                    )
                    .wrapContentHeight()
                    .fillMaxWidth(),
                searchQuery = state.searchQuery.value ?: "",
                onSearchQueryChanged = onSearchQueryChanged
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScroll(bodyScrollState)
                .padding(paddingValues)
                .padding(
                    horizontal = 12.dp,
                    vertical = 20.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top)
        ){
            MarketPlaceCarousel(
                modifier = Modifier
                    .height(206.dp)
                    .fillMaxWidth(),
                items = state.newsCardsList.data,
                onItemClicked = onNewsCardClicked
            )
            PageSection(
                sectionTitle = stringResource(R.string.categories_label)
            ) {
                MarketplaceProductsCategoryFilter(
                    categories = state.filterCategoryList.data,
                    selectedCategoryIndex = state.selectedFilterIndex.value,
                    onCategorySelected = onCategorySelectedIndexChanged
                )
            }
            PageSection(
                sectionTitle = stringResource(R.string.marketplace_popular_products_label),
                rightAction = {
                    Text(
                        modifier = Modifier
                            .padding(
                                4.dp
                            )
                            .clickable{

                            }
                            .wrapContentSize(),
                        text = stringResource(com.youapps.onlybeans.designsystem.R.string.section_see_all),
                        style = MaterialTheme.typography.labelLarge,
                        color = Color(0xFF92400e)
                    )
                }
            ) {
                when(val productsListState = state.productsListState.value){
                    is MarketPlaceProductGridListState.Loading -> {

                    }
                    is MarketPlaceProductGridListState.Success -> {
                        MarketPlaceProductGridList(
                            modifier = Modifier
                                .height(400.dp)
                                .fillMaxWidth(),
                            data = productsListState.data
                        )
                    }
                    is MarketPlaceProductGridListState.Error -> {

                    }
                }
            }

        }
    }


}