package com.youapps.onlybeans.marketplace.ui.screens.home_marketplace

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.youapps.designsystem.components.PageSection
import com.youapps.designsystem.components.menus.OBFilterMenu
import com.youapps.designsystem.components.menus.OBFilterMenuLoader
import com.youapps.onlybeans.domain.entities.products.OBMarketPlaceProduct
import com.youapps.onlybeans.marketplace.R
import com.youapps.onlybeans.marketplace.domain.entities.MarketPlaceNewsCard
import com.youapps.onlybeans.marketplace.ui.components.MarketPlaceCarousel
import com.youapps.onlybeans.marketplace.ui.components.MarketPlaceCarouselLoader
import com.youapps.onlybeans.marketplace.ui.components.MarketplaceTopBar
import com.youapps.onlybeans.marketplace.ui.components.lists.MarketPlaceProductGridList
import com.youapps.onlybeans.marketplace.ui.components.lists.MarketPlaceProductGridListLoader
import com.youapps.onlybeans.marketplace.ui.state.MarketPlaceDataState
import com.youapps.onlybeans.marketplace.ui.state.MarketPlaceStateHolder


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HomeMarketPlace(
    modifier: Modifier = Modifier,
    state: MarketPlaceStateHolder,
    onSearchQueryChanged: (String) -> Unit,
    onCategorySelectedIndexChanged: (Int) -> Unit,
    onNewsCardClicked: (MarketPlaceNewsCard) -> Unit,
    onAddToCardClicked: (product: OBMarketPlaceProduct, isAdded: Boolean) -> Unit,
    onLikeClicked: (product: OBMarketPlaceProduct, isLiked: Boolean) -> Unit,
    onSeeAllProductsClicked: () -> Unit,
    onRefreshMarketPlaceDataClicked : ()-> Unit
) {
    val bodyScrollState = rememberScrollState()
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val usPullRefreshing = remember {
        derivedStateOf {
            state.marketPlaceDataState.value.let { state->
                state is MarketPlaceDataState.Loading && state.withPullToRefresh
            }
        }
    }
    val refreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        isRefreshing = usPullRefreshing.value,
        onRefresh = onRefreshMarketPlaceDataClicked,
        state = refreshState,
        contentAlignment = Alignment.TopCenter,
        indicator = {
            PullToRefreshDefaults.Indicator(
                modifier = Modifier
                    .height(40.dp)
                    .zIndex(4f),
                containerColor = MaterialTheme.colorScheme.background,
                state = refreshState,
                isRefreshing = usPullRefreshing.value,
            )
        }
    ) {
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
            when (val marketPlaceDataState = state.marketPlaceDataState.value) {
                is MarketPlaceDataState.Loading -> {
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
                    ) {
                        MarketPlaceCarouselLoader(
                            modifier = Modifier
                                .height(206.dp)
                                .fillMaxWidth()
                        )
                        PageSection(
                            sectionTitle = stringResource(com.youapps.onlybeans.designsystem.R.string.categories_label)
                        ) {
                            OBFilterMenuLoader(
                                modifier = Modifier
                                    .fillMaxWidth()
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
                                        .clickable(onClick = onSeeAllProductsClicked)
                                        .wrapContentSize(),
                                    text = stringResource(com.youapps.onlybeans.designsystem.R.string.section_see_all),
                                    style = MaterialTheme.typography.labelLarge,
                                    color = Color(0xFF92400e)
                                )
                            }
                        ) {
                            MarketPlaceProductGridListLoader(
                                modifier = Modifier
                                    .height(400.dp)
                                    .fillMaxWidth(),
                            )
                        }
                    }
                }

                is MarketPlaceDataState.Success -> {
                        AnimatedContent(
                            targetState = marketPlaceDataState.showOnlySearchResult
                        ) { targetState ->
                            if (targetState) {
                                Column(
                                    modifier = Modifier
                                        .padding(paddingValues)
                                        .padding(
                                            horizontal = 12.dp,
                                            vertical = 20.dp
                                        ),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Top)
                                ) {
                                    MarketPlaceProductGridList(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        data = marketPlaceDataState.data.products,
                                        onAddToCardClicked = onAddToCardClicked,
                                        onLikeClicked = onLikeClicked
                                    )
                                }
                            } else {
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
                                ) {
                                    MarketPlaceCarousel(
                                        modifier = Modifier
                                            .height(206.dp)
                                            .fillMaxWidth(),
                                        items = marketPlaceDataState.data.newsCards,
                                        onItemClicked = onNewsCardClicked
                                    )
                                    PageSection(
                                        sectionTitle = stringResource(com.youapps.onlybeans.designsystem.R.string.categories_label)
                                    ) {
                                        OBFilterMenu(
                                            filters = marketPlaceDataState.data.filterCategories,
                                            selectedFilterIndex = state.selectedFilterIndex.value,
                                            onFilterSelected = onCategorySelectedIndexChanged
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
                                                    .clickable(onClick = onSeeAllProductsClicked)
                                                    .wrapContentSize(),
                                                text = stringResource(com.youapps.onlybeans.designsystem.R.string.section_see_all),
                                                style = MaterialTheme.typography.labelLarge,
                                                color = Color(0xFF92400e)
                                            )
                                        }
                                    ) {
                                        MarketPlaceProductGridList(
                                            modifier = Modifier
                                                .height(400.dp)
                                                .fillMaxWidth(),
                                            data = marketPlaceDataState.data.products,
                                            onAddToCardClicked = onAddToCardClicked,
                                            onLikeClicked = onLikeClicked
                                        )
                                    }
                                }

                            }
                        }

                }

                is MarketPlaceDataState.Error -> {

                }
            }
        }
    }



}