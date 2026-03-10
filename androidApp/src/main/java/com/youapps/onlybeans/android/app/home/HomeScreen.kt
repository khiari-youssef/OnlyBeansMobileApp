package com.youapps.onlybeans.android.app.home


import ProfileScreen
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.youapps.designsystem.components.NavigationBarScreenTemplate
import com.youapps.designsystem.components.bars.OBBottomNavigationBar
import com.youapps.designsystem.components.bars.OBBottomNavigationBarDefaults
import com.youapps.designsystem.components.popups.LogoutPopup
import com.youapps.onlybeans.android.base.NavigationRoutingData
import com.youapps.onlybeans.android.notifications.ui.screen.NotificationScreenStateHolder
import com.youapps.onlybeans.android.notifications.ui.screen.NotificationsScreen
import com.youapps.onlybeans.android.notifications.ui.screen.NotificationsViewModel
import com.youapps.onlybeans.domain.entities.products.OBProductPricing
import com.youapps.onlybeans.marketplace.ui.components.templates.MarketPlaceCardBottomSheetDialog
import com.youapps.onlybeans.marketplace.ui.screens.home_marketplace.HomeMarketPlace
import com.youapps.onlybeans.marketplace.ui.state.MarketPlaceStateHolder
import com.youapps.onlybeans.marketplace.ui.state.MarketPlaceViewModel
import com.youapps.search_module.search_list_map.ui.community_search_screen.CommunitySearchScreen
import com.youapps.search_module.search_list_map.ui.community_search_state.CommunitySearchStateHolder
import com.youapps.search_module.search_list_map.ui.community_search_state.CommunitySearchViewModel
import com.youapps.users_management.ui.profile.MyProfileViewModel
import com.youapps.users_management.ui.profile.ProfileScreenState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(
    homeDestinations: OBBottomNavigationBarDefaults,
    onHomeExit: (route: String) -> Unit
) {
    val homeNavController = rememberNavController()


    val selectedHomeDestinationIndex = rememberSaveable {
        mutableIntStateOf(0)
    }

    homeNavController.addOnDestinationChangedListener { controller, destination, arguments ->
        destination.route?.run {
            selectedHomeDestinationIndex.intValue = NavigationRoutingData.Home.mapRouteToIndex(this)
        }
    }


    val initialRoute = remember {
        derivedStateOf {
            NavigationRoutingData.Home.mapIndexToRoute(selectedHomeDestinationIndex.intValue)
        }
    }

    val navOpts = remember {
        NavOptions.Builder()
            .setLaunchSingleTop(true)
            .build()
    }
    val isBottomAppBarVisible = rememberSaveable {
        mutableStateOf(true)
    }
    Scaffold(
        modifier = Modifier
            .semantics {
                contentDescription = "HomeScreen"
            }
            .fillMaxSize(),
        bottomBar = {
            AnimatedVisibility(
                visible = isBottomAppBarVisible.value,
                enter = fadeIn(spring()),
                exit = fadeOut(spring())
            ) {
                OBBottomNavigationBar(
                    modifier = Modifier
                        .heightIn(min = 24.dp, max = 56.dp)
                        .fillMaxWidth(),
                    selectedItemIndex = selectedHomeDestinationIndex.intValue,
                    properties = homeDestinations,
                    onItemSelected = { index ->
                        val newDestination = NavigationRoutingData.Home.mapIndexToRoute(index)
                       if ( homeNavController.currentDestination?.route != newDestination) {
                           homeNavController.navigate(
                               route = NavigationRoutingData.Home.mapIndexToRoute(index),
                               navOptions = navOpts
                           )
                       }
                    }
                )
            }
        },
        content = { paddingValues ->
            NavHost(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                navController = homeNavController,
                route = NavigationRoutingData.Home.ROOT,
                startDestination = initialRoute.value
            ) {
                composable(NavigationRoutingData.Home.NETWORK) {
                    NavigationBarScreenTemplate(
                        modifier = Modifier,
                        onExitNavigation = remember {
                            {
                                onHomeExit(NavigationRoutingData.EXIT_APP_ROUTE)
                            }
                        },
                        content = remember {
                            { modifier ->
                                val viewModel = koinViewModel<CommunitySearchViewModel>()
                                val communitySearchState =
                                    CommunitySearchStateHolder.bindViewModelState(viewModel)
                                CommunitySearchScreen(
                                    modifier = modifier,
                                    screenState = communitySearchState,
                                    onSearchQueryChanged = viewModel::updateSearchQuery,
                                    onSearchFilterChanged = { selectedFilterIndex, radiusValue ->
                                        viewModel.setRadiusValue(radiusValue)
                                        viewModel.setSelectedFilterIndex(selectedFilterIndex)
                                    },
                                    searchVisibleArea = { bounds ->
                                        viewModel.searchVisibleArea(bounds)
                                    },
                                    onMapLoaded = {
                                        viewModel.listenToLocationUpdates()
                                    }
                                )
                            }
                        }
                    )
                }
                composable(
                    route = NavigationRoutingData.Home.MARKETPLACE
                ) {
                    val viewModel = koinViewModel<MarketPlaceViewModel>()

                    val screenState = MarketPlaceStateHolder.bindViewModelState(
                        viewModel = viewModel
                    )
                    NavigationBarScreenTemplate(
                        modifier = Modifier
                            .padding(paddingValues),
                        onExitNavigation = remember {
                            {
                                onHomeExit(NavigationRoutingData.EXIT_APP_ROUTE)
                            }
                        },
                        content = remember {
                            { modifier ->
                                var isCardBottomSheetDialog : Boolean by remember {
                                    mutableStateOf(false)
                                }

                                MarketPlaceCardBottomSheetDialog(
                                    isShown = isCardBottomSheetDialog,
                                    onDismiss = {
                                        isCardBottomSheetDialog = false
                                        viewModel.saveCurrentCardState()
                                    },
                                    addedProducts = screenState.currentCardItemsListState.value,
                                    onQuantityChanged = viewModel::updateCardItemQuantity,
                                    onProductRemoved = viewModel::removeProductFromCard
                                )
                                HomeMarketPlace(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    state = screenState,
                                    onSearchQueryChanged = viewModel::setSearchQuery,
                                    onNewsCardClicked = {

                                    },
                                    onCategorySelectedIndexChanged = viewModel::setSelectedFilterIndex,
                                    onLikeClicked = { product, isLiked ->
                                        viewModel.updateProductFavoriteStatus(
                                            product.product.id,
                                            isLiked
                                        )
                                    },
                                    onAddToCardStateClicked = { marketPlaceProduct, isAdded ->
                                        if (isAdded){
                                            viewModel.addProductToCard(
                                                product = marketPlaceProduct.product,
                                                selectedPrice = when (val pricing = marketPlaceProduct.pricing) {
                                                    is OBProductPricing.OBProductMultipleWeightBasedPricing -> pricing.pricePerWeight.values.first()
                                                    is OBProductPricing.OBProductMultipleBundleBasedPricing -> pricing.pricePerBundle.values.first()
                                                    is OBProductPricing.OBProductSinglePricing -> pricing.price
                                                },
                                                quantity = 1
                                            )
                                        } else {
                                            viewModel.removeProductFromCard(marketPlaceProduct.product.id)
                                        }
                                    },
                                    onSeeAllProductsClicked = {
                                        onHomeExit(NavigationRoutingData.VIEW_SCREEN_PRODUCT_LIST)
                                    },
                                    onRefreshMarketPlaceDataClicked = {
                                        viewModel.fetchMarketPlaceData(true)
                                    },
                                    onShowCardBottomSheetDialog = {
                                        isCardBottomSheetDialog = true
                                    }
                                )
                            }
                        }
                    )

                }
                composable(NavigationRoutingData.Home.NOTIFICATIONS) {
                    val viewModel = koinViewModel<NotificationsViewModel>()
                    val screenState = NotificationScreenStateHolder
                        .bindViewModelState(viewModel)
                    NavigationBarScreenTemplate(
                        modifier = Modifier,
                        onExitNavigation = { onHomeExit(NavigationRoutingData.EXIT_APP_ROUTE) },
                    ) { modifier ->
                        NotificationsScreen(
                            modifier = modifier,
                            screenState = screenState,
                            onNotificationClicked = { id->
                               viewModel.markNotificationRead(id)
                            },
                            onRemoveNotification = viewModel::removeNotification,
                            onLoadNextPage = {
                                viewModel.loadMoreNotifications(pageSize = 8)
                            },
                            onRefreshNotifications = {
                                viewModel.getLastNotifications(isPullToRefresh = true)
                            },
                            onMarkAllRead = viewModel::markAllNotificationsRead
                        )
                    }
                }
                composable(NavigationRoutingData.Home.PROFILE) {
                    val profileViewModel: MyProfileViewModel = koinViewModel()
                    var isLogoutPopupVisible by remember {
                        mutableStateOf(false)
                    }
                    val profileScreenCoScope = rememberCoroutineScope()
                    val currentContext = LocalContext.current

                    LogoutPopup(
                        isShown = isLogoutPopupVisible,
                        onCancelled = {
                            isLogoutPopupVisible = false
                        },
                        onConfirmAppExit = {
                            profileScreenCoScope.launch {
                                profileViewModel.logOutCurrentUser().collect { isLoggedOut ->
                                    if (isLoggedOut) {
                                        onHomeExit(NavigationRoutingData.LOGIN)
                                    } else {
                                        Toast.makeText(
                                            currentContext,
                                            "Could not logout user !",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                    isLogoutPopupVisible = false
                                }
                            }
                        }
                    )

                    NavigationBarScreenTemplate(
                        modifier = Modifier
                            .systemBarsPadding()
                            .padding(paddingValues),
                        onExitNavigation = { onHomeExit(NavigationRoutingData.EXIT_APP_ROUTE) },
                    ) { modifier ->
                        val myProfileState: State<ProfileScreenState> =
                            profileViewModel.profileState.collectAsStateWithLifecycle(
                                initialValue = ProfileScreenState.Loading()
                            )

                        val scrollState = rememberScrollState()

                        ProfileScreen(
                            modifier = modifier
                                .verticalScroll(state = scrollState)
                                .fillMaxSize(),
                            screenState = myProfileState.value,
                            onRefreshProfile = {
                                profileViewModel.getMyProfile(withRefresh = true)
                            },
                            onLogOutClicked = {
                                isLogoutPopupVisible = true
                            },
                            onEditProfileClicked = {
                                onHomeExit(NavigationRoutingData.EDIT_PROFILE_SCREEN)
                            },
                            onKeywordClicked = {
                                homeNavController.navigate(NavigationRoutingData.Home.NETWORK)
                            },
                            onProductClicked = {
                                onHomeExit(NavigationRoutingData.VIEW_SCREEN_PRODUCT)
                            },
                            onSeeAllCoffeeBeanClicked = {
                                onHomeExit(NavigationRoutingData.VIEW_SCREEN_PRODUCT_LIST)
                            },
                            onSeeAllCoffeeGearClicked = {
                                onHomeExit(NavigationRoutingData.VIEW_SCREEN_PRODUCT_LIST)
                            }
                        )
                    }
                }
            }
        }
    )
}