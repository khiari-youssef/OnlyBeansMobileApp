package com.youapps.onlybeans.marketplace.ui.screens.home_marketplace

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.youapps.designsystem.components.inputs.OBSearchField
import com.youapps.onlybeans.marketplace.R

@Preview
@Composable
fun HomeMarketPlacePreview() {
    HomeMarketPlace(
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun HomeMarketPlace(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        Surface(
            elevation = 1.dp,
            color = MaterialTheme.colorScheme.surfaceContainerHigh
        ) {
            Row(
                modifier = Modifier
                    .padding(
                        vertical = 12.dp,
                        horizontal = 16.dp
                    )
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                OBSearchField(
                    query = "",
                    onSearchQueryChanged = {

                    },
                    placeholderRes = R.string.marketplace_search_input_placeholder
                )
            }
        }
    }

}