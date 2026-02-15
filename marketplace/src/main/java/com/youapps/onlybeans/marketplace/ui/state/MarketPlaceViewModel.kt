package com.youapps.onlybeans.marketplace.ui.state


import androidx.lifecycle.ViewModel
import com.youapps.onlybeans.marketplace.domain.entities.MarketPlaceNewsCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update


class MarketPlaceViewModel : ViewModel() {


private val _currentSearchQuery : MutableStateFlow<String?> = MutableStateFlow(null)
val currentSearchQuery : Flow<String?> = _currentSearchQuery


private val _currentSelectedFilterIndex : MutableStateFlow<Int> = MutableStateFlow(0)
val selectedFilterIndex : Flow<Int> = _currentSelectedFilterIndex

 val marketPlaceNewsCardList : MarketPlaceNewsCardList =  MarketPlaceNewsCardList(
    data = List(3){
        MarketPlaceNewsCard(
            coverImage = "https://images.unsplash.com/photo-1682979358243-816a75830f77?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3Nzg4Nzd8MHwxfHNlYXJjaHwxfHxhZXN0aGV0aWMlMjBjb2ZmZWUlMjBzaG9wJTIwaW50ZXJpb3J8ZW58MXx8fHwxNzcwOTA4NzgxfDA&ixlib=rb-4.1.0&q=80&w=1080&utm_source=figma&utm_medium=referral",
            tag = "New Collection",
            contentDescription = "Elevate Your Morning Routine"
        )
    }
    )

val marketPlaceFilterCategoryList : MarketPlaceFilterCategoryList = MarketPlaceFilterCategoryList(
    data = listOf("All", "Beans", "Machines", "Grinders")
    )





fun setSearchQuery(searchQuery : String){
    val optimizedNewQuery = searchQuery.replace(" ","")
    val optimizedCurrentQuery = _currentSearchQuery.value?.replace(" ","")
        if (optimizedNewQuery != optimizedCurrentQuery){
            // Search use case callsite here
            _currentSearchQuery.getAndUpdate {
                searchQuery
            }
        }
}


fun setSelectedFilterIndex(index : Int){
    _currentSelectedFilterIndex.update {
        index
    }
}





}