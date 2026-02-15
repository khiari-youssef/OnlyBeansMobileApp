package com.youapps.onlybeans.marketplace.ui.components

import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.youapps.onlybeans.designsystem.R


@Composable
fun ShoppingCardIcon(
    itemsAddedToCard : Int
) {
    if (itemsAddedToCard > 0) {
        BadgedBox(
            modifier = Modifier.wrapContentSize(),
            badge = {
                Badge(
                    contentColor = Color(0xFFD51E1E),
                    content = {
                        Text(
                            modifier = Modifier
                                .wrapContentSize(),
                            text = "$itemsAddedToCard",
                            color = Color.White
                        )
                    }
                )
            }
        ){
            Icon(
                ImageVector.vectorResource(R.drawable.ic_shopping_bag),
                contentDescription = stringResource(R.string.content_description_shopping_bag_button)
            )
        }
    } else {
        Icon(
            ImageVector.vectorResource(R.drawable.ic_shopping_bag),
            contentDescription = stringResource(R.string.content_description_shopping_bag_button)
        )
    }
}