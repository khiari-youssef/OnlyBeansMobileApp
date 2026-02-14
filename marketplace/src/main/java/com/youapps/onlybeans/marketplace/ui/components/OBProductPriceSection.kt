package com.youapps.onlybeans.marketplace.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.youapps.onlybeans.domain.entities.products.OBPrice


@Composable
fun OBProductPriceSection(
    modifier: Modifier = Modifier,
    onOBPrice: OBPrice,
    currency: String
) {
    onOBPrice.discount?.let { discount->
        if (discount > 0) {
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ){
                Row(
                    Modifier
                        .wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        8.dp,
                        Alignment.Start
                    )
                ) {
                    Text(
                        text = "${onOBPrice.price} $currency",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = if (isSystemInDarkTheme()) Color(0xFF94a3b8) else Color(0xFF94a3b8),
                            textDecoration = TextDecoration.LineThrough
                        ),
                        textDecoration = TextDecoration.LineThrough
                    )
                    Text(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(
                                color = Color(0x16dc2626)
                            )
                            .padding(
                                vertical = 1.dp,
                                horizontal = 2.dp
                            ),
                        text = "-${onOBPrice.getDiscountPercentage}%",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFFdc2626)
                        )
                    )


                }
                Text(
                    text = "${onOBPrice.netPrice} $currency",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = Color(0xFFdc2626)
                    )
                )

            }
        } else {
            Text(
                modifier = modifier,
                text = "${onOBPrice.price} $currency",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}