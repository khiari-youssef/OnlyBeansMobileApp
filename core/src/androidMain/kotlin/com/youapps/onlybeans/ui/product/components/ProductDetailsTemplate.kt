package com.youapps.onlybeans.ui.product.components

import OBButtonContainedSecondary
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.youapps.designsystem.OBTheme
import com.youapps.designsystem.components.dialogs.ImageViewerDialog
import com.youapps.onlybeans.ui.product.OBProductTopBar
import com.youapps.designsystem.components.images.OBCoverPhoto
import com.youapps.onlybeans.R
import com.youapps.onlybeans.domain.entities.products.OBProduct
import com.youapps.onlybeans.domain.entities.products.OBProductPricing
import com.youapps.onlybeans.ui.product.obCoffeeBeansMockProduct

@Preview()
@Composable
fun ProductDetailsTemplatePreview(){

    OBTheme{
        ProductDetailsTemplate(
            obProduct = obCoffeeBeansMockProduct,
            onBackClick = {

            },
            onShareClick = {

            },
            content = {

            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailsTemplate(
    modifier: Modifier = Modifier,
    obProduct: OBProduct,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
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
             onShareClick = onShareClick
         )
     },
        bottomBar = {
            Surface(
                modifier = modifier,
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Row(
                    modifier = Modifier
                        .padding(
                            vertical = 8.dp
                        ).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    OBButtonContainedSecondary(
                        modifier = Modifier,
                        text = stringResource(R.string.product_view_market_place),
                        size = OBButtonSize.Large,
                        icon = com.youapps.designsystem.R.drawable.ic_marketplace,
                        onClick = {

                        }
                    )
                }
            }

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
                covers = obProduct.productCovers,
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
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
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
                                .constrainAs(left){
                                    start.linkTo(parent.start)
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    end.linkTo(right.start,8.dp)
                                    width = Dimension.fillToConstraints
                                },
                            title = obProduct.name,
                            subTitle = obProduct.run {
                                this.displayMetadata
                            }
                        )
                        obProduct.rating?.run {
                            OBProductRatingTag(
                                modifier = Modifier.constrainAs(right){
                                    top.linkTo(parent.top)
                                    end.linkTo(parent.end)
                                },
                                rating = averageRating,
                                reviewCount = reviewsNumber
                            )
                        }

                    }
                    content()
                }
            }

        }
    }

}