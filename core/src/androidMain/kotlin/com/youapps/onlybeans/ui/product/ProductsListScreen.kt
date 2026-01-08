package com.youapps.onlybeans.ui.product

import ErrorModal
import LoadingScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.youapps.designsystem.R
import com.youapps.onlybeans.domain.entities.products.OBProductListItem
import com.youapps.onlybeans.domain.exception.DomainErrorType


sealed interface ProductsListScreenState {

    @Stable
    data class Error(val errorType: DomainErrorType = DomainErrorType.Undefined) : ProductsListScreenState
    data object Loading: ProductsListScreenState
    @Stable
    data class Loaded(val data: ProductListData): ProductsListScreenState
}





@Composable
fun ProductsListScreen(
    modifier: Modifier = Modifier,
    title : String,
    dataState: State<ProductsListScreenState>,
    onItemClick : (OBProductListItem)->Unit,
    onRefresh : ()->Unit,
) {
    when(val state = dataState.value){
        is ProductsListScreenState.Error -> {
            ErrorModal(
                title = stringResource(id = com.youapps.onlybeans.R.string.random_error),
                onRetryAction = onRefresh
            )
        }
        is ProductsListScreenState.Loading -> {
            LoadingScreen()
        }
        is ProductsListScreenState.Loaded -> {
            state.data.items.takeIf { it.isNotEmpty() }?.let { listData->
                Column (
                    modifier = modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = title,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)
                    ) {
                        items(listData.size){ index->
                            val item : OBProductListItem = listData[index]
                            ProductOverViewItem(
                                imagePreviewUrl = item.productImagePreview,
                                imagePreviewContentDescription = stringResource(
                                    com.youapps.onlybeans.R.string.content_description_product_image_preview,
                                    item.productName
                                ),
                                title = item.productName,
                                description = item.productDescription,
                                actionIcon = R.drawable.ic_arrow_right,
                                actionIconContentDescription = stringResource(
                                    com.youapps.onlybeans.R.string.content_description_product_image_preview,
                                    item.productName
                                ),
                                onActionClick = {
                                    onItemClick(item)
                                }
                            )
                        }
                    }

                }
            } ?: run {
                ErrorModal(
                    title = stringResource(com.youapps.onlybeans.R.string.product_list_empty_list),
                    onRetryAction = onRefresh
                )
            }
        }

    }

}