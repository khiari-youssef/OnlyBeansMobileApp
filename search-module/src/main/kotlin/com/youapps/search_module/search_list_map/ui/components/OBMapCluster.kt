package com.youapps.search_module.search_list_map.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun OBMapCluster(
    size : Int
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(color = MaterialTheme.colorScheme.secondary)
            .size(40.dp),
        contentAlignment = Alignment.Center
    ){
        Text(
            modifier = Modifier.wrapContentSize(),
            text = "$size",
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}
