package com.youapps.onlybeans.marketplace.ui.components.templates

import OBButtonContainedPrimary
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.os.ConfigurationCompat
import coil.compose.AsyncImage
import com.youapps.designsystem.components.inputs.CounterDisplaySize
import com.youapps.designsystem.components.inputs.OBCounter
import com.youapps.designsystem.components.templates.EmptyStateComponent
import com.youapps.onlybeans.designsystem.R
import com.youapps.onlybeans.domain.entities.products.OBMarketPlaceCardProduct
import com.youapps.onlybeans.domain.entities.products.OBMarketPlaceProduct
import com.youapps.onlybeans.domain.entities.products.OBProduct
import com.youapps.onlybeans.utilities.NumericUtils
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import java.util.Locale




@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun MarketPlaceCardBottomSheetDialog(
    isShown : Boolean = false,
    addedProducts: ImmutableList<OBMarketPlaceCardProduct>,
    onQuantityChanged: (String,Int) -> Unit,
    onProductRemoved : (String)-> Unit,
    onDismiss: ()-> Unit
) {
    if (isShown) {
        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )
        ModalBottomSheet(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            sheetState = sheetState,
            onDismissRequest = onDismiss
        ) {
            Column(
                modifier = Modifier
                    .systemBarsPadding()
                    .fillMaxWidth()
                    .wrapContentHeight(),
            ) {
                Row(
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp
                        )
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        Text(
                            text = "Your Card",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "${addedProducts.size} items",
                            textAlign = TextAlign.Start,
                            style = TextStyle(
                                fontSize = 10.sp,
                                color = Color(0xFF78716c),
                                fontWeight = FontWeight.W400
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    FloatingActionButton(
                        onClick = onDismiss,
                        modifier = Modifier,
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                        elevation = FloatingActionButtonDefaults.elevation(4.dp)
                    ){
                        Icon(
                            ImageVector.vectorResource(R.drawable.ic_cross),
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = ""
                        )
                    }
                }
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
                LazyColumn(
                    modifier = Modifier
                        .padding(
                            vertical = 12.dp,
                            horizontal = 12.dp
                        )
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)
                ) {
                    if (addedProducts.isEmpty()){
                        item {
                            EmptyStateComponent(
                                modifier = Modifier
                                    .padding(
                                        vertical = 16.dp
                                    )
                                    .fillMaxWidth(),
                                title = "Your cart is empty",
                                subtitle = "Add some products to get started",
                                icon = ImageVector.vectorResource(R.drawable.ic_shopping_bag)
                            )
                        }
                    } else {
                        items(
                            addedProducts.size,
                            key = {
                                addedProducts[it].productID
                            }
                        ){ index->
                            CardItem(
                                modifier = Modifier
                                    .animateItem(
                                        fadeInSpec = tween(300),
                                        fadeOutSpec = tween(300),
                                        placementSpec = spring(stiffness = Spring.StiffnessLow)
                                    )
                                    .fillMaxWidth(),
                                obProduct = addedProducts[index],
                                onQuantityChanged = onQuantityChanged,
                                onRemoveItem = onProductRemoved
                            )
                        }
                    }

                }
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
                Column(
                    modifier = Modifier
                        .padding(
                            horizontal = 16.dp
                        )
                        .padding(
                            top = 8.dp,
                            bottom = 12.dp
                        )
                        .wrapContentHeight()
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    AnimatedVisibility(
                        visible = addedProducts.isNotEmpty()
                    ) {
                        TotalSection(
                            modifier = Modifier
                                .padding(
                                    vertical = 8.dp
                                )
                                .fillMaxWidth(),
                            totalPrice = addedProducts.map {
                                it.selectedPrice.run {
                                    netPrice*it.productQuantity
                                }
                            }.sum()
                        )
                    }
                    OBButtonContainedPrimary(
                        text = "Proceed to Checkout",
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {

                    }
                }
            }
        }
    }
}


@Composable
fun TotalSection(
    modifier: Modifier = Modifier,
    totalPrice : Float
) {
    val configuration = LocalConfiguration.current
    // Use ConfigurationCompat to safely get the locale list (handles API differences)
    val locale: Locale = ConfigurationCompat.getLocales(configuration).get(0) ?: Locale.getDefault()

        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(0.7f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Subtotal",
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color(0xFF78716c),
                        fontWeight = FontWeight.W400
                    )
                )
                Text(
                    text = "Tax & shipping calculated at checkout",
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontSize = 10.sp,
                        color = Color(0xFF78716c),
                        fontWeight = FontWeight.W400
                    )
                )
            }
            Text(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxWidth(),
                text = NumericUtils.formatPrice(totalPrice,locale),
                textAlign = TextAlign.End,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.W700
                )
            )

        }
}


@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    obProduct: OBMarketPlaceCardProduct,
    onQuantityChanged : (String,Int) -> Unit,
    onRemoveItem : (String) -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFfafaf9)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val (image, title, subtitle, counter, price, removeBtn) = createRefs()
            AsyncImage(
                modifier = Modifier
                    .constrainAs(image) {
                        start.linkTo(parent.start, 8.dp)
                        top.linkTo(parent.top, 8.dp)
                        bottom.linkTo(parent.bottom, 8.dp)
                    }
                    .clip(RoundedCornerShape(12.dp))
                    .size(80.dp),
                model = obProduct.productImagePreview,
                error = painterResource(R.drawable.placeholder_cover),
                contentDescription = "Card item image : ${obProduct.productName}",
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.placeholder_cover)
            )
            Text(
                modifier = Modifier.constrainAs(title) {
                    start.linkTo(image.end, 8.dp)
                    top.linkTo(image.top)
                },
                text = obProduct.productName,
                textAlign = TextAlign.Start,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.W500
                )
            )
            Text(
                modifier = Modifier.constrainAs(subtitle) {
                    start.linkTo(image.end, 8.dp)
                    top.linkTo(title.bottom)
                },
                text = obProduct.productCardDescription,
                textAlign = TextAlign.Start,
                style = TextStyle(
                    fontSize = 10.sp,
                    color = Color(0xFF78716c),
                    fontWeight = FontWeight.W400
                )
            )
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .padding(4.dp)
                    .clickable(onClick = {
                        onRemoveItem(obProduct.productID)
                    })
                    .constrainAs(removeBtn) {
                        end.linkTo(parent.end, 8.dp)
                        top.linkTo(image.top)
                    },
                imageVector = ImageVector.vectorResource(R.drawable.ic_trash),
                tint = Color(0xFF919191),
                contentDescription = "Remove item from card"
            )
            val configuration = LocalConfiguration.current
            // Use ConfigurationCompat to safely get the locale list (handles API differences)
            val locale: Locale = ConfigurationCompat.getLocales(configuration).get(0)
                ?: Locale.getDefault()
            Text(
                modifier = Modifier.constrainAs(price) {
                    end.linkTo(parent.end, 8.dp)
                    bottom.linkTo(image.bottom)
                },
                text = NumericUtils.formatPrice(obProduct.selectedPrice.netPrice,locale),
                textAlign = TextAlign.Start,
                style = TextStyle(
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.W700
                )
            )
            OBCounter(
                modifier = Modifier
                    .constrainAs(counter){
                        start.linkTo(image.end,8.dp)
                        bottom.linkTo(image.bottom)
                    },
                counterDisplaySize = CounterDisplaySize.Card,
                initialCount = obProduct.productQuantity,
                minCount = 1,
                onCountChanged = {
                    onQuantityChanged(obProduct.productID,it)
                }
            )

        }
    }

}