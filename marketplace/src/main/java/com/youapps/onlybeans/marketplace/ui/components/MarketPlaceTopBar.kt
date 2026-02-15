package com.youapps.onlybeans.marketplace.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.youapps.designsystem.OBFontFamilies
import com.youapps.designsystem.components.inputs.OBSearchField
import com.youapps.onlybeans.marketplace.R

@Composable
fun MarketplaceTopBar(
    modifier: Modifier = Modifier,
    searchQuery : String,
    onSearchQueryChanged : (String)-> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically)
    ) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = stringResource(id = com.youapps.onlybeans.designsystem.R.string.app_name),
                    style = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = OBFontFamilies.BrandFontRegular,
                        fontWeight = FontWeight.W700,
                        fontStyle = FontStyle.Normal,
                        color = MaterialTheme.colorScheme.primary,
                    )
                )
                Text(
                    modifier = Modifier.wrapContentSize(),
                    text = stringResource(id = R.string.marketplace_label),
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = OBFontFamilies.BrandFontRegular,
                        fontWeight = FontWeight.W700,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textIndent = TextIndent(firstLine = 4.sp)
                    )
                )
            }
            ShoppingCardIcon(
                itemsAddedToCard = 1
            )
        }
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            OBSearchField(
                modifier = Modifier.fillMaxWidth(),
                query =searchQuery,
                onSearchQueryChanged = onSearchQueryChanged,
                placeholderRes = R.string.marketplace_search_input_placeholder
            )
        }
    }
}