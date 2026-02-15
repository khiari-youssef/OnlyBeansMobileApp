package com.youapps.onlybeans.marketplace.ui.components.templates

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.youapps.onlybeans.designsystem.R
import com.youapps.onlybeans.marketplace.ui.components.ShoppingCardIcon


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketPlaceDetailsTopBar(
    modifier: Modifier = Modifier,
    title: String?=null,
    scrollBehavior: TopAppBarScrollBehavior?=null,
    topAppBarColors : TopAppBarColors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent,
        scrolledContainerColor = MaterialTheme.colorScheme.primary
    ),
    itemsAddedToCard : Int = 0,
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onShoppingBagClicked : (()-> Unit)?=null
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        scrollBehavior =scrollBehavior ,
        title = {
            title?.run {
                Text(
                    text = this,
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center
                )
            }
        },
        colors = topAppBarColors,
        navigationIcon = {
            // Floating Back Button
            FloatingActionButton(
                onClick = onBackClick,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(40.dp),
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                elevation = FloatingActionButtonDefaults.elevation(4.dp)
            ) {
                Icon(ImageVector.vectorResource(R.drawable.ic_back), contentDescription = stringResource(R.string.content_description_back_button))
            }
        },
        actions = {
            // Floating Share Button
            FloatingActionButton(
                onClick = onShareClick,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(40.dp),
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                elevation = FloatingActionButtonDefaults.elevation(4.dp)
            ) {
                Icon(
                    ImageVector.vectorResource(R.drawable.ic_share),
                    contentDescription = stringResource(R.string.content_description_share_button)
                )
            }

            onShoppingBagClicked?.run {
                FloatingActionButton(
                    onClick = onShoppingBagClicked,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(40.dp),
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    elevation = FloatingActionButtonDefaults.elevation(4.dp)
                ) {
                    ShoppingCardIcon(
                        itemsAddedToCard = itemsAddedToCard
                    )
                }
            }

        }
    )
}