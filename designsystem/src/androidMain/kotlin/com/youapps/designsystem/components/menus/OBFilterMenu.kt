package com.youapps.designsystem.components.menus

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun OBFilterMenu(
    modifier: Modifier = Modifier,
    filters: List<String>,
    selectedFilterIndex: Int = -1,
    onFilterSelected: (Int) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.Top)
    ){

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            itemsIndexed(filters) { index,category ->
                val isSelected = index == selectedFilterIndex
                Surface(
                    onClick = { onFilterSelected(index) },
                    shape = CircleShape, // Makes it pill-shaped
                    color = if (isSelected) Color(0xFF92400e) else Color.White,
                    border = BorderStroke(1.dp,if(isSelected) Color.Transparent else Color(0xFFe7e5e4)),
                    modifier = Modifier.height(40.dp),
                    tonalElevation = 1.dp,
                    shadowElevation = 1.dp,
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    ) {
                        Text(
                            text = category,
                            color = if (isSelected) Color.White else Color(0xFF57534e),
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}