package com.youapps.onlybeans.ui.product

import OBButtonContainedNeutral
import OBButtonContainedSecondary
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.youapps.designsystem.components.buttons.OBLikeButton
import com.youapps.designsystem.components.inputs.OBCounter
import com.youapps.onlybeans.R


@Composable
fun ProductBottomAppBar(
    modifier: Modifier = Modifier,
    currentQuantity : Int = 0,
    isLiked : Boolean = false,
    onProductQuantityChanged : (Int)-> Unit,
    onAddToCartClicked : ()-> Unit,
    onLikeClicked : (isLiked : Boolean)-> Unit
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                )
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            OBCounter(
                modifier = Modifier
                    .weight(0.3f),
                initialCount = currentQuantity.coerceAtLeast(1),
                minCount = 1,
                onCountChanged = onProductQuantityChanged
            )
            OBLikeButton(
                modifier = Modifier
                    .weight(0.2f),
                isLiked = isLiked,
                onClick = onLikeClicked
            )
            OBButtonContainedSecondary(
                modifier = Modifier
                    .weight(0.5f),
                text = stringResource(R.string.product_add_to_card),
                size = OBButtonSize.Large,
                onClick = onAddToCartClicked
            )
        }
    }
}