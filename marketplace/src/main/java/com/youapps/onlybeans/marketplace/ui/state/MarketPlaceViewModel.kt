package com.youapps.onlybeans.marketplace.ui.state


import androidx.lifecycle.ViewModel
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
import com.youapps.onlybeans.marketplace.domain.entities.MarketPlaceNewsCard
import com.youapps.onlybeans.marketplace.ui.components.lists.MarketPlaceProductGridListData
import com.youapps.onlybeans.ui.product.obCoffeeBeansMockProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update


class MarketPlaceViewModel : ViewModel() {


    private val _currentSearchQuery: MutableStateFlow<String?> = MutableStateFlow(null)
    val currentSearchQuery: Flow<String?> = _currentSearchQuery


    private val _currentSelectedFilterIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectedFilterIndex: Flow<Int> = _currentSelectedFilterIndex

    private val _marketPlaceProductGridListState: MutableStateFlow<MarketPlaceProductGridListState> =
        MutableStateFlow(MarketPlaceProductGridListState.Loading)
    val marketPlaceProductGridListState: Flow<MarketPlaceProductGridListState> =
        _marketPlaceProductGridListState

    init {
        _marketPlaceProductGridListState.update {
            MarketPlaceProductGridListState.Success(
                data = MarketPlaceProductGridListData(
                    items = List(10){
                        OBMarketPlaceProduct(
                            marketPlaceID = "marketplace-id-0aegd2sh15srh1",
                            product = OBCoffeeBeansProductDetails(
                                id = "product-coffee-0x5gae1dhfsd1hs1hf1-${it}",
                                name = "Ethiopian Yirgacheffe",
                                displayMetadata = "100% Arabica • Single Origin • Medium Roast",
                                productCovers =  listOf(
                                    "https://plus.unsplash.com/premium_photo-1724820188081-17b09ee3f2b7?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                                    "https://plus.unsplash.com/premium_photo-1724820188081-17b09ee3f2b7?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
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
                                roastLevel = OBRoastLevel.MEDIUM
                            ),
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
    }


    val marketPlaceNewsCardList: MarketPlaceNewsCardList = MarketPlaceNewsCardList(
        data = List(3) {
            MarketPlaceNewsCard(
                coverImage = "https://images.unsplash.com/photo-1682979358243-816a75830f77?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxhZXN0aGV0aWMlMjBjb2ZmZWUlMjBzaG9wJTIwaW50ZXJpb3J8ZW58MXx8fHwxNzcwOTA4NzgxfDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
                tag = "New Collection",
                contentDescription = "Elevate Your Morning Routine"
            )
        }
    )

    val marketPlaceFilterCategoryList: MarketPlaceFilterCategoryList =
        MarketPlaceFilterCategoryList(
            data = listOf("All", "Beans", "Machines", "Grinders")
        )


    fun setSearchQuery(searchQuery: String) {
        val optimizedNewQuery = searchQuery.replace(" ", "")
        val optimizedCurrentQuery = _currentSearchQuery.value?.replace(" ", "")
        if (optimizedNewQuery != optimizedCurrentQuery) {
            // Search use case callsite here
            _currentSearchQuery.getAndUpdate {
                searchQuery
            }
        }
    }


    fun setSelectedFilterIndex(index: Int) {
        _currentSelectedFilterIndex.update {
            index
        }
    }


}